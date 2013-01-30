package com.example.android_test;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class SensorActivity extends Activity implements SensorEventListener {
	 private final SensorManager mSensorManager;
	 private final Sensor mTemp;

	 public SensorActivity() {
	     mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
	     mTemp = mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
	 }

	 protected void onResume() {
	     super.onResume();
	     mSensorManager.registerListener(this, mTemp, SensorManager.SENSOR_DELAY_NORMAL);
	 }

	 protected void onPause() {
	     super.onPause();
	     mSensorManager.unregisterListener(this);
	 }

	 public void onAccuracyChanged(Sensor sensor, int accuracy) {
		 System.out.println("Accurarcy changed");
	 }

	 public void onSensorChanged(SensorEvent event) {
		 System.out.println("Sensor changed");
	 }
	}
