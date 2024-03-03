import java.io.*;
import java.util.*;

public class FileSignatureAnalyzer implements SignatureAnalyzer {
	private Map<String, String> signatures = new TreeMap<>(new LengthComparator());
	private int maxSigBytes;

	public FileSignatureAnalyzer(String signaturesPath) throws IOException {
		loadSignatures(signaturesPath);
	}

	private void loadSignatures(String signaturesPath) throws IOException {
		try (BufferedReader reader = new BufferedReader(new FileReader(signaturesPath))) {
			String line;
			while ((line = reader.readLine()) != null) {
				if (!line.isEmpty()) {
					String[] parts = line.split(",");
					if (parts.length != 2)
						throw new IOException("Invalid signature file format");

					maxSigBytes = Math.max(maxSigBytes, parts[1].replace(" ", "").length() / 2);
					signatures.put(parts[1].trim(), parts[0].trim());
				}
			}
		}
	}

	@Override
	public String analyze(String filePath) {
		try {
			File file = new File(filePath);
			String fileType = "UNDEFINED";
			if (!file.exists() || !file.isFile() || !file.canRead()
			// || !file.isAbsolute()
			) {
				return fileType;
			}
			FileInputStream fis = new FileInputStream(filePath);
			byte[] magicBytes = fis.readNBytes(maxSigBytes);
			String magicBytesHex = bytesToHex(magicBytes);

			for (String key : signatures.keySet()) {
				if (key.equals(magicBytesHex.substring(0, key.length()))) {
					fileType = signatures.get(key);
					break;
				}
			}
			fis.close();
			return fileType;
		} catch (IOException e) {
			return "UNDEFINED";
		}
	}

	private String bytesToHex(byte[] bytes) {
		StringBuilder sb = new StringBuilder();
		for (byte b : bytes) {
			sb.append(String.format("%02X ", b));
		}
		return sb.toString().trim();
	}

}