package fr.school42.sockets.repositories;

import fr.school42.sockets.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UsersRepositoryImpl implements UsersRepository {

  private final JdbcTemplate jdbcTemplate;

  @Autowired
  public UsersRepositoryImpl(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  private RowMapper<User> userRowMapper = (rs, rowNum) -> new User(
      rs.getLong("id"),
      rs.getString("username"),
      rs.getString("password"));

  @Override
  public Optional<User> findById(Long id) {
    List<User> users = jdbcTemplate.query("SELECT * FROM users WHERE id = ?", userRowMapper, id);
    return Optional.ofNullable(users.isEmpty() ? null : users.get(0));
  }

  @Override
  public List<User> findAll() {
    return jdbcTemplate.query("SELECT * FROM users", userRowMapper);
  }

  @Override
  public User save(User entity) {
    if (entity.getId() == null) {
      jdbcTemplate.update("INSERT INTO users (username, password) VALUES (?, ?)",
          entity.getUsername(), entity.getPassword());
      return findByUsername(entity.getUsername()).orElse(null);
    } else {
      jdbcTemplate.update("UPDATE users SET username = ?, password = ? WHERE id = ?",
          entity.getUsername(), entity.getPassword(), entity.getId());
      return entity;
    }
  }

  @Override
  public void delete(Long id) {
    jdbcTemplate.update("DELETE FROM users WHERE id = ?", id);
  }

  @Override
  public Optional<User> findByUsername(String username) {
    List<User> users = jdbcTemplate.query("SELECT * FROM users WHERE username = ?", userRowMapper, username);
    return users.isEmpty() ? Optional.empty() : Optional.of(users.get(0));
  }
}