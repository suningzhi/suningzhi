package com.bypay.action;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.RSAPublicKeySpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DESedeKeySpec;

public class Tools {
	 public static byte[] compute3DES(byte[] key, byte[] data) {
			try {
				byte[] km = new byte[24];
				System.arraycopy(key, 0, km, 0, 16);
				System.arraycopy(key, 0, km, 16, 8);
				Cipher cipher = Cipher.getInstance("DESede/ECB/NoPadding");
				DESedeKeySpec dks = new DESedeKeySpec(km);
				SecretKey k = SecretKeyFactory.getInstance("DESede")
						.generateSecret(dks);
				cipher.init(Cipher.ENCRYPT_MODE, k);
				byte[] result = cipher.doFinal(data);
				return result;
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException("" + e);
			}
		}
	 public static byte[] compute3DES2(byte[] key, byte[] data) {
			try {
//				byte[] km = new byte[24];
//				System.arraycopy(key, 0, km, 0, 16);
//				System.arraycopy(key, 0, km, 16, 8);
				Cipher cipher = Cipher.getInstance("DESede/ECB/NoPadding");
				DESedeKeySpec dks = new DESedeKeySpec(key);
				SecretKey k = SecretKeyFactory.getInstance("DESede")
						.generateSecret(dks);
				cipher.init(Cipher.ENCRYPT_MODE, k);
				byte[] result = cipher.doFinal(data);
				return result;
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException("" + e);
			}
		}
	 public static byte[] decode3DES(byte[] key,byte[] data ){
		 try {
				byte[] km = new byte[24];
				System.arraycopy(key, 0, km, 0, 16);
				System.arraycopy(key, 0, km, 16, 8);
				Cipher cipher = Cipher.getInstance("DESede/ECB/NoPadding");
				DESedeKeySpec dks = new DESedeKeySpec(km);
				SecretKey k = SecretKeyFactory.getInstance("DESede")
						.generateSecret(dks);
				cipher.init(Cipher.DECRYPT_MODE, k);
				byte[] result = cipher.doFinal(data);
				return result;
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException("" + e);
			}
	 }
	 public static byte[] decode3DES2(byte[] key,byte[] data ){
		 try {
				
				Cipher cipher = Cipher.getInstance("DESede/ECB/NoPadding");
				DESedeKeySpec dks = new DESedeKeySpec(key);
				SecretKey k = SecretKeyFactory.getInstance("DESede")
						.generateSecret(dks);
				cipher.init(Cipher.DECRYPT_MODE, k);
				byte[] result = cipher.doFinal(data);
				return result;
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException("" + e);
			}
	 }
	 //陈荣
	 public static byte[] encryptDES(byte[] strkey, byte[] strdate)
		throws Exception {
		SecureRandom sr = new SecureRandom();
		DESKeySpec dks = new DESKeySpec(strkey);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey key = keyFactory.generateSecret(dks);
		Cipher encryptCipher = Cipher.getInstance("DES/ECB/NoPadding");
		encryptCipher.init(Cipher.ENCRYPT_MODE, key, sr);
		return encryptCipher.doFinal(strdate);
	}
	//陈荣
	 public static byte[] DESDECRYPT(byte[] strkey, byte[] strdate)
		throws Exception {
		SecureRandom sr = new SecureRandom();
		DESKeySpec dks = new DESKeySpec(strkey);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey key = keyFactory.generateSecret(dks);
		Cipher encryptCipher = Cipher.getInstance("DES/ECB/NoPadding");
		encryptCipher.init(Cipher.DECRYPT_MODE, key, sr);
		return encryptCipher.doFinal(strdate);
	}
	 public static byte[] accFormat(String acc){
		byte[] account=null;
		if(acc.length()>16){
			account=new byte[24];
			System.arraycopy(acc.getBytes(), 0, account, 0, acc.getBytes().length);
			for(int i =acc.getBytes().length+1;i<account.length;i++)
				account[i]=0x00;
		}else if(acc.length()<20){
			account=new byte[16];
			System.arraycopy(acc.getBytes(), 0, account, 0, 16);
		}else return null;
		printbytes("acc",account);
		return account;
	 }
	public static byte[] currencyFormat(){
		String cur="156";
		byte[] currency=new byte[8];
		System.arraycopy(cur.getBytes(), 0, currency, 0, cur.getBytes().length);
		for(int i =cur.getBytes().length+1;i<currency.length;i++)
			currency[i]=0x00;
		return currency;
	}
	public static byte[] amtFormat(String amt){
		 Double amtDouble=Double.parseDouble(amt)*100;
		 String amountTmp=Integer.toString(amtDouble.intValue());
		 String a=amountTmp;
		 if(amountTmp.length()>12)
			 return null;
		 for(int i=0;i<12-amountTmp.length();i++)
			 a="0"+a;
//		 System.out.println(a);
		 byte[] amtBytes=new byte[16];
		 System.arraycopy(a.getBytes(), 0, amtBytes, 0, a.getBytes().length);
		 for(int i =a.getBytes().length+1;i<amtBytes.length;i++)
			 amtBytes[i]=0x00;
		 return amtBytes;
	}
	public static byte[] otaDataFormat(byte[] otaData){
		byte[] tmp=new byte[((otaData.length+1)/8+1)*8];
		System.arraycopy(otaData,0,tmp,0,otaData.length);
		tmp[otaData.length]=(byte)0x80;
		for(int i =otaData.length+1;i<tmp.length;i++)
			tmp[i]=0x00;
		return tmp;
		
	}
	
	 public static byte[] pinFormat(String pin){
//		 byte[] pinFormat=new byte[8];
//		 pinFormat[0]=0x06;
//		 byte[] pinTmp=pinFormat=StringUtil.hexStringToByteArray(pin);
//		 System.arraycopy(pinTmp, 0, pinFormat, 1, pinTmp.length);
//		 for(int i =pinTmp.length+2;i<pinFormat.length;i++)
//			 pinFormat[i]=(byte)0xFF;
//		 printbytes("pin",pinFormat);
		 pin="06"+pin+"FFFFFFFF";
		 printbytes("pin",StringUtil.hexStringToByteArray(pin));
		 return StringUtil.hexStringToByteArray(pin);
	 }
	 public static String processTrack2Data(String track2Data) {
	        int len = Integer.parseInt(track2Data.substring(0, 2));
	        track2Data = track2Data.substring(0, len + 2);
	        if (len % 2 != 0) {
	            track2Data = track2Data + "F";
	        }
	        return track2Data;
	    }
	 public static String printbytes(String tip, byte[] b) {
			String ret = "";
			String str;
			
//			System.out.println(tip);
			for (int i = 0; i < b.length; i++) {
				str = Integer.toHexString((int) (b[i] & 0xff));
				if (str.length() == 1)
					str = "0" + str;
//				System.out.print(str+ " ");
				ret = ret + str + " ";
			}
//			System.out.println();
			return ret;
		}
	 public static PublicKey getPublicKey(String modulus, String exponent) {
	        try {
	            BigInteger b1 = new BigInteger(modulus);
	            BigInteger b2 = new BigInteger(exponent);
	            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
	            RSAPublicKeySpec keySpec = new RSAPublicKeySpec(b1, b2);
	            return keyFactory.generatePublic(keySpec);
	        } catch (Exception e) {
	            e.printStackTrace();
	            return null;
	        }
	    }

	    public static byte[] encripyRSA(byte[] data, PublicKey publickey) {
	        try {
	            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
	            cipher.init(Cipher.ENCRYPT_MODE, publickey);
	            return cipher.doFinal(data);
	        } catch (Exception ex) {
	            ex.printStackTrace();
	            return null;
	        }
	    }
	    public static byte[] byteArrayConcat(byte[] arr1, byte[] arr2) {
	        byte[] result = new byte[arr1.length + arr2.length];
	        System.arraycopy(arr1, 0, result, 0, arr1.length);
	        System.arraycopy(arr2, 0, result, arr1.length, arr2.length);
	        return result;
	    }
	    public static void main(String[] args)throws Exception{
//	    	String data = "6226660603549466 AEF48A3BB1E486FB 3796F02690068EA30C60F8E5B36857413293618F 201208270900000001 20120827090000 100000";
//	    	String submitTime = "20120827090000";
//	    	String macKey = "3BBE9D988F8E2C08A97A95CB7216292D";
//	    	byte[] desKey = Tools.compute3DES(StringUtil.hexStringToByteArray(macKey),StringUtil.hexStringToByteArray(submitTime+"80"));
//			String mac=Tools.mac(StringUtil.byteArrayToHexString(desKey),data).substring(0, 8);
//			try {
////				String str = mac("", data);
//				System.out.println(mac);
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
	    	
//	    	
//	    	PurchaseReq purchaseReq = new PurchaseReq();
//	    	purchaseReq.setAccountNumber("6226230024362426");
//	    	purchaseReq.setPin("f75bf83cff5bb303");
//	    	purchaseReq.setTrack2Data("3451D37D94DDF5E7B99AC60A631ABEA78577");
//	    	purchaseReq.setMerchantOrderId("201208270900000001");
//	    	purchaseReq.setMerchantOrderTime("20120827090000");
//	    	purchaseReq.setTransType("100000");
//	    	String orgMacStr = purchaseReq.getAccountNumber() + " " + purchaseReq.getPin() + " " + 
//			purchaseReq.getTrack2Data() + " " + purchaseReq.getMerchantOrderId() + " " + 
//			purchaseReq.getMerchantOrderTime() + " " + purchaseReq.getTransType();
//	    	System.out.println(orgMacStr);
//			String macKey = "0FED4E3AC20E7E9E757D7ECC6F20E9DE";//swipeDeviceInfo.getMacKey();
//			byte[] desKey = Tools.compute3DES(StringUtil.hexStringToByteArray(macKey),
//					StringUtil.hexStringToByteArray(purchaseReq.getMerchantOrderTime()+"80"));
//			String mac=Tools.mac(StringUtil.byteArrayToHexString(desKey),orgMacStr).substring(0, 8);
//			System.out.println(mac);
		}
	    public static byte[] getPinEncode(String pinKey,String track2,String pinSrc){
//			  pinKey="5df2b610678a31a1ad927f20dc61f1cb";
//			  track2="4096688386956576=13071010000010600000";
//			  pinSrc="821629";
			byte[] panTmp=StringUtil.hexStringToByteArray(track2.substring(track2.indexOf("=")-13, track2.indexOf("=")-1));
			byte[] pinTmp=StringUtil.hexStringToByteArray(pinSrc);
			byte[] pan= new byte[8];
			pan[0]=0x00;
			pan[1]=0x00;
			System.arraycopy(panTmp, 0, pan, 2, panTmp.length);
			byte[] pin=new byte[8];
			pin[0]=0x06;
			System.arraycopy(pinTmp, 0, pin, 1, pinTmp.length);
			pin[4]=(byte)0xFF;pin[5]=(byte)0xFF;pin[6]=(byte)0xFF;pin[7]=(byte)0xFF;
			byte[] tmp=new byte[8];
			for(int j=0;j<8;j++){
				tmp[j]=(byte) (pin[j]^pan[j]);
			}
			byte[] pinEncode=Tools.compute3DES(StringUtil.hexStringToByteArray(pinKey), tmp);
			return pinEncode;
		}
			public static String getPinKey(String primaryKey,String pinEncode,String pinCheckCode){
			//	  primaryKey="FD81C6BC811CFD6A8A54900F01474309";
			//	  pinEncode="C3AA8C1F7D90188A795538690A9FFF71";
			//	  pinCheckCode="A75C3E79";
				String tmpStr1="0000000000000000";
				byte[] pKey=StringUtil.hexStringToByteArray(primaryKey);
				byte[] pinData=StringUtil.hexStringToByteArray(pinEncode);
				byte []pinKey=Tools.decode3DES(pKey, pinData);
				Tools.printbytes("pinKey", pinKey);
				byte[] pincheck=Tools.compute3DES(pinKey, StringUtil.hexStringToByteArray(tmpStr1));
				Tools.printbytes("pincheck", pincheck);
//				System.out.println(StringUtil.byteArrayToHexString(pincheck));
				if(StringUtil.byteArrayToHexString(pincheck).substring(0, 8).equalsIgnoreCase(pinCheckCode))
					return StringUtil.byteArrayToHexString(pinKey);
				else return null;
			}
			public static String getCsnCode(String csn){
				byte[]  CardCsn=csn.getBytes();
				int outputSize = 6;
				int partNum = 0, tmpVal = 0;
				int CardCsnLen=CardCsn.length;
				String ret="";
				// 中间变量，用来保存每组字符相加的和
				int i,j;
				// 输出结果字串 
				partNum = CardCsnLen / outputSize;
				byte [] a=new byte[6];
				for (i = 0; i < outputSize; i++) {
					tmpVal =0;
					// 每组内的字符取10进制ASCII码相加
					for (j = 0; j < partNum; j++) {
					tmpVal += (int)CardCsn[i * partNum + j];
					}

					ret=ret+tmpVal% 10;
				}
				return ret;
			}
			
			public static String mac(String decKey,String orgData) throws Exception{
				byte[] dataByte=new byte[orgData.getBytes().length+1];
				System.arraycopy(orgData.getBytes(), 0, dataByte, 0, orgData.getBytes().length);
				dataByte[dataByte.length-1]=(byte)0x80;
				Tools.printbytes("",dataByte);
				 
				byte[] addAscData=null;
				
				if(dataByte.length%8!=0){
					addAscData=new byte[(dataByte.length/8+2)*8];
					System.arraycopy(dataByte, 0, addAscData, 0, dataByte.length);
//					addAscData[dataByte.length]=(byte)0x80;
					for(int i =dataByte.length+1;i<addAscData.length;i++)
						addAscData[i]=0x00;
					
				}else{
					addAscData=new byte[dataByte.length+8];
					System.arraycopy(dataByte, 0, addAscData, 0, dataByte.length);
				}
				byte[] add={(byte)0x80,0x00,0x00,0x00,0x00,0x00,0x00,0x00};
				System.arraycopy(add, 0, addAscData, addAscData.length-8, 8);
				Tools.printbytes("",addAscData);

				byte[] initKey={0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00};
				String key=decKey;
				for(int i=0;i<addAscData.length/8;i++){
					byte[]tmp=new byte[8];
					System.arraycopy(addAscData, i*8, tmp, 0, tmp.length);
					Tools.printbytes("",tmp);	
					
					
					for(int j=0;j<8;j++){
						initKey[j]^=tmp[j];
					}
					 DesUtilAlone des = new DesUtilAlone(key);
					 initKey=des.encrypt(initKey);
				}
			
//				System.out.println("------------------");
				Tools.printbytes("",initKey);	
//				System.out.println(StringUtil.byteArrayToHexString(initKey));
//				System.out.println("------------------");
				return StringUtil.byteArrayToHexString(initKey);
			}
			
			public static byte[] getPosMac(String macStr ,byte[] tmp) throws Exception{
				byte[] src=null;;
				if(tmp.length%8!=0){
					src=new byte[(tmp.length/8+1)*8];
				}else{
					src=new byte[tmp.length];
				}
				
				System.arraycopy(tmp, 0, src, 0, tmp.length);
				if(tmp.length!=src.length){
					int i = tmp.length;
					src[i++] = (byte)0x80;
					if(i % 8 !=0){
						for(;i<src.length;i++){
								src[i]=0x00;
						}
					}
				}
				byte[] xorInit=new byte[8];
				System.arraycopy(src, 0, xorInit, 0, 8);
				for(int i=1;i<src.length/8;i++){
					byte[] xorTmp=new byte[8];
					System.arraycopy(src, i*8, xorTmp, 0, xorTmp.length);
					for(int j=0;j<8;j++){
						xorInit[j]^=xorTmp[j];
					}
				}
				String xorHexStrTmp=StringUtil.byteArrayToHexString(xorInit);
				
				byte[] xorHex=xorHexStrTmp.getBytes();
				
				byte [] desSrc=new byte[8];
				System.arraycopy(xorHex, 0, desSrc, 0, 8);
				byte [] desSrc2=new byte[8];
				System.arraycopy(xorHex, 8, desSrc2, 0, 8);
				
				byte [] desEnTmp=compute3DES( StringUtil.hexStringToByteArray(macStr) , desSrc);
				
				for(int j=0;j<8;j++){
					desEnTmp[j]^=desSrc2[j];
				}
				byte [] desEn=compute3DES(StringUtil.hexStringToByteArray(macStr),desEnTmp);
				String aa=StringUtil.byteArrayToHexString(desEn);
				byte[] bb=aa.getBytes();	
				byte[] mac=new byte[8];
				System.arraycopy(bb, 0, mac, 0, 8);
				return mac;
			 
		 }
			
}
