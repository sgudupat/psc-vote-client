package com.client.vote;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class CampaignActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.campaign);
        //generate list
        ArrayList<String> list = new ArrayList<String>();
        //instantiate custom adapter
        CampaignAdapter adapter = new CampaignAdapter(list, this);
        //handle listview and assign adapter
        ListView lView = (ListView) findViewById(R.id.list_view);
        lView.setAdapter(adapter);
    }
}