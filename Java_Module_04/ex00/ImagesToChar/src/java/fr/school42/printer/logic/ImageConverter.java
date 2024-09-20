package fr.school42.printer.logic;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageConverter {

	private final char WHITE_CHAR;
	private final char BLACK_CHAR;

	public ImageConverter(char whiteChar, char blackChar) {
		WHITE_CHAR = whiteChar;
		BLACK_CHAR = blackChar;
	}

	public char[][] convertImage(String imagePath) {
		try {
			BufferedImage image = ImageIO.read(new File(imagePath));
			int width = image.getWidth();
			int height = image.getHeight();

			char[][] imageData = new char[height][width];

			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					int pixel = image.getRGB(x, y);
					imageData[y][x] = pixel == 0xFF000000 ? BLACK_CHAR : WHITE_CHAR;
				}
			}
			return imageData;
		} catch (IOException e) {
			System.err.println("Error reading image: " + e.getMessage());
			return null;
		}
	}
}
