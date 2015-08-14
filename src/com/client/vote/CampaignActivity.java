package com.client.vote;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class CampaignActivity  extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {

	  //  super.onCreate(savedInstanceState);

	    //setContentView( R.layout.campaign );
		 super.onCreate(savedInstanceState);

		    setContentView(R.layout.campaign); 

		    //generate list
		    ArrayList<String> list = new ArrayList<String>();
		    list.add("loreal");
		    list.add("fiama");

		    //instantiate custom adapter
		    CampaignAdapter adapter = new CampaignAdapter(list, this);

		    //handle listview and assign adapter
		    ListView lView = (ListView)findViewById(R.id.list_view);
		    lView.setAdapter(adapter);
	}
	}