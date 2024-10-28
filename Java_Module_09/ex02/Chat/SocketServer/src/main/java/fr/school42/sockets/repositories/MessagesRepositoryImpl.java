package fr.school42.sockets.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import fr.school42.sockets.models.Chatroom;
import fr.school42.sockets.models.Message;
import fr.school42.sockets.models.User;

@Component
public class MessagesRepositoryImpl implements MessagesRepository {

  private final String SQL_SELECT_ALL = "SELECT m.*, u.username, u.password, c.name AS room_name FROM messages m JOIN users u ON m.from_id = u.id JOIN chatrooms c ON m.room_id = c.id ORDER BY m.created_at";
  private final String SQL_SELECT_BY_ROOM_ID = "SELECT m.*, u.username, u.password, c.name AS room_name FROM messages m JOIN users u ON m.from_id = u.id JOIN chatrooms c ON m.room_id = c.id WHERE m.room_id = ? ORDER BY m.created_at LIMIT ? OFFSET ?";
  private final String SQL_SELECT_BY_ID = "SELECT m.*, u.username, u.password, c.name AS room_name FROM messages m JOIN users u ON m.from_id = u.id JOIN chatrooms c ON m.room_id = c.id WHERE m.id = ?";
  private final String SQL_INSERT_MESSAGE = "INSERT INTO messages (from_id, room_id, text, created_at) VALUES (?, ?, ?, ?)";
  private final String SQL_UPDATE_MESSAGE = "UPDATE messages SET from_id = ?, room_id = ?, text = ?, created_at = ? WHERE id = ?";
  private final String SQL_DELETE_MESSAGE = "DELETE FROM messages WHERE id = ?";

  public class MessageMapper implements RowMapper<Message> {
    @Override
    public Message mapRow(ResultSet rs, int rowNum) throws SQLException {
      User sender = new User(rs.getLong("from_id"), rs.getString("username"), rs.getString("password"));
      Chatroom room = new Chatroom(rs.getLong("room_id"), rs.getString("room_name"));
      return new Message(
          rs.getLong("id"),
          sender,
          room,
          rs.getString("text"),
          rs.getTimestamp("created_at"));
    }
  }

  private final JdbcTemplate jdbcTemplate;

  @Autowired
  public MessagesRepositoryImpl(@Qualifier("hikariDataSource") DataSource dataSource) {
    jdbcTemplate = new JdbcTemplate(dataSource);
  }

  @Override
  public Message findById(long id) {
    return jdbcTemplate.query(SQL_SELECT_BY_ID, new MessageMapper(), id).stream().findAny().orElse(null);
  }

  @Override
  public List<Message> findAll() {
    return jdbcTemplate.query(SQL_SELECT_ALL, new MessageMapper());
  }

  @Override
  public List<Message> findByRoomId(Long roomId, int limit, int offset) {
    return jdbcTemplate.query(SQL_SELECT_BY_ROOM_ID, new MessageMapper(), roomId, limit, offset);
  }

  @Override
  public void save(Message entity) {
    jdbcTemplate.update(SQL_INSERT_MESSAGE, entity.getSender().getId(), entity.getChatroom().getId(), entity.getText(),
        entity.getSentAt());
  }

  @Override
  public void update(Message entity) {
    jdbcTemplate.update(SQL_UPDATE_MESSAGE,
        entity.getSender().getId(), entity.getChatroom().getId(), entity.getText(), entity.getSentAt(), entity.getId());
  }

  @Override
  public void delete(Long id) {
    jdbcTemplate.update(SQL_DELETE_MESSAGE, id);
  }
}