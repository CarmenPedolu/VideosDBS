package execution;

import entities.Movie;
import entities.Serial;
import entities.User;
import main.Database;
import utils.Utils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Recommendation {
    private Database data;
    private User user;

    public Recommendation(final Database data, final String username) {
        this.data = data;
        List<User> users = data.getUsers();
        for (User currentUser : users) {
            if (currentUser.getUsername().equals(username)) {
                this.user = currentUser;
            }
        }
    }

    public final Database getData() {
        return data;
    }

    public final void setData(final Database data) {
        this.data = data;
    }

    public final User getUser() {
        return user;
    }

    public final void setUser(final User user) {
        this.user = user;
    }

    /**
     * Find the first not viewed video of an user
     *
     * @return a String with the not viewed video
     */
    public final String standard() {
        Map<String, Integer> history = user.getHistory();
        List<Movie> movies = data.getMovies();
        for (Movie movie : movies) {
            if (!history.containsKey(movie.getTitle())) {
                return ("StandardRecommendation result: " + movie.getTitle());
            }
        }
        List<Serial> serials = data.getSerials();
        for (Serial serial : serials) {
            if (!history.containsKey(serial.getTitle())) {
                return ("StandardRecommendation result: " + serial.getTitle());
            }
        }
        return ("StandardRecommendation cannot be applied!");
    }

    /**
     * Find the video which has the higher rating and is not viewed by the user
     *
     * @return a String with the not viewed video
     */
    public final String bestUnseen() {
        LinkedHashMap<String, Double> videosRating = new LinkedHashMap<>();
        List<Movie> movies = getData().getMovies();
        for (Movie movie : movies) {
            videosRating.put(movie.getTitle(), movie.getAvgRating());
        }
        List<Serial> serials = getData().getSerials();
        for (Serial serial : serials) {
            videosRating.put(serial.getTitle(), serial.getAvgRating());
        }
        List<String> videoSorted = Utils.sortedMapDescendentDouble(videosRating);
        //gasesc care e cel mai bun nevazut din lista
        Map<String, Integer> history = user.getHistory();
        for (String video : videoSorted) {
            if (!history.containsKey(video)) {
                return ("BestRatedUnseenRecommendation result: " + video);
            }
        }
        return ("BestRatedUnseenRecommendation cannot be applied!");
    }

    /**
     * Find the favorite video of the users which is not viewed by the user
     *
     * @return a String with the not viewed video
     */
    public final String favorite() {
        if (user.getSubscriptionType().equals("BASIC")) {
            return ("FavoriteRecommendation cannot be applied!");
        }
        //fac un LinkedHashMap cu nume si rating;
        LinkedHashMap<String, Integer> videosFav = new LinkedHashMap<>();
        List<Movie> movies = getData().getMovies();
        for (Movie movie : movies) {
            if (movie.getFavorite() != 0) {
                videosFav.put(movie.getTitle(), movie.getFavorite());
            }
        }
        List<Serial> serials = getData().getSerials();
        for (Serial serial : serials) {
            if (serial.getFavorite() != 0) {
                videosFav.put(serial.getTitle(), serial.getFavorite());
            }
        }
        List<String> videoSorted = Utils.sortedMapDescendentInteger(videosFav);
        Map<String, Integer> history = user.getHistory();
        for (String video : videoSorted) {
            if (!history.containsKey(video)) {
                return ("FavoriteRecommendation result: " + video);
            }
        }
        return ("FavoriteRecommendation cannot be applied!");
    }

    /**
     * Find the most popular video of the users which is not viewed by the user
     *
     * @return a String with the not viewed video
     */
    public final String popular() {
        if (user.getSubscriptionType().equals("BASIC")) {
            return ("PopularRecommendation cannot be applied!");
        }
        // Fac un LinkedHashMap cu numele genului si
        // numarul de vizualizari de video-uri din acel gen
        LinkedHashMap<String, Integer> allGenders = new LinkedHashMap<>();
        List<Movie> movies = getData().getMovies();
        for (Movie movie : movies) {
            ArrayList<String> genres = movie.getGenres();
            for (String gen : genres) {
                if (!allGenders.containsKey(gen)) {
                    allGenders.put(gen, movie.getNrViews());
                } else {
                    allGenders.replace(gen, allGenders.get(gen) + movie.getNrViews());
                }
            }
        }
        List<Serial> serials = getData().getSerials();
        for (Serial serial : serials) {
            ArrayList<String> genres = serial.getGenres();
            for (String gen : genres) {
                if (!allGenders.containsKey(gen)) {
                    allGenders.put(gen, serial.getNrViews());
                } else {
                    allGenders.replace(gen, allGenders.get(gen) + serial.getNrViews());
                }
            }
        }
        List<String> videoSorted = Utils.sortedMapDescendentInteger(allGenders);
        // Gasesc primul video nevazut din cel mai popular gen
        Map<String, Integer> history = user.getHistory();
        for (String gender : videoSorted) {
            for (Movie movie : movies) {
                if (movie.getGenres().contains(gender) && !history.containsKey(movie.getTitle())) {
                    return ("PopularRecommendation result: " + movie.getTitle());
                }
            }
            for (Serial serial : serials) {
                if (serial.getGenres().contains(gender)
                        && !history.containsKey(serial.getTitle())) {
                    return ("PopularRecommendation result: " + serial.getTitle());
                }
            }
        }
        return ("PopularRecommendation cannot be applied!");
    }
}
