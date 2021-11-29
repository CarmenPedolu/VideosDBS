package entities;

import fileio.UserInputData;

import java.util.ArrayList;
import java.util.Map;

public final class User {
    private String username;
    private String subscriptionType;
    private Map<String, Integer> history;
    private ArrayList<String> favoriteMovies;
    private ArrayList<String> ratedMovies;
    private Map<String, ArrayList<Integer>> ratedSerials;
    private int nrRatings;

    public User(final UserInputData user) {
        this.username = user.getUsername();
        this.subscriptionType = user.getSubscriptionType();
        this.favoriteMovies = user.getFavoriteMovies();
        this.history = user.getHistory();
        this.ratedMovies = null;
        this.ratedSerials = null;
        this.nrRatings = 0;
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

    public void setUsername(final String username) {
        this.username = username;
    }

    public void setSubscriptionType(final String subscriptionType) {
        this.subscriptionType = subscriptionType;
    }

    public void setHistory(final Map<String, Integer> history) {
        this.history = history;
    }

    public void setFavoriteMovies(final ArrayList<String> favoriteMovies) {
        this.favoriteMovies = favoriteMovies;
    }

    public ArrayList<String> getRatedMovies() {
        return ratedMovies;
    }

    public void setRatedMovies(final ArrayList<String> ratedMovies) {
        this.ratedMovies = ratedMovies;
    }

    public Map<String, ArrayList<Integer>> getRatedSerials() {
        return ratedSerials;
    }

    public void setRatedSerials(final Map<String, ArrayList<Integer>> ratedSerials) {
        this.ratedSerials = ratedSerials;
    }

    public int getNrRatings() {
        return nrRatings;
    }

    public void setNrRatings(final int nrRatings) {
        this.nrRatings = nrRatings;
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
