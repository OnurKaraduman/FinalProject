package com.iuce.main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;
import org.opencv.imgproc.Imgproc;

import com.iuce.constant.Constants;
import com.iuce.panel.FacePanel;
import com.iuce.thread.ThreadSender;

public class main {

	private static JFrame jframeImage;
	private static FacePanel panelImage;
	public static DatagramSocket socket;
	private static ThreadSender threadSender;


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			socket = new DatagramSocket(Constants.PORT_ADDRESS);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		threadSender = new ThreadSender(socket);
		threadSender.start();
		initFrame();
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		VideoCapture camFeed = new VideoCapture(1);
		
		Mat matCamFeed = new Mat();
		Mat matSender = new Mat();
		byte[] clientInformation = new byte[10];
		DatagramPacket packetClientInf = null;
		try {
			System.out.println("Client Bekleniyor....");
			packetClientInf = new DatagramPacket(clientInformation,
					clientInformation.length);
			socket.receive(packetClientInf);
			System.out.println("Client baglandi.");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Constants.IP_ADDRESS = packetClientInf.getAddress();
		Constants.CLIENT_PORT = packetClientInf.getPort();
		System.out.println("client ip:"+packetClientInf.getAddress());
		System.out.println("client port: "+packetClientInf.getPort());
		String s = new String(packetClientInf.getData());
		System.out.println("gelen data: "+s);

		while (true) {
			camFeed.read(matCamFeed);
			Imgproc.resize(matCamFeed, matSender, new Size(Constants.IMAGE_WIDTH, Constants.IMAGE_HEIGHT));
			BufferedImage bufImage = null;
			try {
				bufImage = matToBufferedImage(matSender);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			threadSender.setBufImageForSend(bufImage);
			threadSender.run();
			panelImage.setImage(bufImage);
			panelImage.repaint();
		}

	}

	public static void initFrame() {
		jframeImage = new JFrame("Image from client");
		jframeImage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframeImage.setSize(400, 400);
		jframeImage.setVisible(true);

		JButton btnGonder = new JButton("gonder");
		btnGonder.setVisible(true);
		// jframeImage.add(btnGonder);

		panelImage = new FacePanel();
		// panelImage.add(btnGonder);
		// btnGonder.addActionListener(new ActionListener() {
		//
		// @Override
		// public void actionPerformed(ActionEvent e) {
		// // TODO Auto-generated method stub
		// // String mesaj = "Threadler calisiyor";
		// // byte[] data = mesaj.getBytes();
		// // DatagramPacket packet = new DatagramPacket(data, data.length,
		// // Constants.IP_ADDRESS, Constants.PORT_ADDRESS);
		// // try {
		// // serverSocket.send(packet);
		// // } catch (IOException e1) {
		// // // TODO Auto-generated catch block
		// // e1.printStackTrace();
		// // }
		// }
		// });

		jframeImage.setContentPane(panelImage);

	}

	public static BufferedImage matToBufferedImage(Mat mat) throws IOException {
		MatOfByte bytemat = new MatOfByte();

		Highgui.imencode(".jpg", mat, bytemat);

		byte[] bytes = bytemat.toArray();

		InputStream in = new ByteArrayInputStream(bytes);

		BufferedImage img = ImageIO.read(in);
		return img;
	}
}
