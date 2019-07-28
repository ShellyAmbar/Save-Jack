package com.shellyambar.thegame.Game;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class OrientationData implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor accelerometer;
    private Sensor magnometer;
    private float[] orientation = new float[3];
    private float[] accOutput;
    private float[] magnoOutput;
    private float[] startOrientation = null;

    public float[] getOrientation() {
        return orientation;
    }

    public float[] getStartOrientation() {
        return startOrientation;
    }

    public void NewGame(){
        startOrientation = null;
    }

    public OrientationData() {
        sensorManager = (SensorManager) Constants.CURRENT_CONTEXT.getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magnometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

    }

    public void Register(){
        sensorManager.registerListener(this, accelerometer,SensorManager.SENSOR_DELAY_GAME);
        sensorManager.registerListener(this, magnometer,SensorManager.SENSOR_DELAY_GAME);
    }
    public void UnRegister(){
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        boolean success = false;

       if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
           accOutput = event.values;
       }
       else if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD){
           magnoOutput = event.values;
       }
       if(accOutput!=null &&magnoOutput!=null){

           float[] R = new float[9];
           float[] I = new float[9];
         success=SensorManager.getRotationMatrix(R,I,accOutput,magnoOutput);
         if(success){
             SensorManager.getOrientation(R, orientation);
             if(startOrientation == null){
                 startOrientation = new float[orientation.length];
                 System.arraycopy(orientation,0,startOrientation,0,orientation.length);

             }
         }
       }
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
