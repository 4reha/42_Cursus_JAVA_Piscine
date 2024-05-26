package school42.spring.service.services;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import school42.spring.service.models.User;
import school42.spring.service.repositories.UsersRepository;

@Component("usersServiceImpl")
public class UsersServiceImpl implements UsersService {

  private final UsersRepository usersRepository;

  @Autowired
  public UsersServiceImpl(@Qualifier("usersRepositoryJdbcTemplate") UsersRepository usersRepository) {
    this.usersRepository = usersRepository;
  }

  @Override
  public String signUp(String email) {
    if (email == null || email.isEmpty()) {
      throw new IllegalArgumentException("Email is empty");
    }

    UUID uuid = UUID.randomUUID();
    this.usersRepository.save(new User(email, uuid.toString()));

    Optional<User> user = this.usersRepository.findByEmail(email);
    if (user.isPresent()) {
      return user.get().getPassword();
    }
    return null;
  }

}
