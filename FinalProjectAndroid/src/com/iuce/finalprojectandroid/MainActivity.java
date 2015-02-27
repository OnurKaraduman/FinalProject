package com.iuce.finalprojectandroid;

import java.net.DatagramSocket;
import java.net.SocketException;

import com.example.finalprojectandroid.R;
import com.iuce.sender.SenderUDP;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.wifi.p2p.WifiP2pManager.ActionListener;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends ActionBarActivity {

	
	
	private Button btnStart;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initUIElement();
		
		
	}
	public void initUIElement(){
		btnStart = (Button) findViewById(R.id.btnStart1);
		btnStart.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
				startActivity(intent);
				
			}
		});
		
	}

	
	

}
