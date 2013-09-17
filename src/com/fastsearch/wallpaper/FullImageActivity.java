package com.fastsearch.wallpaper;

import java.io.IOException;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.WallpaperManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class FullImageActivity extends Activity implements OnClickListener {

	Bitmap img;
	Button set_wp, save_img;

	@SuppressLint("NewApi")
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.full_image);
		getActionBar().setDisplayHomeAsUpEnabled(true);

		getActionBar().setTitle("Back");
		getActionBar().setLogo(R.drawable.back);

		Intent i = getIntent();
		int position = i.getExtras().getInt("id");

		img = ImageAdapter.mThumbIds.get(position);

		ImageView imageView = (ImageView) findViewById(R.id.full_image_view);
		imageView.setImageBitmap(img);

		set_wp = (Button) findViewById(R.id.set_wp);
		save_img = (Button) findViewById(R.id.save_img);

		set_wp.setOnClickListener(this);
		save_img.setOnClickListener(this);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			// openSearch();
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.set_wp:
			WallpaperManager wpm = WallpaperManager.getInstance(this);
			try {
				wpm.setBitmap(img);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case R.id.save_img:

			break;

		}
	}
}
