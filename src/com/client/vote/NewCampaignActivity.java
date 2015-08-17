package com.client.vote;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import com.client.vote.common.SimpleHttpClient;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;

public class NewCampaignActivity extends Activity {

    String anchorName;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("NewCampaignActivity", "inside new campaign page");
        setContentView(R.layout.create_campaign);
        Intent intent = getIntent();
        anchorName = intent.getStringExtra("anchorName");
    }

    public void submitNewCampaign(View view) {
        Log.i("submitNewCampaign:", "Start");

        EditText question = (EditText) findViewById(R.id.campaign_question);
        EditText startDate = (EditText) findViewById(R.id.campaign_startdate);
        EditText endDate = (EditText) findViewById(R.id.campaign_enddate);
        RadioGroup region = (RadioGroup) findViewById(R.id.createcampaignGroup2);
        RadioButton option = (RadioButton) findViewById(region.getCheckedRadioButtonId());
        String regionValue = (String) option.getHint();
        EditText rewardInfo = (EditText) findViewById(R.id.rewardEdit);

        EditText optionValue1 = (EditText) findViewById(R.id.optionValue1);
        EditText optionValue2 = (EditText) findViewById(R.id.optionValue2);
        EditText optionValue3 = (EditText) findViewById(R.id.optionValue3);
        EditText optionValue4 = (EditText) findViewById(R.id.optionValue4);
        EditText optionValue5 = (EditText) findViewById(R.id.optionValue5);
        try {
            final ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
            postParameters.add(new BasicNameValuePair("anchorId", anchorName));
            postParameters.add(new BasicNameValuePair("question", question.getText().toString()));
            postParameters.add(new BasicNameValuePair("startDate", startDate.getText().toString()));
            postParameters.add(new BasicNameValuePair("endDate", endDate.getText().toString()));
            postParameters.add(new BasicNameValuePair("region", regionValue));
            postParameters.add(new BasicNameValuePair("rewardInfo", rewardInfo.getText().toString()));
            postParameters.add(new BasicNameValuePair("optionValue1", optionValue1.getText().toString()));
            postParameters.add(new BasicNameValuePair("optionValue2", optionValue2.getText().toString()));
            postParameters.add(new BasicNameValuePair("optionValue3", optionValue3.getText().toString()));
            postParameters.add(new BasicNameValuePair("optionValue4", optionValue4.getText().toString()));
            postParameters.add(new BasicNameValuePair("optionValue5", optionValue5.getText().toString()));

            String response = SimpleHttpClient.executeHttpPost("/createCampaign", postParameters);
            Log.i("Response:", response);
            if (response.contains("success")) {
                Intent intent = new Intent(this, CampaignSummaryActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(getApplicationContext(), "Creation Failed, Please Retry !!!", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Log.e("createAnchor", e.getMessage() + "");
            Toast.makeText(getApplicationContext(), "Creation Failed, Please Retry !!!", Toast.LENGTH_LONG).show();
        }
    }

    public void showCampaignSummary(View view) {
        Intent intent = new Intent(this, CampaignSummaryActivity.class);
        intent.putExtra("anchorName", anchorName);
        startActivity(intent);
    }
}