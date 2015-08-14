package com.client.vote;



import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import com.client.vote.common.SimpleHttpClient;

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

public class HomePageActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_signin);
      
    }
    public void signIn(View view) {
   	 final EditText edit = (EditText) findViewById(R.id.login_username);
        EditText pwd = (EditText) findViewById(R.id.login_password);
        try {
            Log.i("triggerLogin:", "triggerLogin");
            final ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
            Log.i("clientid",edit.getText().toString());
            Log.i("psassword",pwd.getText().toString());
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
   public void signUp(View view)
   {
    Intent intent = new Intent(this, RegisterActivity.class);
    startActivity(intent);
   }
  
}
