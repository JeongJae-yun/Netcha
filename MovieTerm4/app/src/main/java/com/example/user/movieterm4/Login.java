package com.example.user.movieterm4;


import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.user.movieterm4.BoxOffice.BoxOffice;
import com.example.user.movieterm4.Map.MapInfo;
import com.example.user.movieterm4.NowPlay.NowPlayMovie;
import com.example.user.movieterm4.WillPlay.WillPlayMovie;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Login extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    EditText chkId, chkPass;
    Button btnLogin, btnRegister;
    private VideoView mVideoview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //-------------------------------------동영상---------------------------------------//

        mVideoview = (VideoView) findViewById(R.id.video_view);
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/"+R.raw.login);
        mVideoview.setVideoURI(uri);
        mVideoview.start();
        mVideoview.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.setLooping(true);


            }
        });
        //-------------------------------------동영상---------------------------------------//

        //----------------------------------------------네비게이터------------------------------------------//

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        SharedPreferences.Editor editor = pref.edit();
        String k = pref.getString("memberId","memberId");

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
            nav_header_id_text.setText("로그인을 통해 즐겨보세요");
        }


        //----------------------------------------------네비게이터------------------------------------------//




        btnLogin = (Button) findViewById(R.id.btnLogin);
        chkId = findViewById(R.id.chkId);
        chkPass = findViewById(R.id.chkPass);
        btnRegister=(Button) findViewById(R.id.btnRegister);
        btnLogin.setOnClickListener(btnListener);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Login.this, Register.class);
                startActivity(intent1);
            }
        });

    }




    //----------------------------------------------네비게이터------------------------------------------//
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
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        SharedPreferences.Editor editor = pref.edit();
        String k = pref.getString("memberId","memberId");

        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.menu1) {
            if (k == "memberId"){
                Intent intent = new Intent(Login.this, Login.class);
                startActivity(intent);
            }else if(k != "memberId"){
                Intent intent = new Intent(Login.this, MemberInfo.class);
                startActivity(intent);
            }
        } else if (id == R.id.menu2) {
            Intent intent = new Intent(Login.this, NowPlayMovie.class);
            startActivity(intent);
        } else if (id == R.id.menu3) {
            Intent intent = new Intent(Login.this, WillPlayMovie.class);
            startActivity(intent);
        } else if(id == R.id.menu4) {
            Intent intent = new Intent(Login.this, BoxOffice.class);
            startActivity(intent);
        }else if(id == R.id.menu5) {
            Intent intent = new Intent(Login.this, MapInfo.class);
            startActivity(intent);
        }else if(id == R.id.menu6) {
            editor.clear();
            editor.commit();
            Intent intent = new Intent(Login.this, LoadingActivity.class);
            startActivity(intent);
        }else if(id==R.id.menu7){
            Intent intent = new Intent(Login.this, netXcha.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
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

    //----------------------------------------------네비게이터------------------------------------------//


    public class Task extends AsyncTask<String, Void, String> {
        String sendMsg, receiveMsg;
        String serverIp = "http://192.168.1.25:8080/0604/login/login.jsp";


        @Override
        protected String doInBackground(String... strings) {
            try {
                String str;
                URL url = new URL(serverIp);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");
                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());

                sendMsg = "memberId=" + strings[0] + "&password=" + strings[1];
                osw.write(sendMsg);
                osw.flush();
                if (conn.getResponseCode() == conn.HTTP_OK) {
                    InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                    BufferedReader reader = new BufferedReader(tmp);
                    StringBuffer buffer = new StringBuffer();
                    while ((str = reader.readLine()) != null) {
                        buffer.append(str);
                    }
                    receiveMsg = buffer.toString().trim();
                    /*publishProgress(receiveMsg); */ //onProgressUpdate로 넘겨줌
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



    View.OnClickListener btnListener = new View.OnClickListener() {


        @Override
        public void onClick(View v) {

            if(chkId.toString().isEmpty()==true || chkPass.toString().isEmpty()==true){
                Toast.makeText(getApplicationContext(),"정보를 입력하세요.",Toast.LENGTH_SHORT).show();
            }else {
                switch (v.getId()) {
                    case R.id.btnLogin:
                        String memberId = chkId.getText().toString();
                        String password = chkPass.getText().toString();
                        try {
                            String result = new Task().execute(memberId, password).get();

                            if (result.contentEquals("true")) {
                                Toast.makeText(Login.this, "로그인", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Login.this, netXcha.class);

                                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
                                SharedPreferences.Editor editor = pref.edit();
                                editor.putString("memberId", memberId);
                                editor.commit();

                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(Login.this, "아이디 또는 비밀번호가 틀렸음", Toast.LENGTH_SHORT).show();
                                chkId.setText("");
                                chkPass.setText("");

                            }

                        } catch (Exception e) {
                        }
                        break;

                    case R.id.btnRegister:
                        Intent intent = new Intent(Login.this, Register.class);
                        startActivity(intent);
                }
            }
        }
    };




}