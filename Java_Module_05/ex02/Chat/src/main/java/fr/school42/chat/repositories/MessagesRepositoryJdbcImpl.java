package fr.school42.chat.repositories;

import fr.school42.chat.models.Message;
import fr.school42.chat.models.Chatroom;
import fr.school42.chat.models.User;

import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Optional;
import java.time.LocalDateTime;

public class MessagesRepositoryJdbcImpl implements MessagesRepository {

    private DataSource dataSource;

    public MessagesRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Optional<Message> findById(Long id) throws RuntimeException {

        final String SQL_SELECT = "SELECT * FROM messages " +
                "JOIN users u ON messages.author = u.id " +
                "JOIN chat_rooms r ON messages.chat_room = r.id " +
                "WHERE messages.id = ?";

        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_SELECT)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(createMessageFromResultSet(resultSet));
                } else {
                    return Optional.empty();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding message by id", e);
        }

    }

    @Override
    public void save(Message message) throws RuntimeException, NotSavedSubEntityException {

        if (message.getAuthor() == null || message.getAuthor().getId() == null) {
            throw new NotSavedSubEntityException("Author not saved");
        }
        if (message.getChatroom() == null || message.getChatroom().getId() == null) {
            throw new NotSavedSubEntityException("Chatroom not saved");
        }

        final String SQL_INSERT = "INSERT INTO messages (text, created_at, author, chat_room) VALUES (?, ?, ?, ?)";

        UserRepository userRepository = new UserRepositoryJdbcImpl(dataSource);
        ChatroomRepository chatroomsRepository = new ChatroomRepositoryJdbcImpl(dataSource);

        Optional<User> author = userRepository.findById(message.getAuthor().getId());
        Optional<Chatroom> chatroom = chatroomsRepository.findById(message.getChatroom().getId());

        if (author.isEmpty()) {
            throw new NotSavedSubEntityException("Author not saved");
        }

        if (chatroom.isEmpty()) {
            throw new NotSavedSubEntityException("Chatroom not saved");
        }

        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_INSERT,
                        Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, message.getText());
            statement.setTimestamp(2, Timestamp.valueOf(message.getDateTime()));
            statement.setLong(3, message.getAuthor().getId());
            statement.setLong(4, message.getChatroom().getId());

            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    message.setId(generatedKeys.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error saving message", e);
        }

    }

    private Message createMessageFromResultSet(ResultSet resultSet) throws SQLException {
        Message message = new Message();
        message.setId(resultSet.getLong("id"));
        message.setText(resultSet.getString("text"));
       LocalDateTime dateTime = resultSet.getTimestamp("created_at") == null ? null
                    : resultSet.getTimestamp("created_at").toLocalDateTime();
            message.setDateTime(dateTime);
        message.setAuthor(createUserFromResultSet(resultSet));
        message.setChatroom(createChatroomFromResultSet(resultSet));
        return message;
    }


	private User createUserFromResultSet(ResultSet resultSet) throws SQLException {
        User author = new User();
        author.setId(resultSet.getLong("author"));
        author.setLogin(resultSet.getString("login"));
        author.setPassword(resultSet.getString("password"));
        return author;
    }

    private Chatroom createChatroomFromResultSet(ResultSet resultSet) throws SQLException {
        Chatroom chatroom = new Chatroom();
        chatroom.setId(resultSet.getLong("chat_room"));
        chatroom.setName(resultSet.getString("name"));
        return chatroom;
    }

}
