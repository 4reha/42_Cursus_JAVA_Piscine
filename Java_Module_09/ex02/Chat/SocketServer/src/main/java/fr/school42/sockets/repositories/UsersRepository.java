package fr.school42.sockets.repositories;

import java.util.Optional;

import fr.school42.sockets.models.User;

public interface UsersRepository extends CrudRepository<User> {

  Optional<User> findByUsername(String username);

}
