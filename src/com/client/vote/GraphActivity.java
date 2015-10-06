package com.client.vote;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class GraphActivity extends Activity {

    LinearLayout lc;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.graphview);
        lc = (LinearLayout) findViewById(R.id.chart);       
        lc.setVisibility(View.GONE);

    }

    public void toggle_contents(View v) {        
        lc.setVisibility(lc.isShown()
                ? View.GONE
                : View.VISIBLE);
    }

}
