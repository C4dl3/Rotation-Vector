package com.cadle.rotationvector;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor sensor;
    private TextView xaxisView;
    private TextView yaxisView;
    private TextView zaxisView;
    private TextView accuracy;
    private DecimalFormat df;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeViewElements();
        df = new DecimalFormat("###.###");
        initializeSensorStuff();
    }

    public void initializeViewElements() {
        // initialize TextViews from activity_main.xml
        xaxisView = (TextView) findViewById(R.id.xaxis);
        yaxisView = (TextView) findViewById(R.id.yaxis);
        zaxisView = (TextView) findViewById(R.id.zaxis);
        accuracy = (TextView) findViewById(R.id.accuracy);
        accuracy.setText("high");
    }

    public void initializeSensorStuff() {
        // selecting ROTATION_VECTOR with the sensorManager
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);

        // (where is the Listener, which sensor, determine the delay)
        sensorManager.registerListener(MainActivity.this, sensor, sensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        // values[0] = x-axis; values[1] = y-axis; values[2] = z-axis
        xaxisView.setText(df.format(sensorEvent.values[0]));
        yaxisView.setText(df.format(sensorEvent.values[1]));
        zaxisView.setText(df.format(sensorEvent.values[2]));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        // i is from 0 to 3
        if (sensor == sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR)) {
            switch (i) {
                case 0:
                    accuracy.setText("very low");
                    break;
                case 1:
                    accuracy.setText("low");
                    break;
                case 2:
                    accuracy.setText("medium");
                    break;
                case 3:
                    accuracy.setText("high");
                    break;

            }
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(MainActivity.this, sensor, sensorManager.SENSOR_DELAY_NORMAL);

    }
    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);

    }
}