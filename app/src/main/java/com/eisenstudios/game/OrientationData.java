package com.eisenstudios.game;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * Created by Connor on 31/10/2017.
 */

public class OrientationData implements SensorEventListener
{
    private SensorManager manager;
    private Sensor accelerometer;
    private Sensor magnometer;

    private float[] accelOutput;
    private float[] magOutput;
    private float[] orientation;
    private float[] startOrientation;

    public OrientationData()
    {
        orientation = new float[3];
        manager = (SensorManager)Constants.CURRENT_CONTEXT.getSystemService(Context.SENSOR_SERVICE);
        accelerometer = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magnometer = manager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        startOrientation = null;
    }

    @Override
    public void onSensorChanged(SensorEvent event)
    {
        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
        {
            accelOutput = event.values;
        }
        else if(event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD)
        {
            magOutput = event.values;
        }

        if(accelOutput != null && magOutput != null)
        {
            float[] rMatrix = new float[9];
            float[] iMatrix = new float[9];
            boolean success = SensorManager.getRotationMatrix(rMatrix, iMatrix, accelOutput, magOutput);
            if(success)
            {
                SensorManager.getOrientation(rMatrix, orientation);
                if(startOrientation == null)
                {
                    startOrientation = new float[orientation.length];
                    System.arraycopy(orientation, 0, startOrientation, 0, orientation.length);
                }
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy)
    {

    }

    public float[] getOrientation()
    {
        return orientation;
    }

    public float[] getStartOrientation()
    {
        return startOrientation;
    }

    public void newGame()
    {
        startOrientation = null;
    }

    public void register()
    {
        manager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
        manager.registerListener(this, magnometer, SensorManager.SENSOR_DELAY_GAME);
    }

    public void pause()
    {
        manager.unregisterListener(this);
    }
}
