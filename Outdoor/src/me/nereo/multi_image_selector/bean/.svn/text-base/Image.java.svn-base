package me.nereo.multi_image_selector.bean;

import java.io.IOException;

import com.hwacreate.outdoor.utl.Bimp;

import android.graphics.Bitmap;

/**
 * 图片实体 Created by Nereo on 2015/4/7.
 */
public class Image {
	public String path;
	private Bitmap bitmap;

	public Image(String path) {
		this.path = path;
	}

	@Override
	public boolean equals(Object o) {
		try {
			Image other = (Image) o;
			return this.path.equalsIgnoreCase(other.path);
		} catch (ClassCastException e) {
			e.printStackTrace();
		}
		return super.equals(o);
	}

	public Bitmap getBitmap() {
		if (bitmap == null) {
			try {
				bitmap = Bimp.revitionImageSize(path);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return bitmap;
	}

	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

}
