import java.io.IOException;
import java.nio.file.Path;

/**
 * The Command interface represents a command that can be executed.
 * It defines a single method, execute, which takes the current directory
 * and an array of arguments as parameters and throws an IOException.
 */
public interface Command {

	void execute(Path currentDirectory, String[] args) throws IOException, TooManyArgumentsException;

	public static class TooManyArgumentsException extends IllegalArgumentException {
		public TooManyArgumentsException(String commandName) {
			super(commandName + ": too many arguments");
		}
	}

	public static class NeedsMoreArgumentsException extends IllegalArgumentException {
		public NeedsMoreArgumentsException(String commandName) {
			super(commandName + ": needs more arguments");
		}
	}

	public static class NoSuchFileOrDirectoryException extends IllegalArgumentException {
		public NoSuchFileOrDirectoryException(String commandName, String fileOrDirectory) {
			super(commandName + ": " + fileOrDirectory + ": No such file or directory");
		}
	}

	public static class NotADirectoryException extends IllegalArgumentException {
		public NotADirectoryException(String commandName, String fileOrDirectory) {
			super(commandName + ": " + fileOrDirectory + ": Not a directory");
		}
	}

	public static class AccessDeniedException extends IllegalArgumentException {
		public AccessDeniedException(String commandName, String fileOrDirectory) {
			super(commandName + ": " + fileOrDirectory + ": Access denied");
		}
	}

	public static class InvalidArgumentsException extends IllegalArgumentException {
		public InvalidArgumentsException(String commandName, String fileOrDirectory) {
			super(commandName + ": " + fileOrDirectory + ": Invalid arguments");
		}
	}
}