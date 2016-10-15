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
 * 调试程序的界面
 *
 */
public class MainActivity extends Activity implements OnClickListener{

	private TextView tvVideoPath;
	 private static final String TAG="MainActivity";
	 private boolean isPermissionOk=false;
	 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		 Thread.setDefaultUncaughtExceptionHandler(new snoCrashHandler());
		
		 //单独列出
		 LoadLanSongSdk.loadLibraries();
		 LanSoEditor.initSo(getApplicationContext(),null);
		 
			 
		 PermissionsManager.getInstance().requestAllManifestPermissionsIfNecessary(this, new PermissionsResultAction() {
	            @Override
	            public void onGranted() {
	            	isPermissionOk=true;
	                Toast.makeText(MainActivity.this, R.string.message_granted, Toast.LENGTH_SHORT).show();
	            }

	            @Override
	            public void onDenied(String permission) {
	            	isPermissionOk=false;
	                String message = String.format(Locale.getDefault(), getString(R.string.message_denied), permission);
	                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
	            }
	        });
		 
		 setContentView(R.layout.functions_layout);
		 
		 tvVideoPath=(TextView)findViewById(R.id.id_main_tvvideo);
//		    
//		 
		 if(isPermissionOk==false && selfPermissionGranted(getApplicationContext(), "android.permission.WRITE_EXTERNAL_STORAGE")==false){
	        	showHintDialog("当前没有读写权限");
	        	isPermissionOk=false;
	        }else{
	        	Log.i("sno","当前有读写权限");
	        	isPermissionOk=true;
	        }
	
//		  VideoEditor.isNvidiaCodec();
		  
//		   showHintDialog();
			
		 	new Handler().postDelayed(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					gotoActivity(VideoEditDemoActivity.class);
					
//					gotoActivity(ScaleExecuteDemoActivity.class);
					
					
//					Intent intent=new Intent(MainActivity.this,VideoPlayerActivity.class);
//			    	intent.putExtra("videopath", "/sdcard/test_1080p.mp4");
//			    	startActivity(intent);
				}
			}, 1000);
			
//			 findViewById(R.id.id_main_use_default_videobtn).setOnClickListener(new OnClickListener() {
//					
//					@Override
//					public void onClick(View v) {
//						// TODO Auto-generated method stub
//						new CopyDefaultVideoAsyncTask().execute();
//					}
//				});
    }
    @Override
    public void onClick(View v) {
    	// TODO Auto-generated method stub
    	if(isPermissionOk)
    	{

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
    @Override
    protected void onDestroy() {
    	// TODO Auto-generated method stub
    	super.onDestroy();
    	LanSoEditor.unInitSo();
    	
    	SDKFileUtils.deleteDir(new File(SDKDir.TMP_DIR));  //删除演示文件夹下的所有文件.
    }
    private void gotoActivity(Class<?> cls)
    {
    	Intent intent=new Intent(MainActivity.this,cls);
    	intent.putExtra("videopath", "/sdcard/ping20s.mp4");
    	startActivity(intent);
    }
}
