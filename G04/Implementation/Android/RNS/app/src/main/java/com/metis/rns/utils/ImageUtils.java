package com.metis.rns.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

public class ImageUtils {

	public static Drawable cutCircle(Drawable drawable, int diameter) {
		Bitmap bitmap = Bitmap.createBitmap(diameter, diameter, Bitmap.Config.ARGB_8888);
		Path path = new Path();
		path.addCircle(((float)diameter - 1) / 2, ((float)diameter - 1) / 2, diameter/2, Path.Direction.CCW);
		Canvas canvas = new Canvas(bitmap);
		canvas.clipPath(path);
		Bitmap source = ((BitmapDrawable) drawable).getBitmap();
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setFilterBitmap(true);
		int sourceHeight = source.getHeight();
		canvas.drawBitmap(source, new Rect(sourceHeight / 5, sourceHeight / 5, sourceHeight * 4 / 5, sourceHeight * 4 / 5), new Rect(0, 0, diameter, diameter), paint);
		return new BitmapDrawable(bitmap);
	}

    public static Bitmap drawableToBitmap(Drawable drawable, int maxWidth, int maxHeight) {
        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bd = (BitmapDrawable) drawable;
            return bd.getBitmap();
        }
        Bitmap bitmap = null;
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();
        w = w < 0 ? 1 : w;
        h = h < 0 ? 1 : h;
        float scale = getScale(maxWidth, maxHeight, w, h);
        int targetWidth = (int)(w * scale);
        int targetHeight = (int)(h * scale);
        bitmap = Bitmap.createBitmap(targetWidth, targetHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, targetWidth, targetHeight);
        drawable.draw(canvas);
        return bitmap;
    }

    public static float getScale(int maxWidth, int maxHeight, int rawWidth, int rawHeight) {
        float ret = 1f;
        if (rawWidth <= 0 || rawHeight <= 0) {
            return ret;
        }
        if (maxWidth > 0 && maxHeight > 0) {
            float widthRatio = (float)maxWidth / rawWidth;
            float heightRatio = (float)maxHeight / rawHeight;
            if (widthRatio < 1f && heightRatio < 1f) {
                ret = widthRatio > heightRatio ? widthRatio : heightRatio;
            }
        } else if (maxWidth > 0) {
            ret = (float)maxWidth / rawWidth;
        } else if (maxHeight > 0){
            ret = (float)maxHeight / rawHeight;
        }
        if (ret > 1f) {
            ret = 1f;
        }
        return ret;
    }
	
}
