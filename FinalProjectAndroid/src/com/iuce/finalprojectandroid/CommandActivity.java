package com.iuce.finalprojectandroid;

import java.net.DatagramSocket;
import java.net.SocketException;

import com.example.finalprojectandroid.R;
import com.example.finalprojectandroid.R.id;
import com.example.finalprojectandroid.R.layout;
import com.example.finalprojectandroid.R.menu;
import com.iuce.constant.Constants;
import com.iuce.sender.SenderUDP;

import android.support.v7.app.ActionBarActivity;
import android.graphics.Bitmap;
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
