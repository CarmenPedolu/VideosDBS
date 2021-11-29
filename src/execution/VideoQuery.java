package execution;

import entities.Action;
import entities.Movie;
import entities.Serial;
import main.Database;
import utils.Utils;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

public final class VideoQuery extends Query {
    private final int filterYear;
    private final String filterGenre;

    public VideoQuery(final Database database, final Action action) {
        super(database, action);
        String year = action.getFilters().get(0).get(0);
        if (year != null) {
            this.filterYear = Integer.parseInt(year);
        } else {
            this.filterYear = 0;
        }
        this.filterGenre = action.getFilters().get(1).get(0);
    }

    /**
     * Find the best getNumber() rated movies sorted by the SortType
     *
     * @return a String with the best movies
     */
    public String ratingMovies() {
        // Fac un map care are toate videouri le care au primit rating si care au filrele il sortez
        Map<String, Double> videoFiltersRating = new HashMap<>();
        List<Movie> movies = getData().getMovies();
        for (Movie movie : movies) {
            if (movie.getAvgRating() != 0) {
                if (!Utils.filterMovie(movie, filterYear, filterGenre)) {
                    continue;
                }
                videoFiltersRating.put(movie.getTitle(), movie.getAvgRating());
            }
        }
        return Utils.sortedDoubleAlphabetical(videoFiltersRating, getSortType(), getNumber());
    }

    /**
     * Find the best getNumber() rated serials sorted by the SortType
     *
     * @return a String with the best serials
     */
    public String ratingSerials() {
        Map<String, Double> videoFiltersRating = new HashMap<>();
        List<Serial> serials = getData().getSerials();
        for (Serial serial : serials) {
            if (serial.getAvgRating() != 0) {
                if (!Utils.filterSerial(serial, filterYear, filterGenre)) {
                    continue;
                }
                videoFiltersRating.put(serial.getTitle(), serial.getAvgRating());
            }
        }
        return Utils.sortedDoubleAlphabetical(videoFiltersRating, getSortType(), getNumber());
    }

    /**
     * Find the getNumber() favorite movies sorted by the SortType
     *
     * @return a String with the favorite movies
     */
    public String favoriteMovies() {
        //pentru fiecare film setez sa vad de cate ori e cel mai favorit
        List<Movie> movies = getData().getMovies();

        Map<String, Integer> movieFiltersFavorite = new HashMap<>();
        // iau toate filmele si le filtrez si le pun intr un map cu nume si numarul de favoritati
        for (Movie movie : movies) {
            if (movie.getFavorite() != 0) {
                if (!Utils.filterMovie(movie, filterYear, filterGenre)) {
                    continue;
                }
                movieFiltersFavorite.put(movie.getTitle(), movie.getFavorite());
            }
        }
        //sortez map ul si dau lista
        return Utils.sortedIntegerAlphabetical(movieFiltersFavorite, getSortType(), getNumber());
    }

    /**
     * Find the getNumber() favorite serials sorted by the SortType
     *
     * @return a String with the favorite serials
     */
    public String favoriteSerials() {
        List<Serial> serials = getData().getSerials();
        Map<String, Integer> serialFiltersFavorite = new HashMap<>();
        for (Serial serial : serials) {
            if (serial.getFavorite() != 0) {
                if (!Utils.filterSerial(serial, filterYear, filterGenre)) {
                    continue;
                }
                serialFiltersFavorite.put(serial.getTitle(), serial.getFavorite());
            }
        }
        return Utils.sortedIntegerAlphabetical(serialFiltersFavorite, getSortType(), getNumber());
    }

    /**
     * Find the getNumber() longest movies sorted by the SortType
     *
     * @return a String with the longest movies
     */
    public String longestMovies() {
        List<Movie> movies = getData().getMovies();
        Map<String, Integer> movieFiltersDuration = new HashMap<>();
        for (Movie movie : movies) {
            if (!Utils.filterMovie(movie, filterYear, filterGenre)) {
                continue;
            }
            movieFiltersDuration.put(movie.getTitle(), movie.getDuration());
        }
        return Utils.sortedIntegerAlphabetical(movieFiltersDuration, getSortType(), getNumber());
    }

    /**
     * Find the getNumber() longest serials sorted by the SortType
     *
     * @return a String with the longest serials
     */
    public String longestSerials() {
        List<Serial> serials = getData().getSerials();
        Map<String, Integer> serialFiltersDuration = new HashMap<>();

        for (Serial serial : serials) {
            if (!Utils.filterSerial(serial, filterYear, filterGenre)) {
                continue;
            }
            serialFiltersDuration.put(serial.getTitle(), serial.getDuration());
        }

        return Utils.sortedIntegerAlphabetical(serialFiltersDuration, getSortType(), getNumber());
    }

    /**
     * Find the getNumber() most viewed movies sorted by the SortType
     *
     * @return a String with the most viewed movies
     */
    public String mostViewedMovies() {
        List<Movie> movies = getData().getMovies();
        Map<String, Integer> movieFiltersViewed = new HashMap<>();

        for (Movie movie : movies) {
            if (movie.getNrViews() != 0) {
                if (!Utils.filterMovie(movie, filterYear, filterGenre)) {
                    continue;
                }
                movieFiltersViewed.put(movie.getTitle(), movie.getNrViews());
            }
        }
        return Utils.sortedIntegerAlphabetical(movieFiltersViewed, getSortType(), getNumber());
    }

    /**
     * Find the getNumber() most viewed serials sorted by the SortType
     *
     * @return a String with the most viewed serials
     */
    public String mostViewedSerials() {
        List<Serial> serials = getData().getSerials();
        Map<String, Integer> serialsFiltersViewed = new HashMap<>();

        for (Serial serial : serials) {
            if (serial.getNrViews() != 0) {
                if (!Utils.filterSerial(serial, filterYear, filterGenre)) {
                    continue;
                }
                serialsFiltersViewed.put(serial.getTitle(), serial.getNrViews());
            }
        }
        return Utils.sortedIntegerAlphabetical(serialsFiltersViewed, getSortType(), getNumber());
    }
}
