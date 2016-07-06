package com.lansosdk.editorDemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.lansoeditor.demo.R;
import com.lansosdk.editorDemo.wrapper.CmdId;

public class FunctionItem3Activity extends Activity implements OnClickListener{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.function_item3_layout);
		
		findViewById(R.id.id_function_item3_videoaddimg).setOnClickListener(this);
		findViewById(R.id.id_function_item3_video2img).setOnClickListener(this);
		findViewById(R.id.id_function_item3_img2video).setOnClickListener(this);
		findViewById(R.id.id_function_item3_imagefade).setOnClickListener(this);
		
		findViewById(R.id.id_function_item3_oneimage2video).setOnClickListener(this);
		
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
				case R.id.id_function_item3_videoaddimg:
					gotoActivity(CmdId.VIDEO_ADDIMAGE_WRAPPER);
				break;
				case R.id.id_function_item3_video2img:
					gotoActivity(CmdId.EXTRACT_IMAGE_WRAPPER);
					break;
				case R.id.id_function_item3_img2video:
					gotoActivity(CmdId.NONE);
					break;
				case R.id.id_function_item3_imagefade:
					gotoActivity(CmdId.NONE);
					break;
				case R.id.id_function_item3_oneimage2video:
					gotoActivity(CmdId.ONE_IMAGE_FADE_WRAPPER);
					break;
				default:
					break;
		}
	}
	  private void gotoActivity(int cmdId)
	    {
	    	Intent intent=new Intent(FunctionItem3Activity.this,VideoWrapperEditorActivity.class);
	    	intent.putExtra("CMD_ID", cmdId);
	    	startActivity(intent);
	    }
}
