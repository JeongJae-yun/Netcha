package com.example.user.movieterm4.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.user.movieterm4.NowPlay.DetailActivity;
import com.example.user.movieterm4.NowPlay.NowPlayMovie;
import com.example.user.movieterm4.R;
import com.example.user.movieterm4.data.Movie;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.RecyclerViewHolders>{

    private ArrayList<Movie> mMovieList;
    private LayoutInflater mInflate;
    private Context mContext;



    //constructor
    public MyRecyclerViewAdapter(Context context, ArrayList<Movie> itemList) {
        this.mContext = context;
        this.mInflate = LayoutInflater.from(context);
        this.mMovieList = itemList;

    }

    @NonNull
    @Override
    public RecyclerViewHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflate.inflate(R.layout.list_item, parent, false);
        RecyclerViewHolders viewHolder = new RecyclerViewHolders(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolders holder, final int position) {
        //포스터만 출력하자.
        final String url = "https://image.tmdb.org/t/p/w500"+mMovieList.get(position).getPoster_path();
        final String url2 = "https://image.tmdb.org/t/p/w500"+mMovieList.get(position).getBackdrop_path();
        Glide.with(mContext)
                .load(url)
                .centerCrop()
                .crossFade()
                .into(holder.imageView);
        //각 아이템 클릭 이벤트

        /*dialogView = (View)View.inflate(mContext, R.layout.likethismovie, null);*/


      /*  holder.imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(mContext)
                        .setTitle("좋아하는 영화로 등록")
                        .setView(dialogView)
                        .setPositiveButton("등록할래요", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {


                                SharedPreferences pref = mContext.getSharedPreferences("MyPref", 0);
                                SharedPreferences.Editor editor = pref.edit();
                                String k = pref.getString("memberId","memberId");
                                try{


                                    String memberId = k;
                                    String title = mMovieList.get(position).getOriginal_title();
                                    String releaseDate =mMovieList.get(position).getRelease_date();
                                    String posterPath = mMovieList.get(position).getPoster_path();

                                    String rst = new Task().execute(memberId,title,releaseDate,posterPath).get();

                                    if (rst.contentEquals("true")) {
                                        Toast.makeText(mContext, "등록 성공", Toast.LENGTH_SHORT).show();
                                    } else{
                                        Toast.makeText(mContext,"등록 실패!!",Toast.LENGTH_SHORT).show();
                                    }

                                }catch(Exception e){
                                    e.printStackTrace();
                                }
                            }
                        })
                        .setNegativeButton("잘못눌렀네요", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(mContext,"취소되었습니다",Toast.LENGTH_SHORT).show();
                            }
                        })
                        .show();


                return false;
            }

        });
*/

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, DetailActivity.class);
                intent.putExtra("id",mMovieList.get(position).getId());
                intent.putExtra("title", mMovieList.get(position).getTitle());
                intent.putExtra("original_title", mMovieList.get(position).getOriginal_title());
                intent.putExtra("poster_path", url);
                intent.putExtra("overview", mMovieList.get(position).getOverview());
                intent.putExtra("release_date", mMovieList.get(position).getRelease_date());
                intent.putExtra("vote_average",mMovieList.get(position).getVote_average());
                intent.putExtra("backdrop_path",url2);
                mContext.startActivity(intent);
                Log.d("Adapter", "Clcked: " + position);


            }
        });



    }



    @Override
    public int getItemCount() {
        return this.mMovieList.size();
    }


    //뷰홀더 - 따로 클래스 파일로 만들어도 된다.
    public static class RecyclerViewHolders extends RecyclerView.ViewHolder {
        public ImageView imageView;

        public RecyclerViewHolders(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
        }
    }

}
