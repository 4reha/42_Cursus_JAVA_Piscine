package fr.school42.printer.logic;

import com.beust.jcommander.IStringConverter;
import com.diogonunes.jcolor.Attribute;

import fr.school42.printer.app.Main;

import com.beust.jcommander.ParameterException;

public class ColorConverter implements IStringConverter<Attribute> {

	@Override
	public Attribute convert(String value) {
		switch (value) {
			case "BLACK":
				return Attribute.BLACK_BACK();
			case "RED":
				return Attribute.RED_BACK();
			case "GREEN":
				return Attribute.GREEN_BACK();
			case "YELLOW":
				return Attribute.YELLOW_BACK();
			case "BLUE":
				return Attribute.BLUE_BACK();
			case "MAGENTA":
				return Attribute.MAGENTA_BACK();
			case "CYAN":
				return Attribute.CYAN_BACK();
			case "WHITE":
				return Attribute.WHITE_BACK();

			default:
				ParameterException ex = new ParameterException("Invalid color string value: " + value);
				ex.setJCommander(Main.jCommander);
				throw ex;
		}
	}
}