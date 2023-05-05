package com.example.deviceinfo;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ConfigurationInfo;
import android.hardware.Camera;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class Info {

    private Context context;

    public Info(Context context) {
        this.context = context;
    }

    // Get manufacturer name
    public String getManufacturer() {
        return Build.MANUFACTURER;
    }

    // Get model name
    public String getModelName() {
        return Build.MODEL;
    }

    // Get model number
    public String getModelNumber() {
        return Build.DEVICE;
    }

    //get processor
    public String processor(){return Build.HARDWARE;}

    //get processorinfo
    public String processorinfo(){return Build.BOARD;}

    // Get internal storage size in GB
    @SuppressLint("DefaultLocale")
    public String getInternalStorageSize() {
        StatFs stat = new StatFs(Environment.getDataDirectory().getPath());
        double bytesAvailable = (double) stat.getBlockSize() * (double) stat.getBlockCount();
        double gbAvailable = bytesAvailable / (1024.0 * 1024.0 * 1024.0);
        return String.format("%.2f GB", gbAvailable);
    }

    // Get current battery level as a percentage
    public int getBatteryLevel() {
        int batteryLevel = -1;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            BatteryManager batteryManager = (BatteryManager) context.getSystemService(Context.BATTERY_SERVICE);
            batteryLevel = batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);
        } else {
            Intent intent = new ContextWrapper(context).registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
            if (intent != null) {
                batteryLevel = (intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) * 100)
                        / intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
            }
        }
        return batteryLevel;
    }

    // Get Android OS version
    public String getAndroidVersion() {
        return Build.VERSION.RELEASE;
    }

    // Get camera megapixels
    public String getCameraMegapixels() {
        Camera camera = Camera.open();
        Camera.Parameters params = camera.getParameters();
        List<Camera.Size> sizes = params.getSupportedPictureSizes();
        Camera.Size size = sizes.get(0);
        double megapixels = (size.width * size.height) / 1000000.0;
        camera.release();
        return String.format("%.2f MP", megapixels);
    }

    // Get GPU information
    public String getGPUInfo() {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ConfigurationInfo configurationInfo = activityManager.getDeviceConfigurationInfo();
        return configurationInfo.getGlEsVersion();
    }

}