package com.iuce.thread;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.zip.Deflater;

import javax.imageio.ImageIO;

import com.iuce.constant.Constants;

public class ThreadSender extends Thread {

	private BufferedImage bufImageForSend;
	private DatagramSocket socket;
	private Deflater deflater;

	public ThreadSender(DatagramSocket socket) {
		// TODO Auto-generated constructor stub
		this.socket = socket;
		deflater = new Deflater();
		bufImageForSend = null;
	}

	public void setBufImageForSend(BufferedImage bufImageForSend) {
		this.bufImageForSend = bufImageForSend;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		// video Streaming

		// bufferedImage null degilse islemmleri yap
		if (bufImageForSend != null) {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			try {
				ImageIO.write(bufImageForSend, "jpg", bos);
			} catch (Exception e) {
				// TODO: handle exception
				// image write exception ->log dosyasina kaydet
			}
			byte[] byteImage = bos.toByteArray();

			//byte[] byteImage = compress(byteImage2);
			System.out.println("Total image byte length: " + byteImage.length);
			if (byteImage.length <= 65500) {
				DatagramPacket packet = new DatagramPacket(byteImage,
						byteImage.length, Constants.IP_ADDRESS,
						Constants.CLIENT_PORT);
				try {
					socket.send(packet);
				} catch (IOException e) {
					// TODO Auto-generated catch block

					// socket send exception -> loga kaydet
				}
			}

		}

	}

	public byte[] compress(byte[] data) {
		deflater.setInput(data);

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(
				data.length);

		deflater.finish();
		byte[] buffer = new byte[1024];
		while (!deflater.finished()) {
			int count = deflater.deflate(buffer); // returns the generated
													// code... index
			outputStream.write(buffer, 0, count);
		}
		try {
			outputStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		byte[] output = outputStream.toByteArray();

		System.out.println("Original: " + data.length / 1024 + " Kb");
		System.out.println("Compressed: " + output.length / 1024 + " Kb");

		return output;

	}

}
