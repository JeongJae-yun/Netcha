package com.example.user.movieterm4;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;


public class mesListAdapter extends BaseAdapter{

    private Context context;
    private List<MovieM> movieList;

    public mesListAdapter(Context context,  List<MovieM> movieList){
        this.context = context;
        this.movieList = movieList;
    }

    @Override
    public int getCount() {
        return movieList.size();
    }

    @Override
    public Object getItem(int i) {
        return movieList.get(i);
    }

    //아이템별 아이디를 반환하는 메소드
    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.message_list, null);

        //뷰에 다음 컴포넌트들을 연결시켜줌
        TextView memberId = (TextView)v.findViewById(R.id.tvMemId);
        TextView movieScore = (TextView)v.findViewById(R.id.tvMovieScore);
        TextView message = (TextView)v.findViewById(R.id.tvMyMessage);


        memberId.setText(movieList.get(i).getMemberId());
        movieScore.setText(movieList.get(i).getMovieScore());
        message.setText(movieList.get(i).getMessage());


        //만든뷰를 반환함
        return v;
    }
}



