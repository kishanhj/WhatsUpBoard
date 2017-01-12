package com.example.validators;

import java.util.HashMap;
import com.example.Mailer.Encoding;

/**
 * Validates the URL
 * @author kishan.j
 */
public class UrlValidator{
	public static boolean validate(String empIdCode,String monthCode) {
		HashMap<String, String> codes = Encoding.getCodeList();
		if(codes.containsValue(empIdCode) && codes.containsValue(monthCode))
			return true;
		else
			return false;
	}


}
