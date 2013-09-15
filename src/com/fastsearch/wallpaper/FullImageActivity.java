package com.fastsearch.wallpaper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

public class FullImageActivity extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.full_image);

		// get intent data
		Intent i = getIntent();

		// Selected image id
		int position = i.getExtras().getInt("id");
		//int adapter = i.getExtras().getInt("adapter");
		//ImageAdapter imageAdapter = new ImageAdapter(this);
		//MainActivity MainActivty = new MainActivity(this);
		
		
		
		ImageView imageView = (ImageView) findViewById(R.id.full_image_view);
		//imageView.setImageResource(R.drawable.img8);
		//Toast.makeText(this, "" + MainActivty.adapter.getCount(), Toast.LENGTH_SHORT)
	//	.show();
		imageView.setImageBitmap(ImageAdapter.mThumbIds.get(position));
	}
}
