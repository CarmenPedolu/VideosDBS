package Entities;

import entertainment.Season;
import fileio.SerialInputData;

import java.util.ArrayList;

public class Serial {
    private String title;
    private int year;
    private ArrayList<String> cast;
    private ArrayList<String> genres;
    private int numberOfSeasons;
    private ArrayList<Season> seasons;
    private double avgRating;
    private int favorite;
    private int nrViews;

    public Serial(final String title, final ArrayList<String> cast,
                           final ArrayList<String> genres,
                           final int numberOfSeasons, final ArrayList<Season> seasons,
                           final int year) {
        this.title = title;
        this.year = year;
        this.cast = cast;
        this.genres = genres;
        this.numberOfSeasons = numberOfSeasons;
        this.seasons = seasons;
        this.avgRating = 0;
        this.favorite = 0;
        this.nrViews = 0;
    }

    public Serial(SerialInputData serial) {
        this.title = serial.getTitle();
        this.year = serial.getYear();
        this.cast = serial.getCast();
        this.genres = serial.getGenres();
        this.numberOfSeasons = serial.getNumberSeason();
        this.seasons = serial.getSeasons();
        this.avgRating = 0;
        this.favorite = 0;
        this.nrViews = 0;
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

    public int getNumberOfSeasons() {
        return numberOfSeasons;
    }

    public void setNumberOfSeasons(int numberOfSeasons) {
        this.numberOfSeasons = numberOfSeasons;
    }

    public ArrayList<Season> getSeasons() {
        return seasons;
    }

    public void setSeasons(ArrayList<Season> seasons) {
        this.seasons = seasons;
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
        return "SerialInputData{" + " title= "
                + this.getTitle() + " " + " year= "
                + this.getYear() + " cast {"
                + this.getCast() + " }\n" + " genres {"
                + this.getGenres() + " }\n "
                + " numberSeason= " + numberOfSeasons
                + ", seasons=" + seasons + "\n\n" + '}';
    }

    public int getDuration() {
        int duration = 0;
        for (Season season : this.seasons) {
            duration += season.getDuration();
        }
        return duration;
    }


}
