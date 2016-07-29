package com.lansosdk.editorDemo.wrapper;

import android.util.Log;
import android.view.View;

import com.lansoeditor.demo.R;
import com.lansosdk.editorDemo.utils.FileUtils;
import com.lansosdk.videoeditor.MediaInfo;
import com.lansosdk.videoeditor.VideoEditor;

public class CmdWrapper {

	protected VideoEditor mEditor = new VideoEditor();
	protected MediaInfo mInfo=null;
	
	private final String DST_VIDEO="/sdcard/avsplit_demo.";
	private final String DST_AUDIO="/sdcard/avsplit_demo.";
	
	protected String srcPath=null;
	
	protected String dstVideo=DST_VIDEO;
	protected String dstAudio=DST_AUDIO;
	
	public int VideoPlayVisibility=View.VISIBLE;
	public int AudioPlayVisibility=View.INVISIBLE;
	
	
	public CmdWrapper()
	{
		reset();
	}
	public VideoEditor getEditor()
	{
		return mEditor;
	}
	public String getTitle()
	{
		return "多媒体处理";
	}
	public void setSourcePath(String src)
	{
		reset();
		srcPath=src;
	}
	public int getHint()
	{
		return R.string.cmdwrapper_hint;
	}
	
	public String getSrcPath()
	{
		return srcPath;
	}
	
	public String getDstVideo()
	{
		return dstVideo;
	}
	
	public String getDstAudio()
	{
		return dstAudio;
	}
	/**
	 * 做一些检查的工作, 看是否符合要求
	 * @return
	 */
	public boolean prepare()
	{
		if(srcPath ==null){
			return false;
		}
		
		mInfo=new MediaInfo(srcPath);
    	if(mInfo.prepare())
    	{
    		//增加上后缀.
    		if(FileUtils.getFileSuffix(dstVideo).isEmpty())
    		{
    			dstVideo+=mInfo.fileSuffix;
    		}
    		
    		if(FileUtils.getFileSuffix(dstAudio).isEmpty())
    		{
    			dstAudio+=mInfo.aCodecName;
    		}
    		
    		//删除之前的文件
    		if(FileUtils.fileExist(dstAudio))
    			FileUtils.deleteFile(dstAudio);
    		
    		if(FileUtils.fileExist(dstVideo))
    			FileUtils.deleteFile(dstVideo);
    		
    		return true;
    	}else{
    		return false;
    	}
    		
	}
	public void doCommand()
	{
		Log.e("CmdWrapper","nothing to do!!!");
	}
	public void reset()
	{
		srcPath=null;
		dstVideo=DST_VIDEO;
		dstAudio=DST_AUDIO;
	}
	public void release()
	{
		if(FileUtils.fileExist(dstAudio))
			FileUtils.deleteFile(dstAudio);
		
		if(FileUtils.fileExist(dstVideo))
			FileUtils.deleteFile(dstVideo);
		srcPath=null;
		dstVideo=DST_VIDEO;
		dstAudio=DST_AUDIO;
	}
}
