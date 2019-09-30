package com.example.user.movieterm4;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.user.movieterm4.Map.MapInfo;
import com.example.user.movieterm4.NowPlay.DetailActivity;

public class VoteMovie extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote_movie);

        final TextView tv = (TextView) findViewById(R.id.tvScore);
        RatingBar rb =(RatingBar)findViewById(R.id.ratingBar1);


        rb.setIsIndicator(false);
        rb.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                String rat = Float.toString(rating);
                tv.setText(rat);
              /*  Intent intent = new Intent(VoteMovie.this, DetailActivity.class);
                intent.putExtra("rat",rat);
                startActivity(intent);*/
            }
        });



    }
}
