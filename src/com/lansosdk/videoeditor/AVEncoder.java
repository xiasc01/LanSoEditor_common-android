package com.lansosdk.videoeditor;

public class AVEncoder {
	
private static final String TAG ="AVEncoder";
	
	private long  mHandler=0;
	
	public  void init(String saveFile,int width,int height,int vfps,int vbitrate,int asamplerate,int abitrate)
	{
		mHandler=encoderInit(saveFile, width, height, vfps, vbitrate, asamplerate, abitrate);
	}
	public boolean init(String dstPath)  //一定是ts的后缀
	{
		mHandler=encoderInit(dstPath,480, 480, 25, 1000000, 44100, 64000);
		return mHandler!=0 ? true: false;
	}
	public boolean init(String dstPath,int sameleRate,int bitrate)  //音频编解码.
	{
		mHandler=encoderInit(dstPath,0, 0, 0, 0, sameleRate, bitrate);
		return mHandler!=0 ? true: false;
	}
	public boolean  init(String dstPath,int width,int height,int vfps,int vbitrate)
	{
		mHandler=encoderInit(dstPath,width,height, vfps, vbitrate, 0, 0);
		return mHandler!=0 ? true: false;
	}
	/**
	 * 只是用来把摄像头的数据 传到过来, 没有测试其他用处.
	 * @param data  yuv的数据.
	 * @param previewW
	 * @param previewH
	 * @param degree
	 * @param ptsMS   当前时间和开始时间的差值.
	 */
	public void pushVideoData(byte[] data,int previewW,int previewH,int degree,long ptsMS)
	{
		if(mHandler!=0){
			
			byte[]  bb=frameCut(data,previewW,previewH,480,480);
			
			byte[] byte2=null;
			if(	degree==90)  //不是后置,就是前置.
				byte2=rotateYUV420Degree90(bb,480,480);  //应该在这里实时的检测当前是后置还是前置,后置,应旋转90, 前置270;
			else 
				byte2=rotateYUV420Degree270(bb,480,480);
			
			encoderWriteVideoFrame(mHandler, byte2, ptsMS); 
		}
	}
	/**
	 *  因为ffmpeg中的AAC规定一帧是1024个采样点.
	 * @param data  音频采样到的数据.这里仅仅测试了mic, 但单声道, 请勿用作别处.
	 * @param ptsMs  当前时间和开始时间的差值.
	 */
	public void pushAudioData(byte[] data,long ptsMs)
	{
		if(mHandler!=0){
			encoderWriteAudioFrame(mHandler, data, ptsMs); 
		}
	}
	
	public void release()
	{
		if(mHandler!=0){
			encoderRelease(mHandler);	
		}
		mHandler=0;
	}
	/**
	 *其他小方法
	 * 只能对NV12或   NV21来做,并且 宽高一致.
	 * @param data
	 * @param imageWidth
	 * @param imageHeight
	 * @return
	 */
	private byte[] rotateYUV420Degree180(byte[] data, int imageWidth, int imageHeight)
	{
		byte [] yuv = new byte[imageWidth*imageHeight*3/2];
		int i = 0;
		int count = 0;

		for (i = imageWidth * imageHeight - 1; i >= 0; i--) {
			yuv[count] = data[i];
			count++;
		}

		i = imageWidth * imageHeight * 3 / 2 - 1;
		for (i = imageWidth * imageHeight * 3 / 2 - 1; i >= imageWidth
				* imageHeight; i -= 2) {
			yuv[count++] = data[i - 1];
			yuv[count++] = data[i];
		}
		return yuv;
	}
	/**
	 * 只能对NV12或   NV21来做,并且 宽高一致.
	 * @param data
	 * @param imageWidth
	 * @param imageHeight
	 * @return
	 */
	private byte[] rotateYUV420Degree270(byte[] data, int imageWidth, int imageHeight)
	{
		final byte [] yuv = new byte[imageWidth*imageWidth*3/2];
		int wh = 0;
		int uvHeight = 0;
		if(imageWidth != 0 || imageHeight != 0)
		{
			wh = imageWidth * imageHeight;
			uvHeight = imageHeight >> 1;//uvHeight = height / 2
		}

		//旋转Y
		int k = 0;
		for(int i = 0; i < imageWidth; i++) {
			int nPos = 0;
			for(int j = 0; j < imageHeight; j++) {
				yuv[k] = data[nPos + i];
				k++;
				nPos += imageWidth;
			}
		}

		for(int i = 0; i < imageWidth; i+=2){
			int nPos = wh;
			for(int j = 0; j < uvHeight; j++) {
				yuv[k] = data[nPos + i];
				yuv[k + 1] = data[nPos + i + 1];
				k += 2;
				nPos += imageWidth;
			}
		}
		return rotateYUV420Degree180(yuv,imageWidth,imageHeight);
	}
	/**
	 * 只能对NV12或   NV21来做,并且 宽高一致.
	 * @param data
	 * @param imageWidth
	 * @param imageHeight
	 * @return
	 */
	private byte[] rotateYUV420Degree90(byte[] data, int imageWidth, int imageHeight)
	{

		final byte [] yuv = new byte[imageWidth*imageHeight*3/2];
		// Rotate the Y luma
		int i = 0;
		for(int x = 0;x < imageWidth;x++)
		{
			for(int y = imageHeight-1;y >= 0;y--)
			{
				yuv[i] = data[y*imageWidth+x];
				i++;
			}
		}
		// Rotate the U and V color components
		i = imageWidth*imageHeight*3/2-1;
		for(int x = imageWidth-1;x > 0;x=x-2)
		{
			for(int y = 0;y < imageHeight/2;y++)
			{
				yuv[i] = data[(imageWidth*imageHeight)+(y*imageWidth)+x];
				i--;
				yuv[i] = data[(imageWidth*imageHeight)+(y*imageWidth)+(x-1)];
				i--;
			}
		}
		return yuv;
	}
	/**
	 *
	 *  对视频是NV12 或NV21的格式 ,进行裁剪, 
	 *   
	 * @param bytes
	 * @param srcWidth
	 * @param srcHeight
	 * @param dstWidth
	 * @param dstHeight
	 * @return
	 */
	byte[] frameCut(byte[] bytes,int srcWidth,int srcHeight,int dstWidth,int dstHeight)
	{
		byte[] retBytes=new byte[dstWidth*dstHeight*3/2];
		
		int srcPos=0;
		int dstPos=0;
		//拷贝Y;
		for(int x = 0;x < dstHeight;x++)  //高度一致.
		{
			System.arraycopy(bytes, srcPos, retBytes, dstPos, dstWidth);
			srcPos+=srcWidth; // 开始下一行.
			dstPos+=dstWidth;
		}
		//copy UV
		for(int x = 0;x < dstHeight/2;x++)  //高度一致.
		{
			for(int i=0;i<dstWidth/2;i++){
				retBytes[dstPos]=bytes[srcPos];
				retBytes[dstPos+1]=bytes[srcPos+1];
				dstPos+=2;
				srcPos+=2;
			}
			srcPos+=srcWidth-dstWidth;
		}
		return retBytes;
	}
	
	private  native long encoderInit(String saveFile,int width,int height,int vfps,int vbitrate,int asamplerate,int abitrate);
	private  native long encoderRelease(long handle);
	/**
	 * 
	 * @param handle
	 * @param yuv420sp  输入的是NV21,底层会把NV21格式, 转换为YUV420P
	 * @param pts
	 * @return
	 */
	private  native int  encoderWriteVideoFrame(long handle,byte[] yuv420sp,long pts);
	/**
	 * 把OpenGL中的通过  GLES20.glReadPixels获取到的数据, 是ABGR格式的数据,传递过来.
	 * @param handle
	 * @param abgr
	 * @param pts 当前时间和开始时间的差值.
	 * @return
	 */
	private  native int  encoderWriteABGRFrame(long handle,byte[] abgr,long pts);
	
	//每次读取的数据.
	/**
	 * 
	 * 这里暂时没有返回长度, 实际注意, audiodata的长度要等于 采样点 *通道数 *2
	 * 
	 * 暂时没有测试..请注意.
	 * @param handle
	 * @param audiodata  每个采样点占用两个字节,
	 * @param pts
	 * @return
	 */
	private  native int  encoderWriteAudioFrame(long handle,byte[] audiodata,long pts);
}
