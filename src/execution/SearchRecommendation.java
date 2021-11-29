package execution;

import entities.Movie;
import entities.Serial;
import main.Database;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class SearchRecommendation extends Recommendation {
    private final String genre;

    public SearchRecommendation(final Database data, final String username, final String genre) {
        super(data, username);
        this.genre = genre;
    }

    /**
     * Find the all the not viewed videos of an user sorted by rating
     *
     * @return a String with the not viewed videos
     */
    public String search() {
        if (getUser().getSubscriptionType().equals("BASIC")) {
            return ("PopularRecommendation cannot be applied!");
        }
        // Fac un LinkedHashMap cu numele si rating-ul Video-ului;
        LinkedHashMap<String, Double> videosRating = new LinkedHashMap<>();
        List<Movie> movies = getData().getMovies();
        for (Movie movie : movies) {
            if (movie.getGenres().contains(genre)) {
                videosRating.put(movie.getTitle(), movie.getAvgRating());
            }
        }
        List<Serial> serials = getData().getSerials();
        for (Serial serial : serials) {
            if (serial.getGenres().contains(genre)) {
                videosRating.put(serial.getTitle(), serial.getAvgRating());
            }
        }
        List<String> videoSorted = new ArrayList(videosRating.entrySet().stream().
                sorted(new Comparator<Map.Entry<String, Double>>() {
                    @Override
                    public int compare(final Map.Entry<String, Double> video1,
                                       final Map.Entry<String, Double> video2) {
                        if (video1.getValue() > video2.getValue()) {
                            return 1;
                        } else if (video1.getValue() < video2.getValue()) {
                            return -1;
                        }
                        return (video1.getKey().compareTo(video2.getKey()));
                    }
                }).map(Map.Entry::getKey).toList());

        Map<String, Integer> history = getUser().getHistory();
        videoSorted.removeIf(history::containsKey);
        if (videoSorted.size() == 0) {
            return ("SearchRecommendation cannot be applied!");
        }
        return ("SearchRecommendation result: " + videoSorted);
    }
}
