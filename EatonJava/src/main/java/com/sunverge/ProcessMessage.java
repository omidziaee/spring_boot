package com.sunverge;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class ProcessMessage {
	final int GET_SEQUENCE_MESSAGE = 1;
	final int SET_SEQUENCE_MESSAGE = 2;
	private byte[] messageCode;
	private byte[] messageData;
	private byte[] messageSequence;
	private byte ackCode;
	private int getMessageType;
	public byte[] message;
	private byte[] nextSeqNumber;
	public byte[] getMessage() {
		return message;
	}
	public void setMessageToProcess(byte[] message) {
		this.message = message;
	}
	public int getGetMessageType() {
		return getMessageType;
	}
	public void setGetMessageType(int getMessageType) {
		this.getMessageType = getMessageType;
	}
	public byte[] getMessageCode() {
		return messageCode;
	}
	public void setMessageCode(byte[] messageCode) {
		this.messageCode = messageCode;
	}
	public byte[] getMessageData() {
		return messageData;
	}
	public void setMessageData(byte[] messageData) {
		this.messageData = messageData;
	}
	public byte[] getMessageSequence() {
		return messageSequence;
	}
	public void setMessageSequence(byte[] messageSequence) {
		this.messageSequence = messageSequence;
	}
	
	public void processGetMessage() throws UnsupportedEncodingException {
		if(getMessageType == 1) {
			processFirstMessage();
		} else if(getMessageType == 2) {
			processSecondMessage();
		} else if(getMessageType == 3) {
			processIndMeasurments();
		}
				
	}
	private void processFirstMessage() throws UnsupportedEncodingException {
		byte[] header = new byte[10];
		System.arraycopy(message, 0, header, 0, 10);
		nextSeqNumber = new byte[4];
		System.arraycopy(message, 10, nextSeqNumber, 0, 4);
		System.out.println("get this: " + ByteBuffer.wrap(nextSeqNumber).order(ByteOrder.LITTLE_ENDIAN).getInt());
		byte[] DeviceID = new byte[16];
		System.arraycopy(message, 14, DeviceID, 0, 16);
		String DeviceIDStr = new String(DeviceID, "ascii");
		System.out.println(DeviceIDStr);
	}
	public byte[] getNextSeqNumber() {
		return nextSeqNumber;
	}
	public void setNextSeqNumber(byte[] nextSeqNumber) {
		this.nextSeqNumber = nextSeqNumber;
	}
	
	private void processSecondMessage() {
		ackCode = message[10];
		
	}
	public byte getAckCode() {
		return ackCode;
	}
	private void processIndMeasurments() {
		byte[] header4 = new byte[10];
		System.arraycopy(message, 0, header4, 0, 10);
		byte[] freq_1 = new byte[4];
		System.arraycopy(message, 12, freq_1, 0, 4);
		System.out.println("freq: " + ByteBuffer.wrap(freq_1).order(ByteOrder.LITTLE_ENDIAN).getInt());
		byte[] mAp0_1 = new byte[4];
		System.arraycopy(message, 46, mAp0_1, 0, 4);
		System.out.println("mAp0: " + ByteBuffer.wrap(mAp0_1).order(ByteOrder.LITTLE_ENDIAN).getInt());
		byte[] LNmVp0_1 = new byte[4];
		System.arraycopy(message, 42, LNmVp0_1, 0, 4);
		System.out.println("LNmVp0: " + ByteBuffer.wrap(LNmVp0_1).order(ByteOrder.LITTLE_ENDIAN).getInt());
		
	}

}
