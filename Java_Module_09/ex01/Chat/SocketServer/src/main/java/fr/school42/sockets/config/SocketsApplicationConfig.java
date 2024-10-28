package fr.school42.sockets.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@ComponentScan("fr.school42.sockets")
@PropertySource("classpath:db.properties")
public class SocketsApplicationConfig {

  @Value("${db.url}")
  private String dbUrl;

  @Value("${db.user}")
  private String dbUser;

  @Value("${db.password}")
  private String dbPassword;

  @Value("${db.driver.name}")
  private String dbDriverName;

  @Bean
  public HikariConfig hikariConfig() {
    HikariConfig config = new HikariConfig();
    config.setJdbcUrl(dbUrl);
    config.setUsername(dbUser);
    config.setPassword(dbPassword);
    config.setDriverClassName(dbDriverName);
    return config;
  }

  @Bean
  public DataSource hikariDataSource() {
    return new HikariDataSource(hikariConfig());
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

}
