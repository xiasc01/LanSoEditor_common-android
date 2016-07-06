package com.lansosdk.editorDemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.lansoeditor.demo.R;

public class SelectMoreActivity extends Activity implements OnClickListener{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.function_more_select);
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		 switch (v.getId()) {
			case R.id.id_function_videoscale:
				
				break;
			case R.id.id_function_audiomix:
//				gotoActivity()
				break;
		default:
			break;
		}
	}
	private void gotoActivity(Class<?> cls)
	{
		startActivity(new Intent(SelectMoreActivity.this,cls));
		
	}
}
