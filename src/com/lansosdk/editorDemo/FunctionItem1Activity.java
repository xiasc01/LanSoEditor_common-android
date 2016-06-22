package com.lansosdk.editorDemo;

import com.lansoeditor.demo.R;
import com.lansosdk.editorDemo.wrapper.CmdId;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

public class FunctionItem1Activity extends Activity implements OnClickListener{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.function_item1_layout);
		
		findViewById(R.id.id_function_item1_avsplit).setOnClickListener(this);
		findViewById(R.id.id_function_item1_avcompose).setOnClickListener(this);
		findViewById(R.id.id_function_item1_audiocut).setOnClickListener(this);
		findViewById(R.id.id_function_item1_videocut).setOnClickListener(this);
		findViewById(R.id.id_function_item1_videoconnect).setOnClickListener(this);
		findViewById(R.id.id_function_item1_audiomix).setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
				case R.id.id_function_item1_avsplit:
					gotoActivity(CmdId.AV_SPLIT_WRAPPER);
					break;
				case R.id.id_function_item1_avcompose:
					gotoActivity(CmdId.NONE);
					break;
				case R.id.id_function_item1_audiocut:
					gotoActivity(CmdId.AUDIO_CUT_WRAPPER);
					break;
				case R.id.id_function_item1_videocut:
					gotoActivity(CmdId.VIDEO_CUT_WRAPPER);
					break;
				case R.id.id_function_item1_videoconnect:
					gotoActivity(CmdId.NONE);
					break;
				case R.id.id_function_item1_audiomix:
					gotoActivity(CmdId.NONE);
					break;
					
				default:
					break;
		}
	}
	  private void gotoActivity(int cmdId)
	    {
	    	Intent intent=new Intent(FunctionItem1Activity.this,VideoWrapperEditorActivity.class);
	    	intent.putExtra("CMD_ID", cmdId);
	    	startActivity(intent);
	    }
}
