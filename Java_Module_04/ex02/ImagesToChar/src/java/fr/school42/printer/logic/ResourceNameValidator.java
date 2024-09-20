package fr.school42.printer.logic;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;

public class ResourceNameValidator implements IParameterValidator {

	@Override
	public void validate(String name, String value) throws ParameterException {
		if (value == null || value.trim().isEmpty()) {
			throw new ParameterException("Parameter " + name + " should not be empty");
		}
		if (!value.endsWith(".bmp")) {
			throw new ParameterException("Parameter " + name + " should be a BMP file");
		}
	}

}
