package fr.school42.chat.repositories;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.sql.DataSource;

import fr.school42.chat.models.*;

public class UserRepositoryJdbcImpl implements UserRepository {

  private DataSource dataSource;

  public UserRepositoryJdbcImpl(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  @Override
  public List<User> findAll(int page, int size) throws RuntimeException {

    final String SQL_SELECT = """
        WITH need_users AS
        (
           SELECT id, login FROM users
           ORDER BY id limit ? offset ?),
        created_chats AS
        (
            SELECT u.id AS user_id, u.login AS user_name,
                array_agg(c.id)   AS created_chat_id,
                array_agg(c.NAME) AS created_chat_name
            FROM need_users u
            LEFT JOIN chat_rooms c ON  u.id = c.owner
            GROUP BY  u.id, u.login),
        used_chats AS
        (
            SELECT u.id AS user_id,
                u.login as user_name,
                array_agg(cc.id) AS used_chat_id,
                array_agg(cc.NAME) AS used_chat_name
            FROM need_users u
            LEFT JOIN users_chat_rooms uc
            ON u.id = uc.user_id
            LEFT JOIN chat_rooms cc
            ON uc.chat_room_id = cc.id
            GROUP BY  u.id, u.login)
        SELECT c.user_id,
            c.user_name,
            c.created_chat_id,
            c.created_chat_name,
            u.used_chat_id,
            u.used_chat_name
        FROM created_chats c
        LEFT JOIN used_chats u
        ON c.user_id = u.user_id
        ORDER BY c.user_id;
          """;

    try (Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(SQL_SELECT)) {
      statement.setInt(1, size);
      statement.setInt(2, page * size);
      try (ResultSet resultSet = statement.executeQuery()) {
        return mapUsers(resultSet);
      }
    } catch (SQLException e) {
      throw new RuntimeException("Error finding all users", e);
    }
  }

  @Override
  public Optional<User> findById(Long id) throws RuntimeException {

    final String SQL_SELECT = "SELECT * FROM messages " +
        "JOIN users u ON messages.author = u.id " +
        "JOIN chat_rooms r ON messages.chat_room = r.id " +
        "WHERE messages.id = ?";

    try (Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(SQL_SELECT)) {
      statement.setLong(1, id);
      try (ResultSet resultSet = statement.executeQuery()) {
        if (resultSet.next()) {
          return Optional.of(createUserFromResultSet(resultSet));
        } else {
          return Optional.empty();
        }
      }
    } catch (SQLException e) {
      throw new RuntimeException("Error finding user by id", e);
    }
  }

  private ArrayList<User> mapUsers(ResultSet resultSet) throws SQLException {

    ArrayList<User> users = new ArrayList<>();

    while (resultSet.next()) {
      User user = new User(
          resultSet.getLong("user_id"),
          resultSet.getString("user_name"),
          "",
          mapChatRooms(resultSet, true),
          mapChatRooms(resultSet, false));
      users.add(user);
    }
    return users;
  }

  private int isUserInList(ArrayList<User> users, Long id) {
    for (User user : users) {
      if (user.getId() == id) {
        return users.indexOf(user);
      }
    }
    return -1;
  }

  private ArrayList<Chatroom> mapChatRooms(ResultSet resultSet, boolean isCreatedByUser) throws SQLException {
    ArrayList<Chatroom> chatrooms = new ArrayList<>();
    String columnName = (isCreatedByUser) ? "created_" : "used_";

    Array chatIds = resultSet.getArray(columnName + "chat_id");
    Array chatNames = resultSet.getArray(columnName + "chat_name");

    if (chatIds != null && chatNames != null) {
      Integer[] ids = (Integer[]) chatIds.getArray();
      String[] names = (String[]) chatNames.getArray();

      for (int i = 0; i < ids.length; i++) {
        Chatroom chatroom = new Chatroom(
            ids[i] == null ? null : Long.valueOf(ids[i]),
            names[i],
            null,
            new ArrayList<>());
        chatrooms.add(chatroom);
      }
    }

    return chatrooms;
  }

  public static User createUserFromResultSet(ResultSet resultSet) throws SQLException {
    User user = new User();
    user.setId(resultSet.getLong("id"));
    user.setLogin(resultSet.getString("login"));
    user.setPassword(resultSet.getString("password"));
    return user;
  }

}
