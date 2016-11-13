package com.lansosdk.commonDemo;


import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import com.anthonycr.grant.PermissionsManager;
import com.anthonycr.grant.PermissionsResultAction;
import com.lansoeditor.demo.R;
import com.lansosdk.videoeditor.LanSoEditor;
import com.lansosdk.videoeditor.LoadLanSongSdk;
import com.lansosdk.videoeditor.MediaInfo;
import com.lansosdk.videoeditor.SDKDir;
import com.lansosdk.videoeditor.SDKFileUtils;
import com.lansosdk.videoeditor.VideoEditor;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.PermissionChecker;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class OnlyTestPlayerActivity extends Activity{

	
	private TextView tvVideoPath;
	private boolean isPermissionOk=false;
	 private static final String TAG="MainActivity";
	 
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		 setContentView(R.layout.only_videoplay_main);
		 
		 //加载so库,并初始化.
		 LoadLanSongSdk.loadLibraries();
		 LanSoEditor.initSo(getApplicationContext(),null);
		 
			//因为从android6.0系统有各种权限的限制, 这里开始先检查是否有读写的权限. 
		 PermissionsManager.getInstance().requestAllManifestPermissionsIfNecessary(this, new PermissionsResultAction() {
	            @Override
	            public void onGranted() {
	            	isPermissionOk=true;
	                Toast.makeText(OnlyTestPlayerActivity.this, R.string.message_granted, Toast.LENGTH_SHORT).show();
	            }

	            @Override
	            public void onDenied(String permission) {
	            	isPermissionOk=false;
	                String message = String.format(Locale.getDefault(), getString(R.string.message_denied), permission);
	                Toast.makeText(OnlyTestPlayerActivity.this, message, Toast.LENGTH_SHORT).show();
	            }
	        });
		 
		  tvVideoPath=(TextView)findViewById(R.id.id_main_tvvideo);
		  findViewById(R.id.id_main_select_video).setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					startSelectVideoActivity();
				}
			});
	        findViewById(R.id.id_main_use_default_videobtn).setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					new com.lansosdk.videoeditor.CopyDefaultVideoAsyncTask(OnlyTestPlayerActivity.this, tvVideoPath, "ping20s.mp4").execute();
				}
			});
		 
		 if(isPermissionOk==false && selfPermissionGranted(getApplicationContext(), "android.permission.WRITE_EXTERNAL_STORAGE")==false){
	        	showHintDialog("当前没有读写权限");
	        	isPermissionOk=false;
	        }else{
	        	isPermissionOk=true;
	        }
		 
			 findViewById(R.id.id_main_player).setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if(checkPath()){
						startPlayerActivity();
					}
				}
		});
			 
			 
			  new Thread(new Runnable() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						MediaInfo mif=new MediaInfo("/sdcard/hei.ts"); //这里是我们的测试视频地址, 如您测试, 则需要修改视频地址.
						mif.prepare();
						Log.i(TAG,"mif is:"+ mif.toString());
						mif.release();
					}
				},"testMediaInfo#1").start();
			  
			  
		  new Thread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						MediaInfo mif=new MediaInfo("/sdcard/tengshi/3.mkv");//这里是我们的测试视频地址, 如您测试, 则需要修改视频地址.
						mif.prepare();
						Log.i(TAG,"mif is:"+ mif.toString());
						mif.release();
					}
				},"testMediaInfo#2").start();
		
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		//only for test. 
		
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		SDKFileUtils.deleteDir(new File(SDKDir.TMP_DIR)); //删除dir
	}

	private void startPlayerActivity()
	{
		Intent intent=new Intent(OnlyTestPlayerActivity.this,VideoPlayerActivity.class);
		intent.putExtra("videopath", tvVideoPath.getText().toString());
//		intent.putExtra("videopath", "/sdcard/teng.mov");
		startActivity(intent);
	}
	
	//-----------------------------------------
	 private final static int SELECT_FILE_REQUEST_CODE=10;
	  	private void startSelectVideoActivity()
	    {
	    	Intent i = new Intent(this, FileExplorerActivity.class);
	    	
	    	i.putExtra("SELECT_MODE", "video");
	    	
		    startActivityForResult(i,SELECT_FILE_REQUEST_CODE);
	    }
	    @Override
	    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    	// TODO Auto-generated method stub
	    	super.onActivityResult(requestCode, resultCode, data);
	    	switch (resultCode) {
			case RESULT_OK:
					if(requestCode==SELECT_FILE_REQUEST_CODE){
						Bundle b = data.getExtras();   
			    		String string = b.getString("SELECT_VIDEO");   
						Log.i("sno","SELECT_VIDEO is:"+string);
						if(tvVideoPath!=null)
							tvVideoPath.setText(string);
					}
				break;

			default:
				break;
			}
	    }
	    private boolean checkPath(){
	    	if(isPermissionOk==false){
	    		Toast.makeText(OnlyTestPlayerActivity.this, "请在系统中打开权限.", Toast.LENGTH_SHORT).show();
	    		return false;
	    	}
	    	
	    	if(tvVideoPath.getText()!=null && tvVideoPath.getText().toString().isEmpty()){
	    		Toast.makeText(OnlyTestPlayerActivity.this, "请输入视频地址", Toast.LENGTH_SHORT).show();
	    		return false;
	    	}	
	    	else{
	    		String path=tvVideoPath.getText().toString();
	    		if((new File(path)).exists()==false){
	    			Toast.makeText(OnlyTestPlayerActivity.this, "文件不存在", Toast.LENGTH_SHORT).show();
	    			return false;
	    		}else{
	    			MediaInfo info=new MediaInfo(path,false);
	    			boolean ret=info.prepare();
	    	        Log.i(TAG,"info:"+info.toString());
	    	        
	    			return ret;
	    		}
	    	}
	    }
	    //--------------------------------------------------------------
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
	  
}


