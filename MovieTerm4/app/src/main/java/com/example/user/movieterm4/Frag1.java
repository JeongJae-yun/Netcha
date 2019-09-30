package com.example.user.movieterm4;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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


public class Frag1 extends Fragment {

    public static Frag1 newInstance() {
        return new Frag1();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstaceState) {
        View view = inflater.inflate(R.layout.fragment_frag1, null);
        Button button1 = (Button)view.findViewById(R.id.button);

        button1.setOnClickListener(new View.OnClickListener() {
            List<MapData> mapData = new ArrayList<MapData>();

            @Override
            public void onClick(View v) {

                try {
                    String rst = String.valueOf(new Task().execute().get());
                    JSONObject json = new JSONObject(rst);
                    JSONArray jArr = json.getJSONArray("List");
                    double latitude = 0;
                    double longitude = 0;
                    String name ="";

                    for (int i = 0; i < jArr.length(); i++) {
                        json = jArr.getJSONObject(i);
                        name = json.getString("name");
                        latitude = Double.parseDouble(json.getString("latitude"));
                        longitude = Double.parseDouble(json.getString("longitude"));
                        mapData.add(new MapData(name,latitude,longitude));
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                ((Main4Activity)getActivity()).replaceFragment(Frag2.newInstance(mapData));
            }
        });

        return view;
    }
    public class Task extends AsyncTask<Void, Void, String> {

        String sendMsg, receiveMsg;
        String serverIp = "http://192.168.142.158:8080/0604/select/list.jsp"; // 연결할 jsp주소

        @Override
        protected String doInBackground(Void ... voids) {
            try {
                String str;
                URL url = new URL(serverIp);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");
                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
                sendMsg = "messageId="+1;

                osw.write(sendMsg);
                osw.flush();
                if(conn.getResponseCode() == conn.HTTP_OK) {
                    InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                    BufferedReader reader = new BufferedReader(tmp);
                    StringBuffer buffer = new StringBuffer();
                    while ((str = reader.readLine()) != null) {
                        buffer.append(str);
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

