package com.example.user.movieterm4;

public class MainPageData {
    private String title;
    private String releaseDate;
    private String posterPath;
    private  String memberId;

    public MainPageData(String title, String releaseDate, String posterPath, String memberId) {
        this.title = title;
        this.releaseDate = releaseDate;
        this.posterPath = posterPath;
        this.memberId = memberId;
    }

    public String getTitle() {
        return title;
    }

    public MainPageData setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public MainPageData setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
        return this;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public MainPageData setPosterPath(String posterPath) {
        this.posterPath = posterPath;
        return this;
    }

    public String getMemberId() {
        return memberId;
    }

    public MainPageData setMemberId(String memberId) {
        this.memberId = memberId;
        return this;
    }
}
