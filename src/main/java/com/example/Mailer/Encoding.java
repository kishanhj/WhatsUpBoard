package com.example.Mailer;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map.Entry;

import com.example.DAO.LinkCodesDAO;
import com.example.constants.StringConstants;

public class Encoding {

	/**
	 * HashMap that contains employee Id and corresponding code
	 */
	private static HashMap<String, String> codeList = new HashMap<>();



	/**
	 * Fetches the latest copy of codeList
	 */
	private static void updateCodeList() {
		codeList = LinkCodesDAO.getCodes();
		return;
	}

	/**
	 * Retrieves the ID for the given code
	 * @param code A 130 character random string
	 * @return An employeeId or Month
	 */
	public static String getCode(String code) {
		updateCodeList();
		return codeList.get(code);

	}

	/**
	 * Generates codes for employeeId  and the  month
	 * @param empId
	 * @param month
	 */
	public static void generatecodes(String empId, String month) {
		String codedEmpId;
		String codedMonth = randomCodeGenerator(130);
		updateCodeList();
		if (codeList == null || !codeList.containsKey(month))
			LinkCodesDAO.addCode(month, codedMonth);
		codedEmpId = randomCodeGenerator(130);
		if (codeList == null || !codeList.containsKey(empId))
			LinkCodesDAO.addCode(empId, codedEmpId);

	}

	/**
	 * Resets the codeList
	 */
	public static void deletecodes() {
		codeList = null;

	}

	/**
	 * Gets the code for given EmployeeId or Month
	 * @param empIdCode
	 * @return
	 */
	public static String getCodeValue(String empIdCode) {
		updateCodeList();
		for (Entry<String, String> entry : codeList.entrySet()) {
			if (entry.getValue().equals(empIdCode)) {
				return entry.getKey();
			}
		}
		return null;
	}

	/**
	 * Generates a random alphanumeric String of specified length
	 * @param length length of String to be generated
	 * @return
	 */
	public static String randomCodeGenerator(int length) {
		final String AB = StringConstants.RANDOM_CODE_STRING;
		final SecureRandom rnd = new SecureRandom();
		StringBuilder stringbuilder = new StringBuilder(length);
		for (int i = 0; i < length; i++)
			stringbuilder.append(AB.charAt(rnd.nextInt(AB.length())));
		return stringbuilder.toString();

	}

	/**
	 * Returns the codeList
	 * @return codeList
	 */
	public static HashMap<String, String> getCodeList() {
		updateCodeList();
		return codeList;
	}

	/**
	 * Checks whether the codeList id empty
	 * @return
	 */
	public static boolean isEmpty() {
		updateCodeList();
		if (codeList.isEmpty())
			return false;
		else
			return true;
	}

}
