package com.client.vote;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class AnchorActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.anchor_summary);

        //generate anchor_summary
        ArrayList<String> list = new ArrayList<String>();
        list.add("loreal");
        list.add("fiama");

        //instantiate custom adapter
        MyCustomAdapter adapter = new MyCustomAdapter(list, this);

        //handle listview and assign adapter
        ListView lView = (ListView) findViewById(R.id.list_view);
        lView.setAdapter(adapter);
    }

}