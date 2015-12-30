package com.example.tz_demo_8_14;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

;

public class RevealHorizontalScrollView extends HorizontalScrollView implements
		OnTouchListener {

	private LinearLayout container;
	private int icon_width;
	private int centerX;

	public RevealHorizontalScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.setBackgroundColor(Color.BLACK);
		// 初始化
		init();
	}

	private void init() {
		container = new LinearLayout(getContext());
		android.view.ViewGroup.LayoutParams params = new LayoutParams(
				android.widget.LinearLayout.LayoutParams.WRAP_CONTENT,
				android.widget.LinearLayout.LayoutParams.WRAP_CONTENT);
		container.setLayoutParams(params);
		container.setOrientation(LinearLayout.HORIZONTAL);
		setOnTouchListener(this);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
		// 当View加载时会回调该方法
		View v = container.getChildAt(0);
		icon_width = v.getWidth();
		// 获取屏幕中心坐标
		centerX = getWidth() / 2;
		centerX -= icon_width / 2;
		container.setPadding(centerX, 0, centerX, 0);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// 监听滑动
		if (event.getAction() == MotionEvent.ACTION_MOVE) {
			// 控制渐变
			reveal();
		}
		return false;
	}

	/**
	 * 控制渐变 滑动距离控制setLevel
	 */
	private void reveal() {
		// HorizontalScrollView滑出去的距离
		int scrollX = getScrollX();
		int index_left = scrollX / icon_width;
		int index_right = index_left + 1;
		for (int i = 0; i < container.getChildCount(); i++) {
			if (i == index_left || i == index_right) {
				// 渐变效果
				ImageView iv_left = (ImageView) container
						.getChildAt(index_left);
				// ratio=5000/icon_width=level/彩色部分的长度
				float ratio = 5000f / icon_width;
				// float
				// level=ratio*彩色部分的长度=ratio*(icon_width-scrollX%icon_width)
				if (null != iv_left) {
					iv_left.setImageLevel((int) (ratio * (icon_width - scrollX
							% icon_width)));
				}
				ImageView iv_right = (ImageView) container
						.getChildAt(index_right);
				if (null != iv_right) {
					iv_right.setImageLevel((int) (10000 - scrollX % icon_width
							* ratio));
				}

			} else {
				// 全灰色
				ImageView iv = (ImageView) container.getChildAt(i);
				iv.setImageLevel(0);
			}
		}
	}

	/**
	 * 加载数据
	 * 
	 * @param revealDrawables
	 */
	public void addImagesViews(Drawable[] revealDrawables) {
		for (int i = 0; i < revealDrawables.length; i++) {
			ImageView imageView = new ImageView(getContext());
			imageView.setImageDrawable(revealDrawables[i]);
			if (i == 0) {
				imageView.setImageLevel(5000);
			}
			container.addView(imageView);
		}
		addView(container);
	}

}
