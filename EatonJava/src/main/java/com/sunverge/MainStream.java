package com.sunverge;

import java.io.IOException;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.SecureRandom;
import java.util.concurrent.TimeUnit;

public class MainStream {

	public static void main(String[] args) throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		// =================This is a new connection for the multithreded one===============================
		EMCBUdpUtils emcb = new EMCBUdpUtils();
		UDPListner listener = new UDPListner();
		listener.init();
		Thread t1 = new Thread(listener);
		t1.start();
		byte[] messageCodeTFirst = { (byte) 0x00, (byte) 0x00 };
		byte[] nonceTFirst = { (byte) 0, (byte) 0, (byte) 0, (byte) 0 };
		byte[] bodyTFirst = new byte[4];
		byte[] outMsgT = emcb.createEMCBudpBuffer(nonceTFirst, messageCodeTFirst, bodyTFirst,
				"1WR0D5QSvj1/6exV4tvN/I9Jt0qaC47vPH793xYTjZM=");
		InetAddress toAddressT = InetAddress.getByName("192.168.10.255");
		listener.send(toAddressT, outMsgT);
		Thread.sleep(2000);
		TimeUnit.SECONDS.sleep(5);
		
		//===send second message to set the sequence number and get the acknowledgment
		byte[] messageCodeTSecond = {(byte)0x80, (byte)0x00};
		Integer intOfNonce = (ByteBuffer.wrap(listener.getNextSeqNumber()).order(ByteOrder.LITTLE_ENDIAN).getInt() + 1);
		ByteBuffer newNonce = ByteBuffer.allocate(4);
		newNonce.putInt(intOfNonce);
		byte[] swaped = new byte[4];
		byte[] newNonceArray = newNonce.array();
		// This is because ByteBuffer.putInt put integer in big-endian by default so we need to reverse it!
		for(int j = 3; j >= 0; j--) {
			swaped[3 - j] = newNonceArray[j];
		}
		byte[] bodyTSecond = new byte[4];
		SecureRandom random = new SecureRandom();
		random.nextBytes(bodyTSecond);
		outMsgT = emcb.createEMCBudpBuffer(swaped, messageCodeTSecond, bodyTSecond,
				"1WR0D5QSvj1/6exV4tvN/I9Jt0qaC47vPH793xYTjZM=");
		listener.send(toAddressT, outMsgT);
		if(listener.isAckKnowledged()) {
			System.out.println("It is ok!");
		}
		
		//===send third message to get the stream=======================================
		byte[] messageCode_3 = {(byte)0x00, (byte)0xff};
		byte[] body_3 = new byte[0];
		int i = 1;
		while(true) {
			Integer intOfNonce_1 = (ByteBuffer.wrap(bodyTSecond).order(ByteOrder.LITTLE_ENDIAN).getInt() + i);;
			ByteBuffer newNonce_1 = ByteBuffer.allocate(4);
			newNonce_1.putInt(intOfNonce_1);
			byte[] swaped_1 = new byte[4];
			byte[] newNonceArray_1 = newNonce_1.array();
			// This is because ByteBuffer.putInt put integer in big-endian by default so we need to reverse it!
			for(int j = 3; j >= 0; j--) {
				swaped_1[3 - j] = newNonceArray_1[j];
			}
			// It is possible to send the UNICAST message but the key should also be the unicast key the following is an unicast example!
			outMsgT = emcb.createEMCBudpBuffer(swaped_1, messageCode_3, body_3, "ApVuESBCW2c3zjp1m3K/AmfUXYMMIAEmthWuvOYhI54=");
			toAddressT = InetAddress.getByName("192.168.10.239");
			listener.send(toAddressT, outMsgT);
			i ++;
			TimeUnit.SECONDS.sleep(2);
		}
		
		

	}

}
