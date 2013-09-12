package com.fastsearch.wallpaper;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {
	private static final String TAG = "ImageManager";
	private Context mContext;

	public ImageAdapter(Context c) {
		mContext = c;
	}

	public int getCount() {
		return mThumbIds.length;
	}

	public Object getItem(int position) {
		return mThumbIds[position];
	}

	public long getItemId(int position) {
		return 0;
	}

	// create a new ImageView for each item referenced by the Adapter
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView imageView;
		if (convertView == null) { // if it's not recycled, initialize some
									// attributes
			imageView = new ImageView(mContext);
			imageView.setLayoutParams(new GridView.LayoutParams(350, 350));
			imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
			imageView.setPadding(5, 5, 5, 5);
		} else {
			imageView = (ImageView) convertView;
		}

		Bitmap image = downloadImage(mThumbIds[position]);
		if ( image != null ) {
	          Log.v(TAG, "Got image by URL: " + mThumbIds[position]);
	          imageView.setImageBitmap(image);
		}
		
		//imageView.setImageResource(mThumbIds[position]);
		return imageView;
	}

	// references to our images
	private String[] mThumbIds = {
			"http://s.4pda.to/forum//uploads/av-2533711-21696238.jpg",
		 };

	public static Bitmap downloadImage(String iUrl) {
		Bitmap bitmap = null;
		HttpURLConnection conn = null;
		BufferedInputStream buf_stream = null;
		try {
			Log.v(TAG, "Starting loading image by URL: " + iUrl);
			conn = (HttpURLConnection) new URL(iUrl).openConnection();
			conn.setDoInput(true);
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.connect();
			buf_stream = new BufferedInputStream(conn.getInputStream(), 8192);
			bitmap = BitmapFactory.decodeStream(buf_stream);
			buf_stream.close();
			conn.disconnect();
			buf_stream = null;
			conn = null;
		} catch (MalformedURLException ex) {
			Log.e(TAG, "Url parsing was failed: " + iUrl);
		} catch (IOException ex) {
			Log.d(TAG, iUrl + " does not exists");
		} catch (OutOfMemoryError e) {
			Log.w(TAG, "Out of memory!!!");
			return null;
		} finally {
			if (buf_stream != null)
				try {
					buf_stream.close();
				} catch (IOException ex) {
				}
			if (conn != null)
				conn.disconnect();
		}
		return bitmap;
	}
};