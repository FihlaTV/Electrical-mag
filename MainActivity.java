package com.kc.Sensor;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.hardware.SensorEventListener;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends Activity implements SensorEventListener{
	
	private TextView etOrientation, etMagnetic, etTemerature, etLight, etPressure;
   	private SensorManager sensorManager;
   	DialChart05View chart5 = null;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
		// 得到 ActionBar get ActionBar
		ActionBar actionBar = getActionBar();
		// 隐藏 Hide
		actionBar.hide();
        setContentView(R.layout.activity_main);
      //初始化UI控件  Initialize the UI control
        etTemerature = (TextView) findViewById(R.id.etTemerature);
        etLight = (TextView) findViewById(R.id.etLight);
        chart5 = (DialChart05View)findViewById(R.id.circle_view2); 
        // 获取真机的传感器管理服务 Get real machine sensor management services
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        // 获取传感器模拟器的传感器管理服务 Get sensor manager service for sensor simulator
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        	Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ALL);
	        SwitchButton sb = (SwitchButton) findViewById(R.id.wiperSwitch1);  
	        sb.setOnChangeListener(new OnChangeListener(){  
              
            @Override  
            public void onChange(SwitchButton sb, boolean state){  
            	if (state==true) {
            		start();
				}else {
				    onPause();
				}
	                Log.d("switchButton", state ? "turn on":"turn off");  
	                Toast.makeText(MainActivity.this, state ? "turn on":"turn off", Toast.LENGTH_SHORT).show();  
	            }  
	        });
    }
    
    public void start(){
       // 为系统的磁场传感器注册监听器 Register the system's magnetic field sensor for the listener
       sensorManager.registerListener(this,
               sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),
               SensorManager.SENSOR_DELAY_GAME);
       // 为系统的光传感器注册监听器 Register listeners for the system's light sensor
       sensorManager.registerListener(this,
               sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT),
               SensorManager.SENSOR_DELAY_GAME);
   }
    
    @Override
    protected void onResume() {
    	if (SwitchButton.mSwitchOn==true) {
    		start();
    	}
        super.onResume();
    }
    
    @Override
    protected void onStop() {
        // 程序退出时取消注册传感器监听器 stop the sensor listener when the program exits
        sensorManager.unregisterListener(this);
        super.onStop();
    }

    @Override
    protected void onPause() {
        // 程序暂停时取消注册传感器监听器 stop the sensor sniffer while the program is paused
        sensorManager.unregisterListener(this);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        // 程序销毁时取消注册传感器监听器 stop the sensor listener when the program is destroyed
        sensorManager.unregisterListener(this);
        super.onDestroy();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float[] values = event.values;
        // 真机上获取触发event的传感器类型 Get the type of sensor that triggered the event on the real machine
        // Sensor sensorType = event.sensor;
        // 模拟器上获取触发event的传感器类型 Get the type of sensor that triggered the event on the simulator
        Sensor sensorType = event.sensor;
        StringBuilder sb = null;
        sb = new StringBuilder();
        // 判断是哪个传感器发生改变 Determine which sensor is changing
        //光照传感器 light sensor
        if (sensorType == sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)) {
            sb.append("当前光的强度为：current brightness");
            sb.append(values[0]);
            etLight.setText(sb.toString());
        }
	    //The X axis is horizontal and points to the right
	    //the Y axis is vertical and points up
	    //the Z axis points towards the outside of the front face of the screen
        //磁场传感器 Magnetic field sensor
        if (sensorType == sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)) {
            sb.append("X方向上的电磁通量：Electromagnetic flux in the X direction: ");
            sb.append(values[0] + "\n");
            sb.append("Y方向上的电磁通量：Electromagnetic flux in the Y direction: ");
            sb.append(values[1] + "\n");
            sb.append("Z方向上的电磁通量：Electromagnetic flux in the Z direction: ");
            sb.append(values[2]);
            etTemerature.setText(sb.toString());
            float x = Math.abs(values[0]);
            float y = Math.abs(values[1]);
            float z = Math.abs(values[2]);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) 
    }
}
