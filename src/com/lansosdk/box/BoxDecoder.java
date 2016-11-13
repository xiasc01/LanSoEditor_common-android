package com.lansosdk.box;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.IntBuffer;

import android.graphics.Bitmap;
import android.util.Log;

/**
 *  先用VideoEditor类来测试 
 *
 */
public class BoxDecoder {
	private final static String TAG="OnlyTestActivity";
	public String mFileName=null;
	 private long decoderHandle=0;
	 IntBuffer mGLRgbBuffer;
	 int cnt=0;
	 String mSuffix;
	public BoxDecoder(String filePath,String suffix)
	{
		mFileName=filePath;
		decoderHandle=	decoderInit(mFileName);
		mSuffix=suffix;
		 mGLRgbBuffer = IntBuffer.allocate(480 * 360);
	}
	
	public long getFrame()
	{
		if(decoderHandle==0){
			return -1;
		}
			long pts=decoderGetFrame(decoderHandle,-1,mGLRgbBuffer.array());
			
			return pts;
		//	if(mEditor.decoderIsEnd(decoderHandle))  //可以在这里增加判断.
//				break;
		
			
//		  Bitmap stitchBmp = Bitmap.createBitmap(480, 360, Bitmap.Config.ARGB_8888);
//          stitchBmp.copyPixelsFromBuffer(mGLRgbBuffer);
//          
//          mGLRgbBuffer.position(0);
//          
//          String str=mSuffix+cnt+".png";
//          cnt++;
//          saveBitmap(stitchBmp,str);
	}
	public void release()
	{
		if(decoderHandle!=0){
			decoderRelease(decoderHandle);
			decoderHandle=0;
		}
	}
	
	private void saveBitmap(Bitmap bmp,String picName) {
		  Log.e(TAG, "保存图片");
		  File f = new File("/sdcard/", picName);
		  if (f.exists()) {
		   f.delete();
		  }
		  try {
		   FileOutputStream out = new FileOutputStream(f);
		   bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
		   out.flush();
		   out.close();
		   Log.i(TAG, "已经保存");
		  } catch (FileNotFoundException e) {
		   // TODO Auto-generated catch block
		   e.printStackTrace();
		  } catch (IOException e) {
		   // TODO Auto-generated catch block
		   e.printStackTrace();
		  }

		 }
	//ceshi .测试
	
	public native long  decoderInit(String filepath);
	/**
	 * seekUs 大于等于0,则开始seek, seek是定位到指定位置前的I帧，而不是精确到当前位置，如果要精确到当前位置，则需要seek后多次获取时间戳来判断。
判断原理是:先计算得到当前两针的差值,然后算要在getFrame几次,才可以到精确的位置,然后getFrame多次

	 * 返回当前时间戳, 时间戳代码US.
	 * @param handle
	 * @param seekUs  >=0 说明要seek
	 * @param out
	 * @return
	 */
	public native long decoderGetFrame(long handle,long seekUs,int[] out);
	
	public native int decoderRelease(long handle);
	public native boolean decoderIsEnd(long handle);
	public static native String decoderValue1();
	public static native String decoderValue2();
	
}
