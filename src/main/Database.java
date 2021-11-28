package main;

import Entities.Actor;
import Entities.Movie;
import Entities.Serial;
import Entities.User;
import Entities.Action;
import checker.Checkstyle;
import checker.Checker;
import common.Constants;
import fileio.*;
import org.json.simple.JSONArray;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class  Database {

    private List<Actor> actors;
    private List<User> users;
    private List<Movie> movies;
    private List<Serial> serials;
    private List<Action> actions;

    public List<Actor> getActors() {
        return actors;
    }

    public void setActors(List<Actor> actors) {
        this.actors = actors;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    public List<Serial> getSerials() {
        return serials;
    }

    public void setSerials(List<Serial> serials) {
        this.serials = serials;
    }

    public List<Action> getActions() {
        return actions;
    }

    public void setActions(List<Action> actions) {
        this.actions = actions;
    }

//    public Database() {
//        this.actions = null;
//        this.movies = null;
//        this.serials = null;
//        this.users = null;
//        this.actors = null;
//    }

    public Database (Input input) {
        List<ActorInputData> actor_input = input.getActors();
        List<UserInputData> user_input = input.getUsers();
        List<MovieInputData> movie_input = input.getMovies();
        List<SerialInputData> serial_input = input.getSerials();
        List<ActionInputData> action_input = input.getCommands();

        //fac cate o lista pentru fiecare si dupa ii dau set la lista aia
        //deep copy sa iau element cu element
        List<Actor> actors = new ArrayList<>();
        for (ActorInputData actor : actor_input) {
            actors.add(new Actor(actor));
        }

        List<Movie> movies = new ArrayList<>();
        for (MovieInputData movie : movie_input) {
            movies.add(new Movie(movie));
        }

        List<Serial> serials = new ArrayList<>();
        for (SerialInputData serial : serial_input) {
            serials.add(new Serial(serial));
        }

        List<User> users = new ArrayList<>();
        for (UserInputData user : user_input) {
            //users.add(new User(user));
            User new_user = new User(user);
            //setez de cate ori apare fiecare movie sau serial la favorite la un user
            ArrayList<String> favorites = user.getFavoriteMovies();
            Map<String, Integer> history = user.getHistory();
            for (Movie movie : movies) {
                if (favorites.contains(movie.getTitle())) {
                    movie.setFavorite(movie.getFavorite() + 1);
                }
                if (history.containsKey(movie.getTitle())) {
                    movie.setNrViews(movie.getNrViews() + history.get(movie.getTitle()));
                }
            }
            for (Serial serial : serials) {
                if (favorites.contains(serial.getTitle())) {
                    serial.setFavorite(serial.getFavorite() + 1);
                }
                if (history.containsKey(serial.getTitle())) {
                    serial.setNrViews(serial.getNrViews() + history.get(serial.getTitle()));
                }
            }
            users.add(new_user);
        }

        List<Action> actions = new ArrayList<>();
        for (ActionInputData action : action_input) {
            actions.add(new Action(action));
           /* if (action.getActionType().equals("command")) {
                actions.add(new Action(action.getActionId(),
                        action.getActionType(), action.getType(),
                        action.getUsername(), action.getTitle(),
                        action.getGrade(), action.getSeasonNumber()));
            }
            if (action.getActionType().equals("query")) {
                System.out.println(action.getFilters().get(2));
                System.out.println(action.getActionId() + " " +action.getFilters().get(3));
                actions.add(new Action(action.getActionId(),
                        action.getActionType(), action.getObjectType(),
                        action.getFilters().get(1).get(0), action.getSortType(),
                        action.getCriteria(),
                        action.getFilters().get(0).get(0),
                        action.getNumber(), action.getFilters().get(2),
                        action.getFilters().get(3)));
                System.out.println(action.toString());
            }
            if (action.getActionType().equals("recommendation")) {
                actions.add(new Action(action.getActionId(),
                        action.getActionType(),action.getType(),
                        action.getUsername(),action.getGenre()));
            }*/
        }

        this.actions = actions;
        this.movies = movies;
        this.serials = serials;
        this.users = users;
        this.actors = actors;
    }

    public Database(List<Actor> actors, List<User> users, List<Movie> movies,
                    List<Serial> serials, List<Action> actions) {
        this.actions = actions;
        this.movies = movies;
        this.serials = serials;
        this.users = users;
        this.actors = actors;
    }

}
