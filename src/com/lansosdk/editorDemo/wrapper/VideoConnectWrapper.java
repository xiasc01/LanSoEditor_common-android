package com.lansosdk.editorDemo.wrapper;

import android.util.Log;
import android.view.View;

import com.lansoeditor.demo.R;

public class VideoConnectWrapper extends CmdWrapper{

	//需要设置两个video....
	private boolean isPrepareSuccess=false;
	public VideoConnectWrapper()
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
	
	public void doCommand()
	{
		Log.i("sno","isPrepareSuccess:"+isPrepareSuccess);
		if(isPrepareSuccess){
			//TODO
//			mEditor.executeAudioCutOut(srcPath,dstAudio,0,mInfo.aDuration/2);
		}
	}
	public boolean prepare()
	{
		//这里额外检查是否同时有音频和视频轨道.
		if(super.prepare() && mInfo.vBitRate>0)
		{
			isPrepareSuccess=true;
			return true;
		}
		else
			return false;
	}
	
}
