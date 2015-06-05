package com.example.test1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import com.example.test1.DatabaseHelper.FeedEntry;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.os.Build;
import android.content.ContentValues;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.database.Cursor;
import android.database.sqlite.*;

public class MainActivity extends ActionBarActivity implements SensorEventListener {
	
	private SensorManager sensorManager;
	private Sensor accelerometer;
	private long lastUpdate = 0;
	private float last_x, last_y, last_z;
	private static final int THRESHOLD = 600;
	//private ArrayList<String> toDisplay;
	private SQLiteDatabase db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
	    accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
	    sensorManager.registerListener(this, accelerometer , SensorManager.SENSOR_DELAY_NORMAL);
	    String[] a = {"a","b","c","d","e","f"};
		//toDisplay = (ArrayList<String>)Arrays.asList(a);
		
		
		db = SQLiteDatabase.create(null);
		ContentValues values = new ContentValues();
		for(int i = 0; i<a.length; i++){
		
			values.put(FeedEntry.COLUMN_NAME_ENTRY_ID, i);
			values.put(FeedEntry.COLUMN_NAME_DATA, a[i]);
			db.insert(FeedEntry.TABLE_NAME, FeedEntry.COLUMN_NAME_DATA, values );
		}
		
		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onSensorChanged(SensorEvent event) {
		Sensor mySensor = event.sensor;
		
	    if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {
	    	 float x = event.values[0];
	         float y = event.values[1];
	         float z = event.values[2];
	         long curTime = System.currentTimeMillis();
	         
	         if ((curTime - lastUpdate) > 100) {
	             long diffTime = (curTime - lastUpdate);
	             lastUpdate = curTime;
	             
	             float speed = Math.abs(x + y + z - last_x - last_y - last_z)/ diffTime * 10000;
	             
	             if (speed > THRESHOLD) {
	            	 //int selection = new Random().nextInt(toDisplay.size());	
	            	 
	            	 String[] columns = {FeedEntry.COLUMN_NAME_DATA};
	            	 String[] rows = {"1"};
	            	 Cursor c = db.query(FeedEntry.TABLE_NAME, 
	            			 columns, 
	            			 FeedEntry.COLUMN_NAME_ENTRY_ID, 
	            			 rows, 
	            			 null, 
	            			 null, 
	            			 "RANDOM()");
	            	 String disp =  c.getString(c.getColumnIndex(FeedEntry.COLUMN_NAME_DATA));
	            	 ((TextView)findViewById(R.id.displayed_text)).setText(disp);
	             }
	  
	             last_x = x;
	             last_y = y;
	             last_z = z;
	         }
	    }
	}
	 
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
	 
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}
		

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			
			 String[] a = {"a","b","c","d","e","f"};
			ArrayList<String> toDisplay = (ArrayList<String>)Arrays.asList(a);
			int selection = new Random().nextInt(toDisplay.size());
			((TextView)rootView.findViewById(R.id.displayed_text)).setText(toDisplay.get(selection));
			
			
			return rootView;
		}
	}
}
