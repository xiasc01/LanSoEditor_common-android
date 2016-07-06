package com.lansosdk.editorDemo.wrapper;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.lansoeditor.demo.R;
import com.lansosdk.videoeditor.CopyFileFromAssets;

public class VideoAddImageWrapper extends CmdWrapper{

	private boolean isPrepareSuccess=false;
	private Context mContext;
	public VideoAddImageWrapper(Context ctx)
	{
		super();
		mContext=ctx;
		VideoPlayVisibility=View.VISIBLE;
		AudioPlayVisibility=View.INVISIBLE;
	}
	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return "视频上增加图片";
	}
	@Override
	public int getHint() {
		// TODO Auto-generated method stub
		return R.string.videowatermark_hint;
	}
	
	public void doCommand()
	{
		Log.i("sno","isPrepareSuccess:"+isPrepareSuccess);
		if(isPrepareSuccess && mContext!=null){
			String imagePath="/sdcard/videoimage.png";
			CopyFileFromAssets.copy(mContext, "ic_launcher.png", "/sdcard", "videoimage.png");
			mEditor.executeAddWaterMark(srcPath, imagePath, 0, 0, dstVideo, (int)(mInfo.vBitRate*1.2f));
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
