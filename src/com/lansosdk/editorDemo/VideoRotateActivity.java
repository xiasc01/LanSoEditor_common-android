package com.lansosdk.editorDemo;

import android.app.Activity;
import android.os.Bundle;

import com.lansoeditor.demo.R;

public class VideoRotateActivity extends Activity{

	String videoPath=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.video_edit_demo_layout);
		 videoPath=getIntent().getStringExtra("videopath");
		
		 
		 
		 
	}
}

