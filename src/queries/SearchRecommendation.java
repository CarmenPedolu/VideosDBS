package queries;

import Entities.Action;
import Entities.Movie;
import Entities.Serial;
import main.Database;

import java.util.*;

public class SearchRecommendation extends Recommendation {
    private String genre;

    public SearchRecommendation(Database data, String username, String genre) {
        super(data, username);
        this.genre = genre;
    }

    public String Search() {
        if (getUser().getSubscriptionType().equals("BASIC")) {
            return ("PopularRecommendation cannot be applied!");
        }
        //fac un LinkedHashMap cu nume si rating; LinkedHashMap ca sa adaug mereu la final
        LinkedHashMap<String, Double> videosRating = new LinkedHashMap<>();
        List<Movie> movies = getData().getMovies();
        for (Movie movie : movies) {
            if (movie.getGenres().contains(genre))
                videosRating.put(movie.getTitle(), movie.getAvgRating());
        }
        List<Serial> serials = getData().getSerials();
        for (Serial serial : serials) {
            if (serial.getGenres().contains(genre))
                videosRating.put(serial.getTitle(), serial.getAvgRating());
        }
        //sortez mapul
        List<String> videoSorted = new ArrayList(videosRating.entrySet().stream().sorted(new Comparator<Map.Entry<String, Double>>() {
            @Override
            public int compare(Map.Entry<String, Double> video1, Map.Entry<String, Double> video2) {
                if (video1.getValue() > video2.getValue()) {
                    return 1;
                } else if (video1.getValue() < video2.getValue()) {
                    return -1;
                }
                return (video1.getKey().compareTo(video2.getKey()));
            }
        }).map(Map.Entry::getKey).toList());

        //gasesc care e cel mai bun nevazut din lista
        Map<String, Integer> history = getUser().getHistory();
        videoSorted.removeIf(history::containsKey);
        if (videoSorted.size() == 0) {
            return ("SearchRecommendation cannot be applied!");
        }
        return ("SearchRecommendation result: " + videoSorted);
    }
}
