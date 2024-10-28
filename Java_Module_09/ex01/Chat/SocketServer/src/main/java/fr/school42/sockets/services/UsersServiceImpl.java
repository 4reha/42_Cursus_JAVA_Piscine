package fr.school42.sockets.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import fr.school42.sockets.models.User;
import fr.school42.sockets.repositories.UsersRepository;

@Component("usersServiceImpl")
public class UsersServiceImpl implements UsersService {

  @Autowired
  private UsersRepository usersRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Override
  public User signIn(String username, String password) {
    if (username == null || username.isEmpty()) {
      throw new IllegalArgumentException("Username is empty");
    }
    if (password == null || password.isEmpty()) {
      throw new IllegalArgumentException("Password is empty");
    }

    Optional<User> user = this.usersRepository.findByUsername(username);
    

    return user.filter(u -> this.passwordEncoder.matches(password, u.getPassword())).orElse(null);
  }

  @Override
  public User signUp(String username, String password) {
    if (username == null || username.isEmpty()) {
      throw new IllegalArgumentException("Username is empty");
    }
    if (password == null || password.isEmpty()) {
      throw new IllegalArgumentException("Password is empty");
    }

    if (this.usersRepository.findByUsername(username).isPresent()) {
      throw new IllegalArgumentException("User already exists");
    }

    password = this.passwordEncoder.encode(password);

    this.usersRepository.save(new User(username, password));

    Optional<User> user = this.usersRepository.findByUsername(username);
    if (user.isPresent()) {
      return user.get();
    }
    return null;
  }

}