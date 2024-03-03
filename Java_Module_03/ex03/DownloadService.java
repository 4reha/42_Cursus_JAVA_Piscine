
import java.io.*;
import java.net.*;
import java.nio.file.Path;

public class DownloadService {

	private Path whereToDownload;

	public DownloadService(Path WhereToDownload) throws IllegalArgumentException {

		this.whereToDownload = WhereToDownload;
		prepareDestination();

	}

	public boolean downloadFile(String url) throws IOException, URISyntaxException {

		String fileName = url.substring(url.lastIndexOf('/') + 1);
		Path filePath = whereToDownload.resolve(fileName);
		File file = filePath.toFile();
		URL urlObj = new URI(url).toURL();
		InputStream in = urlObj.openConnection().getInputStream();
		FileOutputStream fos = new FileOutputStream(file);
		byte[] buffer = in.readAllBytes();
		fos.write(buffer);
		in.close();
		fos.close();
		return true;

	}

	private void prepareDestination() throws IllegalArgumentException {
		File dir = whereToDownload.toFile();
		if (!dir.exists()) {
			if (!dir.mkdirs()) {
				throw new IllegalArgumentException("Destination path does not exist and cannot be created");
			}
		} else if (!dir.isDirectory()) {
			throw new IllegalArgumentException("Destination path is not a directory");
		} else if (!dir.canWrite()) {
			throw new IllegalArgumentException("Destination path is not writable");
		}
	}

}
