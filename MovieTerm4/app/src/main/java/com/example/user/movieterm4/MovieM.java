package com.example.user.movieterm4;

public class MovieM {

    private  String memberId;
    private String movieScore;
    private String message;

    public MovieM(String memberId, String movieScore, String message) {

        this.memberId = memberId;
        this.movieScore = movieScore;
        this.message = message;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getMovieScore() {
        return movieScore;
    }

    public void setMovieScore(String movieScore) {
        this.movieScore = movieScore;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
