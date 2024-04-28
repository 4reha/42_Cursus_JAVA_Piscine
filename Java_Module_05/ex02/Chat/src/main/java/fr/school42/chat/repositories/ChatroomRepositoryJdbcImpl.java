package fr.school42.chat.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import javax.sql.DataSource;

import fr.school42.chat.models.Chatroom;

public class ChatroomRepositoryJdbcImpl implements ChatroomRepository {

    private DataSource dataSource;

    ChatroomRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Optional<Chatroom> findById(Long id) throws RuntimeException {

        final String SQL_SELECT = "SELECT * FROM chat_rooms WHERE id = ?";

        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_SELECT)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(createChatroomFromResultSet(resultSet));
                } else {
                    return Optional.empty();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding chatroom by id", e);
        }

    }

    public static Chatroom createChatroomFromResultSet(ResultSet resultSet) throws SQLException {
        Chatroom chatroom = new Chatroom();
        chatroom.setId(resultSet.getLong("id"));
        chatroom.setName(resultSet.getString("name"));
        return chatroom;
    }

}
