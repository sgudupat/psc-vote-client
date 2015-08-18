package com.client.vote;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.client.vote.common.SimpleHttpClient;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomePageActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_signin);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        preferences.edit().clear().commit();
    }

    public void signIn(View view) {
        final EditText edit = (EditText) findViewById(R.id.login_username);
        EditText pwd = (EditText) findViewById(R.id.login_password);
        try {
            Log.i("triggerLogin:", "triggerLogin");
            final ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
            Log.i("clientid", edit.getText().toString());
            Log.i("psassword", pwd.getText().toString());
            postParameters.add(new BasicNameValuePair("clientId", edit.getText().toString()));
            postParameters.add(new BasicNameValuePair("password", pwd.getText().toString()));
            final Context context = this;
            try {
                Log.i("HomePageActivity", "try");
                String response = SimpleHttpClient.executeHttpPost("/loginClient", postParameters);
                Log.i("Response:", response);
                JSONObject jsonobject = new JSONObject(response);
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("clientId", (String) jsonobject.get("client_id"));
                editor.putString("clientName", (String) jsonobject.get("client_name"));
                editor.putString("emailAddress", (String) jsonobject.get("email_address"));
                editor.putString("websiteURL", (String) jsonobject.get("website_url"));
                editor.putString("about", (String) jsonobject.get("about"));  
                editor.putString("country", (String) jsonobject.get("country")); 
                                                                            
                editor.commit();
                Intent intent = new Intent(context, SignTabActivity.class);
                startActivity(intent);

            } catch (Exception e) {
                Log.e("LoginPageActivity", e.getMessage() + "");
                Toast.makeText(getApplicationContext(), "Login Failed, Please Retry !!!", Toast.LENGTH_LONG).show();
            }
            Log.i("After process:", "Done");
        } catch (Exception e) {
        }
    }

    public void signUp(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public void forgetPassword(View view) {
        Intent intent = new Intent(this, ForgetPasswordActivity.class);
        startActivity(intent);
    }
}