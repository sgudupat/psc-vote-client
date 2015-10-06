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
		setContentView(R.layout.campaign_insight);
		lc = (LinearLayout) findViewById(R.id.insightpiechart_age);	       
		lc.setVisibility(View.GONE);
		rc = (LinearLayout) findViewById(R.id.insightregionchart);	       

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
		int length=x.length;
		for(int i=0;i<length;i++){
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
		int length1=x.length;
		for(int i=0; i< length1;i++){
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
		final ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		postParameters.add(new BasicNameValuePair("campaignId", campaignId));

		try {
			response = SimpleHttpClient.executeHttpPost("/displayStats", postParameters);			
			JSONObject jsonobject = new JSONObject(response);

			String Value = jsonobject.getString("values");
			JSONArray jsonArray2 = new JSONArray(Value);
			int jsonArray3=jsonArray2.length();
			for (int j = 0; j < jsonArray3; j++) {				
				jsonobject = jsonArray2.getJSONObject(j);				
				String ager = jsonobject.getString("age");				
				String cntValue = jsonobject.getString("cnt_value");				
				count = Integer.parseInt(cntValue);
				ageRange.add(ager);
				countValue.add(count);
			}
		} catch (Exception e) {			
		}
		// Pie Chart Section Names
		// Color of each Pie Chart Sections
		int[] colors = {Color.BLUE, Color.MAGENTA, Color.GREEN, Color.CYAN, Color.RED, Color.YELLOW};

		// Instantiating CategorySeries to plot Pie Chart
		CategorySeries distributionSeries = new CategorySeries("Insight Campaign Statistics");
		int countValueSize=countValue.size();
		for (Integer i = 0; i < countValueSize; i++) {
			// Adding a slice with its values and name to the Pie Chart
			distributionSeries.add(ageRange.get(i), countValue.get(i));
		}

		// Instantiating a renderer for the Pie Chart
		DefaultRenderer defaultRenderer = new DefaultRenderer();
		int countValuesize=countValue.size();
		for (Integer i = 0; i < countValuesize; i++) {
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
		final ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		postParameters.add(new BasicNameValuePair("campaignId", campaignId));

		try {
			response = SimpleHttpClient.executeHttpPost("/displayStats", postParameters);			
			JSONObject jsonobject = new JSONObject(response);

			String Value = jsonobject.getString("values");
			JSONArray jsonArray2 = new JSONArray(Value);
			int jsonArray3=jsonArray2.length();
			for (int j = 0; j < jsonArray3; j++) {                
				jsonobject = jsonArray2.getJSONObject(j);               
				String ager = jsonobject.getString("age");               
				String cntValue = jsonobject.getString("cnt_value");                
				count = Integer.parseInt(cntValue);
				ageRange.add(ager);
				countValue.add(count);
			}
		} catch (Exception e) {            
		}
		// Pie Chart Section Names
		// Color of each Pie Chart Sections
		int[] colors = {Color.BLUE, Color.MAGENTA, Color.GREEN, Color.CYAN, Color.RED, Color.YELLOW};

		// Instantiating CategorySeries to plot Pie Chart
		CategorySeries distributionSeries = new CategorySeries("Insight Campaign Statistics");
		int countValuesize=countValue.size();
		for (Integer i = 0; i < countValuesize; i++) {
			// Adding a slice with its values and name to the Pie Chart
			distributionSeries.add(ageRange.get(i), countValue.get(i));
		}

		// Instantiating a renderer for the Pie Chart
		DefaultRenderer defaultRenderer = new DefaultRenderer();
		int countValueSize=countValue.size();
		for (Integer i = 0; i < countValueSize; i++) {
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
		lc.setVisibility(lc.isShown()
				? View.GONE
						: View.VISIBLE);

	}
	public void toggle_contentsregion(View v) {       
		rc.setVisibility(rc.isShown()
				? View.GONE
						: View.VISIBLE);
	}

}