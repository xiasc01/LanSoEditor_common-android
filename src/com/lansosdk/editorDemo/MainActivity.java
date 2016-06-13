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
	 
		TextView tvVideoPath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		 Thread.setDefaultUncaughtExceptionHandler(new snoCrashHandler());
        setContentView(R.layout.activity_main);
        
        LanSoEditor.initSo(getApplicationContext());
        
        tvVideoPath=(TextView)findViewById(R.id.id_main_tv_videopath);
        tvVideoPath.setText("/sdcard/2x.mp4");
        
        findViewById(R.id.id_main_select_video).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startSelectVideoActivity();
			}
		});
 
        
        findViewById(R.id.id_main_demoplay).setOnClickListener(this);
        findViewById(R.id.id_main_demoedit).setOnClickListener(this);
        findViewById(R.id.id_main_mediainfo).setOnClickListener(this);
        
       
        
        if(LanSoEditor.selfPermissionGranted(getApplicationContext(), "android.permission.WRITE_EXTERNAL_STORAGE")==false){
        	showHintDialog("当前没有读写权限");
        	isPermissionOk=false;
        }else{
        	Log.i("sno","当前有读写权限");
        	isPermissionOk=true;
        }
    }
    @Override
    public void onClick(View v) {
    	// TODO Auto-generated method stub
    	
    	if(checkPath()==false || isPermissionOk==false)
			return;
    	
		switch (v.getId()) {
			case R.id.id_main_mediainfo:
				gotoActivity(MediaInfoActivity.class);
				break;
			case R.id.id_main_demoplay:
				gotoActivity(VideoPlayerActivity.class);
				break;
			case R.id.id_main_demoedit:
				gotoActivity(VideoEditDemoActivity.class);
				break;
			default:
				break;
		}
    }
    private boolean isstarted=false;
    @Override
    protected void onResume() {
    	// TODO Auto-generated method stub
    	super.onResume();
    	
//    	if(isstarted)
//    		return;
//    	
//    	new Handler().postDelayed(new Runnable() {
//			
//			@Override
//			public void run() {
//				// TODO Auto-generated method stub
//				isstarted=true;
//				
//				startVideoEditDemo();
//			//	startVideoPlayDemo();
//			}
//		}, 1000);
        showHintDialog();
    }
	private final static int SELECT_FILE_REQUEST_CODE=10;
  	private void startSelectVideoActivity()
    {
    	Intent i = new Intent(this, FileExplorerActivity.class);
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
		.setMessage("SDK版本号是V1.4 [商用版本]\n\n,SDK底层做了授权限制,仅可在此demo中运行,并有效时间到2016年7月15号,请注意.)")
        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
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
    
    private boolean checkPath(){
    	if(tvVideoPath.getText()!=null && tvVideoPath.getText().toString().isEmpty()){
    		Toast.makeText(MainActivity.this, "请输入视频地址", Toast.LENGTH_SHORT).show();
    		return false;
    	}	
    	else{
    		String path=tvVideoPath.getText().toString();
    		if((new File(path)).exists()==false){
    			Toast.makeText(MainActivity.this, "文件不存在", Toast.LENGTH_SHORT).show();
    			return false;
    		}else{
    			MediaInfo info=new MediaInfo(path);
    	        info.prepare();
    	        Log.i(TAG,"info:"+info.toString());
    			return true;
    		}
    	}
    }
    private void gotoActivity(Class<?> cls)
    {
    	String path=tvVideoPath.getText().toString();
    	Intent intent=new Intent(MainActivity.this,cls);
    	intent.putExtra("videopath", path);
    	startActivity(intent);
    }
}
