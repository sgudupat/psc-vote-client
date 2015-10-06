package com.client.vote;

import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.KeyEvent;
import android.widget.TabHost;
import android.widget.Toast;

public class SignTabActivity extends TabActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TabHost mTabHost = getTabHost();
        mTabHost.addTab(mTabHost.newTabSpec("anchor_item").setIndicator("Anchor").setContent(new Intent(this, AnchorSummaryActivity.class)));
        mTabHost.addTab(mTabHost.newTabSpec("campaign_summary").setIndicator("Campaign").setContent(new Intent(this, CampaignSummaryActivity.class)));
        mTabHost.addTab(mTabHost.newTabSpec("insight").setIndicator("Insight").setContent(new Intent(this, InsightActivity.class)));
        mTabHost.addTab(mTabHost.newTabSpec("profile").setIndicator("Profile").setContent(new Intent(this, ProfileActivity.class)));

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //Changes 'back' button action
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //Include the code here           
            Toast.makeText(getApplicationContext(), "are you shore you want to logout2!!!", Toast.LENGTH_LONG).show();
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                    SignTabActivity.this);
            // Setting Dialog Title
            alertDialog.setTitle("Leave application?");
            // Setting Dialog Message
            alertDialog.setMessage("Are you sure you want to leave the application?");
            // Setting Icon to Dialog
            //  alertDialog.setIcon(R.drawable.dialog_icon);
            // Setting Positive "Yes" Button
            alertDialog.setPositiveButton("YES",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(SignTabActivity.this);
                            preferences.edit().clear().commit();
                            finish();
                        }
                    });
            // Setting Negative "NO" Button
            alertDialog.setNegativeButton("NO",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Write your code here to invoke NO event
                            dialog.cancel();
                        }
                    });
            // Showing Alert Message
            alertDialog.show();
        }
        return true;
    }
}