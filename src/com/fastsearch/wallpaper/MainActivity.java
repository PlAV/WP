package com.fastsearch.wallpaper;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnItemClickListener {

	String[] data = { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k" };

	GridView gvMain;
	ArrayAdapter<String> adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		GridView gridview = (GridView) findViewById(R.id.gridview);
		ImageAdapter adapter = new ImageAdapter(this);
		gridview.setAdapter(adapter);
		gridview.setOnItemClickListener(this);

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
