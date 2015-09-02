package com.client.vote;

import java.util.ArrayList;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.BarChart;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.client.vote.common.SimpleHttpClient;

public class CampaignInsightActivity extends Activity {
	String campaignId;
	String userName;
	String question;
	String anchorName;
	String clientName;
	GraphicalView Barchart;
	private GraphicalView mChartView;
	private XYMultipleSeriesDataset dataset;
	private XYMultipleSeriesRenderer renderer;
	private BarChart.Type type;
	 private Context context=this;
	 LinearLayout lc;
	 LinearLayout rc;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 Log.i("CampaignInsightActivity", "inside insightpage ");
		setContentView(R.layout.campaign_insight);
		 lc = (LinearLayout) findViewById(R.id.insightpiechart_age);
	        Log.i("lc", "linear layout view");
	        lc.setVisibility(View.GONE);
	        rc = (LinearLayout) findViewById(R.id.insightregionchart);
	        Log.i("rc", "Linear Layout View");
	        rc.setVisibility(View.GONE);
		/*Intent intent = getIntent();
	
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
		
	        anchorName = intent.getStringExtra("anchorName");
	        clientName = intent.getStringExtra("clientName");
	        campaignId = intent.getStringExtra("campaignId");
	        userName = preferences.getString("username", "");
			question = preferences.getString("question", "");
			 */
	       
	       
	        
		
	
		/*BarGraph bar = new BarGraph();
        Intent barIntent = bar.getIntent(this);
        startActivity(barIntent);*/
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
                 LinearLayout barchart=(LinearLayout)findViewById(R.id.Barchart);
                 // Creating an intent to plot bar chart using dataset and multipleRenderer
                 mChartView = ChartFactory.getBarChartView(context, dataset, multiRenderer, type);
                 barchart.addView(mChartView);
                 // Start Activity
                 

		
			   openAgeChart(campaignId);
			   openRegionChart(campaignId);
			
			   
    }
	/* protected void onResume() {
	      super.onResume(); 
	      if (mChartView == null) {
	        LinearLayout layout = (LinearLayout) findViewById(R.id.Barchart);
	        getBarChartView(android.content.Context context, XYMultipleSeriesDataset dataset, XYMultipleSeriesRenderer renderer, BarChart.Type type)
	          Creates a bar chart view.
	        mChartView = ChartFactory.getBarChartView(this, dataset, renderer, type);//tView(this,getBarDemoDataset(values),renderer,Type.DEFAULT);
	        layout.addView(mChartView);
	      } else {
	        mChartView.repaint();
	      }
	    }
	*/

	/* public class BarchartInfoListener implements View.OnClickListener {


	        private View mChart;
	       
	    
	        public void onClick1(View v) {
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

	           
	        }


			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
	       

	    }*/


	

	

    private void openAgeChart(String campaignId) {
        GraphicalView mChartView;
        String response = "";
        ArrayList<String> ageRange = new ArrayList<String>();
        ArrayList<Integer> countValue = new ArrayList<Integer>();
        ageRange.add("10_20");
        ageRange.add("20_30");
        ageRange.add("30_40");
        countValue.add(2);
        countValue.add(4);
        countValue.add(6);
        int count = 0;
        Log.i("displayAgeComparison:", "displayAgeComparison");
        final ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
        postParameters.add(new BasicNameValuePair("campaignId", campaignId));

        try {
            response = SimpleHttpClient.executeHttpPost("/displayStats", postParameters);
            Log.i("Response:", response);
            JSONObject jsonobject = new JSONObject(response);
            
            String Value = jsonobject.getString("values");
            JSONArray jsonArray2 = new JSONArray(Value);
            for (int j = 0; j < jsonArray2.length(); j++) {
                Log.i("inside", "inside campaign");
                jsonobject = jsonArray2.getJSONObject(j);
                Log.i("campaign ", "json object built");
                String ager = jsonobject.getString("age");
                Log.i("agerane", ager);
                String cntValue = jsonobject.getString("cnt_value");
                Log.i("client id", cntValue);
                count = Integer.parseInt(cntValue);
                ageRange.add(ager);
                countValue.add(count);
            }
        } catch (Exception e) {
            Log.i("Response 2:Error:", e.getMessage());
        }
        // Pie Chart Section Names
        // Color of each Pie Chart Sections
        int[] colors = {Color.BLUE, Color.MAGENTA, Color.GREEN, Color.CYAN, Color.RED, Color.YELLOW};

        // Instantiating CategorySeries to plot Pie Chart
        CategorySeries distributionSeries = new CategorySeries("Insight Campaign Statistics");
        for (Integer i = 0; i < countValue.size(); i++) {
            // Adding a slice with its values and name to the Pie Chart
            distributionSeries.add(ageRange.get(i), countValue.get(i));
        }

        // Instantiating a renderer for the Pie Chart
        DefaultRenderer defaultRenderer = new DefaultRenderer();
        for (Integer i = 0; i < countValue.size(); i++) {
            SimpleSeriesRenderer seriesRenderer = new SimpleSeriesRenderer();
            seriesRenderer.setColor(colors[i]);
            seriesRenderer.setDisplayChartValues(true);
            // Adding a renderer for a slice
            defaultRenderer.addSeriesRenderer(seriesRenderer);
        }

        defaultRenderer.setChartTitle("");
        defaultRenderer.setChartTitleTextSize(10);
        defaultRenderer.setZoomButtonsVisible(false);
        LinearLayout layout = (LinearLayout) findViewById(R.id.insightpiechart_age);


        // Creating an intent to plot bar chart using dataset and multipleRenderer
        mChartView = ChartFactory.getPieChartView(this, distributionSeries, defaultRenderer);
        layout.addView(mChartView);
        // Start Activity
        // startActivity(intent);
       
       
       

    }
    private void openRegionChart(String campaignId) {
        GraphicalView mChartView;
        String response = "";
        ArrayList<String> ageRange = new ArrayList<String>();
        ArrayList<Integer> countValue = new ArrayList<Integer>();
        ageRange.add("10_20");
        ageRange.add("20_30");
        ageRange.add("30_40");
        countValue.add(2);
        countValue.add(4);
        countValue.add(6);
        int count = 0;
        Log.i("displayAgeComparison:", "displayAgeComparison");
        final ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
        postParameters.add(new BasicNameValuePair("campaignId", campaignId));

        try {
            response = SimpleHttpClient.executeHttpPost("/displayStats", postParameters);
            Log.i("Response:", response);
            JSONObject jsonobject = new JSONObject(response);
            
            String Value = jsonobject.getString("values");
            JSONArray jsonArray2 = new JSONArray(Value);
            for (int j = 0; j < jsonArray2.length(); j++) {
                Log.i("inside", "inside campaign");
                jsonobject = jsonArray2.getJSONObject(j);
                Log.i("campaign ", "json object built");
                String ager = jsonobject.getString("age");
                Log.i("agerane", ager);
                String cntValue = jsonobject.getString("cnt_value");
                Log.i("client id", cntValue);
                count = Integer.parseInt(cntValue);
                ageRange.add(ager);
                countValue.add(count);
            }
        } catch (Exception e) {
            Log.i("Response 2:Error:", e.getMessage());
        }
        // Pie Chart Section Names
        // Color of each Pie Chart Sections
        int[] colors = {Color.BLUE, Color.MAGENTA, Color.GREEN, Color.CYAN, Color.RED, Color.YELLOW};

        // Instantiating CategorySeries to plot Pie Chart
        CategorySeries distributionSeries = new CategorySeries("Insight Campaign Statistics");
        for (Integer i = 0; i < countValue.size(); i++) {
            // Adding a slice with its values and name to the Pie Chart
            distributionSeries.add(ageRange.get(i), countValue.get(i));
        }

        // Instantiating a renderer for the Pie Chart
        DefaultRenderer defaultRenderer = new DefaultRenderer();
        for (Integer i = 0; i < countValue.size(); i++) {
            SimpleSeriesRenderer seriesRenderer = new SimpleSeriesRenderer();
            seriesRenderer.setColor(colors[i]);
            seriesRenderer.setDisplayChartValues(true);
            // Adding a renderer for a slice
            defaultRenderer.addSeriesRenderer(seriesRenderer);
        }

        defaultRenderer.setChartTitle("");
        defaultRenderer.setChartTitleTextSize(10);
        defaultRenderer.setZoomButtonsVisible(false);
        LinearLayout layout = (LinearLayout) findViewById(R.id.insightregionchart);


        // Creating an intent to plot bar chart using dataset and multipleRenderer
        mChartView = ChartFactory.getPieChartView(this, distributionSeries, defaultRenderer);
        layout.addView(mChartView);
        // Start Activity
        // startActivity(intent);
       

      

    }

    public void toggle_contentsage(View v) {
        Log.i("toggle function", "inside toggle view");
        lc.setVisibility(lc.isShown()
                ? View.GONE
                : View.VISIBLE);
       
    }
    public void toggle_contentsregion(View v) {
        Log.i("toggle function", "inside toggle view");
        rc.setVisibility(rc.isShown()
                ? View.GONE
                : View.VISIBLE);
    }
   
}