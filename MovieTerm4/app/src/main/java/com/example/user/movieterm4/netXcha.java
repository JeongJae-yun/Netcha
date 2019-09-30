package com.example.user.movieterm4;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.user.movieterm4.BoxOffice.BoxOffice;
import com.example.user.movieterm4.Map.MapInfo;
import com.example.user.movieterm4.NowPlay.NowPlayMovie;
import com.example.user.movieterm4.ViewPager.ImageModel;
import com.example.user.movieterm4.ViewPager.SlidingImage_Adapter;
import com.example.user.movieterm4.WillPlay.WillPlayMovie;
import com.example.user.movieterm4.adapter.MyRecyclerViewAdapter;
import com.viewpagerindicator.CirclePageIndicator;

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
import java.util.Timer;
import java.util.TimerTask;

public class netXcha extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private String TAG = "netXcha";
    private VideoView mVideoview;
    private ImageButton imageButton, imageButton2 ;
    private static ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    private ArrayList<ImageModel> imageModelArrayList;
    private ArrayList<MainPageData> movieMainList;//추가
    private MainPageAdapter adapter;//추가
    private GridView gridView;//추가
    private RecyclerView recyclerView2;

    private int[] myImageList = new int[]{R.drawable.spiderman,R.drawable.xman,R.drawable.maninblack1,R.drawable.frozen};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net_xcha);



        movieMainList = new ArrayList<MainPageData>(); //추가
        recyclerView2 = (RecyclerView)findViewById(R.id.recycler_view2);
        recyclerView2.setLayoutManager(new GridLayoutManager(netXcha.this, 3));

        try {
            String rst = String.valueOf(new Task().execute().get());
            JSONObject json = new JSONObject(rst);
            JSONArray jArr = json.getJSONArray("List");
            String msg1="";
            String msg2="";
            String msg3="";
            String msg4="";

            movieMainList.clear();

            for (int i = 0; i < jArr.length(); i++) {
                json = jArr.getJSONObject(i);
                msg1 = json.optString("title");
                msg2 = json.optString("releaseDate");
                msg3 = json.optString("posterPath");
                msg4 = json.optString("memberId");
                MainPageData mld =  new MainPageData(msg1,msg2,msg3,msg4);
                movieMainList.add(mld);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        adapter = new MainPageAdapter(netXcha.this, movieMainList);
        recyclerView2.setAdapter(adapter);

        /*-------------------------------------gridviewAdapter--------------------------------------------------*/

        imageButton= findViewById(R.id.imageButton3);
        imageButton2=findViewById(R.id.imageButton4);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                intent = new Intent(getApplicationContext(), WillPlayMovie.class);
                startActivity(intent);
            }
        });

        imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                intent = new Intent(getApplicationContext(), NowPlayMovie.class);
                startActivity(intent);
            }
        });


        imageModelArrayList = new ArrayList<>();
        imageModelArrayList = populateList();
        init();


        //-------------------------------------동영상---------------------------------------//

        mVideoview = (VideoView) findViewById(R.id.video_view);
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/"+R.raw.maninblack);
        mVideoview.setVideoURI(uri);
        mVideoview.start();
        mVideoview.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.setLooping(true);


            }
        });
        //-------------------------------------동영상---------------------------------------//


        //-------------------------------------갤러리---------------------------------------//
        Gallery gallery =(Gallery) findViewById(R.id.gallery);
        gallery.setAdapter(new netXcha.MyGalleryAdapter(this));

        Gallery gallery1 =(Gallery) findViewById(R.id.gallery1);
        gallery1.setAdapter(new netXcha.MyGalleryAdapter1(this));
        //-------------------------------------갤러리---------------------------------------//




        //-----------------------------네비게이터------------------------------------------//

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
        //-----------------------------네비게이터------------------------------------------//

    }



    //-------------------------------갤러리뷰1-------------------------------------------------//
    public class MyGalleryAdapter extends BaseAdapter {                      //
        Context context;
        final Integer[]  posterID = {R.drawable.poster3,R.drawable.poster5,R.drawable.poster10,R.drawable.poster11,R.drawable.poster12,R.drawable.poster13};

        public  MyGalleryAdapter(Context c){
            context = c;
        }
        @Override
        public int getCount() {
            return posterID.length;
        }
        @Override
        public Object getItem(int i) {
            return null;
        }
        @Override
        public long getItemId(int i) {
            return 0;
        }
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ImageView imageView = new ImageView(context);
            imageView.setLayoutParams(new Gallery.LayoutParams(600,1100));
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageView.setPadding(5,2,5,5);
            imageView.setImageResource(posterID[position]);
            imageView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    ImageView ivPoster = (ImageView) findViewById(R.id.ivPoster);
                    ivPoster.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    ivPoster.setImageResource(posterID[position]);
                    return  false;
                }
            });
            return imageView;
        }
    }

    //-------------------------------갤러리뷰1-------------------------------------------------//

    //-------------------------------갤러리뷰2-------------------------------------------------//
    public class MyGalleryAdapter1 extends BaseAdapter {                      //
        Context context;
        final Integer[]  posterID = {R.drawable.poster1,R.drawable.poster2,R.drawable.poster4,R.drawable.poster6,R.drawable.poster7,R.drawable.poster8,R.drawable.poster9};

        public  MyGalleryAdapter1(Context c){
            context = c;
        }
        @Override
        public int getCount() {
            return posterID.length;
        }
        @Override
        public Object getItem(int i) {
            return null;
        }
        @Override
        public long getItemId(int i) {
            return 0;
        }
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ImageView imageView = new ImageView(context);
            imageView.setLayoutParams(new Gallery.LayoutParams(600,1100));
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageView.setPadding(5,2,5,5);
            imageView.setImageResource(posterID[position]);
            imageView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    ImageView ivPoster1 = (ImageView) findViewById(R.id.ivPoster1);
                    ivPoster1.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    ivPoster1.setImageResource(posterID[position]);
                    return  false;
                }
            });
            return imageView;
        }
    }

    //-------------------------------갤러리뷰2-------------------------------------------------//

    //--------------------------------뷰페이저-----------------------------------------------//
    private ArrayList<ImageModel> populateList() {

        ArrayList<ImageModel> list = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            ImageModel imageModel = new ImageModel();
            imageModel.setImage_drawable(myImageList[i]);
            list.add(imageModel);
        }

        return list;
    }

    private void init() {

        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(new SlidingImage_Adapter(netXcha.this, imageModelArrayList));

        CirclePageIndicator indicator = (CirclePageIndicator)
                findViewById(R.id.indicator);

        indicator.setViewPager(mPager);

        final float density = getResources().getDisplayMetrics().density;
        indicator.setRadius(5 * density);

        NUM_PAGES = imageModelArrayList.size();

        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 100000, 100000);
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                currentPage = position;

            }

            @Override
            public void onPageScrolled(int pos, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int pos) {

            }
        });

    }
    //---------------------------------뷰페이저-----------------------------------------//



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
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        SharedPreferences.Editor editor = pref.edit();
        String k = pref.getString("memberId","memberId");

        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.menu1) {
            if (k == "memberId"){
                Intent intent = new Intent(netXcha.this, Login.class);
                startActivity(intent);
            }else if(k != "memberId"){
                Intent intent = new Intent(netXcha.this, MemberInfo.class);
                startActivity(intent);
            }
        } else if (id == R.id.menu2) {
            Intent intent = new Intent(netXcha.this, NowPlayMovie.class);
            startActivity(intent);
        } else if (id == R.id.menu3) {
            Intent intent = new Intent(netXcha.this, WillPlayMovie.class);
            startActivity(intent);
        } else if(id == R.id.menu4) {
            Intent intent = new Intent(netXcha.this, BoxOffice.class);
            startActivity(intent);
        }else if(id == R.id.menu5) {
            Intent intent = new Intent(netXcha.this, MapInfo.class);
            startActivity(intent);
        }else if(id == R.id.menu6) {
            editor.clear();
            editor.commit();
            Intent intent = new Intent(netXcha.this, LoadingActivity.class);
            startActivity(intent);
        }else if(id==R.id.menu7){
            Intent intent = new Intent(netXcha.this, netXcha.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    //-----------------------------네비게이터------------------------------------------//

    public class Task extends AsyncTask<Void, Void, String> {

        String sendMsg, receiveMsg;
        String serverIp = "http://192.168.1.25:8080/0604/login/likeMovieList2.jsp"; // 연결할 jsp주소

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