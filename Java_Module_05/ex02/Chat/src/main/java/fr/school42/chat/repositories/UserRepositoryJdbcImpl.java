package fr.school42.chat.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import javax.sql.DataSource;

import fr.school42.chat.models.User;

public class UserRepositoryJdbcImpl implements UserRepository {

    private Connection connection;

    UserRepositoryJdbcImpl(DataSource dataSource) {
        try (Connection cnx = dataSource.getConnection()) {
            this.connection = cnx;
        } catch (SQLException e) {
            throw new RuntimeException(e);

        }
    }

    @Override
    public Optional<User> findById(Long id) throws RuntimeException {
        String sql = "SELECT * FROM users WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setLogin(resultSet.getString("login"));
                user.setPassword(resultSet.getString("password"));
                return Optional.of(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }

}
