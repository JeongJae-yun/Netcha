package com.example.user.movieterm4.BoxOffice;

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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.user.movieterm4.LoadingActivity;
import com.example.user.movieterm4.Login;
import com.example.user.movieterm4.Map.MapInfo;
import com.example.user.movieterm4.MemberInfo;
import com.example.user.movieterm4.NowPlay.NowPlayMovie;
import com.example.user.movieterm4.R;
import com.example.user.movieterm4.WillPlay.WillPlayMovie;
import com.example.user.movieterm4.adapter.MovieAdapter;
import com.example.user.movieterm4.data.SampleData;
import com.example.user.movieterm4.netXcha;

public class BoxOffice extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private MovieAdapter adapter = new MovieAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_box_office);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        //아이템 로드
        adapter.setItems(new SampleData().getItems());

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
                Intent intent = new Intent(BoxOffice.this, Login.class);
                startActivity(intent);
            }else if(k != "memberId"){
                Intent intent = new Intent(BoxOffice.this, MemberInfo.class);
                startActivity(intent);
            }
        } else if (id == R.id.menu2) {
            Intent intent = new Intent(BoxOffice.this, NowPlayMovie.class);
            startActivity(intent);
        } else if (id == R.id.menu3) {
            Intent intent = new Intent(BoxOffice.this, WillPlayMovie.class);
            startActivity(intent);
        } else if(id == R.id.menu4) {
            Intent intent = new Intent(BoxOffice.this, BoxOffice.class);
            startActivity(intent);
        }else if(id == R.id.menu5) {
            Intent intent = new Intent(BoxOffice.this, MapInfo.class);
            startActivity(intent);
        }else if(id == R.id.menu6) {
            editor.clear();
            editor.commit();
            Intent intent = new Intent(BoxOffice.this, LoadingActivity.class);
            startActivity(intent);
        }else if(id==R.id.menu7){
            Intent intent = new Intent(BoxOffice.this, netXcha.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    //----------------------------------------------네비게이터------------------------------------------//
}
