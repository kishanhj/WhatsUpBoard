package com.example.Mailer;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.example.DAO.LinkCodesDAO;
import com.example.constants.StringConstants;

public class Encoding {

	private static HashMap<String, String> codeList = new HashMap<>();

	public static void generatecodes(List<String> empIds, String month) {
		String codedEmpId;
		String codedMonth = randomCodeGenerator(130);
		updateCodeList();
		if (codeList == null || !codeList.containsKey(month))
			LinkCodesDAO.addCode(month, codedMonth);
		for (String empid : empIds) {
			codedEmpId = randomCodeGenerator(130);
			if (codeList == null || !codeList.containsKey(empid))
				LinkCodesDAO.addCode(empid, codedEmpId);
		}

	}

	private static void updateCodeList() {
		codeList = LinkCodesDAO.getCodes();
		return;
	}

	public static String getCode(String code) {
		updateCodeList();
		return codeList.get(code);

	}

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

	public static void deletecodes() {
		codeList = null;

	}

	public static String getCodeValue(String empIdCode) {
		updateCodeList();
		for (Entry<String, String> entry : codeList.entrySet()) {
			if (entry.getValue().equals(empIdCode)) {
				return entry.getKey();
			}
		}
		return null;
	}

	private static String randomCodeGenerator(int length) {
		final String AB = StringConstants.RANDOM_CODE_STRING;
		final SecureRandom rnd = new SecureRandom();
		StringBuilder sb = new StringBuilder(length);
		for (int i = 0; i < length; i++)
			sb.append(AB.charAt(rnd.nextInt(AB.length())));
		return sb.toString();

	}

	public static HashMap<String, String> getCodeList() {
		updateCodeList();
		return codeList;
	}

	public static boolean isEmpty() {
		updateCodeList();
		if (codeList.isEmpty())
			return false;
		else
			return true;
	}
}
