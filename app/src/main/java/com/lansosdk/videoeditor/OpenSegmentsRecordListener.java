/**
 * 杭州蓝松科技, 专业的视频开发团队
 * 
 * www.lansongtech.com
 * 
 * 此代码为开源给客户使用，请勿传递给第三方。 谢谢。
 */
package com.lansosdk.videoeditor;

public interface OpenSegmentsRecordListener {
	
	/**
	 * 相机准备好, 返回的预览大小
	 * @param previewSize  预览size
	 */
	public void segmentCameraReady(int[] previewSize);

	/**
	 * 当前录制的总进度.  包括之前已经存在的视频段  加上正在录制的视频段.
	 * @param totalTime
	 */
	public void segmentProgress(long totalTime);

	/**
	 * 当前段开始录制  在每次开始前调用.
	 */
	public void segmentRecordStart();
	/**
	 *  当前段录制停止了,
	 * @param timeMS  当前在暂停时的, 录制总时间.
	 * @param segmentIdx segmnet的总数, 等于 getSegmentSize();
	 */
	public void segmentRecordPause(int timeMS, int segmentIdx);
}
