package com.rong.common.util;

import java.util.UUID;

public class GenerateSequenceUtil {

	public static synchronized String generateSequenceNo() {
		UUID uuid = UUID.randomUUID();
		String uuidStr = uuid.toString();
		return uuidStr.substring(0, 8) + uuidStr.substring(9, 13) + uuidStr.substring(14, 18) + uuidStr.substring(19, 23) + uuidStr.substring(24);
	}
	
}
