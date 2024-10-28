package fr.school42.sockets.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fr.school42.sockets.models.Chatroom;
import fr.school42.sockets.repositories.ChatroomsRepository;

@Component
public class ChatroomServiceImpl implements ChatroomService {

  @Autowired
  private ChatroomsRepository chatroomRepository;

  @Override
  public Chatroom createRoom(String name) {
    Chatroom chatroom = new Chatroom(name);
    chatroomRepository.save(chatroom);
    return chatroom;
  }

  @Override
  public List<Chatroom> ListRooms() {
    return chatroomRepository.findAll();
  }

  @Override
  public Chatroom getRoom(Long id) {
    return chatroomRepository.findById(id);
  }

}
