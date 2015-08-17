package com.client.vote;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import com.client.vote.common.SimpleHttpClient;
import com.client.vote.domain.Campaign;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class CampaignSummaryActivity extends Activity {

    String clientId;
    String anchorName;
    String campaignId;
    List<Campaign> items = new ArrayList<Campaign>();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.campaign_summary);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        clientId = preferences.getString("clientId", "");
        Intent intent = getIntent();
        anchorName = intent.getStringExtra("anchorName");
        List<Campaign> campaigns = generateData();
        Log.i("campaigns:", campaigns.toString());
        //Display Current Campaign running on Anchor
        if (campaigns.size() > 0) {
            TextView CSCampaignName = (TextView) findViewById(R.id.cs_campaign_name);
            TextView CSStartDate = (TextView) findViewById(R.id.cs_start_date);
            TextView CSEndDate = (TextView) findViewById(R.id.cs_end_date);
            CSCampaignName.setText(campaigns.get(0).getQuestion());
            CSStartDate.setText("Start Date:" + campaigns.get(0).getStartDate());
            CSEndDate.setText("End Date:" + campaigns.get(0).getEndDate());
            campaignId = campaigns.get(0).getCampaignId();
        }
        //Display all expired Campaigns
        //instantiate custom adapter
        if (campaigns.size() > 1) {
            List<Campaign> remainingCampaigns = new ArrayList<Campaign>();
            for (int i = 1; i < campaigns.size(); i++) {
                remainingCampaigns.add(campaigns.get(i));
            }
            TextView previousCampaignsHeading = (TextView) findViewById(R.id.previous_campaign);
            previousCampaignsHeading.setVisibility(View.VISIBLE);

            ListView lView = (ListView) findViewById(R.id.list_view);
            CampaignAdapter adapter = new CampaignAdapter(remainingCampaigns, this);
            //handle list view and assign adapter
            lView.setAdapter(adapter);
        }

    }

    private String fetchCampaignInfo(String clientId, String anchorName) {
        Log.i("fetchCampaignInfo:", "fetchCampaignInfo");
        final ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
        postParameters.add(new BasicNameValuePair("clientId", clientId));
        postParameters.add(new BasicNameValuePair("anchorName", anchorName));
        try {
            String response = SimpleHttpClient.executeHttpPost("/getCampaignFromAnchor", postParameters);
            Log.i("Response:", response);
            return response;
        } catch (Exception e) {
            Log.e("register", e.getMessage() + "");
        }
        return null;
    }

    private List<Campaign> generateData() {
        try {
            //Populate from Server
            String response = fetchCampaignInfo(clientId, anchorName);
            items.clear();
            //Build the list
            JSONArray jsonArray = new JSONArray(response);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonobject = jsonArray.getJSONObject(i);
                String campaignId = jsonobject.getString("campaign_id");
                String question = jsonobject.getString("question");
                String startDate = jsonobject.getString("start_date");
                String endDate = jsonobject.getString("end_date");
                Log.i("campaign name", campaignId);
                items.add(new Campaign(campaignId, anchorName, question, dateFormat.parse(startDate), dateFormat.parse(endDate)));
            }
        } catch (Exception e) {
        }
        return items;
    }

    public void createCampaign(View view) {
        Intent intent = new Intent(this, NewCampaignActivity.class);
        intent.putExtra("anchorName", anchorName);
        startActivity(intent);
    }

    public void showRewardInfo(View view) {
        //This method is useful only for the active Campaign that is displayed in Summary
        Intent intent = new Intent(this, RewardActivity.class);
        intent.putExtra("anchorName", anchorName);
        intent.putExtra("campaignId", campaignId);
        startActivity(intent);
    }

}