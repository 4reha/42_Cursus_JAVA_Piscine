package fr.school42.sockets.server;

import fr.school42.sockets.models.Message;
import fr.school42.sockets.models.User;
import fr.school42.sockets.services.MessagesService;
import fr.school42.sockets.services.UsersService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientListener extends Thread {
  private final Socket socket;
  private final UsersService usersService;
  private final MessagesService messagesService;
  private User currentUser;

  public ClientListener(Socket socket, UsersService usersService, MessagesService messagesService) {
    this.socket = socket;
    this.usersService = usersService;
    this.messagesService = messagesService;
    start();
  }

  @Override
  public void run() {
    try (
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
      out.println("Hello from Server!");

      try {
        String command = in.readLine();
        if ("signIn".equalsIgnoreCase(command)) {
          currentUser = authenticateUser(in, out);
          if (currentUser != null) {
            out.println("Start messaging");
            handleClientMessages(in, out);
          }
        } else if ("signUp".equalsIgnoreCase(command)) {
          registerUser(in, out);
        } else {
          out.println("Invalid command");
        }
      } catch (Throwable e) {
        out.println("Error: " + e.getMessage());
        e.printStackTrace();
      }

    } catch (Throwable e) {
      e.printStackTrace();
    } finally {
      try {
        socket.close();
      } catch (Throwable e) {
        e.printStackTrace();
      }
      Server.clientListeners.remove(this);
    }
  }

  private User authenticateUser(BufferedReader in, PrintWriter out) throws IOException {
    out.println("Enter username:");
    String username = in.readLine();

    out.println("Enter password:");
    String password = in.readLine();

    User user = usersService.signIn(username, password);
    if (user == null) {
      out.println("Invalid credentials");
    }
    return user;
  }

  private void handleClientMessages(BufferedReader in, PrintWriter out) throws IOException {
    String messageText;
    while ((messageText = in.readLine()) != null) {
      if ("Exit".equalsIgnoreCase(messageText)) {
        out.println("You have left the chat.");
        break;
      }

      System.out.println("Message received from " + currentUser.getUsername() + ": " + messageText);

      Message message = messagesService.saveMessage(currentUser.getUsername(), messageText);
      broadcastMessage(currentUser.getUsername(), message.getText());
    }
  }

  private void broadcastMessage(String sender, String messageText) {
    String message = sender + ": " + messageText;
    for (ClientListener client : Server.clientListeners) {
      client.sendMessageToClient(message);
    }
  }

  private void sendMessageToClient(String message) {
    if (currentUser == null) {
      return;
    }
    try {
      PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
      out.println(message);
    } catch (IOException e) {
      System.out.println("Failed to send message to client: " + e.getMessage());
    }
  }

  private void registerUser(BufferedReader in, PrintWriter out) throws IOException {
    out.println("Enter username:");
    String username = in.readLine();

    out.println("Enter password:");
    String password = in.readLine();

    User user = usersService.signUp(username, password);
    if (user != null) {
      out.println("Successful!");
    } else {
      out.println("Registration failed");
    }
  }
}