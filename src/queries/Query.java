package queries;

import Entities.Action;
import Entities.Actor;
import Entities.Movie;
import Entities.Serial;
import main.Database;

import javax.sound.midi.Soundbank;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Query {
    private Database data;
    private int number;
    private String sortType;
//    private int filter_year;
//    private String filter_genre;
//    private List<String> words;
//    private List<String> awards;


    public Query(Database database, Action action) {
        this.data = database;
        this.number = action.getNumber();
        this.sortType = action.getSortType();
//        String year = action.getFilters().get(0).get(0);
//        if (year != null) {
//            this.filter_year = Integer.parseInt(year);
//        } else {
//            this.filter_year = 0;
//        }
//
//        this.filter_genre = action.getFilters().get(1).get(0);
//        this.words = action.getFilters().get(2);
//        this.awards = action.getFilters().get(3);
    }

    public Database getData() {
        return data;
    }

    public void setData(Database data) {
        this.data = data;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getSortType() {
        return sortType;
    }

    public void setSortType(String sortType) {
        this.sortType = sortType;
    }

    public String AverageActor() {
        // Calculez media ratingurilor filmelor și a serialelor,
        // în care au jucat toti actorii
        List<Actor> actorsList = this.data.getActors();
        for (Actor actor : actorsList) {
            ArrayList<String> videos = actor.getFilmography();
            // pun toate ratingurile videourile in care a jucat actorul intr-un arraylist
            ArrayList<Double> ratings = new ArrayList<>();
            for (String video_title : videos) {
                // iau pe rand filmele apoi serialele, sa vad daca de care tip este
                for (Movie movie : data.getMovies()) {
                    if (movie.getTitle().equals(video_title)) {
                        if (movie.getAvgRating() != 0) {
                            ratings.add(movie.getAvgRating());
                        }
                    }
                }
                for (Serial serial : data.getSerials()) {
                    if (serial.getTitle().equals(video_title)) {
                        if (serial.getAvgRating() != 0) {
                            ratings.add(serial.getAvgRating());
                        }
                    }
                }
            }
            if (ratings.size() > 0) {
                double avg_rating_actor = 0;
                for (double avg_rate : ratings) {
                    avg_rating_actor += avg_rate;
                }
                actor.setAvg_rating(avg_rating_actor / ratings.size());
            }
        }
        //sortez ascendent
        actorsList.sort(new Comparator<Actor>() {
            @Override
            public int compare(Actor actor1, Actor actor2) {
                if (actor1.getAvg_rating() > actor2.getAvg_rating()) {
                    return 1;
                } else if (actor1.getAvg_rating() < actor2.getAvg_rating()) {
                    return -1;
                } else {
                    // au ratingul egal si sortez dupa nume
                    return (actor1.getName().compareTo(actor2.getName()));
                }
            }
        });
        if (this.sortType.equals("desc")) {
            Collections.reverse(actorsList);
        }
        ArrayList<String> query_average_actors = new ArrayList<>();
        // adaug toate numele care au ratingul diferit de 0 si le sterg pe cele in plus
        for (Actor actor : actorsList) {
            if (actor.getAvg_rating() != 0) {
                query_average_actors.add(actor.getName());
            }
        }
        while (query_average_actors.size() > this.number){
            query_average_actors.remove(query_average_actors.size() - 1);
        }
        return ("Query result: " + query_average_actors);
    }
}
