package com.client.vote;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class NewAnchorActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("NewAnchorActivity", "inside new anchor page");
        setContentView(R.layout.create_newanchor);
    }
}