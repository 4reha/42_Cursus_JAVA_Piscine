package fr.school42.chat.repositories;

import java.util.List;

import fr.school42.chat.models.User;

public interface UsersRepository {

	List<User> findAll(int page, int size);

}
