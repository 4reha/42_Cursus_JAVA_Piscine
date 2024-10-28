package fr.school42.sockets.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import fr.school42.sockets.models.Command;

public class Client {

  public void start(String host, int port) {
    try (Socket socket = new Socket(host, port);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        Scanner scanner = new Scanner(System.in)) {

      Thread receiveThread = new Thread(() -> {
        try {
          while (true) {
            String jsonString = in.readLine();
            if (jsonString == null) {
              throw new IOException("Connection closed");
            }
            Command command = Command.fromJson(jsonString);
            switch (command.getType()) {
              case "message":
                System.out.println(command.getFrom() + ": " + command.getContent());
                break;
              case "error":
                System.out.println("Error: " + command.getContent());
                break;
              default:
                if (!command.getContent().isEmpty()) {
                  System.out.println(command.getContent());
                }

            }
            for (String option : command.getOptions()) {
              System.out.println(option);
            }

          }
        } catch (IOException e) {
          System.err.println(e.getMessage());
          System.exit(1);
        }
      });
      receiveThread.start();
      while (true) {
        String input = scanner.nextLine();
        out.println(new Command("command", "client", input).toJson());
      }

    } catch (IOException e) {
      System.err.println("Error connecting to server: " + e.getMessage());
    } catch (Throwable e) {
    }
  }

}