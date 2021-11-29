package queries;

import Entities.Movie;
import Entities.Serial;
import Entities.User;
import main.Database;

import java.util.*;

public class Recommendation {
        private Database data;
        private User user;

        public Recommendation(Database data, String username) {
            this.data = data;
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

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }

        public String Standard() {
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

        public String BestUnseen() {
            //fac un LinkedHashMap cu nume si rating; LinkedHashMap ca sa adaug mereu la final
            LinkedHashMap<String, Double> videosRating = new LinkedHashMap<>();
            List<Movie> movies = getData().getMovies();
            for (Movie movie : movies) {
                videosRating.put(movie.getTitle(), movie.getAvgRating());
            }
            List<Serial> serials = getData().getSerials();
            for (Serial serial : serials) {
                videosRating.put(serial.getTitle(), serial.getAvgRating());
            }
            //sortez mapul
            List<String> videoSorted = new ArrayList(videosRating.entrySet().stream().sorted(new Comparator<Map.Entry<String, Double>>() {
                @Override
                public int compare(Map.Entry<String, Double> video1, Map.Entry<String, Double> video2) {
                    if (video1.getValue() > video2.getValue()) {
                        return -1;
                    } else if (video1.getValue() < video2.getValue()) {
                        return 1;
                    }
                    return 0;
                }
            }).map(Map.Entry::getKey).toList());

            //gasesc care e cel mai bun nevazut din lista
            Map<String, Integer> history = user.getHistory();
            for (String video : videoSorted) {
                if (!history.containsKey(video)) {
                    return ("BestRatedUnseenRecommendation result: " + video);
                }
            }
            return ("BestRatedUnseenRecommendation cannot be applied!");
        }

        public String Favorite() {
            if (user.getSubscriptionType().equals("BASIC")) {
                return ("FavoriteRecommendation cannot be applied!");
            }
            //fac un LinkedHashMap cu nume si rating; LinkedHashMap ca sa adaug mereu la final
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
            //sortez mapul
            List<String> videoSorted = new ArrayList(videosFav.entrySet().stream().sorted(new Comparator<Map.Entry<String, Integer>>() {
                @Override
                public int compare(Map.Entry<String, Integer> video1, Map.Entry<String, Integer> video2) {
                    if (video1.getValue() > video2.getValue()) {
                        return -1;
                    } else if (video1.getValue() < video2.getValue()) {
                        return 1;
                    }
                    return 0;
                }
            }).map(Map.Entry::getKey).toList());
            Map<String, Integer> history = user.getHistory();
            for (String video : videoSorted) {
                if (!history.containsKey(video)) {
                    return ("FavoriteRecommendation result: " + video);
                }
            }
            return ("FavoriteRecommendation cannot be applied!");
        }

        public String Popular() {
            if (user.getSubscriptionType().equals("BASIC")) {
                return ("PopularRecommendation cannot be applied!");
            }
            Map<String, Integer> allGenders = new HashMap<>();
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
            // sortez map ul descrescator in functie de nr de vizualizari(value)
            List<String> videoSorted = new ArrayList(allGenders.entrySet().stream().sorted(new Comparator<Map.Entry<String, Integer>>() {
                @Override
                public int compare(Map.Entry<String, Integer> video1, Map.Entry<String, Integer> video2) {
                    if (video1.getValue() > video2.getValue()) {
                        return -1;
                    } else if (video1.getValue() < video2.getValue()) {
                        return 1;
                    }
                    return 0;
                }
            }).map(Map.Entry::getKey).toList());
            // gasesc primul video nevizualizat de primul tip
            Map<String, Integer> history = user.getHistory();
            for (String gender : videoSorted) {
                for (Movie movie : movies) {
                    if (movie.getGenres().contains(gender) && history.containsKey(movie.getTitle()) == false) {
                        return ("PopularRecommendation result: " + movie.getTitle());
                    }
                }
                for (Serial serial : serials) {
                    if (serial.getGenres().contains(gender) && history.containsKey(serial.getTitle()) == false) {
                        return ("PopularRecommendation result: " + serial.getTitle());
                    }
                }
            }
            return ("PopularRecommendation cannot be applied!");
        }
    }
