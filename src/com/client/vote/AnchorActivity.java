package com.client.vote;
import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class AnchorActivity extends Activity{

@Override
protected void onCreate(Bundle savedInstanceState) {

    super.onCreate(savedInstanceState);

    setContentView(R.layout.list); 

    //generate list
    ArrayList<String> list = new ArrayList<String>();
    list.add("loreal");
    list.add("fiama");

    //instantiate custom adapter
    MyCustomAdapter adapter = new MyCustomAdapter(list, this);

    //handle listview and assign adapter
    ListView lView = (ListView)findViewById(R.id.list_view);
    lView.setAdapter(adapter);
}

}