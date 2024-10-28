package fr.school42.sockets.services;

import fr.school42.sockets.models.User;
import fr.school42.sockets.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsersServiceImpl implements UsersService {

  private final UsersRepository usersRepository;
  private final PasswordEncoder passwordEncoder;

  @Autowired
  public UsersServiceImpl(UsersRepository usersRepository, PasswordEncoder passwordEncoder) {
    this.usersRepository = usersRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public boolean signUp(String username, String password) {
    if (usersRepository.findByUsername(username).isPresent()) {
      return false;
    }
    String encodedPassword = passwordEncoder.encode(password);
    User newUser = new User(null, username, encodedPassword);
    usersRepository.save(newUser);
    return true;
  }
}