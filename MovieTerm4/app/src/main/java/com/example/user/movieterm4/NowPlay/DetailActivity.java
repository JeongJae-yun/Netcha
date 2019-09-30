package com.example.user.movieterm4.NowPlay;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.user.movieterm4.Map.MapInfo;
import com.example.user.movieterm4.MovieM;
import com.example.user.movieterm4.R;
import com.example.user.movieterm4.data.Youtube;
import com.example.user.movieterm4.mesListAdapter;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

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

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DetailActivity extends YouTubeBaseActivity {

    ArrayList<Youtube> youtubeList;

    private YouTubePlayerView youTubeView;
    private String trailer01;
    private String m_id;
    private ListView listView;
    private mesListAdapter adapter;
    private List<MovieM> movieList;

    TextView tv1;
    EditText et1;
    View dialogView;
    ScrollView mainScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        youtubeList = new ArrayList<Youtube>();
        listView = (ListView)findViewById(R.id.listView);
        movieList = new ArrayList<MovieM>();
        mainScrollView = (ScrollView)findViewById(R.id.mainScrollView);

        Intent intent = getIntent();
        m_id = intent.getStringExtra("id");
        String title = intent.getStringExtra("title");
        String original_title = intent.getStringExtra("original_title");
        String poster_path = intent.getStringExtra("poster_path");
        String overview = intent.getStringExtra("overview");
        String release_date = intent.getStringExtra("release_date");
        String vote_average = intent.getStringExtra("vote_average");
        String backdrop_path = intent.getStringExtra("backdrop_path");
        Double k = Double.parseDouble(vote_average)*10;
        final int a = k.intValue();
        final String voteCount = k.toString();



        TextView textView_original_title = (TextView)findViewById(R.id.tv_original_title);
        textView_original_title.setText(original_title);
        ImageView imageView_poster = (ImageView) findViewById(R.id.iv_poster);
        ImageView mainPoster = (ImageView) findViewById(R.id.mainPoster);

        final ImageButton btnMap = (ImageButton)findViewById(R.id.btnMap);

        final ImageButton btnVote = (ImageButton)findViewById(R.id.btnVote);

        final TextView tvPercent = (TextView)findViewById(R.id.tvPercent);
        tvPercent.setText(voteCount+"%");

        final ProgressBar pBar = (ProgressBar)findViewById(R.id.proBar);

        try {
            String movieId = m_id;
            String rst1 = new TaskA().execute(movieId).get();          //해당영화의 m_id를 가지고 간다.

            JSONObject json = new JSONObject(rst1);
            JSONArray jArr = json.getJSONArray("List");
            String msg1="";
            String msg2="";
            String msg3="";
            movieList.clear();
            for (int i = 0; i < jArr.length(); i++) {
                json = jArr.getJSONObject(i);
                msg1 = json.optString("memberId");
                msg2 = json.optString("movieScore");
                msg3 = json.optString("message");
                MovieM mm = new MovieM(msg1, msg2, msg3);
                movieList.add(mm);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        adapter = new mesListAdapter(getApplicationContext(), movieList);
        listView.setAdapter(adapter);



        new Thread(new Runnable() {
            int pStatus=0;
            private Handler handler = new Handler();
            @Override
            public void run() {
                // TODO Auto-generated method stub
                while (pStatus < a) {
                    pStatus += 1;

                    handler.post(new Runnable() {

                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            pBar.setProgress(pStatus);
                        }
                    });
                    try {
                        // Sleep for 200 milliseconds.
                        // Just to display the progress slowly
                        Thread.sleep(16); //thread will take approx 3 seconds to finish
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();



        Glide.with(this)
                .load(backdrop_path)
                .into(imageView_poster);
        ImageView myImage = (ImageView) findViewById(R.id.iv_poster);
        myImage.setAlpha(0.5f);

        Glide.with(this)
                .load(poster_path)
                .into(mainPoster);


        TextView textView_overview = (TextView)findViewById(R.id.tv_overview);
        textView_overview.setText(overview);
        TextView textView_release_date = (TextView)findViewById(R.id.tv_release_date);
        textView_release_date.setText(release_date);

        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailActivity.this, MapInfo.class);
                Toast.makeText(getApplicationContext(),"5km내의 모든 영화관을 보여드릴게요!",Toast.LENGTH_LONG).show();
                startActivity(intent);
            }
        });

        /*final TextView tvCoupText = (TextView)findViewById(R.id.tvCoupText);
        btnCoup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
                SharedPreferences.Editor editor = pref.edit();
                String k = pref.getString("memberId","memberId");
                if(k != "memberId") {
                    editor.putString("coup","coup");
                    editor.commit();
                    String strColor = "#80cbc4";
                    tvCoupText.setTextColor(Color.parseColor(strColor));
                    tvCoupText.setText("발급완료");
                    btnCoup.setEnabled(false);
                }else if(k == "memberId"){
                    Toast.makeText(getApplicationContext(),"로그인 후 이용해주세요!",Toast.LENGTH_LONG).show();
                }
            }
        });*/


        btnVote.setOnClickListener(new View.OnClickListener() {
            TextView tvMeScore = (TextView)findViewById(R.id.tvmeScore);
            String ak1;
            @Override
            public void onClick(View v) {


                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
                SharedPreferences.Editor editor = pref.edit();
                String k = pref.getString("memberId", "memberId");
                if (k != "memberId") {

                    dialogView = (View) View.inflate(DetailActivity.this, R.layout.activity_vote_movie, null);

                    RatingBar rb = dialogView.findViewById(R.id.ratingBar1);
                    final TextView tv = dialogView.findViewById(R.id.tvScore);
                    final EditText etMessage = dialogView.findViewById(R.id.etMessage);

                    rb.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                        @Override
                        public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                            final String rat = Float.toString(rating);
                            final double a = rating / 5;
                            tv.setText(rat);

                        }
                    });

                    tv1 = dialogView.findViewById(R.id.tvScore);
                    et1 = dialogView.findViewById(R.id.etMessage);


                    final String ka = pref.getString("memberId", "memberId");

                    new AlertDialog.Builder(DetailActivity.this)
                            .setTitle("영화 투표")
                            .setView(dialogView)
                            .setPositiveButton("투표", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    btnVote.isPressed();
                                    tvMeScore.setText(tv1.getText().toString());
                                    Double k = Double.parseDouble(voteCount);
                                    String ak1 = k.toString();
                                    tvPercent.setText(ak1 + "%");
                                    String memberScore = tvMeScore.getText().toString();
                                    tvMeScore.setVisibility(View.INVISIBLE);

                                    try {

                                        String movieId = m_id;
                                        String memberId = ka;
                                        String movieScore = memberScore;
                                        String message = et1.getText().toString();

                                        String rst = new Task().execute(movieId, memberId, movieScore, message).get();
                                        TextView tvVoteText = (TextView) findViewById(R.id.tvVoteText);
                                        if (rst.contentEquals("true")) {
                                            Toast.makeText(DetailActivity.this, "후기가 등록되었습니다.", Toast.LENGTH_SHORT).show();
                                            btnVote.setBackgroundResource(R.drawable.starresult);
                                            tvVoteText.setText("평가완료");
                                            String strColor = "#80cbc4";
                                            tvVoteText.setTextColor(Color.parseColor(strColor));
                                            btnVote.setEnabled(false);

                                        } else {
                                            Toast.makeText(DetailActivity.this, "후기 등록 실패!!", Toast.LENGTH_SHORT).show();
                                        }

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            })
                            .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(getApplicationContext(), "투표가 취소되었습니다", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .show();

                }else{
                    Toast.makeText(getApplicationContext(), "로그인 후 이용해주세요", Toast.LENGTH_SHORT).show();
                }

            }


        });

        //Asynctask
        YoutubeAsyncTask mProcessTask = new YoutubeAsyncTask();
        mProcessTask.execute(m_id);

            /*-------------------------------리스트 띄울부분----------------------------------------------*/






        listView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mainScrollView.requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

        /*--------------------------------------------------영화찜하기--------------------------------------------------------*/

        final ImageButton btnLove = (ImageButton)findViewById(R.id.btnLove);
        final TextView tvPlusText = (TextView)findViewById(R.id.tvPlusText);
        btnLove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
                SharedPreferences.Editor editor = pref.edit();
                String k = pref.getString("memberId", "memberId");
                if (k != "memberId") {

                    try {

                        String ka = pref.getString("memberId", "memberId");
                        String memberId = ka.toString();

                        Intent intent = getIntent();
                        String title = intent.getStringExtra("original_title").toString();
                        String releaseDate = intent.getStringExtra("release_date").toString();
                        String posterPath = intent.getStringExtra("poster_path").toString();


                        String rst2 = new TaskB().execute(memberId, title, releaseDate, posterPath).get();

                        if (rst2.contentEquals("true")) {
                            Toast.makeText(getApplicationContext(), "등록 성공", Toast.LENGTH_SHORT).show();
                            btnLove.setBackgroundResource(R.drawable.plusresult);
                            tvPlusText.setText("추가완료");
                            String strColor = "#80cbc4";
                            tvPlusText.setTextColor(Color.parseColor(strColor));
                            btnLove.setEnabled(false);

                        } else {
                            Toast.makeText(getApplicationContext(), "등록 실패!!", Toast.LENGTH_SHORT).show();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "로그인 후 이용해주세요", Toast.LENGTH_SHORT).show();
                }
            }
        });


        /*----------------------------------------------------------------------------------------------------------------------*/



    }

    /*------------------------------------------------------------------------------------ -----------------------*/

    public class Task extends AsyncTask<String, String, String> {               // db에 투표정보 넣는거
        String sendMsg, receiveMsg;
        String serverIp = "http://192.168.1.25:8080/0604/login/movieInsert.jsp";

        @Override
        protected String doInBackground(String... strings) {
            try {
                String str;
                URL url = new URL(serverIp);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");
                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());

                sendMsg = "movieId=" + strings[0] + "&memberId=" + strings[1] + "&movieScore=" + strings[2] + "&message=" + strings[3];
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

    public class TaskA extends AsyncTask<String, Void, String> {
        public  String ip ="192.168.1.25:8080"; //자신의 IP번호
        String sendMsg, receiveMsg;
        String serverIp = "http://"+ip+"/0604/login/movieList.jsp"; // 연결할 jsp주소

        @Override
        protected String doInBackground(String... strings) {
            try {
                String str1;
                URL url = new URL(serverIp);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");
                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
                sendMsg = "movieId="+strings[0];

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

    public class TaskB extends AsyncTask<String, String, String> {
        String sendMsg, receiveMsg;
        String serverIp = "http://192.168.1.25:8080/0604/login/likemovieInsert.jsp";

        @Override
        protected String doInBackground(String... strings) {
            try {
                String str2;
                URL url = new URL(serverIp);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");
                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());

                sendMsg = "memberId=" + strings[0] + "&title=" + strings[1] + "&releaseDate=" + strings[2] + "&posterPath=" + strings[3];
                osw.write(sendMsg);
                osw.flush();
                if (conn.getResponseCode() == conn.HTTP_OK) {
                    InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                    BufferedReader reader = new BufferedReader(tmp);
                    StringBuffer buffer = new StringBuffer();
                    while ((str2 = reader.readLine()) != null) {
                        buffer.append(str2);
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

    public void playVideo(final String videoId, YouTubePlayerView youTubePlayerView) {
        //initialize youtube player view
        Log.d("Youtube", "trailer: " + videoId);
        youTubePlayerView.initialize("AIzaSyD2xBenoZ5ODonTmM7fKvwV5uVr2mmdH90",
                new YouTubePlayer.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                        YouTubePlayer youTubePlayer, boolean b) {
                        youTubePlayer.cueVideo(videoId);

                    }

                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                        YouTubeInitializationResult youTubeInitializationResult) {

                    }
                });
    }


    private class YoutubeAsyncTask extends AsyncTask<String, Void, Youtube[]>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Youtube[] youtubeA) {
            super.onPostExecute(youtubeA);

            //ArrayList에 차례대로 집어 넣는다.
            if(youtubeA.length > 0){
                for(Youtube p : youtubeA){
                    youtubeList.add(p);
                }

                //유튜브뷰어를 이용 화면에 출력하자.
                trailer01 = youtubeList.get(0).getKey();
                Log.d("Youtube", "trailer : " + trailer01);
                youTubeView = findViewById(R.id.youtube_view);
                playVideo(trailer01, youTubeView);

            }
        }

        @Override
        protected Youtube[] doInBackground(String... strings) {
            String m_id = strings[0];

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("https://api.themoviedb.org/3/movie/" +m_id+ "/videos?api_key=dd3529cb48a78d9d2e775be63596398a")
                    .build();
            try {
                Response response = client.newCall(request).execute();
                Gson gson = new GsonBuilder().create();
                JsonParser parser = new JsonParser();
                JsonElement rootObject = parser.parse(response.body().charStream())
                        .getAsJsonObject().get("results");
                Youtube[] posts = gson.fromJson(rootObject, Youtube[].class);
                return posts;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }



    /* -------------------------------------------------------------------------------------------------------------*/






    /* --------------------------------------------------------------------------------------------------------------*/
}
