package com.lansosdk.editorDemo.wrapper;

import android.util.Log;
import android.view.View;

import com.lansoeditor.demo.R;

public class VideoCropWrapper extends CmdWrapper{

	private boolean isPrepareSuccess=false;
	public VideoCropWrapper()
	{
		super();
		VideoPlayVisibility=View.VISIBLE;
		AudioPlayVisibility=View.INVISIBLE;
	}
	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return "视频画面裁剪";
	}
	@Override
	public int getHint() {
		// TODO Auto-generated method stub
		return R.string.videocrop_hint;
	}
	
	public void doCommand()
	{
		Log.i("sno","isPrepareSuccess:"+isPrepareSuccess);
		if(isPrepareSuccess){
			float dstBr=(float)mInfo.vBitRate;
  	    	dstBr*=0.7f;
  	    	int dstBr2=(int)dstBr;
			mEditor.executeVideoFrameCrop(srcPath, mInfo.vWidth, mInfo.vHeight/2, 0, 0, dstVideo, mInfo.vCodecName,dstBr2);
		}
	}
	public boolean prepare()
	{
		//这里额外检查有视频轨道.
		if(super.prepare() && mInfo.vBitRate>0)
		{
			isPrepareSuccess=true;
			return true;
		}
		else
			return false;
	}
	
}
