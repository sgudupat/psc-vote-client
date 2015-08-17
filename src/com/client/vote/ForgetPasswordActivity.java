package com.client.vote;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.client.vote.common.SimpleHttpClient;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Random;

public class ForgetPasswordActivity extends Activity {
    String response = "";
    String semail = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("ForgetPasswordActivity", "inside forget password page");
        setContentView(R.layout.forget_password);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        preferences.edit().clear().commit();
    }

    public void resetPassword(View view) {

        final EditText name = (EditText) findViewById(R.id.forgetEdit1);
        final EditText email = (EditText) findViewById(R.id.forgetView4);
        try {
            final ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
            postParameters.add(new BasicNameValuePair("clientId", name.getText().toString()));
            response = SimpleHttpClient.executeHttpPost("/getClient", postParameters);
            Log.i("Response:", response);
        } catch (Exception e) {
            Log.e("register", e.getMessage() + "");
            Toast.makeText(getApplicationContext(), "Login Failed, Please Retry !!!", Toast.LENGTH_LONG).show();
        }
        try {
            JSONObject jsonObj = new JSONObject(response);
            semail = jsonObj.getString("email_address");
            Log.i("email name", semail);
        } catch (JSONException e) {
        }
        if (TextUtils.equals(email.getText().toString(), semail)) {
            String chars = "abcdefghijklmanopqr";
            final int PW_LENGTH = 16;
            Random rnd = new SecureRandom();
            StringBuilder pass = new StringBuilder();
            for (int i = 0; i < PW_LENGTH; i++) {
                pass.append(chars.charAt(rnd.nextInt(chars.length())));
            }
            Log.i("link password", pass.toString());
            String value = pass.toString();
            String link = "http://52.74.246.67:8080/vote/forgotPassword.jsp?key=" + value;
            Log.i("password link", link);
            new LongRunningGetIO(email.getText().toString(), link).execute();
        }
    }
}