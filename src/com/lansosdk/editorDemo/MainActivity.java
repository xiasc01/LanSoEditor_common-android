package com.lansosdk.editorDemo;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Locale;


import com.anthonycr.grant.PermissionsManager;
import com.anthonycr.grant.PermissionsResultAction;
import com.lansoeditor.demo.R;
import com.lansosdk.editorDemo.VideoEditDemoActivity.SubAsyncTask;
import com.lansosdk.videoeditor.CopyFileFromAssets;
import com.lansosdk.videoeditor.LanSoEditor;
import com.lansosdk.videoeditor.MediaInfo;
import com.lansosdk.videoeditor.SDKDir;
import com.lansosdk.videoeditor.SDKFileUtils;
import com.lansosdk.videoeditor.SpriteShader;
import com.lansosdk.videoeditor.VideoEditor;
import com.lansosdk.videoeditor.utils.FileUtils;
import com.lansosdk.videoeditor.utils.snoCrashHandler;


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
		 LanSoEditor.initSo(getApplicationContext(),null);
		 
			 
//		 PermissionsManager.getInstance().requestAllManifestPermissionsIfNecessary(this, new PermissionsResultAction() {
//	            @Override
//	            public void onGranted() {
//	            	isPermissionOk=true;
//	                Toast.makeText(MainActivity.this, R.string.message_granted, Toast.LENGTH_SHORT).show();
//	            }
//
//	            @Override
//	            public void onDenied(String permission) {
//	            	isPermissionOk=false;
//	                String message = String.format(Locale.getDefault(), getString(R.string.message_denied), permission);
//	                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
//	            }
//	        });
		 
		 setContentView(R.layout.functions_layout);
		 
		 tvVideoPath=(TextView)findViewById(R.id.id_main_tvvideo);
//		    
		 findViewById(R.id.id_module_item1).setOnClickListener(this);
		 findViewById(R.id.id_module_item2).setOnClickListener(this);
		 findViewById(R.id.id_module_item3).setOnClickListener(this);
		 findViewById(R.id.id_module_item4).setOnClickListener(this);
		 findViewById(R.id.id_module_item5).setOnClickListener(this);
		 findViewById(R.id.id_module_item6).setOnClickListener(this);
//		 
		 if(isPermissionOk==false && selfPermissionGranted(getApplicationContext(), "android.permission.WRITE_EXTERNAL_STORAGE")==false){
	        	showHintDialog("当前没有读写权限");
	        	isPermissionOk=false;
	        }else{
	        	Log.i("sno","当前有读写权限");
	        	isPermissionOk=true;
	        }
	
		 //  showHintDialog();
			
		 	new Handler().postDelayed(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					gotoActivity(VideoEditDemoActivity.class);
					
//					Intent intent=new Intent(MainActivity.this,VideoPlayerActivity.class);
//			    	intent.putExtra("videopath", "/sdcard/ping20s.mp4");
//			    	startActivity(intent);
				}
			}, 2000);
			
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
    	//if(isPermissionOk)
    	{
    		switch (v.getId()) {
				case R.id.id_module_item1:
					Log.i(TAG,"--------------------------module item1");
					Intent intent=new Intent(getApplicationContext(),VideoPlayerActivity.class);
			    	intent.putExtra("videopath", "/sdcard/ping20s.mp4");
			    	startActivity(intent);
			    	
//					gotoActivity(FunctionItem1Activity.class);
					break;
				case R.id.id_module_item2:
					gotoActivity(FunctionItem2Activity.class);
					break;
				case R.id.id_module_item3:
					gotoActivity(FunctionItem3Activity.class);
					break;
				case R.id.id_module_item4:
					break;
				case R.id.id_module_item5:
					gotoCustomFunction();
					break;
				case R.id.id_module_item6:
					gotoBussnessActivity();
//					Intent intent=new Intent(getApplicationContext(),VideoPlayerActivity.class);
////			    	intent.putExtra("videopath", "/sdcard/n90.mp4");
//					intent.putExtra("videopath", "/sdcard/2x.mp4");
//			    	startActivity(intent);
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
				
				showHintDialog("注意:底层ffmpeg完整可靠运行,已是发行商用版本.\n\nUI界面仅仅是一些常用功能的举例,我们会一直持续的增加,不影响您的使用.请知悉~~");
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
    	SDKFileUtils.deleteDir(new File(SDKDir.TMP_DIR));
    }
    private void gotoActivity(Class<?> cls)
    {
    	Intent intent=new Intent(MainActivity.this,cls);
    	startActivity(intent);
    }
    private void gotoCustomFunction()
    {
    	startActivity(new Intent(MainActivity.this,CustomFunctionActivity.class));
    }
    private void gotoBussnessActivity()
    {
    	startActivity(new Intent(MainActivity.this,BusynessActivity.class));
    }
    
    //--------------------------------------------
  		private ProgressDialog  mProgressDialog;
  	  public class CopyDefaultVideoAsyncTask extends AsyncTask<Object, Object, Boolean>{
			  @Override
			protected void onPreExecute() {
			// TODO Auto-generated method stub
				  mProgressDialog = new ProgressDialog(MainActivity.this);
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
			Toast.makeText(getApplicationContext(), "抱歉! 默认视频文件拷贝失败,请联系我们:视频样片路径:"+str, Toast.LENGTH_SHORT).show();
		 }
	}
}
}
