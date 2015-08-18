package com.client.vote;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;
import com.client.vote.domain.Campaign;

import java.util.ArrayList;
import java.util.List;

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
        endDate.setText("End Date : " + list.get(position).getEndDate());

        Button ciStop = (Button) view.findViewById(R.id.ci_stopBtn);
        Button ciEdit = (Button) view.findViewById(R.id.ci_editBtn);
        Button ciDelete = (Button) view.findViewById(R.id.ci_deleteBtn);
        Button ciReward = (Button) view.findViewById(R.id.ci_rewardBtn);
        Button ciInsight = (Button) view.findViewById(R.id.ci_insightBtn);

        ciStop.setVisibility(View.VISIBLE);
        ciEdit.setVisibility(View.VISIBLE);
        ciDelete.setVisibility(View.VISIBLE);
        ciReward.setVisibility(View.VISIBLE);
        ciInsight.setVisibility(View.VISIBLE);

        Button rewardInfoBtn = (Button) view.findViewById(R.id.ci_rewardBtn);
        rewardInfoBtn.setOnClickListener(new ShowRewardInfoListener(list.get(position).getAnchorName(), list.get(position).getCampaignId()));
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
}