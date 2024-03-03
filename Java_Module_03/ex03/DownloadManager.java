
import java.util.*;
import java.io.*;
import java.net.*;
import java.nio.file.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DownloadManager {

	private Path fileUrlsPath;
	private Path whereToDownload;
	private Queue<String> fileUrls;

	DownloadService downloadService;
	private ExecutorService executor;

	public DownloadManager(String fileUrlsPath, String WhereToDownload, int threadsNum) throws IOException {

		this.fileUrlsPath = Paths.get(fileUrlsPath);
		this.whereToDownload = Paths.get(WhereToDownload);
		this.fileUrls = new PriorityQueue<String>();

		loadFileUrls();

		this.downloadService = new DownloadService(this.whereToDownload);
		this.executor = Executors.newFixedThreadPool(threadsNum, new ThreadFactory());
	}

	private void loadFileUrls() throws IOException {
		File file = fileUrlsPath.toFile();
		BufferedReader br = new BufferedReader(new FileReader(file));

		String line;
		while ((line = br.readLine()) != null) {
			if (isValidUrl(line))
				fileUrls.add(line);

		}
		br.close();
	}

	private static boolean isValidUrl(String url) {
		try {
			new URI(url).toURL();
			return true;
		} catch (Exception e) {
			// System.err.println("Invalid URL: " + url + " " + e.getMessage());
			return false;
		}
	}

	public void start() {

		int i = 0;
		while (!fileUrls.isEmpty()) {
			String url = fileUrls.poll();
			executor.submit(new FileDownloadTask(url, ++i, downloadService));
		}
		executor.shutdown();
	}

}
