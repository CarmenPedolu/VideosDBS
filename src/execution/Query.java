package execution;

import entities.Action;
import entities.Actor;
import entities.Movie;
import entities.Serial;
import main.Database;
import utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class Query {
    private Database data;
    private int number;
    private final String sortType;

    public Query(final Database database, final Action action) {
        this.data = database;
        this.number = action.getNumber();
        this.sortType = action.getSortType();
    }

    public final Database getData() {
        return data;
    }

    public final void setData(final Database data) {
        this.data = data;
    }

    public final int getNumber() {
        return number;
    }

    public final void setNumber(final int number) {
        this.number = number;
    }

    public final String getSortType() {
        return sortType;
    }

    /**
     * Find the actors based on the ratings of the video in which they played
     *
     * @return a String which contains the actors suitable
     */
    public final String averageActor() {
        // Calculez media ratingurilor filmelor și a serialelor,
        // în care au jucat toti actorii
        List<Actor> actorsList = this.data.getActors();
        Map<String, Double> actorsRating = new HashMap<>();
        for (Actor actor : actorsList) {
            ArrayList<String> videos = actor.getFilmography();
            // pun toate ratingurile videourile in care a jucat actorul intr-un arraylist
            ArrayList<Double> ratings = new ArrayList<>();
            for (String videoTitle : videos) {
                for (Movie movie : data.getMovies()) {
                    if (movie.getTitle().equals(videoTitle)) {
                        if (movie.getAvgRating() != 0) {
                            ratings.add(movie.getAvgRating());
                        }
                    }
                }
                for (Serial serial : data.getSerials()) {
                    if (serial.getTitle().equals(videoTitle)) {
                        if (serial.getAvgRating() != 0) {
                            ratings.add(serial.getAvgRating());
                        }
                    }
                }
            }
            // Daca rating-ul e diferit de 0 il setez la actor si il adaug in Map
            if (ratings.size() > 0) {
                double avgRatingActor = 0;
                for (double avgRate : ratings) {
                    avgRatingActor += avgRate;
                }
                actor.setAvgRating(avgRatingActor / ratings.size());
                actorsRating.put(actor.getName(), actor.getAvgRating());
            }
        }
        return Utils.sortedDoubleAlphabetical(actorsRating, getSortType(), getNumber());
    }
}
