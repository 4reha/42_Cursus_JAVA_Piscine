package fr.school42.chat.repositories;

import fr.school42.chat.models.Message;

import java.sql.SQLException;
import java.util.Optional;

public interface MessagesRepository {

	Optional<Message> findById(Long id) throws SQLException;

}
