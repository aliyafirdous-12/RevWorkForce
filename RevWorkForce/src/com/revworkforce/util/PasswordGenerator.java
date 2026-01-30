package com.revworkforce.util;

import java.util.Random;

public class PasswordGenerator {
	
	public static String generateStrongPassword(int length, boolean upper, boolean lower, boolean numbers, boolean symbols) {

		String upperChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String lowerChars = "abcdefghijklmnopqrstuvwxyz";
		String numberChars = "0123456789";
		String symbolChars = "!@#$%^&*()-_=+[]{};:,.<>?";

		String allChars = "";

		if (upper)
			allChars += upperChars;
		if (lower)
			allChars += lowerChars;
		if (numbers)
			allChars += numberChars;
		if (symbols)
			allChars += symbolChars;

		if (allChars.isEmpty())
			return "";

		Random random = new Random();
		StringBuilder password = new StringBuilder();

		for (int i = 0; i < length; i++) {
			int index = random.nextInt(allChars.length());
			password.append(allChars.charAt(index));
		}
		return password.toString();
	}

}