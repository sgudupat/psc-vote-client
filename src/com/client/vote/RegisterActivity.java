package com.client.vote;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Random;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.client.vote.common.SimpleHttpClient;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends Activity {
	private CheckBox agree;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i("RegisterActivity", "inside client register page");
		setContentView(R.layout.sign_up);
	}
	public void regret(View view)
	{
		Intent intent = new Intent(this, RegretActivity.class);
		startActivity(intent);
	}
	public void verification(View view)
	{
		//Intent intent = new Intent(this, RegretActivity.class);
		//startActivity(intent);
		 final EditText cid = (EditText) findViewById(R.id.signup_username);
		 final EditText password = (EditText) findViewById(R.id.signup_password);
		 final EditText email= (EditText) findViewById(R.id.fld_email);
		 agree = (CheckBox) findViewById(R.id.chkIos);
		 
		  String chars = "0123456789";
	        final int PW_LENGTH = 5;
	        Random rnd = new SecureRandom();
	        StringBuilder pass = new StringBuilder();
	        for (int i = 0; i < PW_LENGTH; i++) {
	            pass.append(chars.charAt(rnd.nextInt(chars.length())));
	        }
	        Log.i("otp password", pass.toString());
	        final String otp = pass.toString();
	        EditText textTo;
	    	EditText textSubject;
	    	EditText textMessage;
	    	textTo = (EditText) findViewById(R.id.editTextTo);
			textSubject = (EditText) findViewById(R.id.editTextSubject);
			textMessage = (EditText) findViewById(R.id.editTextMessage);
			 String to = email.getText().toString();
			  String subject = "OTP pssword";
			  String message = otp;

			/*  Intent email = new Intent(Intent.ACTION_SEND);
			  email.putExtra(Intent.EXTRA_EMAIL, new String[]{ to});
			  //email.putExtra(Intent.EXTRA_CC, new String[]{ to});
			  //email.putExtra(Intent.EXTRA_BCC, new String[]{to});
			  email.putExtra(Intent.EXTRA_SUBJECT, subject);
			  email.putExtra(Intent.EXTRA_TEXT, message);

			  //need this to prompts email client only
			email.setType("message/rfc822");
			  
			  startActivity(Intent.createChooser(email, "Choose an Email client :"));*/
			/*  try {   
                  GMailSender sender = new GMailSender("username@gmail.com", "password");
                  sender.sendMail("This is Subject",   
                          "This is Body",   
                          "user@gmail.com",   
                          "user@yahoo.com");   
              } catch (Exception e) {   
                  Log.e("SendMail", e.getMessage(), e);   
              } */
			  
			  new LongRunningGetIO(otp).execute();
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
		                        Log.i("alert dialog otp", aotp);
		                        if (aotp.equals(otp) && (agree.isChecked())) {
		                            try {
		                            	  final ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		                                  postParameters.add(new BasicNameValuePair("clientId", cid.getText().toString()));
		                                  postParameters.add(new BasicNameValuePair("password", password.getText().toString()));
		                                  postParameters.add(new BasicNameValuePair("emailAddress", email.getText().toString()));
		                                String response = SimpleHttpClient.executeHttpPost("/registerClient", postParameters);
		                                Log.i("Response:", response);
		                              
		                               Intent intent = new Intent(context, SignTabActivity.class);
		                                startActivity(intent);
		                                Log.i("inside otp if loop", "search activity started");
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

