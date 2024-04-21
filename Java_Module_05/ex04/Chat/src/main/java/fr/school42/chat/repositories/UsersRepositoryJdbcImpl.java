package fr.school42.chat.repositories;

import java.util.List;

import javax.sql.DataSource;

import fr.school42.chat.models.User;

public class UsersRepositoryJdbcImpl implements UsersRepository {

	private final DataSource dataSource;

	public UsersRepositoryJdbcImpl(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public List<User> findAll(int page, int size) throws SQLException {
		try (Connection connection = dataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(getFindAllSql(page, size))) {
			statement.setInt(1, (page * size));
			statement.setInt(2, size);
			ResultSet resultSet = statement.executeQuery();

			List<User> users = new ArrayList<>();
			while (resultSet.next()) {
				User user = mapUser(resultSet);
				user.setCreatedChatRooms(mapChatRooms(resultSet, true));
				user.setParticipatingChatRooms(mapChatRooms(resultSet, false));
				users.add(user);
			}

			return users;
		}
	}

	private String getFindAllSql(int page, int size) {
		return "SELECT u.*, cr1.id AS created_room_id, cr1.name AS created_room_name, " +
				"cr2.id AS participating_room_id, cr2.name AS participating_room_name " +
				"FROM users u " +
				"LEFT JOIN chat_rooms cr1 ON u.id = cr1.owner " +
				"LEFT JOIN chat_rooms cr2 ON u.id IN (SELECT author FROM messages WHERE chat_room = cr2.id) " +
				"LIMIT ? OFFSET ?";
	}

	private User mapUser(ResultSet resultSet) throws SQLException {
		User user = new User(
				resultSet.getInt("id"),
				resultSet.getString("login"),
				resultSet.getString("password"));
		return user;
	}

	private List<ChatRoom> mapChatRooms(ResultSet resultSet, boolean isCreatedByUser) throws SQLException {
		List<ChatRoom> chatRooms = new ArrayList<>();
		while (resultSet.next()) {
			String columnName = (isCreatedByUser) ? "created_" : "participating_";
			int id = resultSet.getInt(columnName + "room_id");
			String name = resultSet.getString(columnName + "room_name");
			if (id > 0) {
				chatRooms.add(new ChatRoom(id, name, new ArrayList<>()));
			}
		}
		return chatRooms;
	}
}