package com.lansosdk.editorDemo.wrapper;

import android.util.Log;
import android.view.View;

import com.lansoeditor.demo.R;

public class VideoCompressWrapper extends CmdWrapper{

	private boolean isPrepareSuccess=false;
	public VideoCompressWrapper()
	{
		super();
		VideoPlayVisibility=View.VISIBLE;
		AudioPlayVisibility=View.INVISIBLE;
	}
	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return "视频转码压缩";
	}
	@Override
	public int getHint() {
		// TODO Auto-generated method stub
		return R.string.videocompress_hint;
	}
	
	public void doCommand()
	{
		Log.i("sno","isPrepareSuccess:"+isPrepareSuccess);
		if(isPrepareSuccess){
			mEditor.executeVideoCompress(srcPath, dstVideo, 0.7f);
		}
	}
	public boolean prepare()
	{
		//这里额外检查是否有视频轨道.
		if(super.prepare() && mInfo.vBitRate>0)
		{
			isPrepareSuccess=true;
			return true;
		}
		else
			return false;
	}
	
}
