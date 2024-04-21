package fr.school42.chat.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import javax.sql.DataSource;

import fr.school42.chat.models.Chatroom;

public class ChatroomRepositoryJdbcImpl implements ChatroomRepository {

    private Connection connection;

    ChatroomRepositoryJdbcImpl(DataSource dataSource) {
        try (Connection cnx = dataSource.getConnection()) {
            this.connection = cnx;
        } catch (SQLException e) {
            throw new RuntimeException(e);

        }
    }

    @Override
    public Optional<Chatroom> findById(Long id) throws RuntimeException {
        String sql = "SELECT * FROM chat_rooms WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Chatroom chatroom = new Chatroom();
                chatroom.setId(resultSet.getLong("id"));
                chatroom.setName(resultSet.getString("name"));
                return Optional.of(chatroom);
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

}
