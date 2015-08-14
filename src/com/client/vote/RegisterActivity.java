package com.client.vote;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class RegisterActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i("RegisterActivity", "inside client register page");
		setContentView(R.layout.sign_up);
	}
	public void regret(View view)
	{
		//Intent intent = new Intent(this, RegretActivity.class);
		//startActivity(intent);
	}

	
}

