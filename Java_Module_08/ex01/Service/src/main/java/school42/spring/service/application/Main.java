package school42.spring.service.application;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import school42.spring.service.models.User;
import school42.spring.service.repositories.UsersRepository;

public class Main {

  private static void usersRepositoryTests(UsersRepository usersRepository) {

    System.out.println(usersRepository.findAll());

    User user = new User("test6@gmail.com");
    usersRepository.save(user);
    System.out.println(user);

    user.setEmail("test@gmail.com");
    usersRepository.update(user);
    System.out.println(usersRepository.findById(user.getId()));

    usersRepository.delete(user.getId());

    user = usersRepository.findByEmail(user.getEmail()).orElse(null);
    System.out.println(user);

  }

  public static void main(String[] args) {
    ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
    try {
      UsersRepository usersRepository = context.getBean("usersRepositoryJdbc", UsersRepository.class);
      System.out.println("=== Jdbc ===");
      usersRepositoryTests(usersRepository);
      usersRepository = context.getBean("usersRepositoryJdbcTemplate", UsersRepository.class);
      System.out.println("\n=== JdbcTemplate ===\n");
      usersRepositoryTests(usersRepository);
    } finally {
      ((ClassPathXmlApplicationContext) context).close();
    }
  }
}
