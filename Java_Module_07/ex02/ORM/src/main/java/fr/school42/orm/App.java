package fr.school42.orm;

import javax.sql.DataSource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import fr.school42.models.User;

public class App {

  private static final String DB_URL = "jdbc:postgresql://localhost:5432/mydb";
  private static final String DB_USER = "myuser";
  private static final String DB_PASSWORD = "mypassword";

  public static void main(String[] args) {

    DataSource dataSource = new DriverManagerDataSource(DB_URL, DB_USER, DB_PASSWORD);

    OrmManager ormManager = new OrmManager(dataSource);

    User user0 = new User("John", "Doe", 30);
    User user1 = new User(null, null, null);

    ormManager.save(user0);
    ormManager.save(user1);

    User userFromDb = ormManager.findById(2L, User.class);

    System.out.println(userFromDb);

    if (userFromDb != null) {
      userFromDb.setId(null);
      userFromDb.setAge(31);
      userFromDb.setFirstName("Jane");

      ormManager.update(userFromDb);
    }

  }

}
