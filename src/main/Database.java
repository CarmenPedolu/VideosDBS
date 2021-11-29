package main;

import entities.Action;
import entities.Actor;
import entities.Movie;
import entities.Serial;
import entities.User;

import fileio.Input;
import fileio.ActionInputData;
import fileio.ActorInputData;
import fileio.MovieInputData;
import fileio.SerialInputData;
import fileio.UserInputData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public final class Database {
    private List<Actor> actors;
    private List<User> users;
    private List<Movie> movies;
    private List<Serial> serials;
    private List<Action> actions;

    public List<Actor> getActors() {
        return actors;
    }

    public void setActors(final List<Actor> actors) {
        this.actors = actors;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(final List<User> users) {
        this.users = users;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(final List<Movie> movies) {
        this.movies = movies;
    }

    public List<Serial> getSerials() {
        return serials;
    }

    public void setSerials(final List<Serial> serials) {
        this.serials = serials;
    }

    public List<Action> getActions() {
        return actions;
    }

    public void setActions(final List<Action> actions) {
        this.actions = actions;
    }

    public Database(final Input input) {
        List<ActorInputData> actorInput = input.getActors();
        List<UserInputData> userInput = input.getUsers();
        List<MovieInputData> movieInput = input.getMovies();
        List<SerialInputData> serialInput = input.getSerials();
        List<ActionInputData> actionInput = input.getCommands();

        List<Actor> actorsList = new ArrayList<>();
        for (ActorInputData actor : actorInput) {
            actorsList.add(new Actor(actor));
        }

        List<Movie> moviesList = new ArrayList<>();
        for (MovieInputData movie : movieInput) {
            moviesList.add(new Movie(movie));
        }

        List<Serial> serialsList = new ArrayList<>();
        for (SerialInputData serial : serialInput) {
            serialsList.add(new Serial(serial));
        }

        List<User> usersList = new ArrayList<>();
        for (UserInputData user : userInput) {
            User newUser = new User(user);
            // Setez de cate ori apare fiecare movie sau serial la favorite la un user
            // Setez numarul de vizualizari la fiecare movie sau serial
            ArrayList<String> favorites = user.getFavoriteMovies();
            Map<String, Integer> history = user.getHistory();
            for (Movie movie : moviesList) {
                if (favorites.contains(movie.getTitle())) {
                    movie.setFavorite(movie.getFavorite() + 1);
                }
                if (history.containsKey(movie.getTitle())) {
                    movie.setNrViews(movie.getNrViews() + history.get(movie.getTitle()));
                }
            }
            for (Serial serial : serialsList) {
                if (favorites.contains(serial.getTitle())) {
                    serial.setFavorite(serial.getFavorite() + 1);
                }
                if (history.containsKey(serial.getTitle())) {
                    serial.setNrViews(serial.getNrViews() + history.get(serial.getTitle()));
                }
            }
            usersList.add(newUser);
        }

        List<Action> actionsList = new ArrayList<>();
        for (ActionInputData action : actionInput) {
            actionsList.add(new Action(action));
        }

        this.actions = actionsList;
        this.movies = moviesList;
        this.serials = serialsList;
        this.users = usersList;
        this.actors = actorsList;
    }
}
