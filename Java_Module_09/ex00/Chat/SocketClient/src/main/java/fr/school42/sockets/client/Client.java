package fr.school42.sockets.client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
  public Client() {
  }

  public void start(String host, Integer port) {
    try (Socket clientSocket = new Socket(host, port);
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        Scanner scanner = new Scanner(System.in)) {

      String message = in.readLine();
      if (message == null) {
        throw new IOException("Server has closed the connection.");
      }
      System.out.println(message);
      while (message != null && !"Successful!".equalsIgnoreCase(message)) {
        out.println(scanner.nextLine());

        message = in.readLine();
        System.out.println(message);
        if ("exit".equalsIgnoreCase(message) || message.startsWith("Error")) {
          break;
        }
      }

    } catch (IOException e) {
      System.err.println("The server is not available!");
    } catch (Throwable e) {
      System.err.println("Error: " + e.getMessage());
    }
  }

}
