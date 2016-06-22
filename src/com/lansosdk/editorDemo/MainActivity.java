package com.lansosdk.editorDemo;

import java.io.File;


import com.lansoeditor.demo.R;
import com.lansosdk.editorDemo.VideoEditDemoActivity.SubAsyncTask;
import com.lansosdk.videoeditor.LanSoEditor;
import com.lansosdk.videoeditor.MediaInfo;
import com.lansosdk.videoeditor.utils.snoCrashHandler;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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


public class MainActivity extends Activity implements OnClickListener{


	 private static final String TAG="MainActivity";
	 private boolean isPermissionOk=false;
	 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		 Thread.setDefaultUncaughtExceptionHandler(new snoCrashHandler());
		 LanSoEditor.initSo(getApplicationContext());
		 
			 
		 setContentView(R.layout.functions_layout);
		 
		 findViewById(R.id.id_module_item1).setOnClickListener(this);
		 findViewById(R.id.id_module_item2).setOnClickListener(this);
		 findViewById(R.id.id_module_item3).setOnClickListener(this);
		 findViewById(R.id.id_module_item4).setOnClickListener(this);
		 findViewById(R.id.id_module_item5).setOnClickListener(this);
		 findViewById(R.id.id_module_item6).setOnClickListener(this);
		 
		 if(LanSoEditor.selfPermissionGranted(getApplicationContext(), "android.permission.WRITE_EXTERNAL_STORAGE")==false){
	        	showHintDialog("当前没有读写权限");
	        	isPermissionOk=false;
	        }else{
	        	Log.i("sno","当前有读写权限");
	        	isPermissionOk=true;
	        }
//		  showHintDialog();
//		 Intent intent=new Intent(MainActivity.this,VideoPlayerActivity.class);
//		 intent.putExtra("videopath", "/sdcard/test_720p.mp4");
//		 startActivity(intent);
    }
    @Override
    public void onClick(View v) {
    	// TODO Auto-generated method stub
    	if(isPermissionOk)
    	{
    		switch (v.getId()) {
				case R.id.id_module_item1:
					gotoActivity(FunctionItem1Activity.class);
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
					break;
						
				default:
					break;
    		}
    	}
    	
    	
    }
    private boolean isstarted=false;
    @Override
    protected void onResume() {
    	// TODO Auto-generated method stub
    	super.onResume();
    	
//    	if(isstarted)
//    		return;
//    	new Handler().postDelayed(new Runnable() {
//			
//			@Override
//			public void run() {
//				// TODO Auto-generated method stub
//				isstarted=true;
//				gotoActivity(VideoEditDemoActivity.class);
//			}
//		}, 100);
      
    	
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
		.setMessage("SDK版本号是V1.5 [商用版本]\n\nSDK底层做了授权限制,仅可在此demo中运行,并有效时间到2016年6月31号,请注意.)")
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
}
