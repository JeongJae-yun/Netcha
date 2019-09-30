package com.example.user.movieterm4.WillPlay;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.user.movieterm4.BoxOffice.BoxOffice;
import com.example.user.movieterm4.LoadingActivity;
import com.example.user.movieterm4.Login;
import com.example.user.movieterm4.Map.MapInfo;
import com.example.user.movieterm4.MemberInfo;
import com.example.user.movieterm4.NowPlay.NowPlayMovie;
import com.example.user.movieterm4.R;
import com.example.user.movieterm4.adapter.MyRecyclerViewAdapter;
import com.example.user.movieterm4.data.Movie;
import com.example.user.movieterm4.netXcha;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WillPlayMovie extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private RecyclerView recyclerView;
    private MyRecyclerViewAdapter adapter;
    ArrayList<Movie> movieList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_will_play_movie);

        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        movieList = new ArrayList<Movie>();

        //Asynctask - OKHttp
        MyAsyncTask mAsyncTask = new MyAsyncTask();
        mAsyncTask.execute();

        //LayoutManager
        recyclerView.setLayoutManager(new GridLayoutManager(WillPlayMovie.this, 2));


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

    }
    public class MyAsyncTask extends AsyncTask<String, Void, Movie[]> {
        //로딩중 표시
        ProgressDialog progressDialog = new ProgressDialog(WillPlayMovie.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("\t영화를 준비중이에요! 기다려주세요!");
            //show dialog
            progressDialog.show();
        }

        @Override
        protected Movie[] doInBackground(String... strings) {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("https://api.themoviedb.org/3/movie/upcoming?api_key=dd3529cb48a78d9d2e775be63596398a&language=ko-KR&page=1&page2")
                    .build();
            try {
                Response response = client.newCall(request).execute();
                Gson gson = new GsonBuilder().create();
                JsonParser parser = new JsonParser();
                JsonElement rootObject = parser.parse(response.body().charStream())
                        .getAsJsonObject().get("results");
                Movie[] posts = gson.fromJson(rootObject, Movie[].class);
                return posts;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }


        @Override
        protected void onPostExecute(Movie[] result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            //ArrayList에 차례대로 집어 넣는다.
            if (result.length > 0) {
                for (Movie p : result) {
                    movieList.add(p);
                }
            }

            //어답터 설정
            adapter = new MyRecyclerViewAdapter(WillPlayMovie.this, movieList);
            recyclerView.setAdapter(adapter);
        }
    }
    //----------------------------------------------네비게이터------------------------------------------//
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
            SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
            SharedPreferences.Editor editor = pref.edit();
            String k = pref.getString("memberId","memberId");

            // Handle navigation view item clicks here.
            int id = item.getItemId();

            if (id == R.id.menu1) {
                if (k == "memberId"){
                    Intent intent = new Intent(WillPlayMovie.this, Login.class);
                    startActivity(intent);
                }else if(k != "memberId"){
                    Intent intent = new Intent(WillPlayMovie.this, MemberInfo.class);
                    startActivity(intent);
                }
            } else if (id == R.id.menu2) {
                Intent intent = new Intent(WillPlayMovie.this, NowPlayMovie.class);
                startActivity(intent);
            } else if (id == R.id.menu3) {
                Intent intent = new Intent(WillPlayMovie.this, WillPlayMovie.class);
                startActivity(intent);
            } else if(id == R.id.menu4) {
                Intent intent = new Intent(WillPlayMovie.this, BoxOffice.class);
                startActivity(intent);
            }else if(id == R.id.menu5) {
                Intent intent = new Intent(WillPlayMovie.this, MapInfo.class);
                startActivity(intent);
            }else if(id == R.id.menu6) {
                editor.clear();
                editor.commit();
                Intent intent = new Intent(WillPlayMovie.this, LoadingActivity.class);
                startActivity(intent);
            }else if(id==R.id.menu7){
                Intent intent = new Intent(WillPlayMovie.this, netXcha.class);
                startActivity(intent);
            }

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;

    }
    //----------------------------------------------네비게이터------------------------------------------//
}
