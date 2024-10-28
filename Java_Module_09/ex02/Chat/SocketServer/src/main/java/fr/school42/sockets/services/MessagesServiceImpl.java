package fr.school42.sockets.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fr.school42.sockets.models.Chatroom;
import fr.school42.sockets.models.Message;
import fr.school42.sockets.repositories.ChatroomsRepository;
import fr.school42.sockets.repositories.MessagesRepository;
import fr.school42.sockets.repositories.UsersRepository;
import fr.school42.sockets.models.User;

@Component
public class MessagesServiceImpl implements MessagesService {
  @Autowired
  private UsersRepository usersRepository;
  @Autowired
  private ChatroomsRepository chatroomsRepository;
  @Autowired
  private MessagesRepository messageRepository;

  @Override
  public Message saveMessage(String username, Long roomId, String text) {

    if (username == null || username.isEmpty()) {
      throw new IllegalArgumentException("Username is empty");
    }
    if (text == null || text.isEmpty()) {
      throw new IllegalArgumentException("Text is empty");
    }
    if (roomId == null) {
      throw new IllegalArgumentException("Room ID is empty");
    }

    User user = usersRepository.findByUsername(username).orElseThrow();
    Chatroom room = chatroomsRepository.findById(roomId);
    Message message = new Message(user, room, text);
    messageRepository.save(message);
    return message;
  }

  @Override
  public List<Message> getLatestMessages(Long roomId, int limit, int offset) {
    if (roomId == null) {
      throw new IllegalArgumentException("Room ID is empty");
    }
    if (limit < 0) {
      throw new IllegalArgumentException("Limit is negative");
    }
    if (offset < 0) {
      throw new IllegalArgumentException("Offset is negative");
    }

    return messageRepository.findByRoomId(roomId, limit, offset);
  }

}