package com.lansosdk.editorDemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.lansoeditor.demo.R;
import com.lansosdk.editorDemo.wrapper.CmdId;

public class FunctionItem2Activity  extends Activity implements OnClickListener{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.function_item2_layout);
		
		findViewById(R.id.id_function_item2_videooverlay).setOnClickListener(this);
		findViewById(R.id.id_function_item2_videocompress).setOnClickListener(this);
		findViewById(R.id.id_function_item2_videoscale).setOnClickListener(this);
		findViewById(R.id.id_function_item2_videocrop).setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
				case R.id.id_function_item2_videooverlay:
					gotoActivity(CmdId.NONE);
					break;
				case R.id.id_function_item2_videocompress:
					gotoActivity(CmdId.VIDEO_COMPRESS_WRAPPER);
					break;
				case R.id.id_function_item2_videoscale:
					gotoActivity(CmdId.VIDEO_SCALE_WRAPPER);
					break;
				case R.id.id_function_item2_videocrop:
					gotoActivity(CmdId.VIDEO_CROP_WRAPPER);
					break;
				default:
					break;
		}
	}
	  private void gotoActivity(int cmdId)
	    {
	    	Intent intent=new Intent(FunctionItem2Activity.this,VideoWrapperEditorActivity.class);
	    	intent.putExtra("CMD_ID", cmdId);
	    	startActivity(intent);
	    }
}
