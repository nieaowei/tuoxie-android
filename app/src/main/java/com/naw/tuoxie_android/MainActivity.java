package com.naw.tuoxie_android;

import androidx.annotation.NonNull;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;

import org.json.JSONObject;


public class MainActivity extends Activity {
    MapView mMapView = null;
    AMap aMap;
    Handler GPSHandler;
    static int flag=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        Button getLastGPS_bt = findViewById(R.id.getLastGPS_bt);
        TextView userinfotext = findViewById(R.id.userinfotext);

        mMapView = (MapView) findViewById(R.id.map);
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        mMapView.onCreate(savedInstanceState);

        Intent intent=this.getIntent();
        userinfotext.setText("当前用户："+intent.getStringExtra("username"));

        if (aMap == null) {
            aMap = mMapView.getMap();
        }
        aMap.moveCamera(CameraUpdateFactory.zoomTo(15));//拉近镜头


        getLastGPS_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MainDataSource().getLastGPSInfo(MainActivity.this.getIntent().getStringExtra("username"), GPSHandler);
            }
        });


        LatLng latLng = new LatLng(26.881865,112.684922);
        final Marker LastMarker = aMap.addMarker(new MarkerOptions().position(latLng).title("最后一次位置").snippet("DefaultMarker"));

        //定位
        MyLocationStyle myLocationStyle;
        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle.interval(1000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER);//连续定位、且将视角移动到地图中心点，地图依照设备方向旋转，定位点会跟随设备移动。（1秒1次定位）
        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style

        aMap.getUiSettings().setMyLocationButtonEnabled(true);//设置默认定位按钮是否显示，非必需设置。
        aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是fals

//        aMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(aMap.getMyLocation().getLatitude(),aMap.getMyLocation().getLongitude())));

        aMap.setOnMyLocationChangeListener(new AMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                if(flag==0){
                    aMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(aMap.getMyLocation().getLatitude(),aMap.getMyLocation().getLongitude())));
                    flag++;
                }
                LastMarker.setPosition(new LatLng(aMap.getMyLocation().getLatitude(),aMap.getMyLocation().getLongitude()));
//                Log.d("location1:",String.valueOf(aMap.getMyLocation().getLatitude()));
//                Log.d("location12:",String.valueOf(aMap.getMyLocation().getLongitude()));
            }
        });
        //定位结束

        GPSHandler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                switch (msg.getData().getInt("status")){
                    case 400:{
                        Log.d("gpsmarker","400 handler");
                        //更新
                        break;
                    }
                    case 200:{
                        //更新地
                        Log.d("gpsmarker","200 handler");
                        try{
                            JSONObject jsonObject = new JSONObject(msg.getData().getString("data"));
                            LatLng latLng1=new LatLng(jsonObject.getDouble("latitude"),jsonObject.getDouble("longitude"));
                            LastMarker.setPosition(latLng1);
                            LastMarker.setSnippet(jsonObject.getString("time"));
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        break;
                    }
                }
            }
        };
//
//
//        aMap.setMyLocationEnabled(false);
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER);//连续定位、且将视角移动到地图中心点，地图依照设备方向旋转，定位点会跟随设备移动。（1秒1次定位）
//        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
//        aMap.setMyLocationEnabled(true);
//        aMap.getUiSettings().setMyLocationButtonEnabled(true);//设置默认定位按钮是否显示，非必需设置。

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mMapView.onDestroy();
    }
    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView.onPause();
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
    }
}
