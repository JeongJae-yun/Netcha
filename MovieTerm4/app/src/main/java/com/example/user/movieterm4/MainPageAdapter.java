package com.example.user.movieterm4;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.user.movieterm4.NowPlay.DetailActivity;
import com.example.user.movieterm4.adapter.MyRecyclerViewAdapter;
import com.example.user.movieterm4.data.Movie;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainPageAdapter extends RecyclerView.Adapter<MainPageAdapter.RecyclerViewHolders>{


    private LayoutInflater mInflate;
    private Context mContext;
    private ArrayList<MainPageData> movieMainList;



    public MainPageAdapter(Context context,  ArrayList<MainPageData> movieMainList){
        this.mContext = context;
        this.movieMainList = movieMainList;
        this.mInflate = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecyclerViewHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflate.inflate(R.layout.maingridlist, parent, false);
        RecyclerViewHolders viewHolder = new RecyclerViewHolders(view);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolders holder, final int position) {
        //포스터만 출력하자.
        /*final String url = "https://image.tmdb.org/t/p/w500"+movieMainList.get(position).getPosterPath();*/
        final String url = movieMainList.get(position).getPosterPath();
        Glide.with(mContext)
                .load(url)
                .centerCrop()
                .crossFade()
                .into(holder.ivPresentPost);
        //각 아이템 클릭 이벤트
        String strColor = "#80cbc4";
        holder.tvPresentId.setText(movieMainList.get(position).getMemberId()+"'s Pick");
        holder.tvPresentTitle.setText(movieMainList.get(position).getTitle());
        holder.tvPresentTitle.setTextColor(Color.parseColor(strColor));




        /*holder.itemView.setOnClickListener(new View.OnClickListener() {
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
*/


    }


    @Override
    public int getItemCount() {
        return this.movieMainList.size();
    }



    /*//아이템별 아이디를 반환하는 메소드
    @Override
    public long getItemId(int i) {
        return i;
    }*/


    //뷰홀더 - 따로 클래스 파일로 만들어도 된다.
    public static class RecyclerViewHolders extends RecyclerView.ViewHolder {
        public ImageView ivPresentPost;
        public TextView tvPresentId;
        public TextView tvPresentTitle;

        public RecyclerViewHolders(View itemView) {
            super(itemView);
            ivPresentPost = (ImageView) itemView.findViewById(R.id.ivPresentPost);
            tvPresentId = (TextView) itemView.findViewById(R.id.tvPresentId);
            tvPresentTitle = (TextView)itemView.findViewById(R.id.tvPresentTitle);

        }
    }











  /*

 @Override
    public int getCount() {
        return movieMainList.size();
    }

    @Override
    public Object getItem(int i) {
        return movieMainList.get(i);
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.maingridlist, null);

        //뷰에 다음 컴포넌트들을 연결시켜줌

        TextView title = (TextView)v.findViewById(R.id.tvPresentTitle);
        TextView memberId = (TextView)v.findViewById(R.id.tvPresentId);
        ImageView imageView = (ImageView)v.findViewById(R.id.ivPresentPost);

        RecyclerView.ViewHolder holder;



        title.setText(movieMainList.get(i).getTitle());
        memberId.setText(movieMainList.get(i).getMemberId());
        String url = movieMainList.get(i).getPosterPath();
        Glide.with(context)
                .load(url)
                .override(400,600)
                .into(imageView);


        //만든뷰를 반환함
        return v;
    }*/


}
