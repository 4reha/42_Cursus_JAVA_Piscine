import java.io.IOException;

/**
 * The SignatureAnalyzer interface represents a component that analyzes the file
 * type
 * based on its signature (magic numbers) and returns the file type.
 */
public interface SignatureAnalyzer {
	/**
	 * Analyzes the file type from the signature (magic numbers) of the specified
	 * file.
	 *
	 * @param filePath the path of the file to analyze
	 * @return the file type based on its signature
	 * @throws IOException if an I/O error occurs while reading the file
	 */
	String analyze(String filePath) throws IOException;
}