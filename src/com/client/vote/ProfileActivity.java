package com.client.vote;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.client.vote.common.SimpleHttpClient;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;

public class ProfileActivity extends Activity {

	String clientId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.customer_profile);
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
		clientId = preferences.getString("clientId", "");
		String clientName = preferences.getString("clientName", "");        
		String websiteURL = preferences.getString("websiteURL", "");
		String about = preferences.getString("about", "");
		String country = preferences.getString("country", "");

		EditText customerNameField = (EditText) findViewById(R.id.cp_client_name);
		EditText urlField = (EditText) findViewById(R.id.cp_websiteURL);
		EditText detailsField = (EditText) findViewById(R.id.cp_about);
		Spinner countryView = (Spinner) findViewById(R.id.spinner1);

		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.country_array, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		countryView.setAdapter(adapter);
		countryView.setSelection(adapter.getPosition(country));
		customerNameField.setText(clientName);       
		urlField.setText(websiteURL);
		detailsField.setText(about);
		RadioButton radio1 = (RadioButton) findViewById(R.id.cp_clientCountryRadio);
		RadioButton radio2 = (RadioButton) findViewById(R.id.cp_GlobalRadio);
		if (country.contains("Global")) {
			radio2.setChecked(true);
		} else {
			radio1.setChecked(true);
		}
	}

	public void profileUpdate(View view) {
		EditText customerNameField = (EditText) findViewById(R.id.cp_client_name);
		EditText urlField = (EditText) findViewById(R.id.cp_websiteURL);
		EditText detailsField = (EditText) findViewById(R.id.cp_about);
		EditText password = (EditText) findViewById(R.id.cp_password);
		String countryOption;

		RadioGroup rg = (RadioGroup) findViewById(R.id.cp_CountryGroup);
		RadioButton rb = (RadioButton) findViewById(rg.getCheckedRadioButtonId());
		if (TextUtils.equals(rb.getText().toString(), "Global")) {
			countryOption = rb.getText().toString();
		} else {
			Spinner countryTag = (Spinner) findViewById(R.id.spinner1);
			countryOption = countryTag.getSelectedItem().toString();
		}

		Log.i("clientName", customerNameField.getText().toString());
		Log.i("password", password.getText().toString());
		Log.i("websiteURL", urlField.getText().toString());
		Log.i("about", detailsField.getText().toString());
		Log.i("country", countryOption);
		final ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		postParameters.add(new BasicNameValuePair("clientId", clientId));	
		postParameters.add(new BasicNameValuePair("clientName", customerNameField.getText().toString()));
		postParameters.add(new BasicNameValuePair("password", password.getText().toString()));
		postParameters.add(new BasicNameValuePair("websiteURL", urlField.getText().toString()));
		postParameters.add(new BasicNameValuePair("about", detailsField.getText().toString()));
		postParameters.add(new BasicNameValuePair("country", countryOption));
		final Context context = this; 
		try {
			String response = SimpleHttpClient.executeHttpPost("/updateClient", postParameters);
			Log.i("Response:", response);       

			if (response.contains("success")) {
				SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);

				SharedPreferences.Editor editor = prefs.edit();          

				editor.putString("clientName",  customerNameField.getText().toString());                
				editor.putString("websiteURL", urlField.getText().toString());
				editor.putString("about", detailsField.getText().toString());
				editor.putString("country", countryOption);
				editor.commit();
				Intent intent = new Intent(this, SignTabActivity.class);
				startActivity(intent);
			} else {
				Toast.makeText(getApplicationContext(), "Update Failed, Please Retry !!!", Toast.LENGTH_LONG).show();
			}
		} catch (Exception e) {
			Log.i("Response 2:Error:", e.getMessage());
			Toast.makeText(getApplicationContext(), "Update Failed, Please Retry !!!", Toast.LENGTH_LONG).show();
		}
	}

	public void onRadioButtonClicked(View view) {
		// Is the button now checked?
		boolean checked = ((RadioButton) view).isChecked();
		// Check which radio button was clicked
		switch (view.getId()) {
		case R.id.cp_GlobalRadio:
			if (checked)
				break;
		case R.id.cp_clientCountryRadio:
			if (checked)
				break;
		}
	}
}