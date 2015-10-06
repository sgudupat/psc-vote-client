package com.client.vote;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.client.vote.common.CampaignUtil;
import com.client.vote.common.SimpleHttpClient;
import com.client.vote.domain.Campaign;

public class CampaignAdapter extends BaseAdapter implements ListAdapter {

	private List<Campaign> list = new ArrayList<Campaign>();
	private Context context;

	public CampaignAdapter(List<Campaign> list, Context context) {
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
			view = inflater.inflate(R.layout.campaign_item, null);
		}
		//Handle TextView and display string from your list
		TextView question = (TextView) view.findViewById(R.id.ltextView2);
		TextView startDate = (TextView) view.findViewById(R.id.ltextView4);
		TextView endDate = (TextView) view.findViewById(R.id.ltextView5);
		question.setText(list.get(position).getQuestion());
		startDate.setText("Start Date : " + list.get(position).getStartDate());
		Date endDateValue = list.get(position).getEndDate();
		endDate.setText("End Date : " + endDateValue);
		Button ciStop = (Button) view.findViewById(R.id.ci_stopBtn);
		Button ciEdit = (Button) view.findViewById(R.id.ci_editBtn);
		Button ciDelete = (Button) view.findViewById(R.id.ci_deleteBtn);
		Button ciReward = (Button) view.findViewById(R.id.ci_rewardBtn);
		Button ciInsight = (Button) view.findViewById(R.id.ci_insightBtn);
		ciStop.setVisibility(View.INVISIBLE);
		ciDelete.setVisibility(View.INVISIBLE);
		ciEdit.setVisibility(View.INVISIBLE);

		String status = list.get(position).getStatus();

		if (!CampaignUtil.isCampaignStopped(status, endDateValue)) {
			ciStop.setVisibility(View.VISIBLE);
		}
		if (!CampaignUtil.isCampaignExpired(endDateValue)) {
			ciDelete.setVisibility(View.VISIBLE);
		}
		if (CampaignUtil.isCampaignEditable(status, endDateValue)) {
			ciEdit.setVisibility(View.VISIBLE);
		}
		TextView CIMessage = (TextView) view.findViewById(R.id.ci_statusMsg);
		CIMessage.setText(CampaignUtil.campaignStatusMessage(status, endDateValue));
		ciReward.setVisibility(View.VISIBLE);
		ciInsight.setVisibility(View.VISIBLE);

		String anchorName = list.get(position).getAnchorName();
		String campaignId = list.get(position).getCampaignId();

		ciStop.setOnClickListener(new ButtonActionListener(anchorName, campaignId, "STOPPED"));
		ciDelete.setOnClickListener(new ButtonActionListener(anchorName, campaignId, "DELETED"));
		ciEdit.setOnClickListener(new ModifyCampaignListener(anchorName, campaignId));
		ciReward.setOnClickListener(new ShowRewardInfoListener(anchorName, campaignId));
		ciInsight.setOnClickListener(new BarchartInfoListener());
		return view;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	public class ShowRewardInfoListener implements View.OnClickListener {
		private String anchorName;
		private String campaignId;

		public ShowRewardInfoListener(String anchorName, String campaignId) {
			this.anchorName = anchorName;
			this.campaignId = campaignId;
		}

		@Override
		public void onClick(View v) {
			Intent intent = new Intent(context, RewardActivity.class);
			intent.putExtra("anchorName", anchorName);
			intent.putExtra("campaignId", campaignId);
			context.startActivity(intent);
		}
	}

	public class BarchartInfoListener implements View.OnClickListener {


		public void onClick(View v) {
			Intent intent = new Intent(context, GraphActivity.class);
			context.startActivity(intent);
		}


	}


	public class ModifyCampaignListener implements View.OnClickListener {
		private String anchorName;
		private String campaignId;

		public ModifyCampaignListener(String anchorName, String campaignId) {
			this.anchorName = anchorName;
			this.campaignId = campaignId;
		}

		@Override
		public void onClick(View v) {
			Intent intent = new Intent(context, NewCampaignActivity.class);
			intent.putExtra("anchorName", anchorName);
			intent.putExtra("campaignId", campaignId);
			intent.putExtra("modify", "Y");
			context.startActivity(intent);
		}
	}

	public class ButtonActionListener implements View.OnClickListener {
		private String anchorName;
		private String campaignId;
		private String message;

		public ButtonActionListener(String anchorName, String campaignId, String message) {
			this.anchorName = anchorName;
			this.campaignId = campaignId;
			this.message = message;
		}

		@Override
		public void onClick(View v) {           
			final ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
			postParameters.add(new BasicNameValuePair("campaignId", campaignId));
			postParameters.add(new BasicNameValuePair("status", message));
			try {
				String response = SimpleHttpClient.executeHttpPost("/updateCampaignStatus", postParameters);                
				Intent intent = new Intent(context, CampaignSummaryActivity.class);
				intent.putExtra("anchorName", anchorName);
				context.startActivity(intent);
			} catch (Exception e) {                
			}
		}
	}
}