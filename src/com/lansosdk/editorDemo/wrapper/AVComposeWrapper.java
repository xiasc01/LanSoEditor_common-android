package com.lansosdk.editorDemo.wrapper;

import android.util.Log;
import android.view.View;

import com.lansoeditor.demo.R;

public class AVComposeWrapper extends CmdWrapper{

	private String audioSource=null;
	public AVComposeWrapper()
	{
		super();
		VideoPlayVisibility=View.VISIBLE;
		AudioPlayVisibility=View.INVISIBLE;
	}
	@Override
	public int getHint() {
		// TODO Auto-generated method stub
		return R.string.avsplite_hint;
	}
	
	public void setAudioSource(String src)
	{
		audioSource=src;
	}
	public void doCommand()
	{
		Log.i("sno","isPrepareSuccess:"+isPrepareSuccess);
		if(isPrepareSuccess){
			mEditor.executeVideoMergeAudio(srcPath,audioSource, dstVideo);
		}
	}
	
	public boolean prepare()
	{
		//这里额外检查是否同时有音频和视频轨道.
		if(super.prepare())
		{
			return true;
		}
		else
			return false;
	}
	
}
