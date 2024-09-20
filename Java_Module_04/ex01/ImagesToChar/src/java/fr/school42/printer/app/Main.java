package fr.school42.printer.app;

import fr.school42.printer.logic.ImageConverter;

public class Main {

	private static final char DEFAULT_WHITE_CHAR = ' ';
	private static final char DEFAULT_BLACK_CHAR = '*';

	private static void errorExit() {
		System.err.println("invalid arguments");
		System.exit(-1);
	}

	private static char getChar(String[] args, int index) {
		if (args.length >= index + 1) {
			if (args[index].length() == 1)
				return args[index].charAt(0);

			errorExit();
		}

		return index == 0 ? DEFAULT_WHITE_CHAR : DEFAULT_BLACK_CHAR;
	}

	public static void main(String[] args) {

		char whiteChar = getChar(args, 0);
		char blackChar = getChar(args, 1);

		ImageConverter converter = new ImageConverter(whiteChar, blackChar);
		char[][] imageData = converter.convertImage();

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
