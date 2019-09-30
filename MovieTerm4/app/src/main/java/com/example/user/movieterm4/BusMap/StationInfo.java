package com.example.user.movieterm4.BusMap;

import android.content.SharedPreferences;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.movieterm4.R;

public class StationInfo extends AppCompatActivity {

    Double lat1,lat2, Mylat1, Mylat2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station_info);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        SharedPreferences.Editor editor = pref.edit();
        String k = pref.getString("memberId", null);                 //로그인 상태라면.

        String title = "";
        String address = "";

        Bundle extras = getIntent().getExtras();

        if (extras == null) {
            title = "error";
        } else {
            title = extras.getString("title");
            address = extras.getString("address");

            lat1 = extras.getDouble("lat1");
            lat2 = extras.getDouble("lat2");                //영화관 위도,경도

            Mylat1 = extras.getDouble("Mylat1");
            Mylat2 = extras.getDouble("Mylat2");            //나의 위치 위도,경도

        }

        TextView textView1 = (TextView) findViewById(R.id.textView_newActivity_contentString);
        TextView textView2 = (TextView) findViewById(R.id.tvAddress);
        TextView textView3 = (TextView) findViewById(R.id.latlng);
        TextView textView4 = (TextView) findViewById(R.id.tvMessage);
        ImageView ivM = (ImageView) findViewById(R.id.ivMovie);
        String str = title;
        String str1 = address;


        Location locationA = new Location("pointA");       //나의 위치
        locationA.setLatitude(Mylat1); //위도
        locationA.setLongitude(Mylat2);  //경도

        Location locationB = new Location("pointB");        //선택한 영화관 위치 정보
        locationB.setLatitude(lat1);
        locationB.setLongitude(lat2);

        double distance;
        String meter;

        distance = locationA.distanceTo(locationB);

        double distance2 = distance / 1000;
        double distance3 = Math.round(distance2 * 100) / 100.0;

        meter = Double.toString(distance3);

        textView1.setText(str);
        textView2.setText(str1);
        textView3.setText(meter + "Km");

        if (distance3 > 0 && distance3 < 0.5) {
            textView4.setText("금방이쥬!");
        } else if (distance3 >= 0.5 && distance3 < 1.0) {
            textView4.setText("괜찮아. 할수있어.");
        }

    }
}
