package com.sunverge;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class EMCBUdpUtils {
	public byte[] createEMCBudpBuffer(byte[] sequenceNumber, byte[] messageCode, byte[] body, String sigingKey) throws UnsupportedEncodingException{
//		header[0] = 0x45;
//		header[1] = 0x54;
//		header[2] = 0x4E;
//		header[3] = 0x4D;
		// each char is 2 bytes
		byte[] signature = null;
		String start = "ETNM";
		ByteBuffer header = ByteBuffer.allocate(10);
		// each integer is 2 bytes
		for(char ch: start.toCharArray()) {
			header.put((byte)ch);
		}
		for(byte b: sequenceNumber) {
			header.put(b);
		}
		//Little endian
		header.put(8, (byte)(messageCode[1] & 0xff));
		header.put(9, (byte)(messageCode[0] & 0xff));
		
		
		
		byte[] data = new byte[body.length + header.array().length];
		System.arraycopy(header.array(), 0, data, 0, header.array().length);
		System.arraycopy(body, 0, data, header.array().length, body.length);
//		System.out.println("----this is the data-----");
//		int i = 0;
//		for(byte b: data) {
//			System.out.println(i + ": " + Integer.toHexString(b & 0xff));
//			i++;
//		}
//		System.out.println("-------------------------");
		
		
		// Create the signature
		try {
			Mac sha256HMAC = Mac.getInstance("HmacSHA256");
			String msg = sigingKey;
			// The UDPKey is a 32 byte hex string we need to decode it
			byte[] decoded = Base64.getDecoder().decode(msg.getBytes("utf8"));
			SecretKeySpec secretKey = new SecretKeySpec(decoded, "HmacSHA256");
			sha256HMAC.init(secretKey);
			signature = sha256HMAC.doFinal(data);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		i = 0;
//		System.out.println("----this is the signature-----");
//		for(byte b: signature) {
//			System.out.println(i + ": " + Integer.toHexString(b & 0xff));
//			i ++;
//		}
		byte[] res = new byte[data.length + signature.length];
		System.arraycopy(data, 0, res, 0, data.length);
		System.arraycopy(signature, 0, res, data.length, signature.length);
		int i = 0;
		System.out.println("----this is the message-----");
		i = 0;
		for(byte b: res) {
			System.out.print(i + ":" + Integer.toHexString(b & 0xff) + " ");
			i ++;
		}
		System.out.println();
		return res;
	}
	

}
