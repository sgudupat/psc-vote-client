package com.client.vote;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.client.vote.common.SimpleHttpClient;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;

public class RegretActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("RegretActivity", "inside regret page");
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
        Log.i("regret", "regret");
        final ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
        postParameters.add(new BasicNameValuePair("regretInfo", regretInformation.getText().toString()));
        postParameters.add(new BasicNameValuePair("deviceId", deviceId));
        try {
            String response = SimpleHttpClient.executeHttpPost("/postRegret", postParameters);
            Log.i("Response:", response);
        } catch (Exception e) {
            Log.e("regret", e.getMessage() + "");
            Toast.makeText(getApplicationContext(), "fail !!!", Toast.LENGTH_LONG).show();
        }
    }
}