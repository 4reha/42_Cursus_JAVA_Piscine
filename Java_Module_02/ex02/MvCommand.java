import java.io.IOException;
import java.nio.file.*;

class MvCommand implements Command {
	@Override
	public void execute(Path currentDirectory, String[] args) throws IOException {

		if (args.length > 2) {
			throw new Command.TooManyArgumentsException("mv");
		} else if (args.length < 2) {
			throw new Command.NeedsMoreArgumentsException("mv");
		}

		Path what = currentDirectory.resolve(args[0]).normalize();

		if (!what.toFile().exists()) {
			throw new Command.NoSuchFileOrDirectoryException("mv", args[0]);
		}

		if (!what.toFile().canWrite()) {
			throw new Command.AccessDeniedException("mv", what.toString());
		}

		if (args[1].contains("/")) {
			Path where = currentDirectory.resolve(args[1]).normalize();
			this.moveFile(what, where);
		} else {
			this.renameFile(what, args[1]);
		}

	}

	private void moveFile(Path what, Path where) throws IOException {

		if (!where.toFile().exists()) {
			throw new Command.NoSuchFileOrDirectoryException("mv", where.toString());
		}

		if (!where.toFile().isDirectory()) {
			throw new Command.NotADirectoryException("mv", where.toString());
		}

		if (!where.toFile().canWrite()) {
			throw new Command.AccessDeniedException("mv", where.toString());
		}

		Path destination = where.resolve(what.getFileName());

		System.out.println(what.normalize());
		System.out.println(destination.normalize());

		Files.move(what, destination, StandardCopyOption.REPLACE_EXISTING);
	}

	private void renameFile(Path what, String where) throws IOException {

		System.out.println(what.normalize());

		Path destination = what.getParent().resolve(where).normalize();

		System.out.println(destination.normalize());

		Files.move(what, destination, StandardCopyOption.REPLACE_EXISTING);

	}

}