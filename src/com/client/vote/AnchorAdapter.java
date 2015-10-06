package com.client.vote;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.ActivityGroup;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.client.vote.common.SimpleHttpClient;
import com.client.vote.domain.Anchor;

public class AnchorAdapter extends BaseAdapter implements ListAdapter {
	private ArrayList<Anchor> list = new ArrayList<Anchor>();
	private Context context;

	public AnchorAdapter(ArrayList<Anchor> list, Context context) {
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
			view = inflater.inflate(R.layout.anchor_item, null);
		}

		//Handle TextView and display string from your anchor_summary
		TextView listItemText = (TextView) view.findViewById(R.id.list_item_string);
		listItemText.setText(list.get(position).getAnchorName());

		//Handle buttons and add onClickListeners
		Button deleteBtn = (Button) view.findViewById(R.id.delete_btn);
		deleteBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				try {
					final ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
					postParameters.add(new BasicNameValuePair("anchorId", list.get(position).getAnchorName().toString()));

					String response = SimpleHttpClient.executeHttpPost("/deleteAnchor", postParameters);                    
					if (response.contains("success")) {

						list.remove(position);
						notifyDataSetChanged();
					}
				} catch (Exception e) {
					Log.e("register", e.getMessage() + "");
				}
			}
		});
		Button campaignBtn = (Button) view.findViewById(R.id.show_campaign_btn);
		campaignBtn.setOnClickListener(new ShowCampaignListener(list.get(position).getAnchorName()));
		return view;
	}


	@Override
	public long getItemId(int position) {
		return 0;
	}

	public class ShowCampaignListener implements View.OnClickListener {
		private String anchorName;

		public ShowCampaignListener(String anchorName) {
			this.anchorName = anchorName;
		}

		@Override
		public void onClick(View v) {
			/*  Intent intent = new Intent(context, CampaignSummaryActivity.class);

            context.startActivity(intent);*/
			Intent intent = new Intent(context, CampaignSummaryActivity.class);
			intent.putExtra("anchorName", anchorName);
			replaceContentView("campaign_summary", intent);
		}

		public void replaceContentView(String id, Intent newIntent) {
			View view = ((ActivityGroup) context).getLocalActivityManager().startActivity(id, newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)).getDecorView();
			((Activity) context).setContentView(view);
		}

	}
}