package com.sunverge;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class UDPListner implements Runnable{
	final static String broadIP = "192.168.10.255";
	final static int portNo = 32866;
	private static InetAddress broadCastIP;
	private DatagramSocket datagramSocket;
	private int responseLength;
	private DatagramPacket response;
	private byte[] nextSeqNumber;
	private boolean isAckKnowledged;
	ProcessMessage processMessage = new ProcessMessage();
	public boolean isAckKnowledged() {
		return isAckKnowledged;
	}
	public byte[] getNextSeqNumber() {
		return nextSeqNumber;
	}

	public void setNextSeqNumber(byte[] nextSeqNumber) {
		this.nextSeqNumber = nextSeqNumber;
	}

	

	public DatagramPacket getDatagramPacket() {
		return this.response;
	}

	public void init() throws UnknownHostException, SocketException {
		this.broadCastIP = InetAddress.getByName(broadIP);
		datagramSocket = new DatagramSocket();
		datagramSocket.setSoTimeout(6000);
		
	}
	public void closeConnect() {
		this.datagramSocket.close();
	}

	public void send(InetAddress destination, byte[] outMsg) throws IOException {
			// the datagram which is going to be sent
			DatagramPacket out = new DatagramPacket(outMsg, outMsg.length, destination, portNo);
			datagramSocket.send(out);
	}

	public void run() {
		// TODO Auto-generated method stub
		byte[] buffer = new byte[256];
		response = new DatagramPacket(buffer, buffer.length);
		int i = 0;
		// This is the separate thread and we need to run it in a loop
		while(true) {
			try {
				datagramSocket.receive(response);
				if(response.getLength() != 0) {
					i++;
					System.out.println("Counter: " + i);
					System.out.println("Message Length: " + response.getLength());
					// Check for the first message to get the sequence
					if(i == 1) {
						processMessage.setGetMessageType(1);
						processMessage.setMessageToProcess(response.getData());
						processMessage.processGetMessage();
						nextSeqNumber = processMessage.getNextSeqNumber();
					// Check for the second message which is for setting the nexeSeq number
					} else if(i == 2) {
						processMessage.setGetMessageType(2);
						processMessage.setMessageToProcess(response.getData());
						processMessage.processGetMessage();
						byte good = 0;
						System.out.println(processMessage.getAckCode());
						// This does not work
						if(Byte.compare(processMessage.getAckCode(), good) == 0) {
							isAckKnowledged = true;
						}
					} else {
						processMessage.setGetMessageType(3);
						processMessage.setMessageToProcess(response.getData());
						processMessage.processGetMessage();
					}
					
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("Something is wrong!");;
			}
		}
	}

}
