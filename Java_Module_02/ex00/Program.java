
import java.io.FileWriter;
import java.util.Scanner;

public class Program {
	private static final String SIGNATURES_FILE = "signatures.txt";
	private static final String RESULT_FILE = "result.txt";

	public static String readPath() {
		Scanner scanner = new Scanner(System.in);
		String path = "";
		
		System.out.print("-> ");
		if (scanner.hasNextLine())
			path = scanner.nextLine().trim().split("\\s+")[0];
		else
			path = "42";

		return path;
	}

	public static void main(String[] args) {

		try {
			SignatureAnalyzer analyzer = new FileSignatureAnalyzer(SIGNATURES_FILE);
			FileWriter writer = new FileWriter(RESULT_FILE);

			while (true) {
				final String FILE_PATH = readPath();
				if (FILE_PATH.equals("42"))
					break;
				String fileType = analyzer.analyze(FILE_PATH);
				if (!fileType.equals("UNDEFINED")) {
					writer.write(fileType + "\n");
					writer.flush();
					fileType = "PROCESSED";
				}
				System.err.println(fileType);
			}
			writer.close();
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}

	}
}