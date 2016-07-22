package com.lansosdk.editorDemo.wrapper;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.lansoeditor.demo.R;
import com.lansosdk.videoeditor.CopyFileFromAssets;
import com.lansosdk.videoeditor.SDKFileUtils;

public class VideoCropImageCutWrapper extends CmdWrapper{

	private boolean isPrepareSuccess=false;
	private Context mContext;
	public VideoCropImageCutWrapper(Context ctx)
	{
		super();
		mContext=ctx;
		VideoPlayVisibility=View.VISIBLE;
		AudioPlayVisibility=View.INVISIBLE;
	}
	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return "视频剪切+画面裁剪+增加水印+";
	}
	@Override
	public int getHint() {
		// TODO Auto-generated method stub
		return R.string.videocutcropimage_hint;
	}
	
	public void doCommand()
	{
		Log.i("sno","isPrepareSuccess:"+isPrepareSuccess);
		if(isPrepareSuccess && mContext!=null){
			String imagePath="/sdcard/videoimage.png";
			if(SDKFileUtils.fileExist(imagePath)==false){
				CopyFileFromAssets.copy(mContext, "ic_launcher.png", "/sdcard", "videoimage.png");	
			}
			 int cropW=480;
  	    	 int cropH=480;
  	    	 int max=Math.max(mInfo.vWidth,mInfo.vHeight);
  	    	 
  	    	 int cropMax=Math.max(cropW, cropH);
  	    	 float dstBr=(float)mInfo.vBitRate;
  	    	 
  	    	 float ratio=(float)cropMax/(float)max;
  	    	 
  	    	 dstBr*=ratio;  //得到恒定码率的等比例值.
  	    	 dstBr*=0.8f; //再压缩20%.
  	    	 
			mEditor.executeVideoCutCropOverlay(srcPath,mInfo.vCodecName,imagePath,5,10,0,0,240,240,0,0,dstVideo,(int)dstBr);
			
		}
	}
	public boolean prepare()
	{
		//这里额外检查是否有视频轨道.
		if(super.prepare() && mInfo.vBitRate>0 && mInfo.vWidth>240 && mInfo.vHeight>240)
		{
			isPrepareSuccess=true;
			return true;
		}
		else
			return false;
	}
	
}
