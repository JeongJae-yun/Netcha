package com.example.user.movieterm4.WillPlay;

import android.os.Bundle;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.user.movieterm4.Map.MapInfo;
import com.example.user.movieterm4.R;
import com.example.user.movieterm4.data.Youtube;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DetailActivity2 extends YouTubeBaseActivity {
    ArrayList<Youtube> youtubeList;

    private YouTubePlayerView youTubeView;
    private String trailer01;
    private String m_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail2);

        youtubeList = new ArrayList<Youtube>();

        Intent intent = getIntent();
        m_id = intent.getStringExtra("id");
        String title = intent.getStringExtra("title");
        String original_title = intent.getStringExtra("original_title");
        String poster_path = intent.getStringExtra("poster_path");
        String overview = intent.getStringExtra("overview");
        String release_date = intent.getStringExtra("release_date");

        TextView textView_title = (TextView)findViewById(R.id.tv_title);
        textView_title.setText(title);
        TextView textView_original_title = (TextView)findViewById(R.id.tv_original_title);
        textView_original_title.setText(original_title);
        ImageView imageView_poster = (ImageView) findViewById(R.id.iv_poster);
        Button btnMap = (Button) findViewById(R.id.btnMap);
        Button btnCoup = (Button) findViewById(R.id.btnCoup);

        Glide.with(this)
                .load(poster_path)
                .override(1000,1200)
                .centerCrop()
                .crossFade()
                .into(imageView_poster);

        TextView textView_overview = (TextView)findViewById(R.id.tv_overview);
        textView_overview.setText(overview);
        TextView textView_release_date = (TextView)findViewById(R.id.tv_release_date);
        textView_release_date.setText(release_date);

        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailActivity2.this, MapInfo.class);
                Toast.makeText(getApplicationContext(),"5km내의 모든 영화관을 보여드릴게요!",Toast.LENGTH_LONG).show();
                startActivity(intent);
            }
        });



        //Asynctask
        YoutubeAsyncTask mProcessTask = new YoutubeAsyncTask();
        mProcessTask.execute(m_id);
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
                    .url("https://api.themoviedb.org/3/movie/33/videos?api_key=dd3529cb48a78d9d2e775be63596398a")
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
}
