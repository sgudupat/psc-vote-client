package com.client.vote;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.client.vote.common.SimpleHttpClient;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class RewardActivity extends Activity {
	 Bitmap bitmap;
	 String imgPath, fileName;
	    private static int RESULT_LOAD_IMG = 1;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("RewardActivity", "inside rewards page");
        setContentView(R.layout.reward);
    }
	
	public void uploadImage(View view){
		
		 Intent galleryIntent = new Intent(Intent.ACTION_PICK,
	                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
	        // Start the Intent
	        startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
		
	}

	 protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	        super.onActivityResult(requestCode, resultCode, data);
	        try {
	            // When an Image is picked
	            if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK
	                    && null != data) {
	                // Get the Image from data
	 
	                Uri selectedImage = data.getData();
	                String[] filePathColumn = { MediaStore.Images.Media.DATA };
	 
	                // Get the cursor
	                Cursor cursor = getContentResolver().query(selectedImage,
	                        filePathColumn, null, null, null);
	                // Move to first row
	                cursor.moveToFirst();
	 
	                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
	                imgPath = cursor.getString(columnIndex);
	                cursor.close();
	                ImageView imgView = (ImageView) findViewById(R.id.reward_imageView1);
	                // Set the Image in ImageView
	                imgView.setImageBitmap(BitmapFactory
	                        .decodeFile(imgPath));
	                // Get the Image's file name
	             
	 
	            } else {
	                Toast.makeText(this, "You haven't picked Image",
	                        Toast.LENGTH_LONG).show();
	            }
	        } catch (Exception e) {
	            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
	                    .show();
	        }
	 }
	 
	 
	 public void createReward(View view){
		 
		 final EditText pushValue = (EditText) findViewById(R.id.rwd_pushLimitValue);
		  RadioGroup rg = (RadioGroup) findViewById(R.id.rwd_pushRegionGrp);
	        RadioButton rb = (RadioButton) findViewById(rg.getCheckedRadioButtonId());
	        RadioGroup rg2 = (RadioGroup) findViewById(R.id.rwd_pushFilterGrp);
	        RadioButton rb2 = (RadioButton) findViewById(rg.getCheckedRadioButtonId());
	        final EditText rewardDetail = (EditText) findViewById(R.id.reward_scrollView1);
	        final EditText fromDate = (EditText) findViewById(R.id.rwd_validityFrom);
	        final EditText toDate = (EditText) findViewById(R.id.rwd_validityTo);
	        
	     /*   DatePicker fdatePicker = (DatePicker) findViewById(R.id.reward_datePicker1);
	        int sday = fdatePicker.getDayOfMonth();
	        int smonth = fdatePicker.getMonth() + 1;
	        int syear = fdatePicker.getYear();
	        String fromDate=String.valueOf(sday)+"/"+String.valueOf(smonth)+"/"+String.valueOf(syear);
	        
	        DatePicker edatePicker = (DatePicker) findViewById(R.id.datePicker1);
	        int eday = edatePicker.getDayOfMonth();
	        int emonth = edatePicker.getMonth() + 1;
	        int eyear = edatePicker.getYear();
	        String toDate=String.valueOf(eday)+"/"+String.valueOf(emonth)+"/"+String.valueOf(eyear);*/
	        final ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
	        postParameters.add(new BasicNameValuePair("campaignId", ""));
	        postParameters.add(new BasicNameValuePair("rewardDescription", rewardDetail.getText().toString()));
	        postParameters.add(new BasicNameValuePair("imageURL", ""));
	        postParameters.add(new BasicNameValuePair("pushRegion", rb.getText().toString()));
	        postParameters.add(new BasicNameValuePair("pushLimit", pushValue.getText().toString()));
	        postParameters.add(new BasicNameValuePair("pushFilter", rb2.getText().toString()));
	        postParameters.add(new BasicNameValuePair("startdate", fromDate.getText().toString()));
	        postParameters.add(new BasicNameValuePair("enddate", toDate.getText().toString()));
	        try {
	            String response = SimpleHttpClient.executeHttpPost("/createReward", postParameters);
	            Log.i("Response:", response);
		 
	 } catch (Exception e) {
         Log.i("Response 2:Error:", e.getMessage());
         Toast.makeText(getApplicationContext(), "Reward creation failed", Toast.LENGTH_LONG).show();
     }
	 
}
}
