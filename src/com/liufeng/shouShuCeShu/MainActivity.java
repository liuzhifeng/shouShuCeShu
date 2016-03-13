package com.liufeng.shouShuCeShu;



import com.example.a.R;

import cn.domob.android.ads.AdEventListener;
import cn.domob.android.ads.AdView;
import cn.domob.android.ads.AdManager.ErrorCode;
import android.annotation.SuppressLint;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends Activity implements android.view.View.OnClickListener{
	RelativeLayout mAdContainer;
	AdView mAdview;
	private TextView time;
	public static final String PUBLISHER_ID = "56OJ28UYuN2d2QlQiO";
	public static final String InlinePPID = "16TLPCkaApqDHNUvRmceiwri";
	private int count=0; 
	private long firstTime=0;
	private String tag="cishu";
	private int mTime=10;
	private boolean isGameOver=false;
	private boolean isPause=false;
	private long interval=10000;
	private Button tvdj;
	public interface GameListener{
		void timechanged(int currentTime);
		void gameover(int count);
	}
	public GameListener mListener=new GameListener() {
		
		@Override
		public void timechanged(int currentTime) {
			// TODO Auto-generated method stub
			
			time.setText(""+currentTime);
		}
		
	
		@SuppressLint("NewApi")
		@Override
		public void gameover(int count) {
			// TODO Auto-generated method stub
			new AlertDialog.Builder(MainActivity.this).setTitle("时间到!!!").setMessage("你的撸力值为"+count).setPositiveButton("分享手速", new OnClickListener() {
				
				
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					 /*
					  * 分享代码实现，优化
					  */
					Intent sendIntent=new Intent();
					sendIntent.setAction(Intent.ACTION_SEND);
					sendIntent.putExtra(Intent.EXTRA_TEXT, "这是我的简单应用，谢谢赏脸使用，它能测你的手速到底有多快");
					sendIntent.setType("text/plain");
					startActivity(sendIntent);
				}
			}).setNegativeButton("再测一次", new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					/*
					 * 再来一次代码实现
					 */
					recreate();
					
				}
			}).show();
		}
	};
	private static final int TIME_CHANGED=0X110;
	
	private Handler mHandler=new Handler(){
	
		public void handleMessage(Message msg){
			switch (msg.what) {
			case TIME_CHANGED:
				if(isGameOver||isPause)
					return ;
				if(mListener!=null){
					mListener.timechanged(mTime);
				}
				if(mTime==0){
					isGameOver=true;
					mListener.gameover(count); 
				
					return ;
				}
				mTime--;
				mHandler.sendEmptyMessageDelayed(TIME_CHANGED,1000);
				break;
			}
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		time=(TextView)findViewById(R.id.tv_time);
		tvdj=(Button)findViewById(R.id.tvdj);
		tvdj.setOnClickListener(this);
		mHandler.sendEmptyMessage(TIME_CHANGED);
		mAdContainer = (RelativeLayout) findViewById(R.id.adcontainer);
		// Create ad view
		mAdview = new AdView(this, MainActivity.PUBLISHER_ID, MainActivity.InlinePPID);
		mAdview.setKeyword("game");
		mAdview.setUserGender("male");
		mAdview.setUserBirthdayStr("2000-08-08");
		mAdview.setUserPostcode("123456");
		mAdview.setAdEventListener(new AdEventListener() {
			@Override
			public void onAdOverlayPresented(AdView adView) {
				Log.i("DomobSDKDemo", "overlayPresented");
			}
			@Override
			public void onAdOverlayDismissed(AdView adView) {
				Log.i("DomobSDKDemo", "Overrided be dismissed");
			}
			@Override
			public void onAdClicked(AdView arg0) {
				Log.i("DomobSDKDemo", "onDomobAdClicked");
			}
			@Override
			public void onLeaveApplication(AdView arg0) {
				Log.i("DomobSDKDemo", "onDomobLeaveApplication");
			}
			@Override
			public Context onAdRequiresCurrentContext() {
				return MainActivity.this;
			}
			@Override
			public void onAdFailed(AdView arg0, ErrorCode arg1) {
				Log.i("DomobSDKDemo", "onDomobAdFailed");
			}
			@Override
			public void onEventAdReturned(AdView arg0) {
				Log.i("DomobSDKDemo", "onDomobAdReturned");
			}
		});
		RelativeLayout.LayoutParams layout = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		layout.addRule(RelativeLayout.CENTER_HORIZONTAL);
		mAdview.setLayoutParams(layout);
		mAdContainer.addView(mAdview);
	}
	
		
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		isPause=true;
		mHandler.removeMessages(TIME_CHANGED);
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if(isPause){
			isPause=false;
			mHandler.sendEmptyMessage(TIME_CHANGED);
		}
	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		long secondTime=System.currentTimeMillis();
		Log.i(tag, "cishu0");
		if(secondTime-firstTime<=interval){
			count=count+1;
			Log.i(tag, "cishu...");
		}else{
			count=1;
		}
		Log.i(tag, "cishujieshu");
		firstTime=secondTime;
	}
}
