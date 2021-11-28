package queries;

import Entities.Action;
import Entities.Movie;
import Entities.Serial;
import Entities.User;
import main.Database;
import utils.Utils;

import java.util.*;

public class VideoQuery extends Query {
    private int filter_year;
    private String filter_genre;

    public VideoQuery(Database database, Action action) {
        super(database, action);
        String year = action.getFilters().get(0).get(0);
        if (year != null) {
            this.filter_year = Integer.parseInt(year);
        } else {
            this.filter_year = 0;
        }
        this.filter_genre = action.getFilters().get(1).get(0);
    }

    public int getFilter_year() {
        return filter_year;
    }

    public void setFilter_year(int filter_year) {
        this.filter_year = filter_year;
    }

    public String getFilter_genre() {
        return filter_genre;
    }

    public void setFilter_genre(String filter_genre) {
        this.filter_genre = filter_genre;
    }

    public String RatingMovies() {
        // Fac un map care are toate videouri le care au primit rating si care au filrele il sortez
        Map<String, Double> videoFiltersRating = new HashMap<>();

        List<Movie> movies = getData().getMovies();
        for (Movie movie : movies) {
            if (movie.getAvgRating() != 0) {
                if (this.filter_year != 0 && movie.getYear() != this.filter_year) {
                    continue;
                }
                if (this.filter_genre != null && !movie.getGenres().contains(this.filter_genre)) {
                    continue;
                }
                videoFiltersRating.put(movie.getTitle(), movie.getAvgRating());
            }
        }

        //Sortez map ul dupa rating, iar daca e egal, dupa nume
        videoFiltersRating.entrySet().stream().sorted(new Comparator<Map.Entry<String, Double>>() {
            @Override
            public int compare(Map.Entry<String, Double> video1, Map.Entry<String, Double> video2) {
                if (video1.getValue() > video2.getValue()) {
                    return 1;
                } else if (video1.getValue() < video2.getValue()) {
                    return -1;
                } else {
                    return (video1.getKey().compareTo(video2.getKey()));
                }
            }
        });
        ArrayList<String> sortedVideoRating = new ArrayList<>();
        for (Map.Entry<String, Double> entry : videoFiltersRating.entrySet()) {
            sortedVideoRating.add(entry.getKey());
        }
        if (getSortType().equals("desc")) {
            Collections.reverse(sortedVideoRating);
        }
        while (sortedVideoRating.size() > getNumber()) {
            sortedVideoRating.remove(sortedVideoRating.size() - 1);
        }
        return ("Query result: " + sortedVideoRating);
    }

    public String RatingSerials() {
        Map<String, Double> videoFiltersRating = new HashMap<>();
        List<Serial> serials = getData().getSerials();
        for (Serial serial : serials) {
            if (serial.getAvgRating() != 0) {
                if (this.filter_year != 0 && serial.getYear() != this.filter_year) {
                    continue;
                }
                if (this.filter_genre != null && !serial.getGenres().contains(this.filter_genre)) {
                    continue;
                }
                videoFiltersRating.put(serial.getTitle(), serial.getAvgRating());
            }
        }
        videoFiltersRating.entrySet().stream().sorted(new Comparator<Map.Entry<String, Double>>() {
            @Override
            public int compare(Map.Entry<String, Double> video1, Map.Entry<String, Double> video2) {
                if (video1.getValue() > video2.getValue()) {
                    return 1;
                } else if (video1.getValue() < video2.getValue()) {
                    return -1;
                } else {
                    return (video1.getKey().compareTo(video2.getKey()));
                }
            }
        });
        ArrayList<String> sortedVideoRating = new ArrayList<>();
        for (Map.Entry<String, Double> entry : videoFiltersRating.entrySet()) {
            sortedVideoRating.add(entry.getKey());
        }
        if (getSortType().equals("desc")) {
            Collections.reverse(sortedVideoRating);
        }
        while (sortedVideoRating.size() > getNumber()) {
            sortedVideoRating.remove(sortedVideoRating.size() - 1);
        }
        return ("Query result: " + sortedVideoRating);
    }

    public String FavoriteMovies() {
        //pentru fiecare film setez sa vad de cate ori e cel mai favorit
        List<Movie> movies = getData().getMovies();

        Map<String, Integer> movieFiltersFavorite = new HashMap<>();
        // iau toate filmele si le filtrez si le pun intr un map cu nume si nr de favoritati
        for (Movie movie : movies) {
            if (movie.getFavorite() != 0) {
                if (this.filter_year != 0 && movie.getYear() != this.filter_year) {
                    continue;
                }
                if (this.filter_genre != null && !movie.getGenres().contains(this.filter_genre)) {
                    continue;
                }
                movieFiltersFavorite.put(movie.getTitle(), movie.getFavorite());
            }
        }
        //sortez map ul si dau lista
        String message = Utils.sortedQuery(movieFiltersFavorite, getSortType(), getNumber());
        return message;
    }

    public String FavoriteSerials() {
        //pentru fiecare film setez sa vad de cate ori e cel mai favorit
        List<Serial> serials = getData().getSerials();

        Map<String, Integer> movieFiltersFavorite = new HashMap<>();
        // iau toate filmele si le filtrez si le pun intr un map cu nume si nr de favoritati
        for (Serial serial : serials) {
            if (serial.getFavorite() != 0) {
                if (this.filter_year != 0 && serial.getYear() != this.filter_year) {
                    continue;
                }
                if (this.filter_genre != null && !serial.getGenres().contains(this.filter_genre)) {
                    continue;
                }
                movieFiltersFavorite.put(serial.getTitle(), serial.getFavorite());
            }
        }
        //sortez map ul si dau lista
        String message = Utils.sortedQuery(movieFiltersFavorite, getSortType(), getNumber());
        return message;
    }

    public String LongestMovies() {
        List<Movie> movies = getData().getMovies();
        Map<String, Integer> movieFiltersDuration = new HashMap<>();
        // iau toate filmele si le filtrez si le pun intr un map cu nume si cat dureaza
        for (Movie movie : movies) {
            if (this.filter_year != 0 && movie.getYear() != this.filter_year) {
                continue;
            }
            if (this.filter_genre != null && !movie.getGenres().contains(this.filter_genre)) {
                continue;
            }
            movieFiltersDuration.put(movie.getTitle(), movie.getDuration());
        }
        String message = Utils.sortedQuery(movieFiltersDuration, getSortType(), getNumber());
        return message;
    }

    public String LongestSerials() {
        List<Serial> serials = getData().getSerials();
        Map<String, Integer> serialFiltersDuration = new HashMap<>();

        for (Serial serial : serials) {
            if (this.filter_year != 0 && serial.getYear() != this.filter_year) {
                continue;
            }
            if (this.filter_genre != null && !serial.getGenres().contains(this.filter_genre)) {
                continue;
            }
            serialFiltersDuration.put(serial.getTitle(), serial.getDuration());
        }

        //sortez map ul si dau lista
        String message = Utils.sortedQuery(serialFiltersDuration, getSortType(), getNumber());
        return message;
    }

    public String MostViewedMovies() {
        List<Movie> movies = getData().getMovies();
        Map<String, Integer> movieFiltersViewed = new HashMap<>();

        for (Movie movie : movies) {
            if (movie.getNrViews() != 0) {
                if (this.filter_year != 0 && movie.getYear() != this.filter_year) {
                    continue;
                }
                if (this.filter_genre != null && !movie.getGenres().contains(this.filter_genre)) {
                    continue;
                }
                movieFiltersViewed.put(movie.getTitle(), movie.getNrViews());
            }
        }
        //sortez map ul si dau lista
        String message = Utils.sortedQuery(movieFiltersViewed, getSortType(), getNumber());
        return message;
    }

    public String MostViewedSerials() {
        List<Serial> serials = getData().getSerials();
        Map<String, Integer> serialsFiltersViewed = new HashMap<>();

        for (Serial serial : serials) {
            if (serial.getNrViews() != 0) {
                if (this.filter_year != 0 && serial.getYear() != this.filter_year) {
                    continue;
                }
                if (this.filter_genre != null && !serial.getGenres().contains(this.filter_genre)) {
                    continue;
                }
                serialsFiltersViewed.put(serial.getTitle(), serial.getNrViews());
            }
        }
        //sortez map ul si dau lista
        String message = Utils.sortedQuery(serialsFiltersViewed, getSortType(), getNumber());
        return message;
    }
}
