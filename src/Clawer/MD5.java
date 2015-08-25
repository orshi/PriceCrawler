package Clawer;

import java.security.MessageDigest;

public class MD5 {

	public static String GetMD5(byte[]source){
		String s=null;
		char [] hexDigit={'0', '1', '2', '3', '4', '5',
	            '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			MessageDigest mdDigest=MessageDigest.getInstance("MD5");
			mdDigest.update(source);
			
			//Get MD5 code, digested message is 128bit=16 byte
			byte []temp=mdDigest.digest();
			
			
			//Extend 16 byte to 32 byte hex
			char[] str=new char[32];
			int k=0;
			for(int i=0;i<16;i++){
				byte md5byte=temp[i];
				
				//0xf is 15 
				str[k++]=hexDigit[md5byte>>4 & 0xf];
				str[k++]=hexDigit[md5byte & 0xf];				
			}
			
			s=new String(str);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return s;
	}
}
