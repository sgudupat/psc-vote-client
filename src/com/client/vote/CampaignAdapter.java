package com.client.vote;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;

public class CampaignAdapter  extends BaseAdapter implements ListAdapter { 
	private ArrayList<String> list = new ArrayList<String>(); 
	private Context context; 



	public CampaignAdapter(ArrayList<String> list, Context context) { 
	    this.list = list; 
	    this.context = context; 
	} 


	public int getCount() { 
	    return list.size(); 
	} 


	public Object getItem(int pos) { 
	    return list.get(pos); 
	} 





	public View getView(final int position, View convertView, ViewGroup parent) {
	    View view = convertView;
	    if (view == null) {
	        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE); 
	        view = inflater.inflate(R.layout.campaign_list, null);
	    } 

	    //Handle TextView and display string from your list
	    TextView listItemText = (TextView)view.findViewById(R.id.ltextView2); 
	    listItemText.setText(list.get(position)); 

	    //Handle buttons and add onClickListeners
	    Button moreBtn = (Button)view.findViewById(R.id.lbutton1);
	  
	    return view; 
	}


	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	} 
	}

