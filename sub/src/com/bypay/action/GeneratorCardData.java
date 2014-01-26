package com.bypay.action;

import java.math.BigInteger;

public class GeneratorCardData {
	
	public void generator(String sequence) throws Exception{
//		String cardNo = begin + trade + merId + sequence;
		
		String cardNo ="80811376"+sequence;
//		String cardNo ="80813176"+sequence;
//		String cardNo ="80817760"+sequence;
		String key = "1234567812345678";
		byte[] b = Tools.compute3DES(key.getBytes(), cardNo.getBytes());
		String bb = StringUtil.byteArrayToHexString(b);
		String bbb = String.valueOf(new BigInteger(bb,16));
		if(bbb.length()>20)
			bbb = bbb.substring(bbb.length()-20,bbb.length());
		System.out.print("cardNo:" + cardNo +"=" + bbb); 
//		System.out.print(bbb); 
		String mi = String.valueOf(Math.round(Math.random()*1000000));
		while(mi.length()!=6)
			mi = String.valueOf(Math.round(Math.random()*1000000));
		System.out.println("\t");
	}
	
	public static void main(String[] args) {
		GeneratorCardData cardData = new GeneratorCardData();
		int index = 21010083;
//		int index = 21020001;
//		int index = 21026001;
		try {
			for (int i = 0; i <=10; i++) {
//				cardData.generator("88", "05", "5301", index + "");
				cardData.generator(index + "");
				index ++ ;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
