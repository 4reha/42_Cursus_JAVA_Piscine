package fr.school42.sockets.services;

import java.util.List;

import fr.school42.sockets.models.Message;

public interface MessagesService {
    Message saveMessage(String username, Long roomId, String text);

    List<Message> getLatestMessages(Long roomId, int limit, int offset);
}
