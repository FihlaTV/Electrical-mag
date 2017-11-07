package com.kc.Sensor;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends Activity implements SensorEventListener {
	
	private TextView etOrientation, etMagnetic, etTemerature, etLight, etPressure;
    private SensorManager sensorManager;
    DialChart05View chart5 = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		// �õ�ActionBar
		ActionBar actionBar = getActionBar();
		// ����
		actionBar.hide();
        setContentView(R.layout.activity_main);
      //��ʼ��UI�ؼ�
        etTemerature = (TextView) findViewById(R.id.etTemerature);
        etLight = (TextView) findViewById(R.id.etLight);
        chart5 = (DialChart05View)findViewById(R.id.circle_view2); 
        // ��ȡ����Ĵ������������
        // sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        // ��ȡ������ģ�����Ĵ������������
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        //Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ALL);
//        SwitchButton sb = (SwitchButton) findViewById(R.id.wiperSwitch1);  
//        sb.setOnChangeListener(new OnChangeListener() {  
//              
//            @Override  
//            public void onChange(SwitchButton sb, boolean state) {  
//            	if (state==true) {
//            		start();
//				}else {
//					onPause();
//				}
////                Log.d("switchButton", state ? "��":"��");  
////                Toast.makeText(MainActivity.this, state ? "��":"��", Toast.LENGTH_SHORT).show();  
//            }  
//        });
    }
    
    public void start(){
       // Ϊϵͳ�Ĵų�������ע�������
       sensorManager.registerListener(this,
               sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),
               SensorManager.SENSOR_DELAY_GAME);
       // Ϊϵͳ�Ĺ⴫����ע�������
       sensorManager.registerListener(this,
               sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT),
               SensorManager.SENSOR_DELAY_GAME);
   }
    
    @Override
    protected void onResume() {
//    	if (SwitchButton.mSwitchOn==true) {
    		start();
//    	}
        super.onResume();
    }
    
    @Override
    protected void onStop() {
        // �����˳�ʱȡ��ע�ᴫ����������
        sensorManager.unregisterListener(this);
        super.onStop();
    }

    @Override
    protected void onPause() {
        // ������ͣʱȡ��ע�ᴫ����������
        sensorManager.unregisterListener(this);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        // ��������ʱȡ��ע�ᴫ����������
        sensorManager.unregisterListener(this);
        super.onDestroy();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float[] values = event.values;
        // ����ϻ�ȡ����event�Ĵ���������
        // Sensor sensorType = event.sensor;
        // ģ�����ϻ�ȡ����event�Ĵ���������
        Sensor sensorType = event.sensor;
        StringBuilder sb = null;
        sb = new StringBuilder();
        // �ж����ĸ������������ı�
        //���մ�����
        if (sensorType == sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)) {
            sb.append("��ǰ���ǿ��Ϊ��");
            sb.append(values[0]);
            etLight.setText(sb.toString());
        }
        //�ų�������
        if (sensorType == sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)) {
            sb.append("X�����ϵĵ��ͨ����");
            sb.append(values[0] + "\n");
            sb.append("Y�����ϵĵ��ͨ����");
            sb.append(values[1] + "\n");
            sb.append("Z�����ϵĵ��ͨ����");
            sb.append(values[2]);
            etTemerature.setText(sb.toString());
            float x = Math.abs(values[0]);
            float y = Math.abs(values[1]);
            float z = Math.abs(values[2]);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
    
}
