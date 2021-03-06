package com.example.bagofpix;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.Session;

public class MainActivity extends Activity {

	public static Session session;
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		DBHandler.context = this;
		DBHandler db = new DBHandler();
		ArrayList<Story> a = db.get_stories();
		for (int i = 0; i < a.size(); i++) {
			// Create new wrapper linear layout
			LinearLayout ll = new LinearLayout(this);
			LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
			lp.setMargins(10, 10, 10, 10);
			ll.setLayoutParams(lp);
			ll.setOrientation(LinearLayout.HORIZONTAL);
			ll.setClickable(true);
			ll.setOnClickListener(new OnClickListener() {
			    public void onClick(View view) {
			    	LinearLayout ll = (LinearLayout) view;
			    	for (int i=0; i < ll.getChildCount(); i++){
			    	      View v = ll.getChildAt(i);
			    	      if (v instanceof ImageView) {
			    	    	  Intent intent = new Intent(MainActivity.this, ViewStory.class);
			    	    	  ImageView imageView = (ImageView) v;
			    	    	  intent.putExtra("storyId", imageView.getContentDescription().toString());
						      startActivity(intent);
						      break;
			    	      } 
			    	}  
			    }
			   });
			// Create image view
			ImageView imView = new ImageView(this);
			LayoutParams lpImView = new LayoutParams(200,200);
			lpImView.setMargins(10, 10, 10, 10);
			imView.setLayoutParams(lpImView);
			imView.setScaleType(ImageView.ScaleType.FIT_XY);
			if (db.get_photos(a.get(i).getId()).size() == 0) {
				imView.setImageResource(R.drawable.ic_launcher);
			} else {
				String imgUrl = db.get_photos(a.get(i).getId()).get(0).getUrl();
				Bitmap bm = BitmapFactory.decodeFile(imgUrl);
				imView.setImageBitmap(bm);
			}
			imView.setContentDescription(a.get(i).getId() + "");
			// Create text view
			TextView textView = new TextView(this);
			LayoutParams lpTextView = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
			lpTextView.setMargins(10, 10, 10, 10);
			textView.setLayoutParams(lpTextView);
			textView.setGravity(Gravity.CENTER_VERTICAL);
			textView.setTextSize(25);
			textView.setText(a.get(i).getName());
			// add views to layout
			ll.addView(imView);
			ll.addView(textView);
			// add layout to main layout
			LinearLayout sv = (LinearLayout) findViewById(R.id.scroll_view);
			sv.addView(ll);
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void newStory(View view) {
		Intent intent = new Intent(this, CreateStory.class);
	    startActivity(intent);
	}

}
