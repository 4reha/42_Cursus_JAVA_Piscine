package fr.school42.sockets.server;

import fr.school42.sockets.models.Chatroom;
import fr.school42.sockets.models.Message;
import fr.school42.sockets.models.User;
import fr.school42.sockets.models.Command;
import fr.school42.sockets.services.MessagesService;
import fr.school42.sockets.services.UsersService;
import fr.school42.sockets.services.ChatroomService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

public class ClientListener extends Thread {
  private final Socket socket;
  private final UsersService usersService;
  private final MessagesService messagesService;
  private final ChatroomService chatroomService;
  private User currentUser;
  private Chatroom currentRoom;

  public ClientListener(Socket socket, UsersService usersService,
      MessagesService messagesService,
      ChatroomService chatroomService) {
    this.socket = socket;
    this.usersService = usersService;
    this.messagesService = messagesService;
    this.chatroomService = chatroomService;
    start();
  }

  @Override
  public void run() {
    try (
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
      Command command = new Command("response", "server", "Hello from server!");
      out.println(command.toJson());

      try {
        while (true) {
          sendMainMenu(out);
          String choice = Command.fromJson(in.readLine()).getContent();

          if (choice == null)
            return;
          switch (choice) {
            case "1":
              if (signIn(in, out)) {
                handleRoomMenu(in, out);
              }
              break;
            case "2":
              signUp(in, out);
              break;
            case "3":
              out.println(new Command("respone", "server", "Goodbye!").toJson());
              return;
            default:
              out.println(new Command("error", "server", "Invalid choice. Please try again.").toJson());
          }

        }
      } catch (Throwable e) {
        out.println(new Command("error", "server", e.getMessage()).toJson());
        System.out.println("Caught exception: " + e.getMessage());
      }
    } catch (Throwable e) {
      e.printStackTrace();
    } finally {
      try {
        socket.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
      Server.clientListeners.remove(this);
    }
  }

  private void sendMainMenu(PrintWriter out) {
    Command command = new Command("menu", "server", "Main menu", new String[] {
        "1. SignIn", "2. SignUp", "3. Exit" });
    out.println(command.toJson());
  }

  private boolean signIn(BufferedReader in, PrintWriter out) throws IOException {
    out.println(new Command("respone", "server", "Enter username:").toJson());
    String username = Command.fromJson(in.readLine()).getContent();

    out.println(new Command("respone", "server", "Enter password:").toJson());
    String password = Command.fromJson(in.readLine()).getContent();

    currentUser = usersService.signIn(username, password);

    out.println(new Command("respone", "server", "Successfully signed in!").toJson());
    return true;
  }

  private void signUp(BufferedReader in, PrintWriter out) throws IOException {
    out.println(new Command("respone", "server", "Enter username:").toJson());
    String username = Command.fromJson(in.readLine()).getContent();

    out.println(new Command("respone", "server", "Enter password:").toJson());
    String password = Command.fromJson(in.readLine()).getContent();

    User user = usersService.signUp(username, password);
    if (user != null) {
      out.println(new Command("respone", "server", "Successful!").toJson());
    } else {
      out.println(new Command("respone", "error", "Registration failed").toJson());
    }
  }

  private void handleRoomMenu(BufferedReader in, PrintWriter out) throws IOException {
    while (true) {
      sendRoomMenu(out);
      String choice = Command.fromJson(in.readLine()).getContent();

      if (choice == null)
        return;

      switch (choice) {
        case "1":
          createRoom(in, out);
          break;
        case "2":
          if (chooseRoom(in, out)) {
            handleChat(in, out);
          }
          break;
        case "3":
          return;
        default:
          out.println(new Command("error", "server", "Invalid choice. Please try again.").toJson());
      }
    }
  }

  private void sendRoomMenu(PrintWriter out) {
    Command command = new Command("menu", "server", "Room menu:", new String[] {
        "1. Create room", "2. Choose room", "3. Exit" });
    out.println(command.toJson());
  }

  private void createRoom(BufferedReader in, PrintWriter out) throws IOException {
    out.println(new Command("respone", "server", "Enter room name:").toJson());
    String roomName = Command.fromJson(in.readLine()).getContent();
    Chatroom room = chatroomService.createRoom(roomName);
    if (room != null) {
      out.println(new Command("respone", "server", "Room created successfully!").toJson());
    } else {
      out.println(new Command("error", "server", "Failed to create room").toJson());
    }
  }

  private boolean chooseRoom(BufferedReader in, PrintWriter out) throws IOException {
    List<Chatroom> rooms = chatroomService.ListRooms();

    String[] options = new String[rooms.size()];
    for (int i = 0; i < rooms.size(); i++) {
      options[i] = (i + 1) + ". " + rooms.get(i).getName();
    }

    Command command = new Command("menu", "server", "Rooms:", options);
    out.println(command.toJson());

    String choice = Command.fromJson(in.readLine()).getContent();
    if (choice == null)
      return false;

    int choiceNum;
    try {
      choiceNum = Integer.parseInt(choice);
    } catch (NumberFormatException e) {
      out.println(new Command("error", "server", "Invalid choice").toJson());
      return false;
    }
    if (choiceNum > 0 && choiceNum <= rooms.size()) {
      currentRoom = rooms.get(choiceNum - 1);
      out.println(new Command("respone", "server", currentRoom.getName() + " ---").toJson());
      getLatestMessages(out);
      return true;
    } else if (choiceNum == rooms.size() + 1) {
      return false;
    } else {
      out.println(new Command("error", "server", "Invalid choice").toJson());
      return false;
    }
  }

  private void getLatestMessages(PrintWriter out) {
    List<Message> messages = messagesService.getLatestMessages(currentRoom.getId(), 30, 0);

    if (messages.isEmpty()) {
      return;
    }
    String[] options = new String[messages.size()];
    for (int i = 0; i < messages.size(); i++) {
      options[i] = messages.get(i).getSender().getUsername() + ": " + messages.get(i).getText();
    }

    Command command = new Command("menu", "server", "Latest messages:", options);
    out.println(command.toJson());
  }

  private void handleChat(BufferedReader in, PrintWriter out) throws IOException {

    while (true) {
      Command command = Command.fromJson(in.readLine());
      if (command == null)
        return;
      if ("Exit".equalsIgnoreCase(command.getContent())) {
        out.println(new Command("respone", "server", "You have left the chat.").toJson());
        currentRoom = null;
        break;
      }

      try {
        Message message = messagesService.saveMessage(currentUser.getUsername(),
            currentRoom.getId(), command.getContent());
        broadcastMessage(currentUser.getUsername(), message.getText());
      } catch (IllegalArgumentException e) {
        out.println(new Command("error", "server", e.getMessage()).toJson());
      }
    }
  }

  private void broadcastMessage(String sender, String messageText) {
    Command broadcastMsg = new Command("message", sender, messageText);

    for (ClientListener client : Server.clientListeners) {
      if (client.currentRoom != null &&
          client.currentRoom.getId().equals(this.currentRoom.getId())) {
        client.sendMessageToClient(broadcastMsg.toJson().toString());
      }
    }
  }

  private void sendMessageToClient(String jsonMessage) {
    try {
      PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
      out.println(jsonMessage);
    } catch (IOException e) {
      System.out.println("Failed to send message to client: " + e.getMessage());
    }
  }

}