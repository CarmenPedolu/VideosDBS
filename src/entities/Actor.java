package entities;

import actor.ActorsAwards;
import fileio.ActorInputData;

import java.util.ArrayList;
import java.util.Map;

public final class Actor {
    private String name;
    private final String careerDescription;
    private ArrayList<String> filmography;
    private Map<ActorsAwards, Integer> awards;
    private double avgRating;

    public Actor(final ActorInputData actor) {
        this.name = actor.getName();
        this.awards = actor.getAwards();
        this.careerDescription = actor.getCareerDescription();
        this.filmography = actor.getFilmography();
        this.avgRating = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public ArrayList<String> getFilmography() {
        return filmography;
    }

    public void setFilmography(final ArrayList<String> filmography) {
        this.filmography = filmography;
    }

    public Map<ActorsAwards, Integer> getAwards() {
        return awards;
    }

    public void setAwards(final Map<ActorsAwards, Integer> awards) {
        this.awards = awards;
    }

    public String getCareerDescription() {
        return careerDescription;
    }

    public double getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(final double avgRating) {
        this.avgRating = avgRating;
    }

    @Override
    public String toString() {
        return "ActorInputData{"
                + "name='" + name + '\''
                + ", careerDescription='"
                + careerDescription + '\''
                + ", filmography=" + filmography + '}';
    }
}
