package fr.school42.sockets.app;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.beust.jcommander.Parameters;

import fr.school42.sockets.config.SocketsApplicationConfig;
import fr.school42.sockets.server.Server;

@Parameters(separators = "=")
public class Main {

  @Parameter(names = { "--port", "-p" })
  private Integer port;

  public static void main(String[] args) {
    Main main = create(args);
    try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
        SocketsApplicationConfig.class)) {
      Server server = context.getBean(Server.class);
      server.start(main.port);
    }
  }

  private static Main create(String[] args) {
    Main main = new Main();
    try {
      JCommander.newBuilder().addObject(main).build().parse(args);
    } catch (ParameterException ex) {
      System.err.println("Error arguments!");
      System.exit(1);
    }
    if (main.port == null || main.port < 1024 || main.port > 65535) {
      System.err.println("The port number must be in the range from 1024 to 65535!");
      System.exit(1);
    }
    return main;
  }

}
