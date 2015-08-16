package com.client.vote;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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
        TextView campaignId = (TextView) view.findViewById(R.id.ltextView2);
        TextView startDate = (TextView) view.findViewById(R.id.ltextView4);
        TextView endDate = (TextView) view.findViewById(R.id.ltextView5);
        campaignId.setText(list.get(position).getCampaignId());
        startDate.setText("Start Date : " + list.get(position).getStartDate());
        endDate.setText("End Date : " + list.get(position).getEndDate());
        return view;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
}