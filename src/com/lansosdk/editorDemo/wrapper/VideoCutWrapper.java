package com.lansosdk.editorDemo.wrapper;

import android.util.Log;
import android.view.View;

import com.lansoeditor.demo.R;

public class VideoCutWrapper extends CmdWrapper{

	public VideoCutWrapper()
	{
		super();
		VideoPlayVisibility=View.VISIBLE;
		AudioPlayVisibility=View.INVISIBLE;
	}
	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return "视频剪切";
	}
	@Override
	public int getHint() {
		// TODO Auto-generated method stub
		return R.string.videocut_hint;
	}
	
	public void doCommand()
	{
		Log.i("sno","isPrepareSuccess:"+isPrepareSuccess);
		if(isPrepareSuccess){
			mEditor.executeVideoCutOut(srcPath,dstVideo,0,mInfo.vDuration/2);
		}
	}
	
	public boolean prepare()
	{
		if(super.prepare())
		{
			return true;
		}
		else
			return false;
	}
	
}
