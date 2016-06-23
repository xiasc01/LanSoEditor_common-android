package com.lansosdk.editorDemo.wrapper;

import android.util.Log;
import android.view.View;

import com.lansoeditor.demo.R;

public class VideoScaleWrapper extends CmdWrapper{

	private boolean isPrepareSuccess=false;
	public VideoScaleWrapper()
	{
		super();
		VideoPlayVisibility=View.VISIBLE;
		AudioPlayVisibility=View.INVISIBLE;
	}
	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return "视频软件缩放";
	}
	@Override
	public int getHint() {
		// TODO Auto-generated method stub
		return R.string.videoscale_hint;
	}
	
	public void doCommand()
	{
		Log.i("sno","isPrepareSuccess:"+isPrepareSuccess);
		if(isPrepareSuccess){
			float f=(float)mInfo.vBitRate;
			f*=0.7f;
			int tt=(int)f;
			mEditor.executeVideoFrameScale(srcPath, mInfo.vWidth/2, mInfo.vHeight/2, dstVideo,tt);
		}
	}
	public boolean prepare()
	{
		//这里额外检查是否同时有音频和视频轨道.
		if(super.prepare() && mInfo.vBitRate>0 && mInfo.vWidth>0 && mInfo.vHeight>0)
		{
			isPrepareSuccess=true;
			return true;
		}
		else
			return false;
	}
	
}
