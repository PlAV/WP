package com.fastsearch.wallpaper;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {
	private static final String TAG = "ImageManager";
	private final Context mContext;
	ArrayList<Bitmap> bitmapArray;
	final static String DEBUG_TAG = "+++ImageDownloader+++";
	ArrayList<Bitmap> mThumbIds;

	public ImageAdapter(Context c) {
		mThumbIds = new ArrayList<Bitmap>();
		mContext = c;
	}

	@Override
	public int getCount() {
		return mThumbIds.size();
	}

	@Override
	public Object getItem(int position) {
		return mThumbIds.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	// create a new ImageView for each item referenced by the Adapter
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView imageView;
		if (convertView == null) { // if it's not recycled, initialize some
									// attributes
			imageView = new ImageView(mContext);
			Utils utils = new Utils(mContext);
			// MainActivity.getScreenSize("width");
			imageView.setLayoutParams(new GridView.LayoutParams(utils
					.getScreenSize("width") / 2,
					utils.getScreenSize("width") / 2));
			imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
			// imageView.setPadding(5, 5, 5, 5);
		} else {
			imageView = (ImageView) convertView;
		}

		// Bitmap image = downloadImage(mThumbIds[position]);
		// if (image != null) {
		Log.v(TAG, "Got image by URL: " + mThumbIds.get(position));
		// imageView.setImageResource(mThumbIds[position]);
		// imageView.setImageBitmap(image);
		// }

		imageView.setImageBitmap(mThumbIds.get(position));
		// imageView.setImageResource(mThumbIds[position]);
		return imageView;
	}

};