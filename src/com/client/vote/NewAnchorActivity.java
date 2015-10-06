package com.client.vote;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.client.vote.common.SimpleHttpClient;

public class NewAnchorActivity extends Activity {

	String clientId;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);   
		setContentView(R.layout.create_anchor);
	}

	public void signTab(View view) {
		Intent intent = new Intent(this, SignTabActivity.class);
		startActivity(intent);
	}

	public void checkAvailability(View view) {       
		EditText anchorName = (EditText) findViewById(R.id.newanchor_edit);
		TextView anchorStatus = (TextView) findViewById(R.id.anchorStatus);     
		try {
			final ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
			postParameters.add(new BasicNameValuePair("anchorId", anchorName.getText().toString()));
			String response = SimpleHttpClient.executeHttpPost("/checkAnchorAvailability", postParameters);            
			if (response.contains("success")) {
				anchorStatus.setText("Anchor available");
				anchorStatus.setTextColor(Color.parseColor("#00FF00"));
			} else {
				anchorStatus.setText("Anchor not available");
				anchorStatus.setTextColor(Color.parseColor("#FF0000"));
			}
		} catch (Exception e) {
			Log.e("checkAvailability", e.getMessage() + "");
			Toast.makeText(getApplicationContext(), "Check Failed, Please Retry !!!", Toast.LENGTH_LONG).show();
		}
	}

	public void createAnchor(View view) {       
		EditText anchorName = (EditText) findViewById(R.id.newanchor_edit);        
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
		clientId = preferences.getString("clientId", "");        
		try {
			final ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
			postParameters.add(new BasicNameValuePair("anchorId", anchorName.getText().toString()));
			postParameters.add(new BasicNameValuePair("clientId", clientId));
			String response = SimpleHttpClient.executeHttpPost("/createAnchor", postParameters);            
			if (response.contains("success")) {
				Intent intent = new Intent(this, SignTabActivity.class);
				startActivity(intent);
			} else {
				Toast.makeText(getApplicationContext(), "Creation Failed, Please Retry !!!", Toast.LENGTH_LONG).show();
			}
		} catch (Exception e) {
			Log.e("createAnchor", e.getMessage() + "");
			Toast.makeText(getApplicationContext(), "Creation Failed, Please Retry !!!", Toast.LENGTH_LONG).show();
		}
	}
}