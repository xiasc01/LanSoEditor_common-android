package com.lansosdk.editorDemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.lansoeditor.demo.R;

public class AdvanceSDKActivity extends Activity implements OnClickListener{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.functions_advance_layout);
		
		findViewById(R.id.id_advance_filter).setOnClickListener(this);
		findViewById(R.id.id_advance_overlay).setOnClickListener(this);
		findViewById(R.id.id_advance_more).setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		switch (v.getId()) {
			case R.id.id_advance_filter:
				gotoActivity(SelectFilterActivity.class);
				break;
			case R.id.id_advance_overlay:
				gotoActivity(SelectOverlayActivity.class);			
				break;
			case R.id.id_advance_more:
				gotoActivity(SelectMoreActivity.class);		
				break;

		default:
			break;
		}
	}
	private void gotoActivity(Class<?> cls)
	{
		startActivity(new Intent(AdvanceSDKActivity.this,cls));
		
	}
}
