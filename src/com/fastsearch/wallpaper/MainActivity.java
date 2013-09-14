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
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnItemClickListener {

	GridView gvMain;
	ProgressDialog progDialog;
	ArrayAdapter<String> adapter;
	ImageView img1, img2, img3, img4, img5;

	final int PROGRESS_DLG_ID = 666;
	final static String DEBUG_TAG = "+++ImageDownloader+++";
	ArrayList<Bitmap> bitmapArray;
	GridView gridview;

	String[] urls = { "http://huuah.com/images/progress2.png",
			"http://huuah.com/images/progress2.png",
			"http://huuah.com/images/progress2.png",
			"http://huuah.com/images/progress2.png",
			"http://huuah.com/images/progress2.png",
			"http://huuah.com/images/progress2.png",
			"http://huuah.com/images/progress2.png",
			"http://huuah.com/images/progress2.png",
			"http://huuah.com/images/progress2.png",
			"http://huuah.com/images/progress2.png",
			"http://huuah.com/images/progress2.png",
			"http://huuah.com/images/progress2.png",
			"http://huuah.com/images/progress2.png",
			"http://huuah.com/images/progress2.png",
			"http://huuah.com/images/progress2.png",
			"http://huuah.com/images/progress2.png",
			"http://huuah.com/images/progress2.png", };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		gridview = (GridView) findViewById(R.id.gridview);

		progDialog = new ProgressDialog(MainActivity.this);
		progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progDialog.setMessage("Loading...");
		progDialog.setCancelable(false);
		progDialog.show();

		new DownloadImageTask().execute(urls);

	}

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

		}

		@Override
		protected void onProgressUpdate(Void... values) {
			super.onProgressUpdate(values);
			// showDialog(PROGRESS_DLG_ID);
		}

		@Override
		protected void onPostExecute(ArrayList<Bitmap> result) {
			super.onPostExecute(result);

			ImageAdapter adapter = new ImageAdapter(MainActivity.this, result);
			gridview.setAdapter(adapter);
			gridview.setOnItemClickListener(MainActivity.this);
			progDialog.hide();
			/*
			 * for (Integer i = 0; i < result.size(); i++) {
			 * 
			 * img1.setImageBitmap(result.get(i));
			 * img2.setImageBitmap(result.get(i));
			 * img3.setImageBitmap(result.get(i)); }
			 */
			// dismissDialog(PROGRESS_DLG_ID);
			// Log.d("DDD", "DDD" + result);
			// img.setImageBitmap(resulti);
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
	}

}
