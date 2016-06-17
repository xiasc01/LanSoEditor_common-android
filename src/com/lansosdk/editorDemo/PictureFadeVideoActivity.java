package com.lansosdk.editorDemo;

import android.app.Activity;
import android.os.Bundle;

import com.lansoeditor.demo.R;

/**
 * 一张图片 组成和视频,并有渐入检出的效果. 
 *
 */
public class PictureFadeVideoActivity extends Activity{

	String videoPath=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.video_edit_demo_layout);
		 videoPath=getIntent().getStringExtra("videopath");
		
		 
		 
		 
	}
}