package fr.school42.printer.app;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.diogonunes.jcolor.Attribute;
import com.beust.jcommander.ParameterException;
import com.diogonunes.jcolor.Ansi;

import fr.school42.printer.logic.ColorConverter;
import fr.school42.printer.logic.ResourceNameValidator;
import fr.school42.printer.logic.ImageConverter;

@Parameters(separators = "=")
public class Main {

	@Parameter(names = { "-w", "--white" }, description = "White color replacement, Values: BLACK,RED,GREEN,YELLOW,BLUE,MAGENTA,CYAN,WHITE", converter = ColorConverter.class)
	private Attribute whiteColor = Attribute.WHITE_BACK();

	@Parameter(names = { "-b", "--black" }, description = "Black color replacement, Values: BLACK,RED,GREEN,YELLOW,BLUE,MAGENTA,CYAN,WHITE", converter = ColorConverter.class)
	private Attribute blackColor = Attribute.BLACK_BACK();

	@Parameter(names = { "-r",
			"--resource" }, description = "Resource name, (should be in resources folder)", order = 1, validateWith = ResourceNameValidator.class)
	private String resourceName = "image.bmp";

	public static JCommander jCommander;

	public void run() {

		ImageConverter converter = new ImageConverter(resourceName, 'W', 'B');
		char[][] imageData = converter.convertImage();

		if (imageData != null) {
			for (int y = 0; y < imageData.length; y++) {
				for (int x = 0; x < imageData[y].length; x++) {
					System.out.print(imageData[y][x] == 'W' ? Ansi.colorize("  ", whiteColor) : Ansi.colorize("  ", blackColor));
				}
				System.out.println();
			}
		}
	}

	public static void main(String[] argv) {
		try {
			Main main = new Main();

			jCommander = JCommander.newBuilder().addObject(main).build();

			jCommander.setProgramName("images-to-chars-printer");

			jCommander.parse(argv);

			main.run();
		} catch (ParameterException e) {
			System.err.println("Error: " + e.getMessage());
			System.err.println("_____");
			jCommander.usage();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
