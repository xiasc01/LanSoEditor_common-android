package com.lansosdk.editorDemo.wrapper;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.lansoeditor.demo.R;
import com.lansosdk.videoeditor.CopyFileFromAssets;
import com.lansosdk.videoeditor.SDKDir;

public class OneImageFadeWrapper   extends CmdWrapper{

	private boolean isPrepareSuccess=false;
	private Context mContext;
	public OneImageFadeWrapper(Context ctx)
	{
		super();
		mContext=ctx;
		VideoPlayVisibility=View.VISIBLE;
		AudioPlayVisibility=View.GONE;
	}
	@Override
	public String getSrcPath()
	{ 
		//这里fake一个路径, 为了兼容
		return SDKDir.TMP_DIR;
	}
	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return "一张图片转视频";
	}
	@Override
	public int getHint() {
		// TODO Auto-generated method stub
		return R.string.oneimage2video_hint;
	}
	
	public void doCommand()
	{
		dstVideo+=".mp4";  //组合成一个文件路径.
		
			Log.i("sno","isPrepareSuccess:"+isPrepareSuccess);
			String imagePath=SDKDir.TMP_DIR+"/threeword.png";
			CopyFileFromAssets.copy(mContext, "threeword.png", SDKDir.TMP_DIR, "threeword.png");
			mEditor.pictureFadeInOut(imagePath,5,0,40,50,75,dstVideo);
	}
	public boolean prepare()
	{
		return true;
	}
	
}
