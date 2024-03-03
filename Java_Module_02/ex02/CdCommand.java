import java.nio.file.*;

class CdCommand implements Command {

	private Path currentDirectory;

	@Override
	public void execute(Path currentDirectory, String[] args) {

		String newDir;

		if (args.length > 1)
			throw new TooManyArgumentsException("cd");
		else if (args.length == 0)
			newDir = System.getProperty("user.home");
		else {
			if (args[0].equals("~"))
				newDir = System.getProperty("user.home");
			else
				newDir = args[0];
		}

		Path newDirectory = currentDirectory.resolve(newDir).normalize();

		if (!newDirectory.toFile().exists()) {
			throw new NoSuchFileOrDirectoryException("cd", newDir);
		}
		if (!newDirectory.toFile().isDirectory()) {
			throw new NotADirectoryException("cd", newDir);
		}

		this.currentDirectory = newDirectory;

		System.err.println(this.currentDirectory.normalize());
	}

	public Path getCurrentDirectory() {
		return currentDirectory;
	}
}