package entities;

import fileio.MovieInputData;

import java.util.ArrayList;

public final class Movie {
    private int duration;
    private String title;
    private int year;
    private ArrayList<String> cast;
    private ArrayList<String> genres;
    private ArrayList<Double> rating;
    private double avgRating;
    private int favorite;
    private int nrViews;

    public Movie(final MovieInputData movie) {
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

    public void setDuration(final int duration) {
        this.duration = duration;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(final int year) {
        this.year = year;
    }

    public ArrayList<String> getCast() {
        return cast;
    }

    public void setCast(final ArrayList<String> cast) {
        this.cast = cast;
    }

    public ArrayList<String> getGenres() {
        return genres;
    }

    public void setGenres(final ArrayList<String> genres) {
        this.genres = genres;
    }

    public ArrayList<Double> getRating() {
        return rating;
    }

    public void setRating(final ArrayList<Double> rating) {
        this.rating = rating;
    }

    public double getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(final double avgRating) {
        this.avgRating = avgRating;
    }

    public int getFavorite() {
        return favorite;
    }

    public void setFavorite(final int favorite) {
        this.favorite = favorite;
    }

    public int getNrViews() {
        return nrViews;
    }

    public void setNrViews(final int nrViews) {
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
