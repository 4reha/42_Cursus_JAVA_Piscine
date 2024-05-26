package school42.spring.service.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import school42.spring.service.repositories.UsersRepository;
import school42.spring.service.services.UsersService;
import school42.spring.service.services.UsersServiceImpl;

@Configuration
@ComponentScan("school42.spring.service")
public class TestApplicationConfig {

  @Bean(name = { "hikariDataSource", "driverManagerDataSource" })
  public DataSource dataSource() {
    return new EmbeddedDatabaseBuilder()
        .setType(EmbeddedDatabaseType.HSQL)
        .addScript("classpath:schema.sql")
        .addScript("classpath:data.sql")
        .build();
  }

  @Bean("userServiceImpl")
  public UsersService usersService(@Qualifier("usersRepositoryJdbc") UsersRepository usersRepository) {
    return new UsersServiceImpl(usersRepository);
  }

}
