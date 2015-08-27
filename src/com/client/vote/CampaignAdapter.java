package com.client.vote;

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
import com.client.vote.common.CampaignUtil;
import com.client.vote.common.SimpleHttpClient;
import com.client.vote.domain.Campaign;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.Date;
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


        /*private View mChart;
       
        @Override
        public void onClick(View v) {
             String[] mMonth = new String[] {
                        "Jan", "Feb" , "Mar", "Apr", "May", "Jun",
                        "Jul", "Aug" , "Sep", "Oct", "Nov", "Dec"
                    };


                        int[] x = { 0,1,2,3};
                        int[] age = { 10-20,30-40,50-60,60-70};
                        int[] count= {100, 2700, 2900, 2800  };

                        // Creating an  XYSeries for Income
                        XYSeries incomeSeries = new XYSeries("age");
                        // Creating an  XYSeries for Expense
                        XYSeries expenseSeries = new XYSeries("count");
                        // Adding data to Income and Expense Series
                        for(int i=0;i<x.length;i++){
                            incomeSeries.add(i,age[i]);
                            expenseSeries.add(i,count[i]);
                        }

                        // Creating a dataset to hold each series
                        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
                        // Adding Income Series to the dataset
                        dataset.addSeries(incomeSeries);
                        // Adding Expense Series to dataset
                        dataset.addSeries(expenseSeries);

                        // Creating XYSeriesRenderer to customize incomeSeries
                        XYSeriesRenderer incomeRenderer = new XYSeriesRenderer();
                        incomeRenderer.setColor(Color.rgb(130, 130, 230));
                        incomeRenderer.setFillPoints(true);
                        incomeRenderer.setLineWidth(2);
                        incomeRenderer.setDisplayChartValues(true);

                        // Creating XYSeriesRenderer to customize expenseSeries
                        XYSeriesRenderer expenseRenderer = new XYSeriesRenderer();
                        expenseRenderer.setColor(Color.rgb(220, 80, 80));
                        expenseRenderer.setFillPoints(true);
                        expenseRenderer.setLineWidth(2);
                        expenseRenderer.setDisplayChartValues(true);

                        // Creating a XYMultipleSeriesRenderer to customize the whole chart
                        XYMultipleSeriesRenderer multiRenderer = new XYMultipleSeriesRenderer();
                        multiRenderer.setXLabels(0);
                       // multiRenderer.setChartTitle("Income vs Expense Chart");
                      //  multiRenderer.setXTitle("Year 2012");
                       // multiRenderer.setYTitle("Amount in Dollars");
                        multiRenderer.setZoomButtonsVisible(false);
                        for(int i=0; i< x.length;i++){
                            multiRenderer.addXTextLabel(i, mMonth[i]);
                        }

                        // Adding incomeRenderer and expenseRenderer to multipleRenderer
                        // Note: The order of adding dataseries to dataset and renderers to multipleRenderer
                        // should be same
                        multiRenderer.addSeriesRenderer(incomeRenderer);
                        multiRenderer.addSeriesRenderer(expenseRenderer);

                        // Creating an intent to plot bar chart using dataset and multipleRenderer
                        Intent intent = ChartFactory.getBarChartIntent(context, dataset, multiRenderer, Type.DEFAULT);

                        // Start Activity
                        context.startActivity(intent);

           
        }*/
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
            Log.i("campaignid", campaignId);
            final ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
            postParameters.add(new BasicNameValuePair("campaignId", campaignId));
            postParameters.add(new BasicNameValuePair("status", message));
            try {
                String response = SimpleHttpClient.executeHttpPost("/updateCampaignStatus", postParameters);
                Log.i("Response:", response);
                Intent intent = new Intent(context, CampaignSummaryActivity.class);
                intent.putExtra("anchorName", anchorName);
                context.startActivity(intent);
            } catch (Exception e) {
                Log.e("register", e.getMessage() + "");
            }
        }
    }
}