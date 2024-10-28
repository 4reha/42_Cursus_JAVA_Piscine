package fr.school42.sockets.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {

  public void start(String host, int port) {
    try (Socket socket = new Socket(host, port);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        Scanner scanner = new Scanner(System.in)) {

      System.out.println(in.readLine());

      Thread receiveThread = new Thread(() -> {
        try {
          String message;
          while (true) {
            message = in.readLine();
            if (message == null) {
              throw new IOException("Connection lost");
            }
            System.out.println(message);
          }
        } catch (IOException e) {
          System.exit(1);
        }
      });
      receiveThread.start();
      while (true) {
        String input = scanner.nextLine();

        out.println(input);
      }

    } catch (IOException e) {
      System.err.println("Error connecting to server: " + e.getMessage());
    } catch (Throwable e) {
      System.err.println("Error: " + e.getMessage());
    }
  }

}