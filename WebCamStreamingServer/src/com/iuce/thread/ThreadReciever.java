package com.iuce.thread;



import java.awt.List;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;

import com.iuce.constant.Constants;
import com.iuce.model.Client;

/*! 
 *  \brief     ThreadReciever
 *  \details   This class for accept client and recieving data
 *  \author    Onur Karaduman
 *  \date      01.03.2015
 */

public class ThreadReciever extends Thread {

	
	/*Socket defined*/
	private DatagramSocket socket;

	private ArrayList<Client> clientList;
	private int clientID = 0;

	public ThreadReciever(DatagramSocket socket) {
		// TODO Auto-generated constructor stub
		this.socket = socket;
		clientList = new ArrayList<Client>();
		this.start();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();

		while (true) {
			byte[] byteCommand = new byte[1024];
			DatagramPacket packet = new DatagramPacket(byteCommand,
					byteCommand.length);
			try {
				socket.receive(packet);
				System.out.println("client baglandi.");
				/* client vertitabanýmýza kayýtlý mý sorgusu gibi dusunebiliriz*/
				// eger degilse yeni bir client olustururuz
				if (!controlClient(packet.getAddress())) {
					Client client = new Client();
					client.setClientID(clientID);
					client.setClientIPAddress(packet.getAddress());
					client.setClientPort(packet.getPort());
					if (clientList.size() == 0) {
						client.setAuthority(Constants.AUTHORITY_ADMIN);
					} else
						client.setAuthority(Constants.AUTHORITY_GUEST);

					clientList.add(client);
					clientID++;

				}
				String incomingMessage = new String(packet.getData());
				System.out.println("incoming message: " + incomingMessage);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("->recieving failed!! " + e.toString());
				e.printStackTrace();
			}
		}
	}

	/*! \brief This method gets all client */
	public ArrayList<Client> getClientList() {
		return clientList;
	}

	public boolean controlClient(InetAddress clientIPAddress) {
		if (clientList.size() > 0) {
			for (Client client : clientList) {
				if (client.getClientIPAddress() == clientIPAddress) {
					return true;
				}
			}
		}
		return false;
	}
}
