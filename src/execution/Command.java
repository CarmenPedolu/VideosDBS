package execution;

import entities.Movie;
import entities.Serial;
import entities.User;
import entertainment.Season;
import main.Database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Command {
    private Database data;
    private String type;
    private User user;
    private String title;
    private double grade;
    private final int seasonNumber;

    public Command(final Database data, final String type, final String username,
                   final String title, final double grade,
                   final int seasonNumber) {
        this.data = data;
        this.type = type;
        this.title = title;
        this.grade = grade;
        this.seasonNumber = seasonNumber;
        List<User> users = data.getUsers();
        for (User currentUser : users) {
            if (currentUser.getUsername().equals(username)) {
                this.user = currentUser;
            }
        }
    }

    public Database getData() {
        return data;
    }

    public void setData(final Database data) {
        this.data = data;
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public User getUser() {
        return user;
    }

    public void setUser(final User user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(final double grade) {
        this.grade = grade;
    }


    /**
     * Add a film or a movie at favorite
     *
     * @return a String with a message resulted by the command
     */
    public String favorite() {
        // Verific daca e deja adaugat movie-ul in lista de favourite a utilizatorului
        ArrayList<String> favMovies = user.getFavoriteMovies();
        for (String movie : favMovies) {
            if (movie.equals(title)) {
                return ("error -> " + title + " is already in favourite list");
            }
        }

        // Verific daca video-ul a fost vazut
        Map<String, Integer> history = user.getHistory();
        if (!history.containsKey(title)) {
            return ("error -> " + title + " is not seen");
        }

        // Adaug in lista de favorite la un user
        ArrayList<String> allFavs = user.getFavoriteMovies();
        allFavs.add(title);
        user.setFavoriteMovies(allFavs);

        // Incrementez numarul de cate ori e la favorite la un user
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

    /**
     * Set a video as viewed
     *
     * @return a String with the message for the finalization of the command
     */
    public String view() {
        // Incrementez numarul de vizionari al video-ului pentru user
        Map<String, Integer> history = user.getHistory();
        List<Movie> movies = data.getMovies();
        for (Movie movie : movies) {
            if (movie.getTitle().equals(title)) {
                // Incrementez numarul de vizualizari al filmului
                movie.setNrViews(movie.getNrViews() + 1);
                if (!history.containsKey(title)) {
                    history.put(title, 1);
                } else {
                    history.replace(title, history.get(title) + 1);
                }
                return ("success -> " + title + " was viewed with total views of "
                        + history.get(title));
            }
        }

        List<Serial> serials = data.getSerials();
        for (Serial serial : serials) {
            if (serial.getTitle().equals(title)) {
                // Incrementez numarul de vizualizari al serialului
                serial.setNrViews(serial.getNrViews() + 1);
                if (!history.containsKey(title)) {
                    history.put(title, 1);
                } else {
                    history.replace(title, history.get(title) + 1);
                }
                return ("success -> " + title + " was viewed with total views of "
                        + history.get(title));
            }
        }
        return null;
    }

    /**
     * Set the rating of a video
     *
     * @return a String with the message for the finalization of the command
     */
    public String rating() {
        // Verific daca e deja vazut
        Map<String, Integer> history = user.getHistory();
        if (!history.containsKey(title)) {
            return ("error -> " + title + " is not seen");
        }
        // Verific ca userul sa nu fi dat rating deja
        ArrayList<String> ratedMovies = user.getRatedMovies();
        if (ratedMovies != null) {
            for (String video : ratedMovies) {
                if (video.equals(title)) {
                    return ("error -> " + title + " has been already rated");
                }
            }
        }
        Map<String, ArrayList<Integer>> ratedSerials = user.getRatedSerials();
        if (ratedSerials != null) {
            for (Map.Entry<String, ArrayList<Integer>> entry : ratedSerials.entrySet()) {
                ArrayList<Integer> seasonsRated = entry.getValue();
                if (seasonsRated != null) {
                    for (int season : seasonsRated) {
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
                // Fac un arraylist cu toate ratingurile filmului si il adaug si pe acesta
                ArrayList<Double> ratings = movie.getRating();
                if (ratings == null) {
                    ratings = new ArrayList<>();
                }
                ratings.add(grade);
                movie.setRating(ratings);
                // Calculez media ratingurilor si o setez
                double avgRating = 0;
                for (double rate : ratings) {
                    avgRating += rate;
                }
                avgRating = avgRating / ratings.size();
                movie.setAvgRating(avgRating);
                // Adaug la user ca a dat rating la acest film
                if (ratedMovies == null) {
                    ratedMovies = new ArrayList<>();
                }
                ratedMovies.add(title);
                user.setRatedMovies(ratedMovies);
            }
        }

        List<Serial> serials = data.getSerials();
        for (Serial serial : serials) {
            if (serial.getTitle().equals(title)) {
                // Iau ratingurile sezonului curent
                Season currentSeason = serial.getSeasons().get(seasonNumber - 1);
                List<Double> ratingsCurrentSeason = currentSeason.getRatings();
                if (ratingsCurrentSeason == null) {
                    ratingsCurrentSeason = new ArrayList<>();
                }
                ratingsCurrentSeason.add(grade);
                // Calculez media sezonului curent si o adaug intr-un array
                // ca sa fac media tuturor sezoanelor
                ArrayList<Double> avgSeasons = new ArrayList<>();
                double avgCurrentSeason = 0;
                for (double rate : ratingsCurrentSeason) {
                    avgCurrentSeason += rate;
                }
                avgCurrentSeason = avgCurrentSeason / serial.getSeasons().size();
                avgSeasons.add(avgCurrentSeason);
                // Calculez media serialului
                double avgRatingSerial = 0;
                for (double avgRate : avgSeasons) {
                    avgRatingSerial += avgRate;
                }
                serial.setAvgRating(avgRatingSerial / avgSeasons.size());
                // Adaug la seriale la care user a dat rating
                if (ratedSerials == null) {
                    ratedSerials = new HashMap<>();
                }
                ArrayList<Integer> seasonsRatedTitle = ratedSerials.get(title);
                if (seasonsRatedTitle == null) {
                    seasonsRatedTitle = new ArrayList<>();
                }
                seasonsRatedTitle.add(seasonNumber);
                ratedSerials.remove(title);
                ratedSerials.put(title, seasonsRatedTitle);
                user.setRatedSerials(ratedSerials);
            }
        }
        user.setNrRatings(user.getNrRatings() + 1);
        return ("success -> " + title + " was rated with " + grade + " by " + user.getUsername());
    }
}
