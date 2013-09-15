package com.fastsearch.wallpaper;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.GridView;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Toast;

public class MainActivity extends Activity implements OnItemClickListener,
		OnItemLongClickListener, OnTouchListener {

	GridView gvMain;
	ProgressDialog progDialog;
	final static String DEBUG_TAG = "+++ImageDownloader+++";
	ArrayList<Bitmap> bitmapArray;
	GridView gridview;
	ImageAdapter adapter;
	public Boolean firstLoad = true;
	public Integer loadedImg = 0;
	public Integer totalImages = 0;
	Integer loadingImg;
	Integer startY = 0;
	Integer endY = 0;
	Integer moveY = 0;
	Integer final_y = 0;
	Utils utils;
	LayoutParams layoutParams;

	String[] urls = {
			"http://apps-oracle.ru/wp-content/uploads/2010/11/evelution5rt.jpg",
			"http://im5-tub-ua.yandex.net/i?id=198669864-36-72&n=21",
			"http://im4-tub-ua.yandex.net/i?id=333891059-51-72&n=21",
			"http://im7-tub-ua.yandex.net/i?id=88448043-39-72&n=21",
			"http://im6-tub-ua.yandex.net/i?id=181222475-38-72&n=21",
			"http://im5-tub-ua.yandex.net/i?id=198669864-36-72&n=21",
			"http://im4-tub-ua.yandex.net/i?id=333891059-51-72&n=21",
			"http://im7-tub-ua.yandex.net/i?id=88448043-39-72&n=21",
			"http://im6-tub-ua.yandex.net/i?id=181222475-38-72&n=21",
			"http://im5-tub-ua.yandex.net/i?id=198669864-36-72&n=21",
			"http://im4-tub-ua.yandex.net/i?id=333891059-51-72&n=21",
			"http://im7-tub-ua.yandex.net/i?id=88448043-39-72&n=21",
			"http://im6-tub-ua.yandex.net/i?id=181222475-38-72&n=21",
			"http://im5-tub-ua.yandex.net/i?id=198669864-36-72&n=21",
			"http://im4-tub-ua.yandex.net/i?id=333891059-51-72&n=21",
			"http://im7-tub-ua.yandex.net/i?id=88448043-39-72&n=21",
			"http://im6-tub-ua.yandex.net/i?id=181222475-38-72&n=21",
			"http://im5-tub-ua.yandex.net/i?id=198669864-36-72&n=21",
			"http://im4-tub-ua.yandex.net/i?id=333891059-51-72&n=21",
			"http://im7-tub-ua.yandex.net/i?id=88448043-39-72&n=21",
			"http://im6-tub-ua.yandex.net/i?id=181222475-38-72&n=21",
			"http://im5-tub-ua.yandex.net/i?id=198669864-36-72&n=21",
			"http://im4-tub-ua.yandex.net/i?id=333891059-51-72&n=21",
			"http://im7-tub-ua.yandex.net/i?id=88448043-39-72&n=21",
			"http://im6-tub-ua.yandex.net/i?id=181222475-38-72&n=21",
			"http://im5-tub-ua.yandex.net/i?id=198669864-36-72&n=21",
			"http://im4-tub-ua.yandex.net/i?id=333891059-51-72&n=21",
			"http://im7-tub-ua.yandex.net/i?id=88448043-39-72&n=21",
			"http://im6-tub-ua.yandex.net/i?id=181222475-38-72&n=21",
			"http://im5-tub-ua.yandex.net/i?id=198669864-36-72&n=21",
			"http://im4-tub-ua.yandex.net/i?id=333891059-51-72&n=21",
			"http://im7-tub-ua.yandex.net/i?id=88448043-39-72&n=21",
			"http://im6-tub-ua.yandex.net/i?id=181222475-38-72&n=21",
			"http://im5-tub-ua.yandex.net/i?id=198669864-36-72&n=21",
			"http://im4-tub-ua.yandex.net/i?id=333891059-51-72&n=21",
			"http://im7-tub-ua.yandex.net/i?id=88448043-39-72&n=21",
			"http://im6-tub-ua.yandex.net/i?id=181222475-38-72&n=21",
			"http://im5-tub-ua.yandex.net/i?id=198669864-36-72&n=21",
			"http://im4-tub-ua.yandex.net/i?id=333891059-51-72&n=21",
			"http://im7-tub-ua.yandex.net/i?id=88448043-39-72&n=21",
			"http://im6-tub-ua.yandex.net/i?id=181222475-38-72&n=21",
			"http://sfw.so/uploads/posts/2013-09/thumbs/1379107019_vinos_01.jpg", };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setupViews();
		showProgress();
		new DownloadImageTask().execute(urls);
		utils = new Utils(this);
		layoutParams = (LayoutParams) gridview.getLayoutParams();
		gridview.setOnItemClickListener(this);
		gridview.setOnTouchListener(this);
	}

	public void setupViews() {
		gridview = (GridView) findViewById(R.id.gridview);
		adapter = new ImageAdapter(MainActivity.this);
		gridview.setAdapter(adapter);
		gridview.setOnItemClickListener(MainActivity.this);
	}

	public void showProgress() {
		progDialog = new ProgressDialog(MainActivity.this);
		progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progDialog.setMessage("Loading...");
		// progDialog.setCancelable(false);
		progDialog.show();
	}

	public Integer calcImagesOnScreen() {

		Integer heightOfImg = utils.getScreenSize("width") / 2;
		Integer numOfRows = Math.round(utils.getScreenSize("height")
				/ heightOfImg);
		Integer numOfImages = numOfRows * 2;
		return numOfImages;
	}

	class DownloadImageTask extends
			AsyncTask<String, Bitmap, ArrayList<Bitmap>> {

		@Override
		protected ArrayList<Bitmap> doInBackground(String... params) {
			bitmapArray = new ArrayList<Bitmap>();

			totalImages = params.length;

			Log.v(DEBUG_TAG, "firstLoad: " + firstLoad + "  / " + totalImages);
			loadingImg = 0;
			for (Integer i = 0; i < params.length; i++) {

				if (firstLoad) {
					if (i == (calcImagesOnScreen() + 2)) {
						firstLoad = false;
						break;
					}
				} else {
					Log.v(DEBUG_TAG, "QQQ" + i + "/ "+(totalImages - 1));
					
					Log.v(DEBUG_TAG, "totalImages: i=" + i + "TTT /"
							+ loadedImg + "/" + (calcImagesOnScreen() + 2)
							+ "/" + (loadedImg + calcImagesOnScreen() + 2));

					Log.v(DEBUG_TAG, "totalImages: " + totalImages + "/" + i);
					if (i <= loadedImg) {
						Log.v(DEBUG_TAG, "continue" + i);
						continue;
					} else if (i == (totalImages - 1)) {
						Log.v(DEBUG_TAG, "break1" + i);
						break;
					} else if (i == (loadedImg + calcImagesOnScreen() + 2)) {
						Log.v(DEBUG_TAG, "break2" + i);
						break;
					}

				}
				loadingImg = i;
				Log.v(DEBUG_TAG, "ADD: " + i);

				InputStream input = null;
				try {
					URL urlConn = new URL(params[i]);
					input = urlConn.openStream();
					Bitmap img = BitmapFactory.decodeStream(input);
					publishProgress(img);

					// bitmapArray.add(img);
				} catch (MalformedURLException e) {
					Log.d(DEBUG_TAG, "Oops, Something wrong with URL...");
					e.printStackTrace();
				} catch (IOException e) {
					Log.d(DEBUG_TAG,
							"Oops, Something wrong with inpur stream...");
					e.printStackTrace();
				}

			}
			loadedImg += loadingImg;
			Log.v(DEBUG_TAG, "loadedImg: i=" + loadedImg);
			//

			return null;
		}

		protected void onPreExecute(ProgressDialog progress) {

		}

		@Override
		protected void onProgressUpdate(Bitmap... imgs) {
			super.onProgressUpdate(imgs);
			// showDialog(PROGRESS_DLG_ID);

			adapter.mThumbIds.add(imgs[0]);
			adapter.notifyDataSetChanged();
		}

		@Override
		protected void onPostExecute(ArrayList<Bitmap> result) {
			super.onPostExecute(result);
			progDialog.hide();

		}
	}

	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub

	}

	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
			long arg3) {
		// TODO Auto-generated method stub
		Toast.makeText(MainActivity.this, "" + position, Toast.LENGTH_SHORT)
				.show();
		new DownloadImageTask().execute(urls);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		int Action = event.getAction();
		switch (Action) {
		case MotionEvent.ACTION_DOWN:
			startY = (int) Math.abs(event.getY());
			break;
		case MotionEvent.ACTION_MOVE:
			moveY = (int) Math.abs(event.getY());
			if ((startY - endY) > 40) {

			}
			break;
		case MotionEvent.ACTION_UP:
			endY = (int) Math.abs(event.getY());
			break;
		}
		return false;
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
		return false;
	}

}
