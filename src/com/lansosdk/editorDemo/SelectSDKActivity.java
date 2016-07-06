package com.lansosdk.editorDemo;


import com.lansoeditor.demo.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class SelectSDKActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.select_sdk_layout);
		
		
		findViewById(R.id.id_selectsdk_item1).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				gotoActivity(MainActivity.class);
			}
		});
		
		findViewById(R.id.id_selectsdk_item2).setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						gotoActivity(AdvanceSDKActivity.class);
					}
				});
	}
	private void gotoActivity(Class<?> cls)
	{
		startActivity(new Intent(SelectSDKActivity.this,cls));
		
	}
}
