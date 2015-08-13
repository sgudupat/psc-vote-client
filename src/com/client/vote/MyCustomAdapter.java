package com.client.vote;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;

public class MyCustomAdapter extends BaseAdapter implements ListAdapter { 
private ArrayList<String> list = new ArrayList<String>(); 
private Context context; 



public MyCustomAdapter(ArrayList<String> list, Context context) { 
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
        view = inflater.inflate(R.layout.anchor, null);
    } 

    //Handle TextView and display string from your list
    TextView listItemText = (TextView)view.findViewById(R.id.list_item_string); 
    listItemText.setText(list.get(position)); 

    //Handle buttons and add onClickListeners
    Button moreBtn = (Button)view.findViewById(R.id.more_btn);
    Button deleteBtn = (Button)view.findViewById(R.id.delete_btn);
    Button addBtn = (Button)view.findViewById(R.id.add_btn);

    deleteBtn.setOnClickListener(new View.OnClickListener(){
        @Override
        public void onClick(View v) { 
            //do something
            list.remove(position); //or some other task
            //notifyDataSetChanged();
        }
    });
    addBtn.setOnClickListener(new View.OnClickListener(){
        @Override
        public void onClick(View v) { 
            //do something
           // notifyDataSetChanged();
        }
    });

    return view; 
}


@Override
public long getItemId(int position) {
	// TODO Auto-generated method stub
	return 0;
} 
}
