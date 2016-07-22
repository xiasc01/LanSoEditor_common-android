package com.lansosdk.videoeditor;

/**
 * 此代码在高级功能中使用.
 *
 */
public class SpriteShader {

	 /**
	   * 高级版本使用,获取一个opengl的program
	   * @param type
	   * @return  成功返回program的句柄,失败返回-1;
	   */
	  public static native int createProgramHandler(int type);
	  /**
	   *  高级版本使用, 切换滤镜的shader功能.
	   * @param shader  要加载的shader描述代码
	   * @return 成功返回切换后的句柄, 失败返回-1;
	   */
	  public static native int switchFilterShader(String shader);
	  
	  public static native void releaseProgramHandler(int handle);
	  
	  
	  public static native int getPostion(int handle);
	  
	  public static native int getTexCoord(int handle);
	  
	  public static native int getPMatrix(int handle);
	  
	  public static native int getTMatrix(int handle);
	  
	  public static native int getAlphaHandler(int handle);
	  
	  public static native int getRedHandler(int handle);
	  
	  public static native int getGreenHandler(int handle);
	  
	  public static native int getBlueHandler(int handle);
	  
}
