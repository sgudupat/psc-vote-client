package com.client.vote;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class RegretActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		Log.i("RegretActivity", "inside regret page");
		setContentView(R.layout.regret);
	}

}
