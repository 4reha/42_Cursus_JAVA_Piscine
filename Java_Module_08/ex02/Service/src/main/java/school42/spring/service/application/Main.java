package school42.spring.service.application;

import java.util.Optional;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import school42.spring.service.config.ApplicationConfig;
import school42.spring.service.models.User;
import school42.spring.service.repositories.UsersRepository;
import school42.spring.service.services.UsersService;

public class Main {

  public static void main(String[] args) {
    ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);
    try {
      UsersService usersService = context.getBean("usersServiceImpl", UsersService.class);
      UsersRepository usersRepository = context.getBean("usersRepositoryJdbcTemplate", UsersRepository.class);

      String pass = usersService.signUp("test@signup.com");
      System.out.println("Password: " + pass);

      usersRepository.findByEmail("test@signup.com").ifPresent((User user) -> {
        System.out.println("User found: " + user.getEmail() + " " + user.getPassword());
      });
    } finally {
      ((AnnotationConfigApplicationContext) context).close();
    }
  }
}
