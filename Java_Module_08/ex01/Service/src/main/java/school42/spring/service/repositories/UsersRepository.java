package school42.spring.service.repositories;

import java.util.Optional;

import school42.spring.service.models.User;

public interface UsersRepository extends CrudRepository<User> {

  Optional<User> findByEmail(String email);

}
