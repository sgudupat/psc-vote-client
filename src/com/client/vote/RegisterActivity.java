package com.client.vote;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Random;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.client.vote.common.SimpleHttpClient;

public class RegisterActivity extends Activity {
	private CheckBox agree;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);        
		setContentView(R.layout.sign_up);
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
		preferences.edit().clear().commit();
	}

	public void regret(View view) {
		Intent intent = new Intent(this, RegretActivity.class);
		startActivity(intent);
	}

	public void verification(View view) {
		final EditText cid = (EditText) findViewById(R.id.signup_username);
		final EditText password = (EditText) findViewById(R.id.signup_password);
		final EditText email = (EditText) findViewById(R.id.register_email);
		agree = (CheckBox) findViewById(R.id.agree_terms);

		String chars = "0123456789";
		final int PW_LENGTH = 5;
		Random rnd = new SecureRandom();
		StringBuilder pass = new StringBuilder();
		for (int i = 0; i < PW_LENGTH; i++) {
			pass.append(chars.charAt(rnd.nextInt(chars.length())));
		}
		Log.i("otp password", pass.toString());
		final String otp = pass.toString();
		new LongRunningGetIO(email.getText().toString(), otp).execute();
		final Context context = this;
		LayoutInflater li = LayoutInflater.from(context);
		View promptsView = li.inflate(R.layout.prompts, null);
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
		// set prompts.xml to alert dialog builder
		alertDialogBuilder.setView(promptsView);
		final EditText userInput = (EditText) promptsView.findViewById(R.id.editTextDialogUserInput);
		// set dialog message
		alertDialogBuilder.setCancelable(false).setPositiveButton("OK",
				new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				// get user input and set it to result edit text
				String aotp = userInput.getText().toString();                        
				if (aotp.equals(otp) && (agree.isChecked())) {
					try {
						final ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
						postParameters.add(new BasicNameValuePair("clientId", cid.getText().toString()));
						postParameters.add(new BasicNameValuePair("password", password.getText().toString()));
						postParameters.add(new BasicNameValuePair("emailAddress", email.getText().toString()));
						String response = SimpleHttpClient.executeHttpPost("/registerClient", postParameters);                                
						JSONObject jsonobject = new JSONObject(response);
						SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
						SharedPreferences.Editor editor = prefs.edit();
						editor.putString("clientId", (String) jsonobject.get("client_id"));
						editor.putString("emailAddress", (String) jsonobject.get("email_address"));
						editor.commit();
						Intent intent = new Intent(context, SignTabActivity.class);
						startActivity(intent);                               
					} catch (Exception e) {
						Log.e("register", e.getMessage() + "");
						Toast.makeText(getApplicationContext(), "Login Failed, Please Retry !!!", Toast.LENGTH_LONG).show();
					}
				}
			}
		}).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				dialog.cancel();
			}
		});
		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();
		// show it
		alertDialog.show();
	}
}