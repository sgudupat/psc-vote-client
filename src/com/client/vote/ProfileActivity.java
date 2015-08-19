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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

public class ProfileActivity extends Activity {

	String clientId;
	String countries;

	int i=0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.customer_profile);
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
		clientId = preferences.getString("clientId", "");
		countries=preferences.getString("countries", "");
		String clientName = preferences.getString("clientName", "");
		String websiteURL = preferences.getString("websiteURL", "");
		String about = preferences.getString("about", "");
		String country = preferences.getString("country", "");

		EditText customerNameField = (EditText) findViewById(R.id.cp_client_name);
		EditText urlField = (EditText) findViewById(R.id.cp_websiteURL);
		EditText detailsField = (EditText) findViewById(R.id.cp_about);
		Spinner country1 = (Spinner) findViewById(R.id.spinner1);

		Locale[] locales = Locale.getAvailableLocales();
		ArrayList<String> countries = new ArrayList<String>();
		for (Locale locale : locales) {
			String country2 = locale.getDisplayCountry();
			if (country2.trim().length() > 0 && !countries.contains(country2)) {
				countries.add(country2);
			}
		}
		Collections.sort(countries);
		for (String country2 : countries) {
			System.out.println(country2);
			if(country2.equals(country)){
				i=countries.indexOf(country2);

			}
		}

		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, countries);
		// set the view for the Drop down list
		dataAdapter
		.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				// set the ArrayAdapter to the spinner
		country1.setAdapter(dataAdapter);
		country1.setSelection(i);



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

				editor.putString("clientName", customerNameField.getText().toString());
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