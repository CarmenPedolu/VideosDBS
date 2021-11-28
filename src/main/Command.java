package main;

import Entities.Movie;
import Entities.Serial;
import Entities.User;
import entertainment.Season;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Command {
    private Database data;
    private String type;
    private User user;
    private String title;
    private double grade;
    private int seasonNumber;

    public Command(Database data, String type, String username, String title) {
        this.data = data;
        this.type = type;
        this.title = title;
        this.grade = 0;
        this.seasonNumber = 0;
        List<User> users = data.getUsers();
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                this.user = user;
            }
        }
    }

    public Command(Database data, String type, String username, String title, double grade,
                   int seasonNumber) {
        this.data = data;
        this.type = type;
        this.title = title;
        this.grade = grade;
        this.seasonNumber = seasonNumber;
        List<User> users = data.getUsers();
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                this.user = user;
            }
        }
    }

    public Database getData() {
        return data;
    }

    public void setData(Database data) {
        this.data = data;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    public int getSeasonNumber() {
        return seasonNumber;
    }

    public void setSeasonNumber(int seasonNumber) {
        this.seasonNumber = seasonNumber;
    }

    public String Favorite() {
        //verific daca e deja adaugat movie-ul in lista de favourite a utilizatorului
        ArrayList<String> favMovies = user.getFavoriteMovies();
        for (String movie : favMovies) {
            if (movie.equals(title)) {
                return ("error -> " + title + " is already in favourite list");
            }
        }

        //verific daca filmul a fost vazut
        Map<String, Integer> history = user.getHistory();
        if (!history.containsKey(title)) {
            return ("error -> " + title + " is not seen");
        }

        ArrayList<String> all_favs = user.getFavoriteMovies();
        all_favs.add(this.title);
        this.user.setFavoriteMovies(all_favs);

        //incrementez numarul de cate ori e la favorite la un user
        List<Movie> movies = data.getMovies();
        for (Movie movie : movies) {
            if (movie.getTitle().equals(title)) {
                movie.setFavorite(movie.getFavorite() + 1);
            }
        }
        List<Serial> serials = data.getSerials();
        for (Serial serial : serials) {
            if (serial.getTitle().equals(title)) {
                serial.setFavorite(serial.getFavorite() + 1);
            }
        }
        return ("success -> " + title + " was added as favourite");
    }

    public String View() {
        Map<String, Integer> history = user.getHistory();
        List<Movie> movies = data.getMovies();
        for (Movie movie : movies) {
            if (movie.getTitle().equals(title)) {
                movie.setNrViews(movie.getNrViews() + 1);
                if (!history.containsKey(title)) {
                    history.put(title, 1);
                } else {
                    history.replace(title, history.get(title) + 1);
                }
                return ("success -> " + title + " was viewed with total views of " + history.get(this.title));
            }
        }

        List<Serial> serials = data.getSerials();
        for (Serial serial : serials) {
            if (serial.getTitle().equals(title)) {
                serial.setNrViews(serial.getNrViews() + 1);
                if (!history.containsKey(title)) {
                    history.put(title, 1);
                } else {
                    history.replace(title, history.get(title) + 1);
                }
                return ("success -> " + title + " was viewed with total views of " + history.get(this.title));
            }
        }
        return null;
    }

    public String Rating() {
        //verific daca e deja vazut
        Map<String, Integer> history = user.getHistory();
        if (!history.containsKey(title)) {
            return ("error -> " + title + " is not seen");
        }

        //verific sa nu fi dat rating deja
        ArrayList<String> rated_movies = user.getRated_movies();
        if (rated_movies != null) {
            for (String video : rated_movies) {
                if (video.equals(title)) {
                    return ("error -> " + title + " has been already rated");
                }
            }
        }

        Map<String, ArrayList<Integer>> rated_serials = user.getRated_serials();
        if (rated_serials != null) {
            for (Map.Entry<String, ArrayList<Integer>> entry : rated_serials.entrySet()) {
                ArrayList<Integer> seasons_rated = entry.getValue();
                if (seasons_rated != null) {
                    for (int season : seasons_rated) {
                        if ((entry.getKey().equals(title)) && season == seasonNumber) {
                            return ("error -> " + title + " has been already rated");
                        }
                    }
                }
            }
        }

        List<Movie> movies = data.getMovies();
        for (Movie movie : movies) {
            if (movie.getTitle().equals(title)) {
                ArrayList<Double> ratings = movie.getRating();
                if (ratings == null) {
                    ratings = new ArrayList<>();
                }
                ratings.add(grade);
                movie.setRating(ratings);
                double avg_rating = 0;
                for (double rate : ratings) {
                    avg_rating += rate;
                }
                avg_rating = avg_rating / ratings.size();
                movie.setAvgRating(avg_rating);
                // adaug la user ca a dat rating la film
                if (rated_movies == null) {
                    rated_movies = new ArrayList<>();
                }
                rated_movies.add(title);
                user.setRated_movies(rated_movies);
            }
        }

        List<Serial> serials = data.getSerials();
        for (Serial serial : serials) {
            if (serial.getTitle().equals(title)) {
                Season season = serial.getSeasons().get(seasonNumber - 1);
                List<Double> ratings_season = season.getRatings();
                if (ratings_season == null) {
                    ratings_season = new ArrayList<>();
                }
                ratings_season.add(grade);
                //calculez media pentru fiecare sezon si o pun intr-un array
                ArrayList<Double> avg_seasons = new ArrayList<>();
                //fac media array-ului
                for (Season every_season : serial.getSeasons()) {
                    List<Double> rating_season = season.getRatings();
                    double avg_season = 0;
                    for (double rate : rating_season) {
                        avg_season += rate;
                    }
                    avg_season = avg_season / serial.getSeasons().size();
                    avg_seasons.add(avg_season);
                }
                double avg_rating_serial = 0;
                for (double avg_rate : avg_seasons) {
                    avg_rating_serial += avg_rate;
                }
                serial.setAvgRating(avg_rating_serial / avg_seasons.size());
                //adaug la seriale la care user a dat rating
                if (rated_serials == null) {
                    rated_serials = new HashMap<>();
                }
                if (rated_serials.containsKey(title)) {
                    ArrayList<Integer> seasons_rated_title = rated_serials.get(title);
                    seasons_rated_title.add(seasonNumber);
                    rated_serials.remove(title);
                    rated_serials.put(title, seasons_rated_title);
                } else {
                    ArrayList<Integer> seasons_rated_title = new ArrayList<>();
                    seasons_rated_title.add(seasonNumber);
                    rated_serials.put(title, seasons_rated_title);
                }
                user.setRated_serials(rated_serials);
            }
        }
        user.setNr_ratings(user.getNr_ratings() + 1);
        return ("success -> " + title + " was rated with " + grade + " by " + user.getUsername());
    }
}
