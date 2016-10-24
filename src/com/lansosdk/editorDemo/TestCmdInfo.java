package com.lansosdk.editorDemo;

public class TestCmdInfo {

	public final int mHintId;
	public final String  mActivityName;
	public  boolean isNeedVideo1=false;
	public  boolean isNeedVideo2=false;
	public  boolean isNeedAudio1=false;
	public  boolean isNeedAudio2=false;
	
	public final static int NEED_1V=10;
	public final static int NEED_2V=11;
	public final static int NEED_1V1A=12;
	public final static int NEED_2A=13;
	public final static int NEED_1V2A=14;
	
	/**
	 * 
	 * @param hintTextId  显示字符串名字的ID
	 * @param ActivityName  对应Activity名字
	 * @param needV1   是否需要 第一个视频
	 * @param needV2   是否需要第二个视频
	 * @param needA1    是否需要第一个音频
	 * @param needA2    是否需要第二个音频 
	 */
	public TestCmdInfo(int hintTextId,String activityName,int need)
	{
		mHintId=hintTextId;
		mActivityName=activityName;
		
		switch (need) {
			case NEED_1V:
				isNeedVideo1=true;
				break;
			case NEED_2V:
				isNeedVideo1=true;
				isNeedVideo2=true;
				break;
			case NEED_1V1A:
				isNeedVideo1=true;
				isNeedAudio1=true;
				break;
			case NEED_1V2A:
				isNeedVideo1=true;
				isNeedAudio1=true;
				isNeedAudio2=true;
				
				break;
			case NEED_2A:
				isNeedAudio1=true;
				isNeedAudio2=true;
				break;
		default:
			break;
		}
		
	}
}
