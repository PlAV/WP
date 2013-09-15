package com.fastsearch.wallpaper;

import android.content.Context;
import android.util.DisplayMetrics;

public class Utils {
	private Context ctx;

	public Utils(Context ctx) {
		this.ctx = ctx;
		// TODO Auto-generated constructor stub
	}

	public Integer getScreenSize(String type) {
		DisplayMetrics metrics = new DisplayMetrics();
		metrics = ctx.getResources().getDisplayMetrics();
		if (type == "width") {
			return metrics.widthPixels;
		} else if (type == "height") {
			return metrics.heightPixels;
		}
		return null;
	}
}
