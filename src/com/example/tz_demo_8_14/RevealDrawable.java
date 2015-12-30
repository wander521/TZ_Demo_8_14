package com.example.tz_demo_8_14;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Gravity;

public class RevealDrawable extends Drawable {

	private Drawable mUnSelectedDrawable;
	private Drawable mSelectedDrawable;
	private Rect outRect = new Rect();

	public RevealDrawable(Drawable unSelectedDrawable, Drawable selectedDrawable) {
		this.mUnSelectedDrawable = unSelectedDrawable;
		this.mSelectedDrawable = selectedDrawable;
	}

	/**
	 * level:0~10000 全彩色：5000 全灰色：0||10000 渐变色：5000~10000
	 */
	@Override
	public void draw(Canvas canvas) {
		
		int level = getLevel();
		if (level == 0 || level == 10000) {
			// 全灰色
			mUnSelectedDrawable.draw(canvas);
		} else if (level == 5000) {
			// 全彩色
			mSelectedDrawable.draw(canvas);
		} else {
			// 渐变色(一部分灰色，一部分彩色)：
			// 得到当前Drawable的矩形边界
			Rect bounds = getBounds();
			Rect r = outRect;
			
			{// 1.从灰色的图片抠出左边的部分矩形
				// level:0~5000~10000
				float ratio = (level / 5000f) - 1f;
				int w = bounds.width();
				w = (int) (w * Math.abs(ratio));
				int h = bounds.height();
				int gravity = ratio < 0 ? Gravity.LEFT : Gravity.RIGHT;

				Gravity.apply(gravity, // 从左边开始切还是从右边
						w, // 目标矩形的宽
						h, // 目标矩形的高
						bounds, // 被抠出来的原矩形
						r);// 目标矩形 -- 最终画布里面需要的矩形区域

				// 保存画布的原型
				canvas.save();
				// 将画布裁剪一部分出来
				canvas.clipRect(r);
				mUnSelectedDrawable.draw(canvas);
				// 恢复画布
				canvas.restore();
			}
			{// 2. 从彩色的图片抠出右边的部分矩形
				// level:0~5000~10000
				float ratio = (level / 5000f) - 1f;
				int w = bounds.width();
				w -= (int) (w * Math.abs(ratio));
				int h = bounds.height();
				int gravity = ratio < 0 ? Gravity.RIGHT : Gravity.LEFT;

				Gravity.apply(gravity, // 从左边开始切还是从右边
						w, // 目标矩形的宽
						h, // 目标矩形的高
						bounds, // 被抠出来的原矩形
						r);// 目标矩形 -- 最终画布里面需要的矩形区域

				// 保存画布的原型
				canvas.save();
				// 将画布裁剪一部分出来
				canvas.clipRect(r);
				mSelectedDrawable.draw(canvas);
				// 恢复画布
				canvas.restore();
			}
		}
	}
	
	@Override
	protected boolean onLevelChange(int level) {
		// 感知setLevel的调用，然后刷新 -- draw()
		invalidateSelf();
		return true;
	}

	/**
	 * 初始化数据
	 */
	@Override
	protected void onBoundsChange(Rect bounds) {
		// 定义两个Drawable图片的宽高 -- bound边界
		mUnSelectedDrawable.setBounds(bounds);
		mSelectedDrawable.setBounds(bounds);
		super.onBoundsChange(bounds);
	}

	/**
	 * 得到Drawable的实际宽高
	 */
	@Override
	public int getIntrinsicWidth() {
		return mSelectedDrawable.getIntrinsicWidth();
	}

	@Override
	public int getIntrinsicHeight() {
		return mSelectedDrawable.getIntrinsicHeight();
	}

	@Override
	public void setAlpha(int alpha) {

	}

	@Override
	public void setColorFilter(ColorFilter cf) {

	}

	@Override
	public int getOpacity() {
		return 0;
	}

}
