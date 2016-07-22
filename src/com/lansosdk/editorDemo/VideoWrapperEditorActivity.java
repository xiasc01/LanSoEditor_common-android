package com.lansosdk.editorDemo;

import java.io.IOException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.lansoeditor.demo.R;
import com.lansosdk.editorDemo.VideoEditDemoActivity.SubAsyncTask;
import com.lansosdk.editorDemo.wrapper.AVSplitWrapper;
import com.lansosdk.editorDemo.wrapper.AudioCutWrapper;
import com.lansosdk.editorDemo.wrapper.CmdId;
import com.lansosdk.editorDemo.wrapper.CmdWrapper;
import com.lansosdk.editorDemo.wrapper.ExtractImageWrapper;
import com.lansosdk.editorDemo.wrapper.OneImageFadeWrapper;
import com.lansosdk.editorDemo.wrapper.VideoCompressWrapper;
import com.lansosdk.editorDemo.wrapper.VideoCropWrapper;
import com.lansosdk.editorDemo.wrapper.VideoCutWrapper;
import com.lansosdk.editorDemo.wrapper.VideoAddImageWrapper;
import com.lansosdk.editorDemo.wrapper.VideoScaleWrapper;
import com.lansosdk.videoeditor.MediaInfo;
import com.lansosdk.videoeditor.VideoEditor;
import com.lansosdk.videoeditor.onVideoEditorProgressListener;
import com.lansosdk.videoeditor.utils.FileUtils;

/**
 *  音视频分离  : 直接从多媒体文件中分离出音频和视频.
 *
 */
public class VideoWrapperEditorActivity extends Activity implements OnClickListener{

	private final static String TAG="VideoAudioSplitActivity";
	private final static  boolean VERBOSE = false;   
	
	
	private TextView  tvHint;
	private TextView tvSelectFile;
	
	private Context mContext;
	private CmdWrapper mCmdWrapper =null;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.compose_cut_layout);
		tvHint=(TextView)findViewById(R.id.id_compose_cut_tv);
		tvSelectFile=(TextView)findViewById(R.id.id_compose_select_filehint);
		
		mContext=getApplicationContext();
		
		int cmdID=getIntent().getIntExtra("CMD_ID", 0);
		getCmdWrapper(cmdID);
		
		
		//测试使用.
//		String srcPath="/data/local/tmp/res/ping20s.mp4";
//		if(mCmdWrapper!=null)
//			mCmdWrapper.setSourcePath(srcPath);
//		tvSelectFile.setText(srcPath);
		
		
		setTitle(mCmdWrapper.getTitle());
		
		tvHint.setText(mCmdWrapper.getHint());
		
		findViewById(R.id.id_compose_result_videoplay).setVisibility(mCmdWrapper.VideoPlayVisibility);
		findViewById(R.id.id_compose_result_audioplay).setVisibility(mCmdWrapper.AudioPlayVisibility);
		
		findViewById(R.id.id_compose_cut_selectfile).setOnClickListener(this);
		findViewById(R.id.id_compose_start_execute).setOnClickListener(this);
		findViewById(R.id.id_compose_result_videoplay).setOnClickListener(this);
		findViewById(R.id.id_compose_result_audioplay).setOnClickListener(this);
		
		mCmdWrapper.getEditor().setOnProgessListener(new onVideoEditorProgressListener() {
				
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
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
			case R.id.id_compose_cut_selectfile:
				startSelectVideoActivity();
				break;
			case R.id.id_compose_start_execute:
				 startExecuteTask();
				break;
			case R.id.id_compose_result_videoplay:
					playVideo();
				break;
			case R.id.id_compose_result_audioplay:
					playAudio();
				break;

		default:
			break;
		}
	}
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	// TODO Auto-generated method stub
    	super.onActivityResult(requestCode, resultCode, data);
    	switch (resultCode) {
			case RESULT_OK:
					if(requestCode==SELECT_FILE_REQUEST_CODE){
						Bundle b = data.getExtras();   
						String srcPath = b.getString("SELECT_VIDEO");
						if(mCmdWrapper!=null)
							mCmdWrapper.setSourcePath(srcPath);
						else
							Log.i("sno","mDoFunction==null");
						
						Log.i("sno","SELECT_PATH is:"+srcPath);
						
						if(tvSelectFile!=null)
							tvSelectFile.setText(srcPath);
					}
				break;

		default:
			break;
		}
    }
    @Override
    protected void onPause() {
    	// TODO Auto-generated method stub
    	super.onPause();
    	if(audioPlayer!=null){
    		audioPlayer.stop();
    		audioPlayer.release();
    		audioPlayer=null;
    	}
    }
    @Override
    protected void onDestroy() {
    	// TODO Auto-generated method stub
    	super.onDestroy();
    	if (mCmdWrapper!=null) {
    		mCmdWrapper.release();
    		mCmdWrapper=null;
		}
    }
    private void getCmdWrapper(int cmdID)
    {
		switch (cmdID) {
			case CmdId.AV_SPLIT_WRAPPER:
					mCmdWrapper=new AVSplitWrapper();		
				break;
			case CmdId.VIDEO_CUT_WRAPPER:
				mCmdWrapper =new VideoCutWrapper();
				break;
			case CmdId.AUDIO_CUT_WRAPPER:
				mCmdWrapper =new AudioCutWrapper();
				break;
			case CmdId.VIDEO_SCALE_WRAPPER:
				mCmdWrapper=new VideoScaleWrapper(); 
				break;
			case CmdId.VIDEO_CROP_WRAPPER:
				mCmdWrapper= new VideoCropWrapper();
				break;
			case CmdId.VIDEO_COMPRESS_WRAPPER:
				mCmdWrapper=new VideoCompressWrapper();
				break;
			case CmdId.VIDEO_ADDIMAGE_WRAPPER:
				mCmdWrapper= new VideoAddImageWrapper(mContext); 
				break;
			case CmdId.EXTRACT_IMAGE_WRAPPER:
				mCmdWrapper=new ExtractImageWrapper();
				break;
			case CmdId.ONE_IMAGE_FADE_WRAPPER:
				mCmdWrapper=new OneImageFadeWrapper(mContext);
				break;
		default:
			mCmdWrapper=new CmdWrapper();
			break;
		}
    }
    
	private final static int SELECT_FILE_REQUEST_CODE=10;
	private void startSelectVideoActivity()
    {
    	Intent i = new Intent(this, FileExplorerActivity.class);
    	
    	if(mCmdWrapper.VideoPlayVisibility==View.VISIBLE)
    		i.putExtra("SELECT_MODE", "video");
    	else 
    		i.putExtra("SELECT_MODE", "audio");
    	
    	
	    startActivityForResult(i,SELECT_FILE_REQUEST_CODE);
    }
    private void startExecuteTask()
    {
    	if(!isRunning && mCmdWrapper.getSrcPath()!=null)
    	{
	    	if(mCmdWrapper.prepare())
	    	{
	    		new SubAsyncTask().execute();
	    	}else{
	    		showToast("当前文件错误,或音视频文件参数不符合短视频的要求,请重新选择多媒体文件");
	    	}
    	}
    }
    private void showToast(String str)
	{
		Toast.makeText(getApplicationContext(), str,Toast.LENGTH_SHORT).show();
	}
	MediaPlayer audioPlayer=null;
	public void playAudio(){
		
		if(MediaInfo.isSupport(mCmdWrapper.getDstAudio()) && audioPlayer ==null)
		{
			audioPlayer=new MediaPlayer();
			try {
				audioPlayer.setDataSource(mCmdWrapper.getDstAudio());
				audioPlayer.prepare();
				audioPlayer.start();
				
				audioPlayer.setOnCompletionListener(new OnCompletionListener() {
					
					@Override
					public void onCompletion(MediaPlayer mp) {
						// TODO Auto-generated method stub
						audioPlayer.release();
						audioPlayer=null;
					}
				});
				
			}catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
    }
	private void playVideo(){
		if(MediaInfo.isSupport(mCmdWrapper.getDstVideo())){
			Intent intent=new Intent(mContext,VideoPlayerActivity.class);
	    	intent.putExtra("videopath", mCmdWrapper.getDstVideo());
	    	startActivity(intent);
		}else{
			//showToast("目标文件 不支持");
		}
	}
	
	private boolean isRunning=false; 
	ProgressDialog  mProgressDialog;
	public class SubAsyncTask extends AsyncTask<Object, Object, Boolean>{
				  @Override
				protected void onPreExecute() {
				// TODO Auto-generated method stub
					  mProgressDialog = new ProgressDialog(VideoWrapperEditorActivity.this);
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
			    	isRunning=true;
			    	
			    	mCmdWrapper.doCommand();
			    		
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
			}
		}
}