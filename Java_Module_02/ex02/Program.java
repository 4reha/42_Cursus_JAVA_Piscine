import java.nio.file.*;
import java.util.*;

public class Program {
	private Path currentDirectory;
	private Map<String, Command> commands;

	public Program(String startDirectory) {
		currentDirectory = Paths.get(startDirectory);
		if (!currentDirectory.isAbsolute()) {
			throw new IllegalArgumentException("The --current-folder must be absolute");
		} else if (!currentDirectory.toFile().exists()) {
			throw new IllegalArgumentException("The --current-folder does not exist");
		} else if (!currentDirectory.toFile().isDirectory()) {
			throw new IllegalArgumentException("The --current-folder is not a directory");
		}
		commands = new HashMap<>();
		commands.put("ls", new LsCommand());
		commands.put("cd", new CdCommand());
		commands.put("mv", new MvCommand());
	}

	public void run() {
		System.out.println(currentDirectory.normalize());
		Scanner scanner = new Scanner(System.in);
		while (true) {
			System.out.print("$> ");
			try {
				String input;
				if (scanner.hasNextLine())
					input = scanner.nextLine().trim();
				else
					input = "exit";

				if (input.isEmpty())
					continue;
				if (input.equals("exit"))
					break;
				String[] parts = input.split("\\s+");
				String commandName = parts[0];

				Command command = commands.get(commandName);
				if (command == null)
					throw new UnknownCommandException(commandName);

				command.execute(currentDirectory, Arrays.copyOfRange(parts, 1, parts.length));
				if (commandName.equals("cd")) {
					currentDirectory = ((CdCommand) command).getCurrentDirectory();
				}

			} catch (Exception e) {
				System.err.println("Program: " + e.getMessage());
			}
		}
		scanner.close();
	}

	public static void main(String[] args) {
		try {
			if (args.length != 1)
				throw new IllegalArgumentException("Usage: java Program --current-folder=\"<start-directory>\"");
			else {
				String[] parts = args[0].split("=");
				if (parts.length != 2 || !parts[0].equals("--current-folder"))
					throw new IllegalArgumentException("Usage: java Program --current-folder=\"<start-directory>\"");

				new Program(parts[1]).run();
			}
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
		}
	}

	class UnknownCommandException extends RuntimeException {
		public UnknownCommandException(String commandName) {
			super(commandName + ": unknown command");
		}
	}
}
