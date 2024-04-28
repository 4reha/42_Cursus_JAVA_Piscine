package fr.school42.chat.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.sql.DataSource;

import fr.school42.chat.models.*;

public class UserRepositoryJdbcImpl implements UserRepository {

  private DataSource dataSource;

  public UserRepositoryJdbcImpl(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  @Override
  public List<User> findAll(int page, int size) throws RuntimeException {

    final String SQL_SELECT = """
        SELECT
        	u.*,
        	cr1.id AS created_room_id,
        	cr1.name AS created_room_name,
        	cr2.id AS participating_room_id,
        	cr2.name AS participating_room_name
        FROM
        	users u
        	LEFT JOIN chat_rooms cr1 ON u.id = cr1.owner
        	LEFT JOIN chat_rooms cr2 ON u.id IN(
        		SELECT
        			author FROM messages
        		WHERE
        			chat_room = cr2.id)
        LIMIT ? OFFSET ?;
        """;

    try (Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(SQL_SELECT)) {
      statement.setInt(1, size);
      statement.setInt(2, page * size);
      try (ResultSet resultSet = statement.executeQuery()) {
        return mapUsers(resultSet);
      }
    } catch (SQLException e) {
      throw new RuntimeException("Error finding all users", e);
    }
  }

  @Override
  public Optional<User> findById(Long id) throws RuntimeException {

    final String SQL_SELECT = "SELECT * FROM messages " +
        "JOIN users u ON messages.author = u.id " +
        "JOIN chat_rooms r ON messages.chat_room = r.id " +
        "WHERE messages.id = ?";

    try (Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(SQL_SELECT)) {
      statement.setLong(1, id);
      try (ResultSet resultSet = statement.executeQuery()) {
        if (resultSet.next()) {
          return Optional.of(createUserFromResultSet(resultSet));
        } else {
          return Optional.empty();
        }
      }
    } catch (SQLException e) {
      throw new RuntimeException("Error finding user by id", e);
    }
  }

  private ArrayList<User> mapUsers(ResultSet resultSet) throws SQLException {

    ArrayList<User> users = new ArrayList<>();

    while (resultSet.next()) {
      Long id = resultSet.getLong("id");
      int index = isUserInList(users, id);
      User user = null;
      if (index == -1) {
        user = new User(
            resultSet.getLong("id"),
            resultSet.getString("login"),
            resultSet.getString("password"));
        users.add(user);
      } else {
        user = users.get(index);
      }
      user.addCreatedRoom(mapChatRooms(resultSet, true));
      user.addChatroom(mapChatRooms(resultSet, false));
    }
    return users;
  }

  private int isUserInList(ArrayList<User> users, Long id) {
    for (User user : users) {
      if (user.getId() == id) {
        return users.indexOf(user);
      }
    }
    return -1;
  }

  private Chatroom mapChatRooms(ResultSet resultSet, boolean isCreatedByUser) throws SQLException {
    String columnName = (isCreatedByUser) ? "created_" : "participating_";
    Long id = resultSet.getLong(columnName + "room_id");
    String name = resultSet.getString(columnName + "room_name");
    return new Chatroom(id, name, null, new ArrayList<>());
  }

  public static User createUserFromResultSet(ResultSet resultSet) throws SQLException {
    User user = new User();
    System.out.println(resultSet);
    user.setId(resultSet.getLong("id"));
    user.setLogin(resultSet.getString("login"));
    user.setPassword(resultSet.getString("password"));
    return user;
  }

}
