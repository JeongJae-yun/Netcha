package com.example.user.movieterm4.model;

public class Movie {
    private String url;
    private String rank;
    private String title;
    private String ReleaseDate;

    public Movie(String url, String rank, String title, String ReleaseDate) {

        this.url = url;
        this.rank = rank;
        this.title = title;
        this.ReleaseDate = ReleaseDate;

    }

    public String getUrl() {
        return url;
    }

    public String getRank() {
        return rank;
    }

    public String getTitle() {
        return title;
    }

    public String getReleaseDate() {
        return ReleaseDate;
    }
}
