package fr.school42.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class EmbeddedDataSourceTest {

  private DataSource dataSource;

  @BeforeEach
  public void init() {
    dataSource = new EmbeddedDatabaseBuilder()
        .setType(EmbeddedDatabaseType.HSQL)
        .addScripts("schema.sql", "data.sql")
        .build();
  }

  @Test
  public void testDataSourceConnection() throws SQLException {
    Connection connection = dataSource.getConnection();
    assertNotNull(connection);
    connection.close();
  }
}