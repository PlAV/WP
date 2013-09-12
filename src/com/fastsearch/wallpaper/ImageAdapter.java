package com.fastsearch.wallpaper;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
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

	public ImageAdapter(Context c, ArrayList<Bitmap> result) {
		mContext = c;
		this.mThumbIds = result;
		// new DownloadImageTask().execute(urls);
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
			imageView.setLayoutParams(new GridView.LayoutParams(350, 350));
			imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
			imageView.setPadding(5, 5, 5, 5);
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

	// references to our images

	class DownloadImageTask extends AsyncTask<String, Void, ArrayList<Bitmap>> {
		@Override
		protected ArrayList<Bitmap> doInBackground(String... params) {
			publishProgress(new Void[] {});

			bitmapArray = new ArrayList<Bitmap>();
			Integer count = 0;
			for (Integer i = 0; i < params.length; i++) {
				count++;
				InputStream input = null;
				try {
					URL urlConn = new URL(params[i]);
					input = urlConn.openStream();
					Bitmap img = BitmapFactory.decodeStream(input);
					bitmapArray.add(img);
				} catch (MalformedURLException e) {
					Log.d(DEBUG_TAG, "Oops, Something wrong with URL...");
					e.printStackTrace();
				} catch (IOException e) {
					Log.d(DEBUG_TAG,
							"Oops, Something wrong with inpur stream...");
					e.printStackTrace();
				}

			}
			//

			return bitmapArray;
		}

		protected void onPreExecute(ProgressDialog progress) {
			progress = new ProgressDialog(mContext);

			progress.setMessage("Загрузка каталога...");
			progress.setIndeterminate(true);
			progress.setCancelable(true);
			progress.show();
		}

		@Override
		protected void onProgressUpdate(Void... values) {
			super.onProgressUpdate(values);
			// showDialog(PROGRESS_DLG_ID);
		}

		@Override
		protected void onPostExecute(ArrayList<Bitmap> result) {
			super.onPostExecute(result);

			/*
			 * for (Integer i = 0; i < result.size(); i++) {
			 * 
			 * img1.setImageBitmap(result.get(i));
			 * img2.setImageBitmap(result.get(i));
			 * img3.setImageBitmap(result.get(i)); }
			 */

		}
	}

	public static Bitmap downloadImage(String mThumbIds2) {
		Bitmap bitmap = null;
		HttpURLConnection conn = null;
		BufferedInputStream buf_stream = null;
		try {
			Log.v(TAG, "Starting loading image by URL: " + mThumbIds2);
			conn = (HttpURLConnection) new URL(mThumbIds2).openConnection();
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
			Log.e(TAG, "Url parsing was failed: " + mThumbIds2);
		} catch (IOException ex) {
			Log.d(TAG, mThumbIds2 + " does not exists");
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