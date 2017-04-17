package com.lansosdk.videoeditor;

import java.io.File;

public class SDKDir {
	public static final String TMP_DIR="/sdcard/lansongBox/";
	
	public static String getPath()
	{
		File file=new File(TMP_DIR);
		if(file.exists()==false){
			file.mkdir();
		}
		return TMP_DIR;
	}
//	public static final String TMP_DIR="/data/local/tmp/res/";
}
