package com.sunverge;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Connection {
	final static String broadIP = "192.168.10.255";
	final static int portNo = 32866;
	private static InetAddress broadCastIP;
	private DatagramSocket datagramSocket;
	private int responseLength;
	private DatagramPacket response;

	public DatagramPacket getDatagramPacket() {
		return this.response;
	}

	public void init() throws UnknownHostException, SocketException {
		this.broadCastIP = InetAddress.getByName(broadIP);
		datagramSocket = new DatagramSocket();
		datagramSocket.setSoTimeout(4000);
	}
	public void closeConnect() {
		this.datagramSocket.close();
	}

	public void send(InetAddress destination, byte[] outMsg, int outMsgLen) throws IOException {
		boolean isConnected = false;
		while (!isConnected) {
			// the datagram which is going to be sent
			byte[] buffer = new byte[outMsgLen];
			this.response = new DatagramPacket(buffer, buffer.length);
			DatagramPacket out = new DatagramPacket(outMsg, outMsg.length, destination, portNo);
			datagramSocket.send(out);
			datagramSocket.receive(response);

			if (this.response.getLength() != 0) {
				isConnected = true;
			}

		}
	}

}
