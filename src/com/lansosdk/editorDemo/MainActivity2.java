package com.lansosdk.editorDemo;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Locale;


import com.anthonycr.grant.PermissionsManager;
import com.anthonycr.grant.PermissionsResultAction;
import com.lansoeditor.demo.R;
import com.lansosdk.editorDemo.VideoEditDemoActivity.SubAsyncTask;
import com.lansosdk.editorDemo.utils.FileUtils;
import com.lansosdk.editorDemo.utils.snoCrashHandler;
import com.lansosdk.editorDemo.wrapper.CmdId;
import com.lansosdk.videoeditor.CopyFileFromAssets;
import com.lansosdk.videoeditor.LanSoEditor;
import com.lansosdk.videoeditor.LoadLanSongSdk;
import com.lansosdk.videoeditor.MediaInfo;
import com.lansosdk.videoeditor.SDKDir;
import com.lansosdk.videoeditor.SDKFileUtils;
import com.lansosdk.videoeditor.VideoEditor;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore.Video;
import android.support.v4.content.PermissionChecker;
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
		 
		 LoadLanSongSdk.loadLibraries();
		 LanSoEditor.initSo(getApplicationContext(),null);
		 
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
		   
		 if(isPermissionOk==false && selfPermissionGranted(getApplicationContext(), "android.permission.WRITE_EXTERNAL_STORAGE")==false){
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
    @SuppressLint("NewApi") 
	  public static boolean selfPermissionGranted(Context context,String permission) {
	        // For Android < Android M, self permissions are always granted.
	        boolean result = true;
	        int targetSdkVersion = 0;
	        try {
	            final PackageInfo info = context.getPackageManager().getPackageInfo(
	                    context.getPackageName(), 0);
	            targetSdkVersion = info.applicationInfo.targetSdkVersion;
	        } catch (PackageManager.NameNotFoundException e) {
	            e.printStackTrace();
	        }

	        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

	            if (targetSdkVersion >= Build.VERSION_CODES.M) {
	                // targetSdkVersion >= Android M, we can
	                // use Context#checkSelfPermission
	                result = context.checkSelfPermission(permission)
	                        == PackageManager.PERMISSION_GRANTED;
	            } else {
	                // targetSdkVersion < Android M, we have to use PermissionChecker
	                result = PermissionChecker.checkSelfPermission(context, permission)
	                        == PermissionChecker.PERMISSION_GRANTED;
	            }
	        }
	        return result;
	    }	
    private void showHintDialog()
   	{
      	 	
    	Calendar c = Calendar.getInstance();
   		int year=c.get(Calendar.YEAR);
   		int month=c.get(Calendar.MONTH)+1;
   		
   		int lyear=VideoEditor.getLimitYear();
   		int lmonth=VideoEditor.getLimitMonth();
   		
   		Log.i(TAG,"current year is:"+year+" month is:"+month +" limit year:"+lyear+" limit month:"+lmonth);
   		String timeHint=getResources().getString(R.string.sdk_limit);
   		timeHint=String.format(timeHint, lyear,lmonth);
   		
   		new AlertDialog.Builder(this)
   		.setTitle("提示")
   		.setMessage(timeHint)
           .setPositiveButton("确定", new DialogInterface.OnClickListener() {
   			
   			@Override
   			public void onClick(DialogInterface dialog, int which) {
   				// TODO Auto-generated method stub
   				
   				showHintDialog("注意:  native-jni层我们提供了三个ARM架构的so动态库,实际仅用一个即可.建议用armeabi-v7a .\n\nUI界面仅仅是一些常用功能的举例,我们会一直持续的增加,不影响您的使用.请知悉~~");
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
   		 String str=SDKDir.TMP_DIR+"ping20s.mp4";
   		 if(FileUtils.fileExist(str)){
   			 Toast.makeText(getApplicationContext(), "默认视频文件拷贝完成.视频样片路径:"+str, Toast.LENGTH_SHORT).show();
   			 if(tvVideoPath!=null)
    				tvVideoPath.setText(str);
   		 }else{
   			Toast.makeText(getApplicationContext(), "抱歉! 默认视频文件拷贝失败,请联系我们.视频样片路径:"+str, Toast.LENGTH_SHORT).show();
   		 }
   	}
   }
}
