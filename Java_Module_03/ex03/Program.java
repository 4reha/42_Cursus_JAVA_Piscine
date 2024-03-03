
public class Program {

	private static final String FILE_URLS_PATH = "files_urls.txt";
	private static final String DOWNLOADS_DIR = "./downloads";

	private static int getArg(String[] args, String __prefix) {

		try {
			for (int i = 0; i < args.length; i++) {
				if (args[i].startsWith(__prefix)) {
					int arg = Integer.parseInt(args[i].trim().substring(__prefix.length()));
					if (arg > 0)
						return arg;
				}
			}
		} catch (NumberFormatException e) {
		}
		System.err.println("Invalid argument");
		System.exit(-1);
		return 0;
	}

	public static void main(String[] args) {

		try {

			int threadsCount = getArg(args, "--threadsCount=");

			DownloadManager downloadManager = new DownloadManager(FILE_URLS_PATH, DOWNLOADS_DIR, threadsCount);

			downloadManager.start();

		} catch (Exception e) {

			e.printStackTrace();

		}

	}
}
