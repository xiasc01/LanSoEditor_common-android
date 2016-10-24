package com.lansosdk.editorDemo;


import java.util.ArrayList;
import java.util.List;

import com.lansoeditor.demo.R;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class DemoActivity extends Activity{

	private TestCmdInfo[] mTestCmdArray={
			 new TestCmdInfo(R.string.demo_cmd_avsplit, "demo_cmd_video_cut",TestCmdInfo.NEED_1V),
			 new TestCmdInfo(R.string.demo_cmd_avsplit, "demo_cmd_video_cut",TestCmdInfo.NEED_1V),
			 new TestCmdInfo(R.string.demo_cmd_avsplit, "demo_cmd_video_cut",TestCmdInfo.NEED_1V),
			 
			
	};
	/**
	 *  new TestCmdInfo(R.string.demo_cmd_avsplit,
 new TestCmdInfo(R.string.demo_cmd_avmerge,
 new TestCmdInfo(R.string.demo_cmd_replaceaudio,
 new TestCmdInfo(R.string.demo_cmd_cutaudio,
 new TestCmdInfo(R.string.demo_cmd_cutvideo,
 new TestCmdInfo(R.string.demo_cmd_concatvideo,
 new TestCmdInfo(R.string.demo_cmd_convataudio,
 new TestCmdInfo(R.string.demo_cmd_videocompress,
 new TestCmdInfo(R.string.demo_cmd_videocrop,
 new TestCmdInfo(R.string.demo_cmd_videoscale_soft,
 new TestCmdInfo(R.string.demo_cmd_videoscale_hard,
 new TestCmdInfo(R.string.demo_cmd_videowatermark,
 new TestCmdInfo(R.string.demo_cmd_videocropwatermark,
 new TestCmdInfo(R.string.demo_cmd_audiodelaymix,
 new TestCmdInfo(R.string.demo_cmd_audiovolumemix,
 new TestCmdInfo(R.string.demo_cmd_videogetframes,
 new TestCmdInfo(R.string.demo_cmd_videogetoneframe,
 new TestCmdInfo(R.string.demo_cmd_ontpicturevideo,
 new TestCmdInfo(R.string.demo_cmd_morepicturevideo,
 new TestCmdInfo(R.string.demo_cmd_videopad,
 new TestCmdInfo(R.string.demo_cmd_videocroppad,
 new TestCmdInfo(R.string.demo_cmd_videorotate,
 new TestCmdInfo(R.string.demo_cmd_videoclockwise90,
 new TestCmdInfo(R.string.demo_cmd_videocounterClockwise90,
 new TestCmdInfo(R.string.demo_cmd_videozeroangle,
 new TestCmdInfo(R.string.demo_cmd_videoaddanglemeta,
 new TestCmdInfo(R.string.demo_cmd_videoadjustspeed,
 new TestCmdInfo(R.string.demo_cmd_videomirrorh,
 new TestCmdInfo(R.string.demo_cmd_videomirrorv,
 new TestCmdInfo(R.string.demo_cmd_videorotateh,
 new TestCmdInfo(R.string.demo_cmd_videorotatev,
 new TestCmdInfo(R.string.demo_cmd_videoreverse,
 new TestCmdInfo(R.string.demo_cmd_audioreverse,
 new TestCmdInfo(R.string.demo_cmd_avreverse,
	 */
	private ListView  mListView=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		 setContentView(R.layout.demo_layout);
		 
		 mListView=(ListView)findViewById(R.id.id_demo_list);
		  
		 mListView.setAdapter(new ArrayAdapter<String>(DemoActivity.this,
					android.R.layout.simple_list_item_1, getTextArray()));
		 
		 
		 mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Log.i("sno","onclick  postion:"+position);
			}
		});
	}
	/**
	 * 获取要显示的字符串.
	 * @return
	 */
	private String[]  getTextArray()
	{
		 List<String> cmdList=new ArrayList<String>();
		 for(int i=0; i<mTestCmdArray.length; i++){
			 
			 TestCmdInfo item=mTestCmdArray[i];
			 
			 String str="NO.";
			 str+=String.valueOf(i+1);
			 str+="     ";
			 str+=getResources().getString(item.mHintId);
			 cmdList.add(str);
		 }
		 
		 String[] strArray=new String[cmdList.size()];  
	     for(int i=0;i<cmdList.size();i++){  
	    	 strArray[i]=(String)cmdList.get(i);  
	     } 
	     return strArray;
	}	
}
