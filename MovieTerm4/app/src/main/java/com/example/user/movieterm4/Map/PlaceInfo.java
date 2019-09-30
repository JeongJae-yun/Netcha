package com.example.user.movieterm4.Map;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.movieterm4.BoxOffice.BoxOffice;
import com.example.user.movieterm4.LoadingActivity;
import com.example.user.movieterm4.Login;
import com.example.user.movieterm4.Main4Activity;
import com.example.user.movieterm4.MemberInfo;
import com.example.user.movieterm4.NowPlay.NowPlayMovie;
import com.example.user.movieterm4.R;
import com.example.user.movieterm4.WillPlay.WillPlayMovie;
import com.example.user.movieterm4.netXcha;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class PlaceInfo extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Double lat1,lat2, Mylat1, Mylat2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_info);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        SharedPreferences.Editor editor = pref.edit();
        String k = pref.getString("memberId", "memberId");                 //로그인 상태라면.

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
        final String theaterTitle = title;
        String str1 = address;


        boolean M = theaterTitle.contains("Mega");
        boolean L = theaterTitle.contains("LOTTE");
        boolean C = theaterTitle.contains("cgv");

        if (M == true) {
            ivM.setImageResource(R.drawable.megathe);
        } else if (L == true) {
            ivM.setImageResource(R.drawable.theater);
        } else {
            ivM.setImageResource(R.drawable.cgvtheater);                   //영화관 이미지
        }

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

        textView1.setText(theaterTitle);
        textView2.setText(str1);
        textView3.setText("Distance To Destination: "+meter + "Km");
        String strColor = "#80cbc4";
        textView3.setTextColor(Color.parseColor(strColor));





        //-----------------------------네비게이터------------------------------------------//
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View nav_header_view = navigationView.getHeaderView(0);

        TextView nav_header_id_text = (TextView) nav_header_view.findViewById(R.id.tvMemId);
        if(k != "memberId") {
            nav_header_id_text.setText(k + "님. 환영합니다");
        }else if(k == "memberId"){
            nav_header_id_text.setText("로그인을 통해 쿠폰을 받으세요");
        }
        //-----------------------------네비게이터------------------------------------------//
    }



    public class Task extends AsyncTask<String, String, String> {
        String sendMsg, receiveMsg;
        String serverIp = "http://192.168.142.158:8080/0604/select/insert.jsp";

        @Override
        protected String doInBackground(String... strings) {
            try {
                String str;
                URL url = new URL(serverIp);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");
                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());

                sendMsg = "name=" + strings[0] + "&latitude=" + strings[1] + "&longitude=" + strings[2];
                osw.write(sendMsg);
                osw.flush();
                if (conn.getResponseCode() == conn.HTTP_OK) {
                    InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                    BufferedReader reader = new BufferedReader(tmp);
                    StringBuffer buffer = new StringBuffer();
                    while ((str = reader.readLine()) != null) {
                        buffer.append(str);
                    }
                    receiveMsg = buffer.toString();
                    publishProgress(receiveMsg);                                //onProgressUpdate로 넘겨줌
                } else {
                    Log.i("통신 결과", conn.getResponseCode() + "에러");
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return receiveMsg;
        }
    }





    //-----------------------------네비게이터------------------------------------------//
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        SharedPreferences.Editor editor = pref.edit();
        String k = pref.getString("memberId","memberId");

        NavigationView navigationView1 = (NavigationView) findViewById(R.id.nav_view);
        Menu nv = navigationView1.getMenu();
        MenuItem item = nv.findItem(R.id.menu1);

        if(k == "memberId"){
            item.setTitle("로그인");
        }else if(k != "memberId" ){
            item.setTitle("회원 정보");
        }

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        SharedPreferences.Editor editor = pref.edit();
        String k = pref.getString("memberId","memberId");


        if (id == R.id.menu1) {
            if (k == "memberId"){
                Intent intent = new Intent(PlaceInfo.this, Login.class);
                startActivity(intent);
            }else if(k != "memberId"){
                Intent intent = new Intent(PlaceInfo.this, MemberInfo.class);
                startActivity(intent);
            }
        } else if (id == R.id.menu2) {
            Intent intent = new Intent(PlaceInfo.this, NowPlayMovie.class);
            startActivity(intent);
        } else if (id == R.id.menu3) {
            Intent intent = new Intent(PlaceInfo.this, WillPlayMovie.class);
            startActivity(intent);
        } else if(id == R.id.menu4) {
            Intent intent = new Intent(PlaceInfo.this, BoxOffice.class);
            startActivity(intent);
        }else if(id == R.id.menu5) {
            Intent intent = new Intent(PlaceInfo.this, MapInfo.class);
            startActivity(intent);
        }else if(id == R.id.menu6) {
            editor.clear();
            editor.commit();
            Intent intent = new Intent(PlaceInfo.this, LoadingActivity.class);
            startActivity(intent);
        }else if(id==R.id.menu7){
            Intent intent = new Intent(PlaceInfo.this, netXcha.class);
            startActivity(intent);
        }



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }

    //-----------------------------네비게이터------------------------------------------//
}
