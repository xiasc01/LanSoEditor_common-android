package com.lansosdk.editorDemo;

import java.io.File;
import java.io.IOException;
import java.util.Locale;


import com.anthonycr.grant.PermissionsManager;
import com.anthonycr.grant.PermissionsResultAction;
import com.lansoeditor.demo.R;
import com.lansosdk.editorDemo.MainActivity.CopyDefaultVideoAsyncTask;
import com.lansosdk.editorDemo.VideoEditDemoActivity.SubAsyncTask;
import com.lansosdk.editorDemo.wrapper.CmdId;
import com.lansosdk.videoeditor.CopyFileFromAssets;
import com.lansosdk.videoeditor.LanSoEditor;
import com.lansosdk.videoeditor.MediaInfo;
import com.lansosdk.videoeditor.SDKDir;
import com.lansosdk.videoeditor.SDKFileUtils;
import com.lansosdk.videoeditor.VideoEditor;
import com.lansosdk.videoeditor.utils.FileUtils;
import com.lansosdk.videoeditor.utils.snoCrashHandler;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore.Video;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 *  发布时候的界面 
 *
 */
public class MainActivity2 extends Activity implements OnClickListener{

	 private static final String TAG="MainActivity2";
	 private boolean isPermissionOk=false;
	 private TextView tvVideoPath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		 Thread.setDefaultUncaughtExceptionHandler(new snoCrashHandler());
		 LanSoEditor.initSo(getApplicationContext());
		 
		 PermissionsManager.getInstance().requestAllManifestPermissionsIfNecessary(this, new PermissionsResultAction() {
	            @Override
	            public void onGranted() {
	            	isPermissionOk=true;
	                Toast.makeText(MainActivity2.this, R.string.message_granted, Toast.LENGTH_SHORT).show();
	            }

	            @Override
	            public void onDenied(String permission) {
	            	isPermissionOk=false;
	                String message = String.format(Locale.getDefault(), getString(R.string.message_denied), permission);
	                Toast.makeText(MainActivity2.this, message, Toast.LENGTH_SHORT).show();
	            }
	        });
		 
			 
		 setContentView(R.layout.main_demos_list_layout);
		 
		 findViewById(R.id. id_demo_list_avsplit).setOnClickListener(this);
		 findViewById(R.id.id_demo_list_audiocut).setOnClickListener(this);
		 findViewById(R.id.id_demo_list_videocut).setOnClickListener(this);
		 findViewById(R.id.id_demo_list_videocompress).setOnClickListener(this);
		 findViewById(R.id.id_demo_list_videocrop).setOnClickListener(this);
		 findViewById(R.id.id_demo_list_videoaddimg).setOnClickListener(this);
		 findViewById(R.id.id_demo_list_video2img).setOnClickListener(this);
		 findViewById(R.id.id_demo_list_oneimage2video).setOnClickListener(this);

		 findViewById(R.id.id_demo_list_kuozhan).setOnClickListener(this);
		 findViewById(R.id.id_demo_list_connectus).setOnClickListener(this);
		 
		 
		 findViewById(R.id.id_demo_list_use_default_videobtn).setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					new CopyDefaultVideoAsyncTask().execute();
				}
			});
		 
		   tvVideoPath=(TextView)findViewById(R.id.id_demo_list_tvvideo);
		   
		 if(isPermissionOk==false && LanSoEditor.selfPermissionGranted(getApplicationContext(), "android.permission.WRITE_EXTERNAL_STORAGE")==false){
	        	showHintDialog("当前没有读写权限");
	        	isPermissionOk=false;
	        }else{
	        	Log.i("sno","当前有读写权限");
	        	isPermissionOk=true;
	        }
			 showHintDialog();
    }
    @Override
    public void onClick(View v) {
    	// TODO Auto-generated method stub
    	if(isPermissionOk)
    	{
    		switch (v.getId()) {
	    		case R.id.id_demo_list_avsplit:
					gotoActivity(CmdId.AV_SPLIT_WRAPPER);
					break;
	    		case R.id.id_demo_list_audiocut:
					gotoActivity(CmdId.AUDIO_CUT_WRAPPER);
					break;
	    		case R.id.id_demo_list_videocut:
					gotoActivity(CmdId.VIDEO_CUT_WRAPPER);
					break;
	    		case R.id.id_demo_list_videocompress:
					gotoActivity(CmdId.VIDEO_COMPRESS_WRAPPER);
					break;
//	    		case R.id.id_demo_list_videoscale:  不推荐用ffmpeg来做视频缩放, 应该用MediaCodec+opengl来做.
//					gotoActivity(CmdId.VIDEO_SCALE_WRAPPER);
//					break;
	    		case R.id.id_demo_list_videocrop:
					gotoActivity(CmdId.VIDEO_CROP_WRAPPER);
					break;
	    		case R.id.id_demo_list_videoaddimg:
					gotoActivity(CmdId.VIDEO_ADDIMAGE_WRAPPER);
					break;
	    		case R.id.id_demo_list_video2img:
					gotoActivity(CmdId.EXTRACT_IMAGE_WRAPPER);
					break;
	    		case R.id.id_demo_list_oneimage2video:
					gotoActivity(CmdId.ONE_IMAGE_FADE_WRAPPER);
					break;
	    		case R.id.id_demo_list_kuozhan:
	    			gotoActivity(CustomFunctionActivity.class);
	    		break;
	    		case R.id.id_demo_list_connectus:
	    			gotoActivity(BusynessActivity.class);
	    			break;
				default:
					break;
    		}
    	}
    }
    @Override
    protected void onResume() {
    	// TODO Auto-generated method stub
    	super.onResume();
    	
    }
    private void showHintDialog(String hint){
    	new AlertDialog.Builder(this)
		.setTitle("提示")
		.setMessage(hint)
        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
			}
		})
        .show();
    }
    		
    private void showHintDialog()
	{
		new AlertDialog.Builder(this)
		.setTitle("提示")
		.setMessage(R.string.sdk_limit)
        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
				showHintDialog("注意:底层ffmpeg完整可靠运行,已是发行商用版本.\n\nUI界面仅仅是一些常用功能的举例,我们会一直持续的增加,不影响您的使用.请知悉~~");
			}
		})
        .show();
	}
    
    @Override
    protected void onDestroy() {
    	// TODO Auto-generated method stub
    	super.onDestroy();
    	LanSoEditor.unInitSo();
    	SDKFileUtils.deleteDir(new File(SDKDir.TMP_DIR));
    }
    private void gotoActivity(Class<?> cls)
    {
    	Intent intent=new Intent(MainActivity2.this,cls);
    	startActivity(intent);
    }
    private void gotoActivity(int cmdId)
    {
    	Intent intent=new Intent(MainActivity2.this,VideoWrapperEditorActivity.class);
    	intent.putExtra("CMD_ID", cmdId);
    	startActivity(intent);
    }
    //--------------------------------------------
  		private ProgressDialog  mProgressDialog;
  	   public class CopyDefaultVideoAsyncTask extends AsyncTask<Object, Object, Boolean>{
  			  @Override
  			protected void onPreExecute() {
  			// TODO Auto-generated method stub
  				  mProgressDialog = new ProgressDialog(MainActivity2.this);
  		          mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
  		          mProgressDialog.setMessage("正在拷贝...");
  		          mProgressDialog.setCancelable(false);
  		          mProgressDialog.show();
  		          super.onPreExecute();
  			}
     	    @Override
     	    protected synchronized Boolean doInBackground(Object... params) {
     	    	// TODO Auto-generated method stub
     	    	
     	    	
            String str=SDKDir.TMP_DIR+"ping20s.mp4";
            if(FileUtils.fileExist(str)==false){
            	CopyFileFromAssets.copy(getApplicationContext(), "ping20s.mp4", SDKDir.TMP_DIR, "ping20s.mp4");
            }
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
   		 String str="视频样片路径:"+SDKDir.TMP_DIR+"ping20s.mp4";
   		 if(FileUtils.fileExist(str)){
   			 Toast.makeText(getApplicationContext(), "默认视频文件拷贝完成."+str, Toast.LENGTH_SHORT).show();
   			 if(tvVideoPath!=null)
    				tvVideoPath.setText(str);
   		 }else{
   			Toast.makeText(getApplicationContext(), "抱歉! 默认视频文件拷贝失败,请联系我们", Toast.LENGTH_SHORT).show();
   		 }
   	}
   }
}
