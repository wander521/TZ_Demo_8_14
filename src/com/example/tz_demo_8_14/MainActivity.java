package com.example.tz_demo_8_14;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private ImageView img;
	private int[] mImgIds_gray = new int[] { R.drawable.cloud_gray,
			R.drawable.lei_rain_gray, R.drawable.rain_gray,
			R.drawable.sun_cloud_gray, R.drawable.sun_gray, R.drawable.xue_gray };
	private int[] mImgIds_normal = new int[] { R.drawable.cloud,
			R.drawable.lei_rain, R.drawable.rain,
			R.drawable.sun_cloud, R.drawable.sun, R.drawable.xue };
	private Drawable[] revealDrawables;
	protected int level=5000;
	private RevealHorizontalScrollView hsv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// QQ:215298766
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
//		img=(ImageView) findViewById(R.id.img);
		initData();
		initView();
//		img.setImageDrawable(revealDrawables[0]);
//		img.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				if (level >=0 && level<=10000) {
//					level-=1000;
//					img.getDrawable().setLevel(level);
//				}
//			}
//		});
		
	}
	
	protected void initData(){
		revealDrawables=new Drawable[mImgIds_gray.length];
	}
	protected void initView(){
		for (int i = 0; i < mImgIds_gray.length; i++) {
			RevealDrawable revealDrawable=new RevealDrawable(getResources().getDrawable(mImgIds_gray[i])
					, getResources().getDrawable(mImgIds_normal[i]));
			revealDrawables[i]=revealDrawable;
		}
		hsv=(RevealHorizontalScrollView) findViewById(R.id.hsv);
		hsv.addImagesViews(revealDrawables);
	}
}
