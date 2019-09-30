package com.example.user.movieterm4;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.movieterm4.NowPlay.DetailActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MemberInfo extends AppCompatActivity {

    TextView tvId,tvName, tvEmail, tvPhone;
    ImageView ivCoup;

    ScrollView mainScrollView;
    private ListView listView2;
    private List<MovieLikeData> movieLikeList;
    private List<MemberData> memberList;
    private likeListAdapter adapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_info);
        listView2 = (ListView)findViewById(R.id.listView2);
        movieLikeList = new ArrayList<MovieLikeData>();
        memberList = new ArrayList<MemberData>();
        mainScrollView = (ScrollView)findViewById(R.id.MainScrollView2);

        try {
            SharedPreferences pref = getApplication().getSharedPreferences("MyPref", 0);
            SharedPreferences.Editor editor = pref.edit();
            String k = pref.getString("memberId","memberId");
            String memberId = k;

            String rst1 = new Task().execute(memberId).get();

            JSONObject json = new JSONObject(rst1);
            JSONArray jArr = json.getJSONArray("List");
            String msg1="";
            String msg2="";
            String msg3="";

            movieLikeList.clear();

            for (int i = 0; i < jArr.length(); i++) {
                json = jArr.getJSONObject(i);
                msg1 = json.optString("title");
                msg2 = json.optString("releaseDate");
                msg3 = json.optString("posterPath");
                MovieLikeData mld =  new MovieLikeData(msg1,msg2,msg3);
                movieLikeList.add(mld);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        adapter = new likeListAdapter(getApplicationContext(),movieLikeList);
        listView2.setAdapter(adapter);



        /*----------------------------------------------------------------------*/

        try {
            SharedPreferences pref = getApplication().getSharedPreferences("MyPref", 0);
            SharedPreferences.Editor editor = pref.edit();
            String k = pref.getString("memberId","memberId");
            String memberId = k;

            String rst1 = new TaskA().execute(memberId).get();

            JSONObject json = new JSONObject(rst1);
            JSONArray jArr = json.getJSONArray("List");
            String msg1="";
            String msg2="";
            String msg3="";
            String msg4="";

            memberList.clear();

            for (int i = 0; i < jArr.length(); i++) {
                json = jArr.getJSONObject(i);
                msg1 = json.optString("memberId");
                msg2 = json.optString("memberName");
                msg3 = json.optString("email");
                msg4 = json.optString("phoneNumber");
                MemberData md = new MemberData(msg1,msg2,msg3,msg4);
                memberList.add(md);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        TextView tvId = (TextView)findViewById(R.id.tvId);
        TextView tvName = (TextView)findViewById(R.id.tvName);
        TextView tvPhone = (TextView)findViewById(R.id.tvPhone);
        TextView tvEmail = (TextView)findViewById(R.id.tvEmail);

        tvId.setText(memberList.get(0).getMemberId());
        tvName.setText(memberList.get(0).getMemberName());
        tvPhone.setText(memberList.get(0).getPhoneNumber());
        tvEmail.setText(memberList.get(0).getEmail());

        /*---------------------------------------------------------------------------*/








        Button btnChk = findViewById(R.id.btnChk);


        btnChk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        SharedPreferences pref = getApplication().getSharedPreferences("MyPref", 0);
        SharedPreferences.Editor editor = pref.edit();
        String k = pref.getString("memberId","memberId");










        listView2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mainScrollView.requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
    }

    public class Task extends AsyncTask<String, Void, String> {
        public  String ip ="192.168.1.25:8080"; //자신의 IP번호
        String sendMsg, receiveMsg;
        String serverIp = "http://"+ip+"/0604/login/likeMovieList.jsp"; // 연결할 jsp주소

        @Override
        protected String doInBackground(String... strings) {
            try {
                String str1;
                URL url = new URL(serverIp);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");
                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
                sendMsg = "memberId="+strings[0];

                osw.write(sendMsg);
                osw.flush();
                if(conn.getResponseCode() == conn.HTTP_OK) {
                    InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                    BufferedReader reader = new BufferedReader(tmp);
                    StringBuffer buffer = new StringBuffer();
                    while ((str1 = reader.readLine()) != null) {
                        buffer.append(str1);
                    }
                    receiveMsg = buffer.toString();
                } else {
                    Log.i("통신 결과", conn.getResponseCode()+"에러");
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return receiveMsg;
        }
    }

    public class TaskA extends AsyncTask<String, Void, String> {
        public  String ip ="192.168.1.25:8080"; //자신의 IP번호
        String sendMsg, receiveMsg;
        String serverIp = "http://"+ip+"/0604/login/selectById.jsp"; // 연결할 jsp주소

        @Override
        protected String doInBackground(String... strings) {
            try {
                String str1;
                URL url = new URL(serverIp);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");
                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
                sendMsg = "memberId="+strings[0];

                osw.write(sendMsg);
                osw.flush();
                if(conn.getResponseCode() == conn.HTTP_OK) {
                    InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                    BufferedReader reader = new BufferedReader(tmp);
                    StringBuffer buffer = new StringBuffer();
                    while ((str1 = reader.readLine()) != null) {
                        buffer.append(str1);
                    }
                    receiveMsg = buffer.toString();
                } else {
                    Log.i("통신 결과", conn.getResponseCode()+"에러");
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return receiveMsg;
        }
    }
}
