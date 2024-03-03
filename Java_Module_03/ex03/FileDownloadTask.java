
public class FileDownloadTask implements Runnable {

	private String url;
	private int fileNumber;
	private DownloadService downloadService;

	public FileDownloadTask(String url, int fileNumber, DownloadService downloadService) {
		this.url = url;
		this.fileNumber = fileNumber;
		this.downloadService = downloadService;
	}

	@Override
	public void run() {
		System.out.println(Thread.currentThread().getName() + " start download file number " + fileNumber);
		try {
			if (downloadService.downloadFile(url))
				System.out.println(Thread.currentThread().getName() + " finish download file number " + fileNumber);
		} catch (Exception e) {
			System.err.println(
					Thread.currentThread().getName() + " failed download file number " + fileNumber + " " + e.getMessage());
		}

	}
}