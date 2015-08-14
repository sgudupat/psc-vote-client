package com.client.vote;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

public class SignTabActivity extends TabActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setContentView(R.layout.activity_main);

        TabHost mTabHost = getTabHost();

        mTabHost.addTab(mTabHost.newTabSpec("anchor_item").setIndicator("Anchor").setContent(new Intent(this, AnchorSummaryActivity.class)));
        mTabHost.addTab(mTabHost.newTabSpec("campaign").setIndicator("Campaign").setContent(new Intent(this, CampaignActivity.class)));
        mTabHost.addTab(mTabHost.newTabSpec("insight").setIndicator("Insight").setContent(new Intent(this, InsightActivity.class)));
        mTabHost.addTab(mTabHost.newTabSpec("profile").setIndicator("Profile").setContent(new Intent(this, ProfileActivity.class)));
        //mTabHost.setCurrentTab(0);


    }
}
