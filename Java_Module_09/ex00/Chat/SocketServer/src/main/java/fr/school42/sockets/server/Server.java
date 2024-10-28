package fr.school42.sockets.server;

import fr.school42.sockets.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

@Component
public class Server {
  private final UsersService usersService;

  @Autowired
  public Server(UsersService usersService) {
    this.usersService = usersService;
  }

  public void start(int port) {
    try (ServerSocket serverSocket = new ServerSocket(port)) {
      System.out.println("Server started on port " + port);
      while (true) {
        try (Socket clientSocket = serverSocket.accept();
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

          out.println("Hello from Server!");

          String command = in.readLine();
          if ("signUp".equals(command)) {
            out.println("Enter username:");
            String username = in.readLine();
            out.println("Enter password:");
            String password = in.readLine();

           if (username == null || username.isEmpty() || password == null || password.isEmpty()){
              out.println("Error: Username and password cannot be empty");
              continue;
            }

            boolean success = usersService.signUp(username, password);
            if (success) {
              out.println("Successful!");
            } else {
              out.println("Error: Username already exists");
            }
          } else {
            out.println("Unknown command");
          }
        } catch (IOException e) {
          System.err.println("Error handling client connection: " + e.getMessage());
        }
      }
    } catch (IOException e) {
      System.err.println("Failed to start server: " + e.getMessage());
    }
  }
}