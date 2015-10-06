package com.client.vote;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.client.vote.common.SimpleHttpClient;

public class RegretActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);       
		setContentView(R.layout.regret);
	}

	public void loginPage(View view) {
		regretInfo();
		Intent intent = new Intent(this, HomePageActivity.class);
		startActivity(intent);
	}


	public void regretInfo() {
		EditText regretInformation = (EditText) findViewById(R.id.regretedit1);
		String deviceId = Secure.getString(getContentResolver(), Secure.ANDROID_ID);    
		final ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		postParameters.add(new BasicNameValuePair("regretInfo", regretInformation.getText().toString()));
		postParameters.add(new BasicNameValuePair("deviceId", deviceId));
		try {
			String response = SimpleHttpClient.executeHttpPost("/postRegret", postParameters);            
		} catch (Exception e) {            
			Toast.makeText(getApplicationContext(), "fail !!!", Toast.LENGTH_LONG).show();
		}
	}
}