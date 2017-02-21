package com.lansosdk.videoeditor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.IntBuffer;

import android.graphics.Bitmap;
import android.util.Log;

public class AVDecoder {

//	
//	private void saveBitmap(int width,int height) {
	
//	 Bitmap stitchBmp = Bitmap.createBitmap(480, 360, Bitmap.Config.ARGB_8888);
//// stitchBmp.copyPixelsFromBuffer(mGLRgbBuffer);
//// 
//// mGLRgbBuffer.position(0);
//// 
//// String str=mSuffix+cnt+".png";
//// cnt++;
//// saveBitmap(stitchBmp,str);
	 
	 
//		  Log.e(TAG, "保存图片");
//		  File f = new File("/sdcard/", picName);
//		  if (f.exists()) {
//		   f.delete();
//		  }
//		  try {
//		   FileOutputStream out = new FileOutputStream(f);
//		   bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
//		   out.flush();
//		   out.close();
//		   Log.i(TAG, "已经保存");
//		  } catch (FileNotFoundException e) {
//		   // TODO Auto-generated catch block
//		   e.printStackTrace();
//		  } catch (IOException e) {
//		   // TODO Auto-generated catch block
//		   e.printStackTrace();
//		  }
//
//		 }
	
	/**
	 * 
	 * @param filepath
	 * @return
	 */
		public static native long  decoderInit(String filepath);
		/**
		 * 解码一帧, 发送上去.  seekUS大于等于0, 说明要seek, 
		 * 注意:如果您设置了seek大于等于0, 因为视频编码原理是基于IDR刷新帧的, seek时会选择在你设置时间的最近前一个IDR刷新帧的位置,请注意!
		 *  
		 *  
		 * 这里只seek一次开始解码, 解码后直接把数据发送上去. 用decoderIsEnd来判断当前是否已经解码好.
		 * 
		 * 建议:如果您的需求每次都解码同一个视频,视频总帧数在20帧以下,并每帧的字节不是很大, 建议一次解码后, 用list保存起来,不用每次都解码同一个视频.
		 * 
		 * @param handle  当前文件的句柄,
		 * 
		 * @param seekUs  是否要seek, 大于等于0说明要seek, 
		 * 
		 * @param out  输出.
		 * 
		 * @return  返回的是当前帧的时间戳.单位是秒.
		 */
		public static native long decoderFrame(long handle,long seekUs,int[] out);
		
		/**
		 * 释放当前解码器.
		 * 
		 * @param handle
		 * @return
		 */
		public static native int decoderRelease(long handle);
		/**
		 * 解码是否到文件尾.
		 * @param handle
		 * @return
		 */
		public static native boolean decoderIsEnd(long handle);
}
