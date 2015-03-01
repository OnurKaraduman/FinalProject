package com.iuce.finalprojectandroid;

import com.example.finalprojectandroid.R;
import com.example.finalprojectandroid.R.id;
import com.example.finalprojectandroid.R.layout;
import com.example.finalprojectandroid.R.menu;
import com.iuce.constant.Constants;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SettingsActivity extends ActionBarActivity {

	private Button btnConnect;
	private EditText edttxtIPAddress;
	private EditText edttxtPortAddress;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		initUIElement();
//		edttxtIPAddress.setText("172.28.8.43");
		edttxtIPAddress.setText("192.168.2.118");
		edttxtPortAddress.setText("999");
	}

	public void initUIElement() {

		edttxtIPAddress = (EditText) findViewById(R.id.edttxtIPAddress);
		edttxtPortAddress = (EditText) findViewById(R.id.edttxtPortAddress);
		btnConnect = (Button) findViewById(R.id.btnConnect);

		btnConnect.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String ipAddress = edttxtIPAddress.getText().toString();
				int portAddress = Integer.parseInt(edttxtPortAddress.getText()
						.toString());

				if (ipAddress.equals("")) {
					Toast.makeText(SettingsActivity.this,
							"Please insert ip address", Toast.LENGTH_LONG)
							.show();
				} else {
					Constants.IP_STRING = ipAddress;
					Constants.PORT_ADDRESS_SERVER = portAddress;
					Intent intent = new Intent(SettingsActivity.this, CommandActivity.class);
					startActivity(intent);

				}

			}
		});
	}
}
