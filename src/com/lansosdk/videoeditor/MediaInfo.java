package com.lansosdk.videoeditor;



import java.io.File;

import android.content.Context;
import android.os.Message;
import android.util.Log;

/**
 *  暂时只支持一个音频和一个视频组成的多媒体文件,如MP4等,如果有多个音频,则音频数据是最后一个音频的info.
 *
 */
public class MediaInfo {
//

	 private static final String TAG="MediaInfo";
	 private static final boolean VERBOSE = true; 
	 
	 /***************video track info(total 12)*************** */
	 /**
	  * 
	  */
	 public  int vHeight;
     public  int vWidth;
     public  int vCodecHeight;
     public  int vCodecWidth;    
     
     /**
      * 视频的码率,注意,一般的视频编码时采用的是动态码率VBR,故这里得到的是平均值, 建议在使用时,乘以1.5后,使用.
      * 
      */
     public int vBitRate; 
     /**
      * 视频文件中的视频流总帧数.
      */
     public int vTotalFrames;  
     
     public float vDuration;  //单位秒.
     public float vFrameRate;
     public float vRotateAngle;
     
     public boolean vHasBFrame;
     /**
      * 视频可以使用的解码器
      */
     public String vCodecName;
     /**
      * 视频的 像素格式.
      */
     public String vPixelFmt;
     
     /********************audio track info******************/
     
     public int aSampleRate;
     public int aChannels;
     /**
      * 视频文件中的音频流 总帧数.
      */
     public int aTotalFrames;
     public int aBitRate;
     public int aMaxBitRate;   
     public float aDuration;
     public String  aCodecName;
    
     
     public final String filePath;
     public final String fileName; //视频的文件名, 路径的最后一个/后的字符串.
     public final String fileSuffix; //文件的后缀名.
     
     private boolean getSuccess=false;
     
     /**
      * 是否检测 硬件解码器,不检测会执行的快一些,默认为检测(如果您不是很明白,建议使用默认值,毕竟也只是慢30ms一下的事情.).
      * 
      *  一般在不需要解码的场合,可以设置为false,比如文件浏览器选择音视频文件, 音视频分离剪切的场合.
      */
     private boolean isCheckCodec=true;  //是否检测 
     
     public MediaInfo(String path)
     {
    	 filePath=path;
    	 fileName=getFileNameFromPath(path);
    	 fileSuffix=getFileSuffix(path);
    	 isCheckCodec=true;
     }
     /**
      * 
      * @param path
      * @param checkCodec  默认是true,即检测解码器. 如不想检测解码器,则设置为false;这样执行prepare会快一些.
      */
     public MediaInfo(String path,boolean checkCodec)
     {
    	 filePath=path;
    	 fileName=getFileNameFromPath(path);
    	 fileSuffix=getFileSuffix(path);
    	 isCheckCodec=checkCodec;
     }
     /**
      * 准备当前媒体的信息
      * 
      * 去底层运行相关方法, 得到媒体信息.
      * @return  如获得当前媒体信息并支持格式,则返回true, 否则返回false;
      */
     public boolean prepare()
     {
    	int ret=0;
    	 if(fileExist(filePath)){ //这里检测下mfilePath是否是多媒体后缀.
    		 ret= nativePrepare(filePath,isCheckCodec);	 
    		 if(ret>=0){
    			 getSuccess=true;
    			 return isSupport();
    		 }else{
    			 Log.e(TAG,"mediainfo prepare media is failed:"+filePath);
    			 return false;
    		 }
    	 }else{
    		 Log.e(TAG,"mediainfo file is may be not exist!");
    		 return false;
    	 } 
     }
     public void release()
     {
    	 //TODO nothing 
    	 getSuccess=false;
     }
     /**
      * 传递过来的文件是否支持
      * 
      * @return
      */
     public boolean isSupport()
     {
    	 //既没有音频,又没有视频,则不支持.
    	 if(vBitRate <=0 && aBitRate<=0)
    		 return false;
    	 
    	 if(vBitRate>0)  //有视频,
    	 {
    		 if(vHeight==0 || vWidth==0)
    		 {
    			 return false;
    		 }
    		 if(vFrameRate>60) //如果帧率大于60帧, 则不支持.  
    			 return false;
    		 
    		 if(vCodecName==null || vCodecName.isEmpty())
    			 return false;
    		 
    		 if(vDuration<3)
    			 return false;
    		 
    	 }else if(aBitRate>0)  //有音频
    	 {
    		 if(aChannels==0)
    			 return false;
    		 
    		 if(aCodecName==null || aCodecName.isEmpty())
    			 return false;
    		 
    		 if(aDuration<3)
    			 return false;
    	 }
   
    	 return true;
     }
     @Override
    public String toString() {
    	// TODO Auto-generated method stub
    	 String info="file name:"+filePath+"\n";
    	 info+= "fileName:"+fileName+"\n";
    	 info+= "fileSuffix:"+fileSuffix+"\n";
    	 info+= "vHeight:"+vHeight+"\n";
    	 info+= "vWidth:"+vWidth+"\n";
    	 info+= "vCodecHeight:"+vCodecHeight+"\n";
    	 info+= "vCodecWidth:"+vCodecWidth+"\n";
    	 info+= "vBitRate:"+vBitRate+"\n";
    	 info+= "vTotalFrames:"+vTotalFrames+"\n";
    	 info+= "vDuration:"+vDuration+"\n";
    	 info+= "vFrameRate:"+vFrameRate+"\n";
    	 info+= "vRotateAngle:"+vRotateAngle+"\n";
    	 info+= "vHasBFrame:"+vHasBFrame+"\n";
    	 info+= "vCodecName:"+vCodecName+"\n";
    	 info+= "vPixelFmt:"+vPixelFmt+"\n";
    	 
    	 info+= "aSampleRate:"+aSampleRate+"\n";
    	 info+= "aChannels:"+aChannels+"\n";
    	 info+= "aTotalFrames:"+aTotalFrames+"\n";
    	 info+= "aBitRate:"+aBitRate+"\n";
    	 info+= "aMaxBitRate:"+aMaxBitRate+"\n";
    	 info+= "aDuration:"+aDuration+"\n";
    	 info+= "aCodecName:"+aCodecName+"\n";
    	 
    	if(getSuccess)
    		return info;
    	else
    	 return "MediaInfo is not ready.or call failed";
    }
     public native int nativePrepare(String filepath,boolean checkCodec);
     
     //used by JNI
     private void setVideoCodecName(String name)
     {
    	 this.vCodecName=name;
     }
     //used by JNI
     private void setVideoPixelFormat(String pxlfmt)
     {
    	 this.vPixelFmt=pxlfmt;
     }
     //used by JNI
     private void setAudioCodecName(String name)
     {
    	 this.aCodecName=name;
     }
     public static boolean isSupport(String videoPath)
     {
    	 if(fileExist(videoPath))
    	 {
    		 MediaInfo  info=new MediaInfo(videoPath,false);
        	 return  info.prepare();
    	 }else{
    		 if(VERBOSE)
    			 Log.i(TAG,"video:"+videoPath+" not support");
    		 return false;
    	 }
     }
     //-------------------------------文件操作-------------------------
     private static boolean fileExist(String absolutePath)
	 {
		 if(absolutePath==null)
			 return false;
		 else 
			 return (new File(absolutePath)).exists();
	 }
     
     private  String getFileNameFromPath(String path){
	        if (path == null)
	            return "";
	        int index = path.lastIndexOf('/');
	        if (index> -1)
	            return path.substring(index+1);
	        else
	            return path;
	    }
     private  String getFileSuffix(String path){
	        if (path == null)
	            return "";
	        int index = path.lastIndexOf('.');
	        if (index> -1)
	            return path.substring(index+1);
	        else
	            return "";
	    }
     /*
      * ****************************************************************************
      * 测试
 //        new Thread(new Runnable() {
//			
//			@Override
//			public void run() {
//				// TODO Auto-generated method stub
//				MediaInfo mif=new MediaInfo("/sdcard/2x.mp4");
//				mif.prepare();
//				Log.i(TAG,"mif is:"+ mif.toString());
//				mif.release();
//			}
//		},"testMediaInfo#1").start();
//  new Thread(new Runnable() {
//			
//			@Override
//			public void run() {
//				// TODO Auto-generated method stub
//				MediaInfo mif=new MediaInfo("/sdcard/2x.mp4");
//				mif.prepare();
//				Log.i(TAG,"mif is:"+ mif.toString());
//				mif.release();
//			}
//		},"testMediaInfo#2").start();
      */
}
