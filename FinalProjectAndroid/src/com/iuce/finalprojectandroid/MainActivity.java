package com.iuce.finalprojectandroid;


import com.example.finalprojectandroid.R;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

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
