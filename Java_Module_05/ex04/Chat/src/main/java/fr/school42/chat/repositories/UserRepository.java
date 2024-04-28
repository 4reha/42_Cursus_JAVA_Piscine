package fr.school42.chat.repositories;

import java.util.List;
import java.util.Optional;

import fr.school42.chat.models.User;

public interface UserRepository {
    Optional<User> findById(Long id) throws RuntimeException;

    List<User> findAll(int page, int size) throws RuntimeException;
}
