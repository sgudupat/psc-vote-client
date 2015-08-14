package com.client.vote;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class ForgetPasswordActivity extends Activity{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		Log.i("ForgetPasswordActivity","inside forget password page");
		setContentView(R.layout.forget_password);
	}

}
