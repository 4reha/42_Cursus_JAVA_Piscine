package fr.school42.services;

import fr.school42.exceptions.AlreadyAuthenticatedException;
import fr.school42.exceptions.EntityNotFoundException;
import fr.school42.models.User;
import fr.school42.repositories.UsersRepository;

public class UsersServiceImpl {
  private UsersRepository usersRepository;

  public UsersServiceImpl(UsersRepository usersRepository) {
    this.usersRepository = usersRepository;
  }

  public boolean authenticate(String login, String password)
      throws AlreadyAuthenticatedException, EntityNotFoundException {
    User user = usersRepository.findByLogin(login);

    if (user.isAuthenticated()) {
      throw new AlreadyAuthenticatedException("User is already authenticated");
    }

    if (user.getPassword().equals(password)) {
      user.setAuthenticated(true);
      usersRepository.update(user);
      return true;
    }

    return false;
  }
}