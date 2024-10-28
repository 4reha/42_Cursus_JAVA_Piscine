package fr.school42.sockets.repositories;

import java.util.List;

import fr.school42.sockets.models.Message;

public interface MessagesRepository extends CrudRepository<Message> {

  List<Message> findByRoomId(Long roomId, int limit, int offset);

}
