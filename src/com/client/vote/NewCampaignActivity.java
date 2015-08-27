package com.client.vote;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import com.client.vote.common.SimpleHttpClient;
import com.client.vote.domain.Campaign;
import com.client.vote.domain.Option;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class NewCampaignActivity extends Activity {

    String anchorName;
    String modify = "";
    Campaign campaign = new Campaign();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("NewCampaignActivity", "inside new campaign page");
        setContentView(R.layout.create_campaign);
        Intent intent = getIntent();
        anchorName = intent.getStringExtra("anchorName");
        modify = intent.getStringExtra("modify");
        if (TextUtils.equals(modify, "Y")) {
            String campaignId = intent.getStringExtra("campaignId");
            getCampaign(campaignId);
            //Load objects and make respective fields non editable
            EditText ncQuestion = (EditText) findViewById(R.id.campaign_question);
            ncQuestion.setText(campaign.getQuestion());
            ncQuestion.setEnabled(false);
            EditText ncStartDate = (EditText) findViewById(R.id.campaign_startdate);
            ncStartDate.setText(sdf.format(campaign.getStartDate()));
            EditText ncEndDate = (EditText) findViewById(R.id.campaign_enddate);
            ncEndDate.setText(sdf.format(campaign.getEndDate()));
            EditText ncRewardInfo = (EditText) findViewById(R.id.rewardEdit);
            ncRewardInfo.setText(campaign.getRewardInfo());
            //RadioGroup ncRegion = (RadioGroup) findViewById(R.id.createcampaignGroup2);
            String regionCountry = campaign.getRegionCountry();
            if (regionCountry.equalsIgnoreCase("Global")) {
                RadioButton r1 = (RadioButton) findViewById(R.id.region_radio1);
                r1.setChecked(true);
            } else {
                RadioButton r2 = (RadioButton) findViewById(R.id.regoin_radio2);
                r2.setChecked(true);
            }
            List<Option> options = campaign.getOptions();
            for (int i = 0; i < options.size(); i++) {
                if (i == 0) {
                    EditText option1 = (EditText) findViewById(R.id.optionValue1);
                    option1.setText(options.get(i).getOptionValue());
                    option1.setEnabled(false);
                }
                if (i == 1) {
                    EditText option2 = (EditText) findViewById(R.id.optionValue2);
                    option2.setText(options.get(i).getOptionValue());
                    option2.setEnabled(false);
                }
                if (i == 2) {
                    EditText option3 = (EditText) findViewById(R.id.optionValue3);
                    option3.setText(options.get(i).getOptionValue());
                    option3.setEnabled(false);
                }
                if (i == 3) {
                    EditText option4 = (EditText) findViewById(R.id.optionValue4);
                    option4.setText(options.get(i).getOptionValue());
                    option4.setEnabled(false);
                }
                if (i == 4) {
                    EditText option5 = (EditText) findViewById(R.id.optionValue5);
                    option5.setText(options.get(i).getOptionValue());
                    option5.setEnabled(false);
                }
            }
        }
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
        if (TextUtils.equals(modify, "Y")) {
            try {
                final ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
                postParameters.add(new BasicNameValuePair("campaignId", campaign.getCampaignId()));
                postParameters.add(new BasicNameValuePair("startDate", startDate.getText().toString()));
                postParameters.add(new BasicNameValuePair("endDate", endDate.getText().toString()));
                postParameters.add(new BasicNameValuePair("region", regionValue));
                postParameters.add(new BasicNameValuePair("rewardInfo", rewardInfo.getText().toString()));

                String response = SimpleHttpClient.executeHttpPost("/modifyCampaign", postParameters);
                Log.i("Response:", response);
                if (response.contains("success")) {
                    Intent intent = new Intent(this, CampaignSummaryActivity.class);
                    intent.putExtra("anchorName", anchorName);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Creation Failed, Please Retry !!!", Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                Log.e("createAnchor", e.getMessage() + "");
                Toast.makeText(getApplicationContext(), "Creation Failed, Please Retry !!!", Toast.LENGTH_LONG).show();
            }
        } else {
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
                    intent.putExtra("anchorName", anchorName);
                    startActivity(intent);
                } else {
                    showAlert(response);
                }
            } catch (Exception e) {
                Log.e("createAnchor", e.getMessage() + "");
                showAlert("Creation Failed, Please Retry !!!");
            }
        }
    }

    private void showAlert(String message) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(NewCampaignActivity.this);
        // Setting Dialog Title
        alertDialog.setTitle("Error");
        // Setting Dialog Message
        alertDialog.setMessage(message);
        // Setting Icon to Dialog
        //  alertDialog.setIcon(R.drawable.dialog_icon);
        // Setting Positive "Yes" Button
/*        alertDialog.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });*/
        // Setting Negative "NO" Button
        alertDialog.setNegativeButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to invoke NO event
                        dialog.cancel();
                    }
                });
        // Showing Alert Message
        alertDialog.show();
    }
    public void showCampaignSummary(View view) {
        Intent intent = new Intent(this, CampaignSummaryActivity.class);
        intent.putExtra("anchorName", anchorName);
        startActivity(intent);
    }

    private void getCampaign(String campaignId) {
        final ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
        postParameters.add(new BasicNameValuePair("campaignId", campaignId));
        try {
            String response = SimpleHttpClient.executeHttpPost("/getCampaign", postParameters);
            Log.i("Response:", response);
            JSONObject jsonobject = new JSONObject(response);
            campaign.setCampaignId(jsonobject.getString("campaign_id"));
            campaign.setQuestion(jsonobject.getString("question"));
            campaign.setStartDate(sdf.parse(jsonobject.getString("start_date")));
            campaign.setEndDate(sdf.parse(jsonobject.getString("end_date")));
            campaign.setStatus(jsonobject.getString("status"));
            campaign.setRewardInfo(jsonobject.getString("rewardInfo"));
            campaign.setRegionCountry(jsonobject.getString("regionCountry"));
            List<Option> options = new ArrayList<Option>();
            String optionsStr = jsonobject.getString("options");
            JSONArray jsonArray = new JSONArray(optionsStr);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject option = jsonArray.getJSONObject(i);
                options.add(new Option(option.getString("option_id"), option.getString("option_value")));
            }
            campaign.setOptions(options);
            Log.i("campaign name", campaign.toString());
        } catch (Exception e) {
            Log.e("register", e.getMessage() + "");
        }
    }
}