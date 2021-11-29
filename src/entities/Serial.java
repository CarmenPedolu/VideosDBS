package entities;

import entertainment.Season;
import fileio.SerialInputData;

import java.util.ArrayList;

public final class Serial {
    private String title;
    private int year;
    private ArrayList<String> cast;
    private ArrayList<String> genres;
    private final int numberOfSeasons;
    private ArrayList<Season> seasons;
    private double avgRating;
    private int favorite;
    private int nrViews;

    public Serial(final SerialInputData serial) {
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

    public ArrayList<Season> getSeasons() {
        return seasons;
    }

    public void setSeasons(final ArrayList<Season> seasons) {
        this.seasons = seasons;
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
        return "SerialInputData{" + " title= "
                + this.getTitle() + " " + " year= "
                + this.getYear() + " cast {"
                + this.getCast() + " }\n" + " genres {"
                + this.getGenres() + " }\n "
                + " numberSeason= " + numberOfSeasons
                + ", seasons=" + seasons + "\n\n" + '}';
    }

    /**
     * Return the duration of visualization of the serial
     *
     * @return an Integer
     */
    public int getDuration() {
        int duration = 0;
        for (Season season : this.seasons) {
            duration += season.getDuration();
        }
        return duration;
    }
}
