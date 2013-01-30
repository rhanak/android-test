package com.example.android_test;

import java.util.List;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends Activity {

	TextView textSENSOR_list, textSENSOR_total, textLINEARACCELERATION_reading,
			textMAXLINEARACCELERATION_reading,
			textACCELERATION_reading,
			textMAXACCELERATION_reading,
			textCONVERSIONLINEAR_reading;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		textSENSOR_total = (TextView) findViewById(R.id.SENSOR_total);
		textSENSOR_list = (TextView) findViewById(R.id.SENSOR_list);
		
		textLINEARACCELERATION_reading = (TextView) findViewById(R.id.LINEARACCELERATION_reading);
		textMAXLINEARACCELERATION_reading = (TextView) findViewById(R.id.MAXLINEARACCELERATION_reading);
		
		textACCELERATION_reading = (TextView) findViewById(R.id.ACCELERATION_reading);
		textMAXACCELERATION_reading = (TextView) findViewById(R.id.MAXACCELERATION_reading);
		textCONVERSIONLINEAR_reading = (TextView) findViewById(R.id.CONVERSIONLINEAR_reading);
		
		SensorManager mySensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

		
		Sensor mySensor = mySensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
		mySensorManager.registerListener(LinearAccelerationSensorListener, mySensor,
		SensorManager.SENSOR_DELAY_NORMAL);
		
		mySensor = mySensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		mySensorManager.registerListener(AccelerationSensorListener, mySensor,
				SensorManager.SENSOR_DELAY_NORMAL);

		// List of Sensors Available
		List<Sensor> mysensorList = mySensorManager
				.getSensorList(Sensor.TYPE_ALL);

		// Print how may Sensors are there
		textSENSOR_total.setText(mysensorList.size() + " sensors!");

		// Print each Sensor available using sSensList as the String to be
		// printed
		String sSensList = new String("");
		Sensor tmp;
		int x, i;
		for (i = 0; i < mysensorList.size(); i++) {
			tmp = mysensorList.get(i);
			sSensList = " " + sSensList + "     " + tmp.getName() + ",type:"
					+ tmp.getType(); // Add the sensor name to the string of
										// sensors available
		}
		// if there are sensors available show the list
		if (i > 0) {
			textSENSOR_list.setText(sSensList);
		}

	}

	private final SensorEventListener LinearAccelerationSensorListener = new SensorEventListener() {
		float max = 0.0F;

		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onSensorChanged(SensorEvent event) {

			textLINEARACCELERATION_reading.setText("Linear Sensor: " + event.values[0]);

			if (event.values[0] > max) {
				max = event.values[0];
				textMAXLINEARACCELERATION_reading.setText("MAX: " + max);
			}

		}

	};

	private final SensorEventListener AccelerationSensorListener = new SensorEventListener() {
		float gravity[] = new float[3];
		float linear_acceleration[] = new float[3];
		float maxx = 0.0F, maxy = 0.0F, maxz = 0.0F;

		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onSensorChanged(SensorEvent event) {
			final float alpha = 0.8F;

			gravity[0] = alpha * gravity[0] + (1 - alpha) * event.values[0];
			gravity[1] = alpha * gravity[1] + (1 - alpha) * event.values[1];
			gravity[2] = alpha * gravity[2] + (1 - alpha) * event.values[2];

			linear_acceleration[0] = event.values[0] - gravity[0];
			linear_acceleration[1] = event.values[1] - gravity[1];
			linear_acceleration[2] = event.values[2] - gravity[2];

			textACCELERATION_reading.setText("X:"
					+ linear_acceleration[0] + " Y:" + linear_acceleration[1]
					+ " Z:" + linear_acceleration[2]);

			if (event.values[0] > maxx) {
				maxx = event.values[0];
				textMAXACCELERATION_reading.setText("MAX X: " + maxx + " Y:" + maxy
						+ " Z:" + maxz);
				textCONVERSIONLINEAR_reading.setText("AcceltoLinear: " + this.accelerometertoLinearAcceleration(event.values[0],event.values[1],event.values[2]));
			} else if (event.values[1] > maxy) {
				maxy = event.values[1];
				textMAXACCELERATION_reading.setText("MAX X: " + maxx + " Y:" + maxy
						+ " Z:" + maxz);
				textCONVERSIONLINEAR_reading.setText("AcceltoLinear: " + this.accelerometertoLinearAcceleration(event.values[0],event.values[1],event.values[2]));
			} else if (event.values[2] > maxz) {
				maxz = event.values[2];
				textMAXACCELERATION_reading.setText("MAX X: " + maxx + " Y:" + maxy
						+ " Z:" + maxz);
				textCONVERSIONLINEAR_reading.setText("AcceltoLinear: " + this.accelerometertoLinearAcceleration(event.values[0],event.values[1],event.values[2]));
			}

		}
		
		private float accelerometertoLinearAcceleration(float x, float y, float z) {
			return (float)Math.sqrt((x*x) + (y*y) + (z*z));
		}

	};

}
