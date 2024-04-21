package fr.school42.chat.repositories;

import fr.school42.chat.models.Message;
import java.util.Optional;

public interface MessagesRepository {

	Optional<Message> findById(Long id);

	Message save(Message message);

	Message update(Message message);

}
