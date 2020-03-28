package com.sunverge;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.SecureRandom;
import java.util.concurrent.TimeUnit;

public class Main {

	public static void main(String[] args) throws IOException, InterruptedException {
		EMCBUdpUtils emcb = new EMCBUdpUtils();
		// First message
		byte[] messageCode = {(byte)0x00, (byte)0x00};
		byte[] nonce = {(byte)0, (byte)0, (byte)0, (byte)0};
		byte[] body = new byte[4];
		byte[] outMsg = emcb.createEMCBudpBuffer(nonce, messageCode, body, "1WR0D5QSvj1/6exV4tvN/I9Jt0qaC47vPH793xYTjZM=");
		int i = 0;
		InetAddress toAddress = InetAddress.getByName("192.168.10.255");
		Connection connect_1 = new Connection();
		connect_1.init();
		connect_1.send(toAddress, outMsg, 70);
		// response is 10-28-32 bytes the first 10 is the same as the header that we sent the next 28 is 
		// 4 byte sequence# 2 bytes DeviceID 4 bytes protocol version which is one and 4 byte the nonce
		// the sequence number that master sends.
		DatagramPacket packet = connect_1.getDatagramPacket();
		byte[] res = packet.getData();
		System.out.println("----this is the response-----");
		i = 0;
		InetAddress fromAddress = packet.getAddress();
		for(byte b: res) {
			System.out.print(i + ":" + Integer.toHexString(b & 0xff) + " ");
			i++;
		}
		System.out.println();
		// After we receive the first packet we can also check from which address we get it.
		//Todo: I don't know how would it work for multiple devices which means multiple clients respond
		System.out.println(fromAddress.getHostAddress());
		byte[] header = new byte[10];
		System.arraycopy(packet.getData(), 0, header, 0, 10);
		byte[] nextSequence = new byte[4];
		System.arraycopy(packet.getData(), 10, nextSequence, 0, 4);
		System.out.println("get this: " + ByteBuffer.wrap(nextSequence).order(ByteOrder.LITTLE_ENDIAN).getInt());
		byte[] DeviceID = new byte[16];
		System.arraycopy(packet.getData(), 14, DeviceID, 0, 16);
		String DeviceIDStr = new String(DeviceID, "ascii");
		//System.out.println(DeviceIDStr);
		//-----------------------------------------------------------
		// Second message
		byte[] body2 = new byte[4];
		SecureRandom random = new SecureRandom();
		random.nextBytes(body2);
		byte[] messageCode2 = {(byte)0x80, (byte)0x00};
		// As it mentioned in the document the number we send can be between -100 to 100 of what we get
		Integer intOfNonce = (ByteBuffer.wrap(nextSequence).order(ByteOrder.LITTLE_ENDIAN).getInt() + 99);
		ByteBuffer newNonce = ByteBuffer.allocate(4);
		newNonce.putInt(intOfNonce);
		byte[] swaped = new byte[4];
		byte[] newNonceArray = newNonce.array();
		// This is because ByteBuffer.putInt put integer in big-endian by default so we need to reverse it!
		for(int j = 3; j >= 0; j--) {
			swaped[3 - j] = newNonceArray[j];
		}
		System.out.println("swaped: " + ByteBuffer.wrap(swaped).order(ByteOrder.LITTLE_ENDIAN).getInt());
		outMsg = emcb.createEMCBudpBuffer(swaped, messageCode2, body2, "1WR0D5QSvj1/6exV4tvN/I9Jt0qaC47vPH793xYTjZM=");
		i = 0;
		TimeUnit.SECONDS.sleep(1);
		InetAddress toAddress2 = InetAddress.getByName("192.168.10.255");
		connect_1.init();
		// the length can not be more than ...???
		connect_1.send(toAddress2, outMsg, 43);
		
		DatagramPacket packet2 = connect_1.getDatagramPacket();
		res = packet2.getData();
		System.out.println("----this is the response-----");
		i = 0;
		for(byte b: res) {
			System.out.print(i + ":" + Integer.toHexString(b & 0xff) + " ");
			i++;
		}
		System.out.println();
		byte[] header2 = new byte[10];
		System.arraycopy(packet2.getData(), 0, header2, 0, 10);
		byte[] nextSequence2 = new byte[4];
		System.arraycopy(packet2.getData(), 10, nextSequence2, 0, 4);
		System.out.println(ByteBuffer.wrap(nextSequence2).order(ByteOrder.LITTLE_ENDIAN).getInt());
		byte[] DeviceID2 = new byte[16];
		System.arraycopy(packet2.getData(), 14, DeviceID2, 0, 16);
		String DeviceIDStr2 = new String(DeviceID2, "ascii");
		//System.out.println(DeviceIDStr2);
		
		
		//---------------------------------------------------------------------
		// Third message toggle the breaker
		byte[] messageCode_3 = {(byte)0x00, (byte)0xff};
		byte[] body_3 = new byte[0];
		Integer intOfNonce_3 = (ByteBuffer.wrap(nextSequence2).order(ByteOrder.LITTLE_ENDIAN).getInt() + 1);
		ByteBuffer newNonce_3 = ByteBuffer.allocate(4);
		newNonce_3.putInt(intOfNonce_3);
		byte[] swaped_3 = new byte[4];
		byte[] newNonceArray_3 = newNonce.array();
		// This is because ByteBuffer.putInt put integer in big-endian by default so we need to reverse it!
		for(int j = 3; j >= 0; j--) {
			swaped_3[3 - j] = newNonceArray_3[j];
		}
		System.out.println("swaped3: " + ByteBuffer.wrap(swaped_3).order(ByteOrder.LITTLE_ENDIAN).getInt());
		outMsg = emcb.createEMCBudpBuffer(body2, messageCode_3, body_3, "1WR0D5QSvj1/6exV4tvN/I9Jt0qaC47vPH793xYTjZM=");
		connect_1.init();
		// the length can not be more than ...???
		InetAddress toAddress_3 = InetAddress.getByName("192.168.10.255");
		TimeUnit.SECONDS.sleep(1);
		connect_1.send(toAddress_3, outMsg, 268);
		
		
		DatagramPacket packet3 = connect_1.getDatagramPacket();
		res = packet3.getData();
		System.out.println("----this is the response-----");
		i = 0;
		for(byte b: res) {
			System.out.print(i + ":" + Integer.toHexString(b & 0xff) + " ");
			i++;
		}
		System.out.println();
		byte[] header3 = new byte[10];
		System.arraycopy(packet3.getData(), 0, header3, 0, 10);
		byte[] freq = new byte[4];
		System.arraycopy(packet3.getData(), 12, freq, 0, 4);
		System.out.println("freq: " + ByteBuffer.wrap(freq).order(ByteOrder.LITTLE_ENDIAN).getInt());
		byte[] mAp0 = new byte[4];
		System.arraycopy(packet3.getData(), 46, mAp0, 0, 4);
		System.out.println("mAp0: " + ByteBuffer.wrap(mAp0).order(ByteOrder.LITTLE_ENDIAN).getInt());
		byte[] LNmVp0 = new byte[4];
		System.arraycopy(packet3.getData(), 42, LNmVp0, 0, 4);
		System.out.println("LNmVp0: " + ByteBuffer.wrap(LNmVp0).order(ByteOrder.LITTLE_ENDIAN).getInt());
		
		TimeUnit.SECONDS.sleep(3);
		// This is the second measurement so it should be at least plus one ! Note negative numbers does not work!!
		Integer intOfNonce_1 = (ByteBuffer.wrap(body2).order(ByteOrder.LITTLE_ENDIAN).getInt() + 2);
		ByteBuffer newNonce_1 = ByteBuffer.allocate(4);
		newNonce_1.putInt(intOfNonce_1);
		byte[] swaped_1 = new byte[4];
		byte[] newNonceArray_1 = newNonce_1.array();
		// This is because ByteBuffer.putInt put integer in big-endian by default so we need to reverse it!
		for(int j = 3; j >= 0; j--) {
			swaped_1[3 - j] = newNonceArray_1[j];
		}
		outMsg = emcb.createEMCBudpBuffer(swaped_1, messageCode_3, body_3, "1WR0D5QSvj1/6exV4tvN/I9Jt0qaC47vPH793xYTjZM=");
		connect_1.init();
		connect_1.send(toAddress_3, outMsg, 268);
		connect_1.closeConnect();
		
		DatagramPacket packet4 = connect_1.getDatagramPacket();
		res = packet4.getData();
		System.out.println("----this is the response-----");
		i = 0;
		for(byte b: res) {
			System.out.print(i + ":" + Integer.toHexString(b & 0xff) + " ");
			i++;
		}
		System.out.println();
		byte[] header4 = new byte[10];
		System.arraycopy(packet4.getData(), 0, header4, 0, 10);
		byte[] freq_1 = new byte[4];
		System.arraycopy(packet4.getData(), 12, freq_1, 0, 4);
		System.out.println("freq: " + ByteBuffer.wrap(freq_1).order(ByteOrder.LITTLE_ENDIAN).getInt());
		byte[] mAp0_1 = new byte[4];
		System.arraycopy(packet4.getData(), 46, mAp0_1, 0, 4);
		System.out.println("mAp0: " + ByteBuffer.wrap(mAp0_1).order(ByteOrder.LITTLE_ENDIAN).getInt());
		byte[] LNmVp0_1 = new byte[4];
		System.arraycopy(packet4.getData(), 42, LNmVp0_1, 0, 4);
		System.out.println("LNmVp0: " + ByteBuffer.wrap(LNmVp0_1).order(ByteOrder.LITTLE_ENDIAN).getInt());
		connect_1.closeConnect();
		
		
	}
}
