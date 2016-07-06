package com.lansosdk.editorDemo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.lansoeditor.demo.R;
import com.lansosdk.videoeditor.MediaInfo;
import com.lansosdk.videoeditor.VideoEditor;
import com.lansosdk.videoeditor.onVideoEditorProgressListener;
import com.lansosdk.videoeditor.utils.FileUtils;
import com.lansosdk.videoeditor.utils.snoCrashHandler;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

public class VideoEditDemoActivity extends Activity{


	private final static String TAG="videoEditDemoActivity";
	private final static  boolean VERBOSE = false;   
	
	
	String videoPath="/sdcard/2x.mp4";
	VideoEditor mEditor = new VideoEditor();
	ProgressDialog  mProgressDialog;
	int videoDuration;
	MediaInfo   mMediaInfo;
	private String dstPath="/sdcard/video_demo_framecrop.mp4";
	

	private boolean isRunning=false; 
	
	  @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
			 Thread.setDefaultUncaughtExceptionHandler(new snoCrashHandler());
	        
			 setContentView(R.layout.video_edit_demo_layout);
	        
				
			 mMediaInfo=new MediaInfo(videoPath);
				
			 TextView tvHint=(TextView)findViewById(R.id.id_video_editor_demo_hint);
			 tvHint.setText(R.string.video_editor_demo_hint);
			 
			 
	        findViewById(R.id.id_video_edit_btn).setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if(videoPath==null)
						return ;
					
					mMediaInfo.prepare();
					
					Log.i(TAG,mMediaInfo.toString());
					
					if(mMediaInfo.vDuration>60*1000){//大于60秒
						showHintDialog();
					}else{
						startVideoEditorTask();
					}
				}
			});
	        
	        findViewById(R.id.id_video_play_btn).setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent=new Intent(VideoEditDemoActivity.this,VideoPlayerActivity.class);
			    	intent.putExtra("videopath", dstPath);
			    	startActivity(intent);
					
					
//					if(FileUtils.fileExist(dstPath)){
//				    	Intent intent=new Intent(VideoEditDemoActivity.this,VideoPlayerActivity.class);
//				    	intent.putExtra("videopath", dstPath);
//				    	startActivity(intent);
//					}else{
//						Toast.makeText(VideoEditDemoActivity.this, R.string.file_not_exist,Toast.LENGTH_SHORT).show();
//					}
				}
			});
	        
	        mEditor.setOnProgessListener(new onVideoEditorProgressListener() {
				
				@Override
				public void onProgress(VideoEditor v, int percent) {
					// TODO Auto-generated method stub
					Log.i(TAG,"current percent is:"+percent);
					if(mProgressDialog!=null)
						mProgressDialog.setMessage("正在处理中..."+String.valueOf(percent)+"%");
				}
			});
	  } 
	  @Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

//        if(FileUtils.fileExist(dstPath)){
//        	FileUtils.deleteFile(dstPath);
//        }
//        findViewById(R.id.id_video_play_btn).setEnabled(false);
	}
		private void showHintDialog()
		{
			new AlertDialog.Builder(this)
			.setTitle("提示")
			.setMessage("视频过大,可能会需要一段时间,您确定要处理吗?")
	        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					startVideoEditorTask();
				}
			})
			.setNegativeButton("取消", null)
	        .show();
		}
		private void startVideoEditorTask()
		{
			if(isRunning)
				return;
			new SubAsyncTask().execute();
		}
	  public class SubAsyncTask extends AsyncTask<Object, Object, Boolean>{
			  @Override
			protected void onPreExecute() {
			// TODO Auto-generated method stub
				  mProgressDialog = new ProgressDialog(VideoEditDemoActivity.this);
		          mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		          mProgressDialog.setMessage("正在处理中...");
		          mProgressDialog.setCancelable(false);
		          mProgressDialog.show();
		          isRunning=true;
		          super.onPreExecute();
			}
      	    @Override
      	    protected synchronized Boolean doInBackground(Object... params) {
      	    	// TODO Auto-generated method stub
      	    	
//      	    	if(mMediaInfo!=null){
      	    	//这里是要做音频编码格式的检测,以方便快速的提取,直接拷贝,不用解码后再编码.
//      	    		if(mMediaInfo.aCodecName.equals("mp3")){
//      	    			mEditor.executeDeleteVideo("/sdcard/2x.mp4","/sdcard/2x_nov.mp3");
//      	    		}else if(mMediaInfo.aCodecName.equals("aac")){
//      	    			mEditor.executeDeleteVideo("/sdcard/2x.mp4","/sdcard/2x_nov.aac");
//      	    		}
//      	    	}
      	    	
//      	    	mEditor.executeVideoCutOut(videoPath,"/sdcard/2x_cut.mp4",5,5);
//	mEditor.executeGetAllFrames("/sdcard/2x.mp4","/sdcard/","getpng");
      	    	 isRunning=true;
      	    	mEditor.executeVideoFrameCrop(videoPath, 240, 240, 0, 0, dstPath,mMediaInfo.vCodecName,mMediaInfo.vBitRate);
      	    	 
      	    	 
//      	    	mEditor.executeConvertMp4toTs("/sdcard/2x.mp4","/sdcard/2x0.ts");
      	    	//因为静态码率
//      	    	mEditor.executeAddWaterMark("/sdcard/test_720p.mp4","/sdcard/watermark.png",0,0,"/sdcard/A1.mp4",(int)(mMediaInfo.vBitRate*1.5f));
      	    	
//      	    	mEditor.pictureFadeInOut("/sdcard/threeword.png",5,0,40,50,75,dstPath);
      	    //	mEditor.pictureFadeIn("/sdcard/testfade.png",3,0,60,"/sdcard/testfade2.mp4");
      	//  	mEditor.pictureFadeOut("/sdcard/testfade.png",3,0,60,"/sdcard/testfade3.mp4");
      	    	
      	    	
//      	    	 mEditor.waterMarkFadeIn("/sdcard/2x.mp4","/sdcard/watermark.png",2,5,0,30,0,0,dstPath);
      	    	
      	    	
//      	    	mEditor.executeRotateAngle("/sdcard/2x.mp4", mMediaInfo.vCodecName, 90, "/sdcard/F1.mp4",1000000);
      	    	
      	    	//这里检测mp3的时长,
//      	    	mEditor.audioAdjustVolumeMix("/sdcard/hongdou.mp3", "/sdcard/kaimendaji.mp3", 3.0f, 0.5f, "/sdcard/hongdouxxx.mp3");
      	    	
      	    //	mVideoEditor.audioAdjustVolumeMix("/sdcard/save_encodec4.aac","/sdcard/kaimen20s.mp3",3.0f,0.4f,"/sdcard/jni_amix.aac");
      	    	//mVideoEditor.avReverse(null, null, null);
      	    	//String srcPath,int totalTime,int fadeinstart,int fadeinCnt,int fadeoutstart,int fadeoutCnt,
//     		   	String dstPath
      	    	//demoVideoGray();
      	    	//demodeleteMisuc();
      	    	 
      	    	
//      	    	 mEditor.audioPcmCut("/sdcard/j1.pcm", 44100, 2, 2, 1000, 5000, "/sdcard/p2.pcm");
      	    	 
//      	    	mEditor.audioPcmMute("/sdcard/j1.pcm", 44100, 2, 2, 8000, 25000, "/sdcard/p3.pcm");
      	    	
//      	    	mEditor.audioPcmReplace("/sdcard/j1.pcm", "/sdcard/p2.pcm",44100, 2, 2, 8000,"/sdcard/p4.pcm");
      	    	
      	    	return null;
      	    }
    	@Override
    	protected void onPostExecute(Boolean result) { 
    		// TODO Auto-generated method stub
    		super.onPostExecute(result);
    		if( mProgressDialog!=null){
	       		 mProgressDialog.cancel();
	       		 mProgressDialog=null;
    		}
    		isRunning=false;
    		
    		if(FileUtils.fileExist(dstPath))
    			findViewById(R.id.id_video_play_btn).setEnabled(true);
    		
    	}
    }
	  @Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		  if(isRunning){
			  Log.e(TAG,"VideoEditor is running...cannot back!!! ");
			  return ;
		  }
			  
		  
		super.onBackPressed();
	}
	  //-------------------------------------------------------------------------
	 
}

