package fr.school42.sockets.services;

import fr.school42.sockets.models.User;

public interface UsersService {
  User signUp(String username, String password);

  User signIn(String username, String password);
}
