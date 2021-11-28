package Entities;

import fileio.MovieInputData;

import java.util.ArrayList;

public class Movie {
    private int duration;
    private String title;
    private int year;
    private ArrayList<String> cast;
    private ArrayList<String> genres;
    private ArrayList<Double> rating;
    private double avgRating;
    private int favorite;
    private int nrViews;

    public Movie(String title, ArrayList<String> cast,
                 ArrayList<String> genres, int year,
                 int duration) {
        this.title = title;
        this.year = year;
        this.cast = cast;
        this.genres = genres;
        this.duration = duration;
        this.rating = null;
        this.avgRating = 0;
        this.favorite = 0;
        this.nrViews = 0;
    }

    public Movie(MovieInputData movie) {
        this.duration = movie.getDuration();
        this.title = movie.getTitle();
        this.year = movie.getYear();
        this.cast = movie.getCast();
        this.genres = movie.getGenres();
        this.rating = null;
        this.avgRating = 0;
        this.favorite = 0;
        this.nrViews = 0;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public ArrayList<String> getCast() {
        return cast;
    }

    public void setCast(ArrayList<String> cast) {
        this.cast = cast;
    }

    public ArrayList<String> getGenres() {
        return genres;
    }

    public void setGenres(ArrayList<String> genres) {
        this.genres = genres;
    }

    public ArrayList<Double> getRating() {
        return rating;
    }

    public void setRating(ArrayList<Double> rating) {
        this.rating = rating;
    }

    public double getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(double avg_rating) {
        this.avgRating = avg_rating;
    }

    public int getFavorite() {
        return favorite;
    }

    public void setFavorite(int favorite) {
        this.favorite = favorite;
    }

    public int getNrViews() {
        return nrViews;
    }

    public void setNrViews(int nrViews) {
        this.nrViews = nrViews;
    }

    @Override
    public String toString() {
        return "MovieInputData{" + "title= "
                + this.getTitle() + "year= "
                + this.getYear() + "duration= "
                + duration + "cast {"
                + this.getCast() + " }\n"
                + "genres {" + this.getGenres() + " }\n ";
    }
}

