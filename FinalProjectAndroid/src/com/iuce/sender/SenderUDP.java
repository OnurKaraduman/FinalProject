package com.iuce.sender;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.UnknownHostException;

import com.iuce.constant.Constants;

public class SenderUDP {

	private DatagramSocket socket;

	public SenderUDP(DatagramSocket socket) {
		// TODO Auto-generated constructor stub
		this.socket = socket;
	}

	public void send(String sendingData) {
		byte[] sendByte = sendingData.getBytes();
		DatagramPacket packet = null;
		try {
			packet = new DatagramPacket(sendByte, sendByte.length,
					Constants.getIP_ADDRESS_SERVER(),
					Constants.PORT_ADDRESS_SERVER);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			System.out.println("->Packet creation failed!!!" + e.toString());
			e.printStackTrace();
		}
		try {
			socket.send(packet);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("->Packet send failed!!!" + e.toString());
			e.printStackTrace();
		}
	}

}
