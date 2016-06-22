package com.lansosdk.editorDemo.wrapper;

import android.util.Log;
import android.view.View;

import com.lansoeditor.demo.R;

public class ExtractImageWrapper  extends CmdWrapper{

	public ExtractImageWrapper()
	{
		super();
		VideoPlayVisibility=View.GONE;
		AudioPlayVisibility=View.GONE;
	}
	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return "从视频中提取图片";
	}
	@Override
	public int getHint() {
		// TODO Auto-generated method stub
		return R.string.extractimage_hint;
	}
	
	public void doCommand()
	{
		Log.i("sno","isPrepareSuccess:"+isPrepareSuccess);
		if(isPrepareSuccess){
			mEditor.executeGetSomeFrames(srcPath,"/sdcard/","img",10/mInfo.vDuration);
		}
	}
	public boolean prepare()
	{
		//这里额外检查是否有视频轨道.
		if(super.prepare() && mInfo.vBitRate>0 && mInfo.vWidth>0 && mInfo.vHeight>0)
		{
			return true;
		}
		else
			return false;
	}
	
}
