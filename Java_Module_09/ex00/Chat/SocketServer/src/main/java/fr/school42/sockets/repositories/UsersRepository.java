package fr.school42.sockets.repositories;

import fr.school42.sockets.models.User;

import java.util.Optional;

public interface UsersRepository extends CrudRepository<User> {
  Optional<User> findByUsername(String username);
}