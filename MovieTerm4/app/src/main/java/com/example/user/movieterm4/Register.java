package com.example.user.movieterm4;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Register extends AppCompatActivity {

    EditText et, et2, et3,et4,et5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Button btn = (Button)findViewById(R.id.button);
        et = findViewById(R.id.etId) ;
        et2 = findViewById(R.id.etPass) ;
        et3 = findViewById(R.id.etName) ;
        et4 = findViewById(R.id.etEm) ;
        et5 = findViewById(R.id.etPh) ;


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (et.toString().isEmpty()== true || et2.toString().isEmpty()== true ||
                        et3.toString().isEmpty()==true || et4.toString().isEmpty()==true || et5.toString().isEmpty()==true){
                    Toast.makeText(getApplicationContext(),"정보를 모두 입력하세요",Toast.LENGTH_SHORT).show();
                }else{

                    try {
                        String memberId = et.getText().toString();
                        String password = et2.getText().toString();
                        String memberName = et3.getText().toString();
                        String email = et4.getText().toString();
                        String phoneNumber = et5.getText().toString();
                        String rst = new Register.Task().execute(memberId,password,memberName,email,phoneNumber).get();

                        if (rst.contentEquals("true")) {
                            Toast.makeText(Register.this, "회원가입 완료", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Register.this, Login.class);

                            SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
                            SharedPreferences.Editor editor = pref.edit();
                            editor.putString("memberId",memberId);
                            editor.putString("password",password);
                            editor.putString("memberName",memberName);
                            editor.putString("email",email);
                            editor.putString("phoneNumber",phoneNumber);
                            editor.commit();

                            startActivity(intent);
                        } else{
                            Toast.makeText(Register.this,"회원가입 불가능",Toast.LENGTH_SHORT).show();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        });
    }

    public class Task extends AsyncTask<String, String, String> {
        String sendMsg, receiveMsg;
        String serverIp = "http://192.168.1.25:8080/0604/login/register.jsp";

        @Override
        protected String doInBackground(String... strings) {
            try {
                String str;
                URL url = new URL(serverIp);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");
                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());

                sendMsg = "memberId=" + strings[0] + "&password=" + strings[1] + "&memberName=" + strings[2] + "&email=" + strings[3] + "&phoneNumber=" + strings[4];
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
}
