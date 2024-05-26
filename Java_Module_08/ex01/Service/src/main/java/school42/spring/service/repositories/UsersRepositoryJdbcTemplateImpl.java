package school42.spring.service.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import school42.spring.service.models.User;

public class UsersRepositoryJdbcTemplateImpl implements UsersRepository {

  private final String SQL_SELECT_ALL = "select * from users";
  private final String SQL_SELECT_BY_ID = "select * from users where id = ?";
  private final String SQL_SELECT_BY_EMAIL = "select * from users where email = ?";
  private final String SQL_INSERT_USER = "insert into users (email) values (?)";
  private final String SQL_UPDATE_USER = "update users set email = ? where id = ?";
  private final String SQL_DELETE_USER = "delete from users where id = ?";

  public class UserMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
      return new User(rs.getLong("id"), rs.getString("email"));
    }
  }

  private final JdbcTemplate jdbcTemplate;

  UsersRepositoryJdbcTemplateImpl(DataSource dataSource) {
    this.jdbcTemplate = new JdbcTemplate(dataSource);
  }

  @Override
  public User findById(long id) {
    return jdbcTemplate.queryForObject(SQL_SELECT_BY_ID, new UserMapper(), id);
  }

  @Override
  public Optional<User> findByEmail(String email) {
    try {
      return Optional.ofNullable(jdbcTemplate.queryForObject(SQL_SELECT_BY_EMAIL, new UserMapper(), email));
    } catch (EmptyResultDataAccessException e) {
      return Optional.empty();
    }
  }

  @Override
  public List<User> findAll() {
    return jdbcTemplate.query(SQL_SELECT_ALL, new UserMapper());
  }

  @Override
  public void save(User entity) {
    KeyHolder keyHolder = new GeneratedKeyHolder();

    jdbcTemplate.update(connection -> {
      var ps = connection.prepareStatement(SQL_INSERT_USER, new String[] { "id" });
      ps.setString(1, entity.getEmail());
      return ps;
    }, keyHolder);
    entity.setId(keyHolder.getKey().longValue());

  }

  @Override
  public void update(User entity) {
    jdbcTemplate.update(SQL_UPDATE_USER, entity.getEmail(), entity.getId());
  }

  @Override
  public void delete(Long id) {
    jdbcTemplate.update(SQL_DELETE_USER, id);
  }

}
