package com.client.vote;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ActivityGroup;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ListView;

import com.client.vote.common.SimpleHttpClient;
import com.client.vote.domain.Anchor;

public class AnchorSummaryActivity extends ActivityGroup {

	final Context context = this;
	ArrayList<Anchor> items = new ArrayList<Anchor>();
	String clientId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.anchor_summary);
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
		clientId = preferences.getString("clientId", "");        
		ListView listView = (ListView) findViewById(R.id.list_view);
		// Adding items to list view
		// 1. pass context and data to the custom adapter        
		final AnchorAdapter adapter = new AnchorAdapter(generateData(clientId), AnchorSummaryActivity.this);
		listView.setAdapter(adapter);
	}

	private ArrayList<Anchor> generateData(String clientId) {
		try {
			//Populate from Server
			String anchors = getAnchors(clientId);
			items.clear();
			//Build the list
			JSONArray jsonArray = new JSONArray(anchors);
			int jsonArray1=jsonArray.length();
			for (int i = 0; i < jsonArray1; i++) {
				JSONObject jsonobject = jsonArray.getJSONObject(i);
				String dbClientId = jsonobject.getString("client_id");
				String dbAnchorId = jsonobject.getString("anchor_id");
				String dbCreationDate = jsonobject.getString("creation_date");
				String dbAnchorPrice = jsonobject.getString("price");
				String dbAnchorStatus = jsonobject.getString("active");
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				items.add(new Anchor(dbClientId, dbAnchorId, dateFormat.parse(dbCreationDate), dbAnchorPrice, dbAnchorStatus));
			}
		} catch (Exception e) {
		}
		return items;
	}

	private String getAnchors(String clientId) {        
		final ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		postParameters.add(new BasicNameValuePair("clientId", clientId));
		try {            
			String response = SimpleHttpClient.executeHttpPost("/getClientAnchors", postParameters);           
			return response;
		} catch (Exception e) {            
			return "fail";
		}
	}

	public void newAnchor(View view) {
		Intent intent = new Intent(this, NewAnchorActivity.class);
		replaceContentView("anchor_item", intent);
	}

	public void replaceContentView(String id, Intent newIntent) {
		View view = ((ActivityGroup) context).getLocalActivityManager().startActivity(id, newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)).getDecorView();
		((Activity) context).setContentView(view);
	}
}