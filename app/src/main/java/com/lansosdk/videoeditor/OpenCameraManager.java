package com.lansosdk.videoeditor;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.ImageFormat;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PreviewCallback;
import android.hardware.Camera.Size;
import android.os.Build;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;

@SuppressWarnings("deprecation")
public class OpenCameraManager {
	private Camera camera = null;
	private boolean isPreviewOn = false;
	private int cameraFacingType = CameraInfo.CAMERA_FACING_BACK;
	private Activity activity;
	private int defaultScreenResolution = -1;

	private int previewSize[] = new int[2];
	private int rotateDegree=0;
	
	private int mDesireWidth,mDesireHeight;
	public OpenCameraManager(Activity activity,int desireW,int desireH) {
		super();
		this.activity = activity;
		mDesireWidth=desireW;
		mDesireHeight=desireH;
	}

	Thread startThread;

	public void openCamera() {
		startThread = new Thread(new Runnable() {
			@Override
			public void run() {
				int n = Camera.getNumberOfCameras();
				if (n > 1) {
					for (int i = 0; i < n; i++) {
						CameraInfo info = new CameraInfo();
						Camera.getCameraInfo(i, info);
						if (info.facing == cameraFacingType) {
							camera = Camera.open(i);
							break;
						}
					}
				} else {
					camera = Camera.open();
					cameraFacingType = CameraInfo.CAMERA_FACING_BACK;
				}
			}
		});
		startThread.start();
	}

	public boolean flashEnable() {
		return activity.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)
				&& cameraFacingType == CameraInfo.CAMERA_FACING_BACK;

	}

	private void startThreadJoin() {
		if (startThread != null) {
			try {
				startThread.join();
			} catch (Exception e) {
				//ignore
			}
		}
	}

	public void setPreviewDisplay(SurfaceHolder holder) {
		try {
			startThreadJoin();
			camera.setPreviewDisplay(holder);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setPreviewTexture(SurfaceTexture surfaceTexture) {
		try {
			startThreadJoin();
			camera.setPreviewTexture(surfaceTexture);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setPreviewCallBack(PreviewCallback callback) {
		camera.setPreviewCallback(callback);
	}

	boolean previewing = false;

	public boolean isPreviewing() {
		return previewing;
	}

	public boolean cameraChangeEnable() {
		return Camera.getNumberOfCameras() > 1;
	}

	public void setPreviewCallBackWithBuffer(int previewWidth, int previewHeight, PreviewCallback callback) {
		int buffersize = previewWidth * previewHeight * ImageFormat.getBitsPerPixel(ImageFormat.NV21) / 8;
		byte[] previewBuffer = new byte[buffersize];
		startThreadJoin();
		camera.addCallbackBuffer(previewBuffer);
		camera.setPreviewCallbackWithBuffer(callback);
	}

	public void startPreview() {
		if (!isPreviewOn && camera != null) {
			isPreviewOn = true;
			startThreadJoin();
			camera.startPreview();
			previewing = true;
		}
	}

	public void stopPreview() {
		if (isPreviewOn && camera != null) {
			isPreviewOn = false;
			camera.stopPreview();
			previewing = false;
		}
	}

	public void closeCamera() {
		if (camera != null) {
			stopPreview();
			camera.setPreviewCallback(null);
			camera.release();
			camera = null;
		}
	}

	public void release() {
		if (camera != null) {
			stopPreview();
			camera.setPreviewCallback(null);
			camera.release();
			camera = null;
		}
	}
	public boolean supportFocus() {
		if(camera!=null){
			Parameters mParameters = camera.getParameters();
			return (mParameters.getMaxNumFocusAreas() > 0 ? true : false);
		}else{
			return false;
		}
	}

	public boolean isUseBackCamera() {
		if(camera==null){
			return false;
		}
		startThreadJoin();
		return cameraFacingType == CameraInfo.CAMERA_FACING_BACK;
	}

	public boolean isUseFrontCamera() {
		if(camera==null){
			return false;
		}
		startThreadJoin();
		return cameraFacingType == CameraInfo.CAMERA_FACING_FRONT;
	}

	public void useBackCamera() {
		
		startThreadJoin();
		cameraFacingType = CameraInfo.CAMERA_FACING_BACK;
	}

	public void useFrontCamera() {
		startThreadJoin();
		cameraFacingType = CameraInfo.CAMERA_FACING_FRONT;
	}

	public void changeCamera() {
		startThreadJoin();
		if (cameraFacingType == CameraInfo.CAMERA_FACING_BACK) {
			useFrontCamera();
		} else if (cameraFacingType == CameraInfo.CAMERA_FACING_FRONT) {
			useBackCamera();
		}
		closeCamera();
		openCamera();
	}

	public void updateParameters() {
		startThreadJoin();
		
		
		Camera.Parameters camParams = camera.getParameters();

		Parameters parameters = camera.getParameters();
		List<Camera.Size> supportedPreviewSizes = parameters.getSupportedPreviewSizes();
		
		
		int desireWidth,desireHeight;
		desireWidth=mDesireWidth;
		desireHeight=mDesireHeight;
		
		if (supportedPreviewSizes != null && supportedPreviewSizes.size() > 0) {
			
			//先把分辨率从小到大排列.
			Collections.sort(supportedPreviewSizes, new ResolutionComparator());
			
			rotateDegree=determineDisplayOrientation(activity, cameraFacingType);
			if(rotateDegree==90 || rotateDegree==270){  //竖屏的话, 用户设置过来宽度和高度调换了
				desireWidth=mDesireHeight;
				desireHeight=mDesireWidth;
			}
			Camera.Size preSize = null;
			if (defaultScreenResolution == -1) {
				boolean hasSize = false;
				
				Log.i("TAG","desire preview size:"+desireWidth+" desireHeight:"+desireHeight);
//				for (int i = 0; i < supportedPreviewSizes.size(); i++) {
//					Size size = supportedPreviewSizes.get(i);
//					Log.i("TAG","support size:"+size.width+" x "+size.height);
//				}	
				
				for (int i = 0; i < supportedPreviewSizes.size(); i++) {
					Size size = supportedPreviewSizes.get(i);
					if (size != null && size.width >= desireWidth && size.height >= desireHeight) {
						preSize = size;
						hasSize = true;
						break;
					}
				}
				
				if (!hasSize) {
					int mediumResolution = supportedPreviewSizes.size() / 2;
					if (mediumResolution >= supportedPreviewSizes.size()) {
						mediumResolution = supportedPreviewSizes.size() - 1;
					}
					preSize = supportedPreviewSizes.get(mediumResolution);
				}
			} else {
				if (defaultScreenResolution >= supportedPreviewSizes.size()) {
					defaultScreenResolution = supportedPreviewSizes.size() - 1;
				}
				preSize = supportedPreviewSizes.get(defaultScreenResolution);
			}
			if (preSize != null) {
				previewSize[0] = preSize.width;
				previewSize[1] = preSize.height;
				
				Log.i("TAG","last got preview size:"+previewSize[0]+" x "+previewSize[1]);
				
				camParams.setPreviewSize(previewSize[0], previewSize[1]);
			}
		}
		
		camParams.setPreviewFrameRate(25);//gzj++
		
		if (Build.VERSION.SDK_INT > Build.VERSION_CODES.FROYO) {
			
			rotateDegree=determineDisplayOrientation(activity, cameraFacingType);
			//Log.i("TAG","获取到的摄像头旋转角度是:"+rotateDegree);
		//	camParams.setRotation(90);
			
			
			
			  
			camera.setDisplayOrientation(rotateDegree);
			
			List<String> focusModes = camParams.getSupportedFocusModes();
			if (cameraFacingType == CameraInfo.CAMERA_FACING_BACK && focusModes != null) {//  fix
				Log.i("video", Build.MODEL);
				if (((Build.MODEL.startsWith("GT-I950")) || (Build.MODEL.endsWith("SCH-I959")) || (Build.MODEL
					.endsWith("MEIZU MX3"))) && focusModes.contains(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE)) {
					camParams.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
				} else if (focusModes.contains(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO)) {
					camParams.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO);
				} else {
					camParams.setFocusMode(Camera.Parameters.FOCUS_MODE_FIXED);
				}
			}
			
			
		} else {
			camera.setDisplayOrientation(90);

		}
		camera.setParameters(camParams);
	}

	public void doFocus(List<Camera.Area> focusList) {
		Camera.Parameters param = camera.getParameters();
		param.setFocusAreas(focusList);
		param.setMeteringAreas(focusList);
		try {
			camera.setParameters(param);
		} catch (Exception e) {
			camera.autoFocus(null);
		}
	}

	public boolean changeFlash() {
		boolean flashOn = false;
		if (flashEnable()) {
			Parameters params = camera.getParameters();
			if (Parameters.FLASH_MODE_TORCH.equals(params.getFlashMode())) {
				params.setFlashMode(Parameters.FLASH_MODE_OFF);
				flashOn = false;
			} else {
				params.setFlashMode(Parameters.FLASH_MODE_TORCH);
				flashOn = true;
			}
			camera.setParameters(params);
		}
		return flashOn;
	}

	public void closeFlash() {
		if (flashEnable()) {
			Parameters params = camera.getParameters();
			if (Parameters.FLASH_MODE_TORCH.equals(params.getFlashMode())) {
				params.setFlashMode(Parameters.FLASH_MODE_OFF);
				camera.setParameters(params);
			}
		}
	}

	public Camera getCamera() {
		startThreadJoin();

		return camera;
	}

	public int[] getPreviewSize() {
		startThreadJoin();

		return previewSize;
	}
	public int getRotateDegree()
	{
		return rotateDegree;
	}

	private int determineDisplayOrientation(Activity activity, int defaultCameraId) {
		int displayOrientation = 0;
		if (Build.VERSION.SDK_INT > Build.VERSION_CODES.FROYO) {
			CameraInfo cameraInfo = new CameraInfo();
			Camera.getCameraInfo(defaultCameraId, cameraInfo);

			int degrees = getRotationAngle(activity);

			if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
				displayOrientation = (cameraInfo.orientation + degrees) % 360;
				displayOrientation = (360 - displayOrientation) % 360;
			} else {
				displayOrientation = (cameraInfo.orientation - degrees + 360) % 360;
			}
		}
		return displayOrientation;
	}

	public static int getRotationAngle(Activity activity) {
		int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();
		int degrees = 0;

		switch (rotation) {
		case Surface.ROTATION_0:
			degrees = 0;
			break;
		case Surface.ROTATION_90:
			degrees = 90;
			break;
		case Surface.ROTATION_180:
			degrees = 180;
			break;
		case Surface.ROTATION_270:
			degrees = 270;
			break;
		}
		return degrees;
	}

	class ResolutionComparator implements Comparator<Camera.Size> {
		@Override
		public int compare(Camera.Size size1, Camera.Size size2) {
			if (size1.height != size2.height) {
				return size1.height - size2.height;
			} else {
				return size1.width - size2.width;
			}
		}
	}
}
