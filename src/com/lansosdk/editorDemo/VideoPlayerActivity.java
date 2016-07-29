package com.lansosdk.editorDemo;

import java.io.IOException;

import com.lansoeditor.demo.R;
import com.lansosdk.editorDemo.utils.TextureRenderView;
import com.lansosdk.videoeditor.MediaInfo;
import com.lansosdk.videoeditor.player.IMediaPlayer;
import com.lansosdk.videoeditor.player.IMediaPlayer.OnPlayerCompletionListener;
import com.lansosdk.videoeditor.player.VPlayer;
import com.lansosdk.videoeditor.player.IMediaPlayer.OnPlayerPreparedListener;

import android.app.Activity;
import android.graphics.SurfaceTexture;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Surface;
import android.view.View;
import android.view.TextureView.SurfaceTextureListener;
import android.view.View.OnClickListener;
import android.widget.Toast;



public class VideoPlayerActivity extends Activity {
	   

	private TextureRenderView textureView;
	private TextureRenderView textureView2;
    private MediaPlayer mediaPlayer=null;  
    String videoPath=null;
  
    private static final boolean VERBOSE = false; 
    private static final String TAG = "VideoPlayerActivity";
    
    
    @Override  
    protected void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.player_layout);  
        
    	
        videoPath=getIntent().getStringExtra("videopath");

        MediaInfo info=new MediaInfo(videoPath);
        info.prepare();
        Log.i(TAG,"info:"+info.toString());
        
        
        
        textureView=(TextureRenderView)findViewById(R.id.surface1);
        textureView.setSurfaceTextureListener(new SurfaceTextureListener() {
			
			@Override
			public void onSurfaceTextureUpdated(SurfaceTexture surface) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width,
					int height) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public void onSurfaceTextureAvailable(SurfaceTexture surface, int width,
					int height) {
				// TODO Auto-generated method stub
//				play(new Surface(surface));
				VPlayVideo(new Surface(surface));
			}
		});
        
//        findViewById(R.id.id_player_subspeed).setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				mVPlayer.seekBack();
//			}
//		});
//		findViewById(R.id.id_player_addspeed).setOnClickListener(new OnClickListener() {
//					
//					@Override
//					public void onClick(View v) {
//						// TODO Auto-generated method stub
//						mVPlayer.seekFront();
//					}
//			});
        
    }  
    public void play(Surface surface)  {  

    	if(videoPath==null)
    		return ;
        mediaPlayer = new MediaPlayer();  
        mediaPlayer.reset();  
        mediaPlayer.setOnCompletionListener(new OnCompletionListener() {
			
			@Override
			public void onCompletion(MediaPlayer mp) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "视频播放完毕",Toast.LENGTH_LONG).show();
			}
		});
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);  
        try {
			mediaPlayer.setDataSource(videoPath);
			  mediaPlayer.setSurface(surface);  
		        mediaPlayer.prepare();  
		        
		        textureView.setVideoSize(mediaPlayer.getVideoWidth(), mediaPlayer.getVideoHeight());
		        textureView.requestLayout();
		        
		        mediaPlayer.start();  
		        
		}  catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }  
    private Handler getTimeHandler=new  Handler();
    private Runnable  getTimeRunnable=new Runnable() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			if(mVPlayer!=null){
				
				Log.i(TAG,"mVPlayer.getCurrentPosition()===>"+mVPlayer.getCurrentPosition());
				getTimeHandler.postDelayed(getTimeRunnable, 1000);
			}
		}
	};

    private VPlayer  mVPlayer=null;
    private void VPlayVideo(final Surface surface)
    {
          if (videoPath != null){
        	  mVPlayer=new VPlayer(this);
        	  mVPlayer.setVideoPath(videoPath);
        	  mVPlayer.setLooping(true);
              mVPlayer.setOnPreparedListener(new OnPlayerPreparedListener() {
    			
    			@Override
    			public void onPrepared(IMediaPlayer mp) {
    				// TODO Auto-generated method stub
    						mVPlayer.setSurface(surface);
    					    textureView.setVideoSize(mp.getVideoWidth(), mp.getVideoHeight());
    				        textureView.requestLayout();
    				        mVPlayer.setLooping(true);
    				        
    				        mVPlayer.start();
//    				        getTimeHandler.postDelayed(getTimeRunnable, 1000);
    					}
    			});
              mVPlayer.setOnCompletionListener(new OnPlayerCompletionListener() {
				
				@Override
				public void onCompletion(IMediaPlayer mp) {
					// TODO Auto-generated method stub
//					getTimeHandler.removeCallbacks(getTimeRunnable);
				}
			});
        	  mVPlayer.prepareAsync();
          }else {
              Log.e("sno", "Null Data Source\n");
              finish();
              return;
          }
    }
   
    private void VplayerSeekTo(int delayMs,final int seekToMS){
    	   new Handler().postDelayed(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
						if(mVPlayer!=null){
							mVPlayer.pause();
							mVPlayer.seekTo(seekToMS);
							mVPlayer.start();
						}
				}
			}, delayMs);
    }
    @Override  
    protected void onPause() {  
        if (mediaPlayer!=null) {  
        	mediaPlayer.stop();
        	mediaPlayer.release();
        	mediaPlayer=null;  
        }
        if (mVPlayer!=null) {  
        	mVPlayer.stop();
        	mVPlayer.release();
        	mVPlayer=null;  
        }
        super.onPause();  
    }  
  
    @Override  
    protected void onDestroy() {  
        super.onDestroy();  
    }  
}
