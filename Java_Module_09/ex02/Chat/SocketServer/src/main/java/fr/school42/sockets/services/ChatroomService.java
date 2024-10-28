package fr.school42.sockets.services;

import java.util.List;

import fr.school42.sockets.models.Chatroom;

public interface ChatroomService {

  public Chatroom createRoom(String name);

  public List<Chatroom> ListRooms();

  public Chatroom getRoom(Long id);

}
