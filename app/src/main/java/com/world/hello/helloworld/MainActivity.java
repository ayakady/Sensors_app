package com.world.hello.helloworld;

import android.Manifest;
import android.app.Application;
import android.app.DownloadManager;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;

import java.io.*;

import android.util.*;

import java.io.File;

import android.os.SystemClock;
import android.os.Environment;
import android.widget.Toast;
import android.content.Intent;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Calendar;


public class MainActivity extends Activity implements SensorEventListener, View.OnClickListener {

    // private float mLastX, mLastY, mLastZ;
    //private boolean mInitialized;
    private SensorManager mSensorManager;
    LocationManager locationManager;
    public final int REQUEST_LOCATION=1;
    private Sensor mAccelerometer;
    //private final float NOISE = (float) 2.0;
    FileWriter out;
    String anamoly = "smooth";
    boolean pothole;
    boolean Bump;
    String entry = " ";
    long time_gps;


    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //  mInitialized = false;
        Button startButton = (Button) findViewById(R.id.button);
        Button stopButton = (Button) findViewById(R.id.button2);
        Button pot = (Button) findViewById(R.id.button3);
        Button Bum = (Button) findViewById(R.id.button4);
        Bum.setOnClickListener(this);
        startButton.setOnClickListener(this);
        stopButton.setOnClickListener(this);
        pot.setOnClickListener(this);
        locationManager = (LocationManager)
                getSystemService(Context.LOCATION_SERVICE);


        //----LocationListener locationListener = new MyLocationListener();
        //----if (Build.VERSION.SDK_INT >=Build.VERSION_CODES.M) {
          //----  if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    public void requestPermissions(@NonNull String[] permissions, int requestCode)
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for Activity#requestPermissions for more details.
             //----   return;
          //----  }

       //--- }else {



        //---}

       //---- locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, locationListener);






       // LocationManager l = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
       // l.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        //this.onLocationChanged(null);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

       // mSensorManager.registerListener(this, mAccelerometer , SensorManager.SENSOR_DELAY_NORMAL);

        try {
            Context context = getApplicationContext();
            File path = context.getExternalFilesDir(null);
            out = new FileWriter(new File(path, "acclast.txt"));
        }

            catch (IOException e) {
            e.printStackTrace();
        }




       // tex.setText(path.getAbsolutePath());
    }

    protected void onResume() {
        super.onResume();
     //   mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // can be safely ignored for this demo
    }

     // tex.setText(path.getAbsolutePath());


    void getlocation(){
        if (ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)!=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED)
        {

            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_LOCATION);



        }
        else
        {
            Location location=locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (location!=null)
            {
               TextView talti= (TextView)findViewById(R.id.locationlati);
                TextView tlong= (TextView)findViewById(R.id.locationlong);
                TextView taltitude= (TextView)findViewById(R.id.Altitude);
                TextView tspeed= (TextView)findViewById(R.id.Speed);
                double lati=location.getLatitude();
                double longi=location.getLongitude();
                double Alti=location.getAltitude();
                double Speedy=location.getSpeed();
                time_gps=location.getTime();

                talti.setText("latitude:" + lati);
                tlong.setText("Longitude:" + longi);
                taltitude.setText("Altitude:"+ Alti);
                tspeed.setText("speed:"+ Speedy);
               //((EditText)findViewById(R.id.editText).setText("latitude:" + lati);
                //(findViewById(R.id.locationlong)).setText("longitude:" + longi);

            }
        }

    }

public void OnRequestPermissionResult(int requestcode,@NonNull String[] permissions,@NonNull int[] Grantresults)
{

    super.onRequestPermissionsResult(requestcode, permissions, Grantresults);
    switch(requestcode)
    {
        case REQUEST_LOCATION:
            getlocation();
            break;
    }

}


    public void onSensorChanged(SensorEvent event) {

        getlocation();

        TextView tvX= (TextView)findViewById(R.id.x_axis);
        TextView tvY= (TextView)findViewById(R.id.y_axis);
        TextView tvZ= (TextView)findViewById(R.id.z_axis);
        TextView tex= (TextView)findViewById(R.id.new_text);
        ImageView iv = (ImageView)findViewById(R.id.image);
       // float x = event.values[0];
        //float y = event.values[1];
        //float z = event.values[2];

            tvX.setText("x: "+event.values[0]);
            tvY.setText("y: "+event.values[1]);
            tvZ.setText("z: "+event.values[2]);
        Long tsLong = System.currentTimeMillis();
        String ts2 = tsLong.toString();
        long time1=SystemClock.currentThreadTimeMillis();
        Timestamp tsTemp = new Timestamp(time1);


        String ts1 =  tsTemp.toString();
        Date currentTime = Calendar.getInstance().getTime();
        String ts =  currentTime.toString();

if(pothole) {
     entry = ts2 + " " + "pothole" + " " + tvX.getText().toString() + "," + tvY.getText().toString() + "," + tvZ.getText().toString() + ",";
    pothole=false;
}
else if(Bump)
{
    entry = ts2 + " " + "Bump" + " " + tvX.getText().toString() + "," + tvY.getText().toString() + "," + tvZ.getText().toString() + ",";

Bump=false;
}

       else
{
     entry = ts2 + "               " + tvX.getText().toString() + "," + tvY.getText().toString() + "," + tvZ.getText().toString() +time_gps + ",";
}
       // String anamoly="smooth";

        try {

           //if(num==0) {


           //}

            out.append(entry);
            out.append("\n\r");
            //num=num+1;
           //out.close();
        } catch (IOException e) {
            e.printStackTrace();
            //Logger.logError(TAG, e);
        }

    }

   // Button clickButton = (Button) findViewById(R.id.button);
   // clickButton.setOnClickListener(new View.OnClickListener() {

      //  public void onClick(View view) {
          //  mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);

      //  }
   // });
        public void onClick(View v) {
            // TODO Auto-generated method stub
            switch (v.getId()){
                case R.id.button:
                    mSensorManager.registerListener(this, mAccelerometer,SensorManager.SENSOR_DELAY_NORMAL);

                    break;
                case R.id.button2:
                    mSensorManager.unregisterListener(this);
                   break;
                case R.id.button3:
                    //anamoly="potehole";
                    pothole=true;
                    break;
                case R.id.button4:
                    //anamoly="potehole";
                    Bump=true;
                    break;



            }

        }

    }