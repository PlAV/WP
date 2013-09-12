package com.fastsearch.wallpaper;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

public class DownloadImages extends AsyncTask<String, Void, Void> {

	TextView tv;
	String url;
	private final static String TAG = "ImageManager";

	DownloadImages(String url) {
		this.url = url;
	}

	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);

	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		tv.setText("Started Running....");
	}

	@Override
	protected Void doInBackground(String... params) {

		return null;
	}

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

}