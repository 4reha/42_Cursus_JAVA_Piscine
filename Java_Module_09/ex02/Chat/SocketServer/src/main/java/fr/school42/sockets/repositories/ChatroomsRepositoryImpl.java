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

@Component
public class ChatroomsRepositoryImpl implements ChatroomsRepository {

  private final String SQL_SELECT_ALL = "select * from chatrooms order by name";
  private final String SQL_SELECT_BY_ID = "select * from chatrooms where id = ?";
  private final String SQL_INSERT_CHATROOM = "insert into chatrooms (name) values ( ? )";
  private final String SQL_UPDATE_CHATROOM = "update chatrooms set name = ? where id = ?";
  private final String SQL_DELETE_CHATROOM = "delete from chatrooms where id = ?";

  public class ChatroomMapper implements RowMapper<Chatroom> {
    @Override
    public Chatroom mapRow(ResultSet rs, int rowNum) throws SQLException {
      return new Chatroom(
          rs.getLong("id"),
          rs.getString("name"));
    }
  }

  private final JdbcTemplate jdbcTemplate;

  @Autowired
  public ChatroomsRepositoryImpl(@Qualifier("hikariDataSource") DataSource dataSource) {
    jdbcTemplate = new JdbcTemplate(dataSource);
  }

  @Override
  public Chatroom findById(long id) {
    return jdbcTemplate.query(SQL_SELECT_BY_ID, new ChatroomMapper(), id).stream().findAny().orElse(null);
  }

  @Override
  public List<Chatroom> findAll() {
    return jdbcTemplate.query(SQL_SELECT_ALL, new ChatroomMapper());
  }

  @Override
  public void save(Chatroom entity) {
    jdbcTemplate.update(SQL_INSERT_CHATROOM, entity.getName());
  }

  @Override
  public void update(Chatroom entity) {
    jdbcTemplate.update(SQL_UPDATE_CHATROOM, entity.getName(), entity.getId());
  }

  @Override
  public void delete(Long id) {
    jdbcTemplate.update(SQL_DELETE_CHATROOM, id);
  }
}