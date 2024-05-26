package school42.spring.service.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import school42.spring.service.models.User;

@Component("usersRepositoryJdbc")
public class UsersRepositoryJdbcImpl implements UsersRepository {

  private final String SQL_SELECT_ALL = "select * from users";
  private final String SQL_SELECT_BY_ID = "select * from users where id = ?";
  private final String SQL_SELECT_BY_EMAIL = "select * from users where email = ?";
  private final String SQL_INSERT_USER = "insert into users (email, password) values ( ? , ? )";
  private final String SQL_UPDATE_USER = "update users set email = ? , password = ? where id = ?";
  private final String SQL_DELETE_USER = "delete from users where id = ?";

  private final DataSource dataSource;

  @Autowired
  public UsersRepositoryJdbcImpl(@Qualifier("hikariDataSource") DataSource dataSource) {
    this.dataSource = dataSource;
  }

  @Override
  public User findById(long id) {

    try (Connection connection = dataSource.getConnection();
        PreparedStatement pst = connection.prepareStatement(SQL_SELECT_BY_ID)) {
      pst.setLong(1, id);
      ResultSet rs = pst.executeQuery();
      if (rs.next()) {
        return mapRowToUser(rs);
      }
    } catch (SQLException e) {
      throw new IllegalStateException(e);
    }
    return null;
  }

  @Override
  public Optional<User> findByEmail(String email) {
    try (Connection connection = dataSource.getConnection();
        PreparedStatement pst = connection.prepareStatement(SQL_SELECT_BY_EMAIL)) {
      pst.setString(1, email);
      ResultSet rs = pst.executeQuery();
      if (rs.next()) {
        return Optional.of(mapRowToUser(rs));
      }
    } catch (SQLException e) {
      throw new IllegalStateException(e);
    }
    return Optional.empty();
  }

  @Override
  public List<User> findAll() {
    List<User> users = new ArrayList<User>();
    try (Connection connection = dataSource.getConnection();
        PreparedStatement pst = connection.prepareStatement(SQL_SELECT_ALL)) {
      ResultSet rs = pst.executeQuery();
      while (rs.next()) {
        users.add(mapRowToUser(rs));
      }
    } catch (SQLException e) {
      throw new IllegalStateException(e);
    }
    return users;
  }

  @Override
  public void save(User entity) {

    try (Connection connection = dataSource.getConnection();
        PreparedStatement pst = connection.prepareStatement(SQL_INSERT_USER, PreparedStatement.RETURN_GENERATED_KEYS)) {
      pst.setString(1, entity.getEmail());
      pst.setString(2, entity.getPassword());
      pst.executeUpdate();
      ResultSet rs = pst.getGeneratedKeys();
      if (rs.next()) {
        entity.setId(rs.getLong(1));
      }
    } catch (SQLException e) {
      throw new IllegalStateException(e);
    }
  }

  @Override
  public void update(User entity) {
    try (Connection connection = dataSource.getConnection();
        PreparedStatement pst = connection.prepareStatement(SQL_UPDATE_USER)) {
      pst.setString(1, entity.getEmail());
      pst.setString(2, entity.getPassword());
      pst.setLong(3, entity.getId());
      pst.executeUpdate();
    } catch (SQLException e) {
      throw new IllegalStateException(e);
    }
  }

  @Override
  public void delete(Long id) {
    try (Connection connection = dataSource.getConnection();
        PreparedStatement pst = connection.prepareStatement(SQL_DELETE_USER)) {
      pst.setLong(1, id);
      pst.executeUpdate();
    } catch (SQLException e) {
      throw new IllegalStateException(e);
    }
  }

  private User mapRowToUser(ResultSet rs) throws SQLException {
    return new User(
        rs.getLong("id"),
        rs.getString("email"),
        rs.getString("password"));
  }

}
