package com.lansosdk.editorDemo.wrapper;

import com.lansoeditor.demo.R;
import com.lansosdk.videoeditor.MediaInfo;
import com.lansosdk.videoeditor.VideoEditor;
import com.lansosdk.videoeditor.utils.FileUtils;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

/**
 *  对一条命令的包装下, 为了更方便演示,  
 *
 */
public class AVSplitWrapper extends CmdWrapper{

	private boolean isPrepareSuccess=false;
	public AVSplitWrapper()
	{
		super();
		VideoPlayVisibility=View.VISIBLE;
		AudioPlayVisibility=View.VISIBLE;
	}
	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return "演示音视频分离";
	}
	@Override
	public int getHint() {
		// TODO Auto-generated method stub
		return R.string.avsplite_hint;
	}
	
	public void doCommand()
	{
		Log.i("sno","isPrepareSuccess:"+isPrepareSuccess);
		if(isPrepareSuccess){
			mEditor.executeDeleteAudio(srcPath, dstVideo);
	    	mEditor.executeDeleteVideo(srcPath, dstAudio);	
		}
	}
	
	public boolean prepare()
	{
		//这里额外检查是否同时有音频和视频轨道.
		if(super.prepare() && mInfo.vBitRate>0 && mInfo.aBitRate>0)
		{
			isPrepareSuccess=true;
			return true;
		}
		else
			return false;
	}
}
