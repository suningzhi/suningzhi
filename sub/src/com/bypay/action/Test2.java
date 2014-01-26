package com.bypay.action;

import java.math.BigDecimal;

public class Test2 {
	public static void main(String[] args) {
        BigDecimal b = new BigDecimal();
        //double f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        double f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		/*String ss="2.0";
		Double k=Double.parseDouble(ss);*/
		System.out.println(f1);
	}
}
