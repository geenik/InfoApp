package com.example.deviceinfo;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.Manifest;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final int MY_CAMERA_REQUEST_CODE = 100;
    Info info;
    TextView manufacture;
    TextView model_name;
    TextView model_num;
    TextView android_version;
    TextView intenalstorage;
    TextView battery;
    TextView cameraamp;
    TextView camera_apr;
    TextView processor;
    TextView gpu;
    TextView processor_info;
    Button btn;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        info= new Info(this);
        manufacture=findViewById(R.id.manufacture);
        model_name=findViewById(R.id.model_name);
        model_num=findViewById(R.id.model_number);
        android_version=findViewById(R.id.android_version);
        intenalstorage=findViewById(R.id.battery);
        battery=findViewById(R.id.battery);
        cameraamp=findViewById(R.id.cameramp);
        processor=findViewById(R.id.processor);
        processor_info = findViewById(R.id.processor_info);
        gpu=findViewById(R.id.gpu);
        btn=findViewById(R.id.btn);

        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_REQUEST_CODE);
        }
        manufacture.setText("Brand :        "+info.getManufacturer());
        model_name.setText("Model :         "+info.getModelName());
        model_num.setText( "model no :     "+info.getModelNumber());
        android_version.setText("Version :      "+info.getAndroidVersion());
        intenalstorage.setText( "Storage :      "+info.getInternalStorageSize());
             battery.setText(   "Battery :      "+info.getBatteryLevel()+"%");
             cameraamp.setText( "Camera :       "+info.getCameraMegapixels());
             processor.setText("processor :     "+info.processor());
             processor_info.setText("processor info : "+info.processorinfo());
             gpu.setText("GPU :    V"+info.getGPUInfo());

        btn.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), sensor_reading.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }
}