package Entities;

import fileio.UserInputData;

import java.util.ArrayList;
import java.util.Map;

public class User {
    private String username;
    private String subscriptionType;
    private Map<String, Integer> history;
    private ArrayList<String> favoriteMovies;
    private ArrayList<String> rated_movies;
    private Map<String, ArrayList<Integer>> rated_serials;
    private int nr_ratings;

    public User(final String username, final String subscriptionType,
                final Map<String, Integer> history,
                final ArrayList<String> favoriteMovies) {
        this.username = username;
        this.subscriptionType = subscriptionType;
        this.favoriteMovies = favoriteMovies;
        this.history = history;
        this.rated_movies = null;
        this.rated_serials = null;
        this.nr_ratings = 0;
    }

    public User(UserInputData user) {
        this.username = user.getUsername();
        this.subscriptionType = user.getSubscriptionType();
        this.favoriteMovies = user.getFavoriteMovies();
        this.history = user.getHistory();
        this.rated_movies = null;
        this.rated_serials = null;
        this.nr_ratings = 0;
    }

    public String getUsername() {
        return username;
    }

    public Map<String, Integer> getHistory() {
        return history;
    }

    public String getSubscriptionType() {
        return subscriptionType;
    }

    public ArrayList<String> getFavoriteMovies() {
        return favoriteMovies;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setSubscriptionType(String subscriptionType) {
        this.subscriptionType = subscriptionType;
    }

    public void setHistory(Map<String, Integer> history) {
        this.history = history;
    }

    public void setFavoriteMovies(ArrayList<String> favoriteMovies) {
        this.favoriteMovies = favoriteMovies;
    }

    public ArrayList<String> getRated_movies() {
        return rated_movies;
    }

    public void setRated_movies(ArrayList<String> rated_movies) {
        this.rated_movies = rated_movies;
    }

    public Map<String, ArrayList<Integer>> getRated_serials() {
        return rated_serials;
    }

    public void setRated_serials(Map<String, ArrayList<Integer>> rated_serials) {
        this.rated_serials = rated_serials;
    }

    public int getNr_ratings() {
        return nr_ratings;
    }

    public void setNr_ratings(int nr_ratings) {
        this.nr_ratings = nr_ratings;
    }

    @Override
    public String toString() {
        return "UserInputData{" + "username='"
                + username + '\'' + ", subscriptionType='"
                + subscriptionType + '\'' + ", history="
                + history + ", favoriteMovies="
                + favoriteMovies + '}';
    }
}
