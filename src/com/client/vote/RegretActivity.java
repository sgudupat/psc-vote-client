package com.client.vote;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class RegretActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		Log.i("RegretActivity", "inside regret page");
		setContentView(R.layout.regret);
	}
	public void loginPage(View view)
	{
		Intent intent = new Intent(this, HomePageActivity.class);
		startActivity(intent);
	}

}
