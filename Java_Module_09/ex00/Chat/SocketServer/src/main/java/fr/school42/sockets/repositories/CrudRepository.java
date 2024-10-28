package fr.school42.sockets.repositories;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<T> {
  Optional<T> findById(Long id);

  List<T> findAll();

  T save(T entity);

  void delete(Long id);
}