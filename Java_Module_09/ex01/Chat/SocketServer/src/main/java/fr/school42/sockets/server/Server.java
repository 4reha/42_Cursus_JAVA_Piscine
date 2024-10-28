package fr.school42.sockets.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import fr.school42.sockets.services.MessagesService;
import fr.school42.sockets.services.UsersService;

@Component
public class Server {
  @Autowired
  private UsersService usersService;
  @Autowired
  private MessagesService messageService;
  public static LinkedList<ClientListener> clientListeners = new LinkedList<>();

  public Server() {
  }

  public void start(int port) {
    try (ServerSocket serverSocket = new ServerSocket(port)) {
      System.out.println("Server started on port " + port);
      while (true) {
        try {
          Socket socket = serverSocket.accept();
          clientListeners.add(new ClientListener(socket, usersService, messageService));
        } catch (Throwable e) {
          System.err.println("Failed to create client listener: " + e.getMessage());
        }
      }
    } catch (IOException e) {
      System.err.println("Failed to start server: " + e.getMessage());
    }
  }
}
