package com.rong.common.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.Random;

public class NumberUtil {

	public  static BigDecimal get2ScaleNumber(BigDecimal value){
		return value.setScale(2,RoundingMode.FLOOR);
	}
	
	public static String createOrderCode(){
		String date = DateTimeUtil.formatDateTime(new Date(),"yyyyMMddHHmmssSSS");
		String random = String.valueOf(new Random().nextInt(100000));
		return "xk" + date + random;
	}
	
	public static void main(String[] args) {
		System.out.println(createOrderCode());
	}
	

}
