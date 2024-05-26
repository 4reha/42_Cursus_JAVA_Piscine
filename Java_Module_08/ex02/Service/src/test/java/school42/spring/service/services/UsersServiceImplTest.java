package school42.spring.service.services;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import school42.spring.service.config.TestApplicationConfig;
import school42.spring.service.models.User;
import school42.spring.service.repositories.UsersRepository;

public class UsersServiceImplTest {

  static ApplicationContext context;

  static UsersService usersServiceImpl;

  static UsersRepository usersRepository;

  @BeforeAll
  public static void setUp() {
    context = new AnnotationConfigApplicationContext(TestApplicationConfig.class);
    usersServiceImpl = context.getBean("usersServiceImpl", UsersServiceImpl.class);
    usersRepository = context.getBean("usersRepositoryJdbcTemplate", UsersRepository.class);
  }

  @Test
  void testConnection() throws SQLException {
    DataSource dataSource = context.getBean("hikariDataSource", DataSource.class);
    assertNotNull(dataSource);
    assertNotNull(dataSource.getConnection());
  }

  @ParameterizedTest
  @ValueSource(strings = { "test1@jdbc.ma", "test2@jdbc.ma" })
  void testSignUp(String email) {
    String password = usersServiceImpl.signUp(email);
    assertNotNull(password);
    User user = usersRepository.findByEmail(email).orElse(null);
    assertNotNull(user);
    assertEquals(password, user.getPassword());
  }

}
