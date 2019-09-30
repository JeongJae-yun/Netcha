package com.example.user.movieterm4;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class likeListAdapter extends BaseAdapter {

    private Context context;
    private List<MovieLikeData> movieLikeList;

    public likeListAdapter(Context context,  List<MovieLikeData> movieLikeList){
        this.context = context;
        this.movieLikeList = movieLikeList;
    }

    @Override
    public int getCount() {
        return movieLikeList.size();
    }

    @Override
    public Object getItem(int i) {
        return movieLikeList.get(i);
    }

    //아이템별 아이디를 반환하는 메소드
    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.likemovielist, null);

        //뷰에 다음 컴포넌트들을 연결시켜줌

        TextView title = (TextView)v.findViewById(R.id.tvLikeTitle);
        TextView releaseDate = (TextView)v.findViewById(R.id.tvLikeRelease);
        ImageView imageView = (ImageView)v.findViewById(R.id.ivMoviePoster);


        title.setText(movieLikeList.get(i).getTitle());
        releaseDate.setText(movieLikeList.get(i).getReleaseDate());
        String url = movieLikeList.get(i).getPosterPath();
        Glide.with(context)
                .load(url)
                .override(300,500)
                .into(imageView);


        //만든뷰를 반환함
        return v;
    }
}
