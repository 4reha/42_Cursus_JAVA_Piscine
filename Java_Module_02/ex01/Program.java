import java.nio.file.*;
import java.util.*;

public class Program {
	public static void main(String[] args) {
		if (args.length != 2) {
			System.out.println("Please provide two file paths as arguments.");
			return;
		}

		List<String> words1 = getWordsFromFile(args[0]);
		List<String> words2 = getWordsFromFile(args[1]);

		Set<String> dictionary = createDictionary(words1, words2);

		Map<String, Long> freq1 = getFrequencyMap(words1);
		Map<String, Long> freq2 = getFrequencyMap(words2);

		List<Long> vector1 = createVector(dictionary, freq1);
		List<Long> vector2 = createVector(dictionary, freq2);

		double similarity = calculateSimilarity(vector1, vector2);

		writeDictionaryToFile(dictionary, "dictionary.txt");

		System.out.printf("Similarity: %.2f\n", similarity);

	}

	private static List<String> getWordsFromFile(String filePath) {
		try {
			String fileContent = Files.readString(Path.of(filePath));
			return Arrays.asList(fileContent.trim().split("\\s+"));
		} catch (Exception e) {
			System.err.println("Error reading file: " + filePath);
			System.exit(-1);
			return null;
		}
	}

	private static Set<String> createDictionary(List<String> words1, List<String> words2) {
		Set<String> dictionary = new HashSet<>();
		dictionary.addAll(words1);
		dictionary.addAll(words2);
		return dictionary;
	}

	private static Map<String, Long> getFrequencyMap(List<String> words) {

		Map<String, Long> freqMap = new HashMap<>();

		for (String word : words) {
			String lowerCaseWord = word.toLowerCase();
			freqMap.put(lowerCaseWord, freqMap.getOrDefault(lowerCaseWord, 0L) + 1);
		}

		return freqMap;
	}

	private static List<Long> createVector(Set<String> dictionary, Map<String, Long> freqMap) {

		List<Long> vector = new ArrayList<>(dictionary.size());

		for (String word : dictionary) {
			vector.add(freqMap.getOrDefault(word, 0L));
		}

		return vector;
	}

	private static double calculateSimilarity(List<Long> vector1, List<Long> vector2) {

		double numerator = 0.0;

		for (int i = 0; i < vector1.size(); i++) {
			numerator += vector1.get(i) * vector2.get(i);
		}

		double denominatorA = 0.0;

		for (int i = 0; i < vector1.size(); i++) {
			denominatorA += vector1.get(i) * vector1.get(i);
		}

		double denominatorB = 0.0;

		for (int i = 0; i < vector2.size(); i++) {
			denominatorB += vector2.get(i) * vector2.get(i);
		}

		double denominator = Math.sqrt(denominatorA) * Math.sqrt(denominatorB);

		if (denominator != 0.0) {
			return numerator / denominator;
		}
		return 0.0;
	}

	private static void writeDictionaryToFile(Set<String> dictionary, String filePath) {
		try {
			Files.write(Path.of(filePath), dictionary);
		} catch (Exception e) {
			System.err.println("Error writing file: " + filePath);
			System.exit(-1);
		}
	}
}