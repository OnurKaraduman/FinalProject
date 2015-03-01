package com.iuce.finalprojectandroid;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import com.example.finalprojectandroid.R;
import com.example.finalprojectandroid.R.id;
import com.example.finalprojectandroid.R.layout;
import com.example.finalprojectandroid.R.menu;
import com.iuce.constant.Constants;
import com.iuce.sender.SenderUDP;

import android.support.v7.app.ActionBarActivity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class CommandActivity extends ActionBarActivity {

	public SenderUDP senderUdp;
	public DatagramSocket socket;

	// UI element define
	private ImageView imageView;
	private Button btnLeft;
	private Button btnRight;
	private Button btnGo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_command);
		initUI();
		//create datagramSocket
				try {
					socket = new DatagramSocket();
				} catch (SocketException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				senderUdp = new SenderUDP(socket);
				startGetStream();
	}

	public void startGetStream(){
		Thread t = new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				// TODO Auto-generated method stub

				byte[] sendData = new byte[1024];
				byte[] receiveData = new byte[65500];
				DatagramSocket clientSocket = null;
				try {
					clientSocket = new DatagramSocket();
				} catch (SocketException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

//				String sentence = messageEdit.getText().toString();
				String sentence = "start";
				sendData = sentence.getBytes();
				DatagramPacket sendPacket = null;
				try {
					sendPacket = new DatagramPacket(
							sendData, sendData.length, Constants.getIP_ADDRESS_SERVER(), Constants.PORT_ADDRESS_SERVER);
				} catch (UnknownHostException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					clientSocket.send(sendPacket);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				while (true) {
					receiveData = new byte[65500];
					DatagramPacket receivePacket = new DatagramPacket(
							receiveData, receiveData.length);

					try {
						clientSocket.receive(receivePacket);
//						System.out.println("gelen veri boyutu: "
//								+ receivePacket.getData());
						ByteArrayInputStream byt = new ByteArrayInputStream(
								receivePacket.getData());

						BufferedInputStream br = new BufferedInputStream(
								byt);
						final Bitmap bitmap = BitmapFactory
								.decodeStream(br);

						final InetAddress returnIPAddress = receivePacket
								.getAddress();

						final int port = receivePacket.getPort();
						runOnUiThread(new Runnable() {

							@Override
							public void run() {
								// TODO Auto-generated method stub

								imageView.setImageBitmap(bitmap);
							}
						});

						// clientSocket.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		t.start();
	}
	
	public void initUI() {
		imageView = (ImageView) findViewById(R.id.imageView1);
		btnLeft = (Button) findViewById(R.id.btnLeft);
		btnRight = (Button) findViewById(R.id.btnRight);
		btnGo = (Button) findViewById(R.id.btnGo);
		btnLeft.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				senderUdp.send(Constants.COMMAND_LEFT);
			}
		});
		btnRight.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				senderUdp.send(Constants.COMMAND_RIGHT);
			}
		});
		btnGo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				senderUdp.send(Constants.COMMAND_GO);
			}
		});
	}
	public  void repaintImageView(final Bitmap bm){
		runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				imageView.setImageBitmap(bm);
			}
		});
		
	}
}
