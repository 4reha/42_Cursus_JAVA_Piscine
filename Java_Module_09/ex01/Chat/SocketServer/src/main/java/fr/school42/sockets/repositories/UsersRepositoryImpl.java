package fr.school42.sockets.repositories;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import org.springframework.stereotype.Component;

import fr.school42.sockets.models.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

@Component
public class UsersRepositoryImpl implements UsersRepository {

  private final String SQL_SELECT_ALL = "select * from users";
  private final String SQL_SELECT_BY_ID = "select * from users where id = ?";
  private final String SQL_SELECT_BY_USERNAME = "select * from users where username = ?";
  private final String SQL_INSERT_USER = "insert into users (username, password) values ( ? , ? )";
  private final String SQL_UPDATE_USER = "update users set username = ? , password = ? where id = ?";
  private final String SQL_DELETE_USER = "delete from users where id = ?";

  public class UserMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
      return new User(rs.getLong("id"), rs.getString("username"), rs.getString("password"));
    }
  }

  private final JdbcTemplate jdbcTemplate;

  @Autowired
  public UsersRepositoryImpl(@Qualifier("hikariDataSource") DataSource dataSource) {
    jdbcTemplate = new JdbcTemplate(dataSource);
  }

  @Override
  public User findById(long id) {
    return jdbcTemplate.query(SQL_SELECT_BY_ID, new UserMapper(), id).stream().findAny().orElse(null);
  }

  @Override
  public Optional<User> findByUsername(String username) {
    return jdbcTemplate.query(SQL_SELECT_BY_USERNAME, new UserMapper(), username).stream().findAny();
  }

  @Override
  public List<User> findAll() {
    return jdbcTemplate.query(SQL_SELECT_ALL, new UserMapper());
  }

  @Override
  public void save(User entity) {
    KeyHolder keyHolder = new GeneratedKeyHolder();

    jdbcTemplate.update(connection -> {
      PreparedStatement ps = connection.prepareStatement(SQL_INSERT_USER, new String[] { "id" });
      ps.setString(1, entity.getUsername());
      ps.setString(2, entity.getPassword());
      return ps;
    }, keyHolder);
    entity.setId(keyHolder.getKey().longValue());

  }

  @Override
  public void update(User entity) {
    jdbcTemplate.update(SQL_UPDATE_USER, entity.getUsername(), entity.getPassword(), entity.getId());
  }

  @Override
  public void delete(Long id) {
    jdbcTemplate.update(SQL_DELETE_USER, id);
  }

}
