package fr.school42.printer.app;

import fr.school42.printer.logic.ImageConverter;

public class Main {

	private static final char DEFAULT_WHITE_CHAR = ' ';
	private static final char DEFAULT_BLACK_CHAR = '*';

	private static void printUsage() {
		System.err.println("invalid arguments");
		System.exit(-1);
	}

	private static String getFilePath(String[] args) {
		if (args.length >= 1)
			return args[0].trim();

		printUsage();
		return null;
	}

	private static char getChar(String[] args, int index) {
		if (args.length >= index + 1) {
			if (args[index].length() == 1)
				return args[index].charAt(0);
			else
				printUsage();
		}

		return index == 1 ? DEFAULT_WHITE_CHAR : DEFAULT_BLACK_CHAR;
	}

	public static void main(String[] args) {

		String imagePath = getFilePath(args);
		char whiteChar = getChar(args, 1);
		char blackChar = getChar(args, 2);

		ImageConverter converter = new ImageConverter(whiteChar, blackChar);
		char[][] imageData = converter.convertImage(imagePath);

		if (imageData != null) {
			for (int y = 0; y < imageData.length; y++) {
				for (int x = 0; x < imageData[y].length; x++) {
					System.out.print(imageData[y][x]);
				}
				System.out.println();
			}
		}
	}
}
