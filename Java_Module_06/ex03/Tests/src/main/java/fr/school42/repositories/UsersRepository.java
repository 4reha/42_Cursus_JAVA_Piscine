package fr.school42.repositories;

import fr.school42.exceptions.EntityNotFoundException;
import fr.school42.models.User;

public interface UsersRepository {
  User findByLogin(String login) throws EntityNotFoundException;

  void update(User user) throws EntityNotFoundException;
}
