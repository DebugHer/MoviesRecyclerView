package com.example.aadegoke.moviesrecyclerview;

/**
 * Created by a.adegoke on 4/24/2018.
 */

public class Movies {

    private String title;
    private String release_date ;
    private String overview;
    private String poster_path;

    public Movies(String title, String release_date, String overview,  String poster_path) {
        this.title = title;
        this.release_date = release_date;
        this.overview = overview;
        this.poster_path = poster_path;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

}
