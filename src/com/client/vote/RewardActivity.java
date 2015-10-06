package com.client.vote;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.client.vote.common.SimpleHttpClient;

public class RewardActivity extends Activity {

	ProgressDialog prgDialog;
	String encodedString;
	String imgPath, fileName;
	Bitmap bitmap;
	private static int RESULT_LOAD_IMG = 1;
	String anchorName;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);        
		setContentView(R.layout.reward);
		prgDialog = new ProgressDialog(this);
		// Set Cancelable as False
		prgDialog.setCancelable(false);
		Intent intent = getIntent();
		anchorName = intent.getStringExtra("anchorName");
	}

	public void uploadImage(View view) {
		Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		try {
			// When an Image is picked
			if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK && null != data) {
				// Get the Image from data
				Uri selectedImage = data.getData();
				String[] filePathColumn = {MediaStore.Images.Media.DATA};
				// Get the cursor
				Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
				// Move to first row
				cursor.moveToFirst();

				int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
				imgPath = cursor.getString(columnIndex);
				cursor.close();

				ImageView imgView = (ImageView) findViewById(R.id.reward_imageView1);
				// Set the Image in ImageView
				imgView.setImageBitmap(BitmapFactory.decodeFile(imgPath));

				String fileNameSegments[] = imgPath.split("/");
				fileName = fileNameSegments[fileNameSegments.length - 1];                
				// Put file name in Async Http Post Param which will used in jsp web app
				// Get the Image's file name
			} else {
				Toast.makeText(this, "You haven't picked Image", Toast.LENGTH_LONG).show();
			}
		} catch (Exception e) {

			Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
		}
	}

	// When Upload button is clicked
	public void uploadImages() {
		// When Image is selected from Gallery
		if (imgPath != null && !imgPath.isEmpty()) {
			prgDialog.setMessage("Converting Image to Binary Data");
			prgDialog.show();
			// Convert image to String using Base64
			encodeImage();
			// When Image is not selected from Gallery
		} else {
			Toast.makeText(getApplicationContext(), "You must select image from gallery before you try to upload", Toast.LENGTH_LONG).show();
		}
	}

	// AsyncTask - To convert Image to String
	public void encodeImage() {
		new AsyncTask<Void, Void, String>() {

			@Override
			protected String doInBackground(Void... params) {
				BitmapFactory.Options options = null;
				options = new BitmapFactory.Options();
				options.inSampleSize = 3;
				bitmap = BitmapFactory.decodeFile(imgPath, options);
				ByteArrayOutputStream stream = new ByteArrayOutputStream();
				// Must compress the Image to reduce image size to make upload easy
				bitmap.compress(Bitmap.CompressFormat.PNG, 50, stream);
				byte[] byte_arr = stream.toByteArray();
				// Encode Image to String
				encodedString = Base64.encodeToString(byte_arr, 0);
				return "";
			}

			@Override
			protected void onPostExecute(String msg) {
				prgDialog.setMessage("Calling Upload");
				uploadImage();
			}
		}.execute(null, null, null);
	}

	// Make Http call to upload Image to Php server
	public void uploadImage() {
		final ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		postParameters.add(new BasicNameValuePair("image", encodedString));
		postParameters.add(new BasicNameValuePair("filename", fileName));
		try {
			String response = SimpleHttpClient.executeHttpPost("/uploadImage", postParameters);            
		} catch (Exception e) {           
			Toast.makeText(getApplicationContext(), "Reward creation failed", Toast.LENGTH_LONG).show();
		}
	}

	public void createReward(View view) {
		uploadImages();
		final EditText pushValue = (EditText) findViewById(R.id.rwd_pushLimitValue);
		RadioGroup rgRegion = (RadioGroup) findViewById(R.id.rwd_pushRegionGrp);
		RadioButton rbRegion = (RadioButton) findViewById(rgRegion.getCheckedRadioButtonId());
		RadioGroup rgFilter = (RadioGroup) findViewById(R.id.rwd_pushFilterGrp);
		RadioButton rbFilter = (RadioButton) findViewById(rgFilter.getCheckedRadioButtonId());
		final EditText rewardDetail = (EditText) findViewById(R.id.reward_scrollView1);
		final EditText fromDate = (EditText) findViewById(R.id.rwd_validityFrom);
		final EditText toDate = (EditText) findViewById(R.id.rwd_validityTo);
		Intent intent = getIntent();
		// fetch value from key-value pair and make it visible on TextView.
		String campaignId = intent.getStringExtra("campaignId");        
		String region = rbRegion.getHint().toString();        
		if (region.equalsIgnoreCase("CC")) {
			SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
			region = preferences.getString("country", "");            
			if (region == null || region.equals("") || region.equals("null")) {
				Intent profileIntent = new Intent(this, ProfileActivity.class);
				startActivity(profileIntent);
			}
		}
		final ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		postParameters.add(new BasicNameValuePair("campaignId", campaignId));
		postParameters.add(new BasicNameValuePair("rewardDescription", rewardDetail.getText().toString()));
		postParameters.add(new BasicNameValuePair("imageURL", "http://52.74.246.67:8080/vote/images/one.jpg"));
		postParameters.add(new BasicNameValuePair("pushRegion", region));
		postParameters.add(new BasicNameValuePair("pushLimit", pushValue.getText().toString()));
		postParameters.add(new BasicNameValuePair("pushFilter", rbFilter.getHint().toString()));
		postParameters.add(new BasicNameValuePair("startDate", fromDate.getText().toString()));
		postParameters.add(new BasicNameValuePair("endDate", toDate.getText().toString()));
		try {
			String response = SimpleHttpClient.executeHttpPost("/createReward", postParameters);          
			if (response.contains("success")) {
				Intent intent2 = new Intent(this, CampaignSummaryActivity.class);
				intent2.putExtra("anchorName", anchorName);
				startActivity(intent2);
			}
		} catch (Exception e) {            
			Toast.makeText(getApplicationContext(), "Reward creation failed", Toast.LENGTH_LONG).show();
		}
	}
}