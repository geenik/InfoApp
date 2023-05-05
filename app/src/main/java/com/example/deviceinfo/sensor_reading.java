package com.example.deviceinfo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.util.Log;

import com.example.deviceinfo.databinding.ActivitySensorReadingBinding;

public class sensor_reading extends AppCompatActivity implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor accelerometer, gyroscope, barometer, rotationVector, proximity, ambientLight;
    private ActivitySensorReadingBinding bind;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = ActivitySensorReadingBinding.inflate(getLayoutInflater());
        setContentView(bind.getRoot());
        // Get the sensor manager
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        // Get the sensors
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        barometer = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
        rotationVector = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
        proximity = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        ambientLight = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        // Register the sensors for updates
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, gyroscope, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, barometer, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, rotationVector, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, proximity, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, ambientLight, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        // Get the sensor data
        Sensor sensor = event.sensor;
        float[] values = event.values;

        // Handle each sensor separately
        if (sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            // Handle accelerometer readings
            float x = values[0];
            float y = values[1];
            float z = values[2];
            bind.AccelerometerX.setText("X : "+x);
            bind.AccelerometerY.setText("Y : "+y);
            bind.AccelerometerZ.setText("Z : "+z);
        } else if (sensor.getType() == Sensor.TYPE_GYROSCOPE) {
            // Handle gyroscope readings
            float x = values[0];
            float y = values[1];
            float z = values[2];
            bind.GyroscopeX.setText("X : "+x);
            bind.GyroscopeY.setText("Y : "+y);
            bind.GyroscopeZ.setText("Z : "+z);
            Log.d("SensorActivity", "Gyroscope: x=" + x + ", y=" + y + ", z=" + z);
        } else if (sensor.getType() == Sensor.TYPE_PRESSURE) {
            // Handle barometer readings
            float pressure = values[0];
            bind.Barometer.setText("Barometer  :       "+pressure);
        } else if (sensor.getType() == Sensor.TYPE_ROTATION_VECTOR) {
            // Handle rotation vector readings
            float x = values[0];
            float y = values[1];
            float z = values[2];
            bind.rotateX.setText("X : "+x);
            bind.rotateY.setText("Y : "+y);
            bind.rotateZ.setText("Z : "+z);
        } else if (sensor.getType() == Sensor.TYPE_PROXIMITY) {
            // Handle proximity sensor readings
            float distance = values[0];
            bind.proximity.setText("Proximity  :      "+distance);
        } else if (sensor.getType() == Sensor.TYPE_LIGHT) {
            // Handle ambient light sensor readings
            float light = values[0];
            bind.AmbientLight.setText("Light      :        "+light);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
    @Override
    protected void onPause() {
        super.onPause();
        // Unregister the sensor listener
        sensorManager.unregisterListener(this);
    }
    protected void onResume() {
        super.onResume();
        // Register the sensor listener again
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, gyroscope, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, barometer, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, rotationVector, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, proximity, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, ambientLight, SensorManager.SENSOR_DELAY_NORMAL);
    }
}