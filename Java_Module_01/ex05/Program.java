
public class Program {

  public static Menu.Mode getMode(String[] args) {
    Menu.Mode mode = Menu.Mode.PROD;

    for (String arg : args) {
      arg = arg.trim().toLowerCase();
      if (arg.startsWith("--profile=")) {
        String profile = arg.substring(10);
        if (profile.equals("dev") || profile.equals("development")) {
          mode = Menu.Mode.DEV;
        } else if (profile.equals("prod") || profile.equals("production")) {
          mode = Menu.Mode.PROD;
        } else {
          throw new IllegalArgumentException("Invalid argument: " + arg);
        }
      } else {
        throw new IllegalArgumentException("Invalid argument: " + arg);
      }
    }

    return mode;
  }

  public static void main(String[] args) {
    // args = --profile=dev

    try {
      Menu menu = new Menu();

      menu.start(getMode(args));

    } catch (Exception e) {

      System.err.println(e.getMessage());

    }
  }
}
