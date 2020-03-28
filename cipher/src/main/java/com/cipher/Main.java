package com.cipher;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;

public class Main {

	public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchPaddingException {
		// TODO Auto-generated method stub
		Cipher cipher = Cipher.getInstance("AES");
		byte[] nonce = new byte[4];
		SecureRandom random = new SecureRandom();
		random.nextBytes(nonce);
//		for (byte b: nonce) {
//			System.out.println(b);
//		}
		
		
		

	}

}
