import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.nio.file.Path;

class LsCommand implements Command {
	@Override
	public void execute(Path currentDirectory, String[] args) throws IOException {

		if (args.length == 0)
			args = new String[] { "." };

		for (String arg : args) {
			Path path = currentDirectory.resolve(arg);
			if (!path.toFile().exists()) {
				throw new Command.NoSuchFileOrDirectoryException("ls", arg);
			}
			if (!path.toFile().canRead())
				throw new Command.AccessDeniedException("ls", arg);

			if (path.toFile().isFile())
				listFile(path);
			else
				listDirectory(path);
		}
	}

	private void listDirectory(Path path) throws IOException {
		Files.list(path).forEach(p -> {
			listFile(p);
		});
	}

	private void listFile(Path path) {
		System.out.println(path.getFileName() + " " + formatSize(path.toFile().length()));
	}

	private String formatSize(long size) {
		if (size < 1024)
			return size + " B";
		else if (size < 1024 * 1024)
			return size / 1024 + " KB";
		else if (size < 1024 * 1024 * 1024)
			return size / (1024 * 1024) + " MB";
		else
			return size / (1024 * 1024 * 1024) + " GB";
	}
}
