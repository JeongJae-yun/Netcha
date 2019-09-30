package com.example.user.movieterm4.data;

import com.example.user.movieterm4.model.Movie;

import java.util.ArrayList;

public class SampleData {

    ArrayList<Movie> items = new ArrayList<>();

    public ArrayList<Movie> getItems() {

        Movie movie1 = new Movie("https://images.squarespace-cdn.com/content/v1/51b3dc8ee4b051b96ceb10de/1553660258784-PJIECJYCG8COKW50WOCE/ke17ZwdGBToddI8pDm48kNvT88LknE-K9M4pGNO0Iqd7gQa3H78H3Y0txjaiv_0fDoOvxcdMmMKkDsyUqMSsMWxHk725yiiHCCLfrh8O1z5QPOohDIaIeljMHgDF5CVlOqpeNLcJ80NK65_fV7S1USOFn4xF8vTWDNAUBm5ducQhX-V3oVjSmr829Rco4W2Uo49ZdOtO_QXox0_W7i2zEA/image-asset.jpeg?format=2500w",
                "1", "Aladdin", "2019.05.23");


        Movie movie2 = new Movie("https://www.vitalthrills.com/wp-content/uploads/2019/05/mibbannerheader-696x465.jpg",
                "2", "Man In Black", "2019.06.12");

        Movie movie3 = new Movie("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRnabwfy2_WAVSZvUjWn5eOI1tUuPIaLE9zXqKX8e50AbmRgF3E",
                "3", "X-Man : Dark Phoenix", "2019.06.05");

        Movie movie4 = new Movie("https://pbs.twimg.com/media/D2wo1l0UkAA3Bu7.jpg",
                "4", "Avengers: End Game", "2019.04.24");

        Movie movie5 = new Movie("http://www.movienewz.com/wp-content/uploads/2019/02/hustle_movie_trailer-720x405.jpg",
                "5", "The Hustle", "2019.05.09");

        Movie movie6 = new Movie("http://optimal.inven.co.kr/upload/2019/04/13/bbs/i16267357903.jpg",
                "6", "명탐정 피카츄", "2019.05.03");

        Movie movie7 = new Movie("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRk8GmN0dGJQi7yvpFGsc0u4nZ98WVfPZJ0X2Udo9lscQVQEIOH",
                "7", "고질라", "2019.04.04");

        Movie movie8 = new Movie("https://upload.wikimedia.org/wikipedia/en/0/0f/Rocketman_%28film%29.png",
                "8", "RocketMan", "2019.05.22");

        Movie movie9 = new Movie("https://i.ytimg.com/vi/43kPmVebah0/maxresdefault.jpg",
                "9", "이웃집 토토로", "2019.06.06");

        Movie movie10 = new Movie("https://thescreen.co.kr/thescreen/wp-content/uploads/2019/04/20190411%E1%84%92%E1%85%A6%E1%86%AF%E1%84%87%E1%85%A9%E1%84%8B%E1%85%B5001.jpg",
                "10", "헬보이", "2019.04.10");

        items.add(movie1);
        items.add(movie2);
        items.add(movie3);
        items.add(movie4);
        items.add(movie5);
        items.add(movie6);
        items.add(movie7);
        items.add(movie8);
        items.add(movie9);
        items.add(movie10);
        return items;
    }
}
