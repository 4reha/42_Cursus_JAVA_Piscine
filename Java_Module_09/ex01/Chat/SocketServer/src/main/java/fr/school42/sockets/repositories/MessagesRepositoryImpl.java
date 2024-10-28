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

import fr.school42.sockets.models.Message;
import fr.school42.sockets.models.User;


@Component
public class MessagesRepositoryImpl implements MessagesRepository {

  private final String SQL_SELECT_ALL = "select * from messages";
  private final String SQL_SELECT_BY_ID = "select * from messages where id = ?";
  private final String SQL_INSERT_MESSAGE = "insert into messages (user_id, text, created_at) values ( ? , ? , ? )";
  private final String SQL_UPDATE_MESSAGE = "update messages set user_id = ? , text = ? , created_at = ? where id = ?";
  private final String SQL_DELETE_MESSAGE = "delete from messages where id = ?";

  public class MessageMapper implements RowMapper<Message> {
    @Override
    public Message mapRow(ResultSet rs, int rowNum) throws SQLException {
      User sender = new User(rs.getLong("user_id"), rs.getString("username"), rs.getString("password"));
      return new Message(rs.getLong("id"), sender, rs.getString("text"), rs.getTimestamp("created_at"));
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
  public void save(Message entity) {
    jdbcTemplate.update(SQL_INSERT_MESSAGE, entity.getSender().getId(), entity.getText(), entity.getSentAt());
  }

  @Override
  public void update(Message entity) {
    jdbcTemplate.update(SQL_UPDATE_MESSAGE,
        entity.getSender().getId(), entity.getText(), entity.getSentAt(), entity.getId());
  }

  @Override
  public void delete(Long id) {
    jdbcTemplate.update(SQL_DELETE_MESSAGE, id);
  }

}