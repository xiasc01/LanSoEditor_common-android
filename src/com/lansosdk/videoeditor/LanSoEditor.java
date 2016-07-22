package com.lansosdk.videoeditor;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.os.Build;
import android.support.v4.content.PermissionChecker;
import android.util.Log;


public class LanSoEditor {

	private static boolean isLoaded=false;
	  
		static synchronized void loadLibraries() {
	        if (isLoaded)
	            return;
	        Log.d("lansoeditor","load libraries......");
	    	System.loadLibrary("ffmpegeditor");
	    	System.loadLibrary("lsdisplay");
	    	System.loadLibrary("lsplayer");
	    	
    	    isLoaded=true;
	  }
		
		  public static void initSo(Context context,String keyfile)
		  {
			  		loadLibraries();
		    	    nativeInit(context,context.getAssets(),keyfile);
		  }
	    public static void unInitSo()
	    {
	    	if(isLoaded){
	    		nativeUninit();
	    	}
	    }
	    public static native void nativeInit(Context ctx,AssetManager ass,String filename);
	    public static native void nativeUninit();
	    
}
