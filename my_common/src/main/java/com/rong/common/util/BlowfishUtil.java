package com.rong.common.util;

public class BlowfishUtil {
	public static String[] BASE = null;
	static {
		BASE = new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "0",
				"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l",
				"m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x",
				"y", "z", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
				"K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
				"W", "X", "Y", "Z" };
	};

	public static String getKey() {
		StringBuilder key=new StringBuilder();
		for (int i = 0; i < 4; i++) {
			int r = (int) (Math.random() * 61);
			key.append(BASE[r]);
		}
		return key.toString();
	}
}
