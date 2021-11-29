package utils;

import actor.ActorsAwards;
import common.Constants;
import entertainment.Genre;
import entities.Movie;
import entities.Serial;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Collections;

/**
 * The class contains static methods that helps with parsing.
 * <p>
 * We suggest you add your static methods here or in a similar class.
 */
public final class Utils {
    /**
     * for coding style
     */
    private Utils() {
    }

    public static final int AWARDSINDEX = 3;

    /**
     * Transforms a string into an enum
     *
     * @param genre of video
     * @return an Genre Enum
     */
    public static Genre stringToGenre(final String genre) {
        return switch (genre.toLowerCase()) {
            case "action" -> Genre.ACTION;
            case "adventure" -> Genre.ADVENTURE;
            case "drama" -> Genre.DRAMA;
            case "comedy" -> Genre.COMEDY;
            case "crime" -> Genre.CRIME;
            case "romance" -> Genre.ROMANCE;
            case "war" -> Genre.WAR;
            case "history" -> Genre.HISTORY;
            case "thriller" -> Genre.THRILLER;
            case "mystery" -> Genre.MYSTERY;
            case "family" -> Genre.FAMILY;
            case "horror" -> Genre.HORROR;
            case "fantasy" -> Genre.FANTASY;
            case "science fiction" -> Genre.SCIENCE_FICTION;
            case "action & adventure" -> Genre.ACTION_ADVENTURE;
            case "sci-fi & fantasy" -> Genre.SCI_FI_FANTASY;
            case "animation" -> Genre.ANIMATION;
            case "kids" -> Genre.KIDS;
            case "western" -> Genre.WESTERN;
            case "tv movie" -> Genre.TV_MOVIE;
            default -> null;
        };
    }

    /**
     * Transforms a string into an enum
     *
     * @param award for actors
     * @return an ActorsAwards Enum
     */
    public static ActorsAwards stringToAwards(final String award) {
        return switch (award) {
            case "BEST_SCREENPLAY" -> ActorsAwards.BEST_SCREENPLAY;
            case "BEST_SUPPORTING_ACTOR" -> ActorsAwards.BEST_SUPPORTING_ACTOR;
            case "BEST_DIRECTOR" -> ActorsAwards.BEST_DIRECTOR;
            case "BEST_PERFORMANCE" -> ActorsAwards.BEST_PERFORMANCE;
            case "PEOPLE_CHOICE_AWARD" -> ActorsAwards.PEOPLE_CHOICE_AWARD;
            default -> null;
        };
    }

    /**
     * Transforms an array of JSON's into an array of strings
     *
     * @param array of JSONs
     * @return a list of strings
     */
    public static ArrayList<String> convertJSONArray(final JSONArray array) {
        if (array != null) {
            ArrayList<String> finalArray = new ArrayList<>();
            for (Object object : array) {
                finalArray.add((String) object);
            }
            return finalArray;
        } else {
            return null;
        }
    }

    /**
     * Transforms an array of JSON's into a map
     *
     * @param jsonActors array of JSONs
     * @return a map with ActorsAwardsa as key and Integer as value
     */
    public static Map<ActorsAwards, Integer> convertAwards(final JSONArray jsonActors) {
        Map<ActorsAwards, Integer> awards = new LinkedHashMap<>();

        for (Object iterator : jsonActors) {
            awards.put(stringToAwards((String) ((JSONObject) iterator).get(Constants.AWARD_TYPE)),
                    Integer.parseInt(((JSONObject) iterator).get(Constants.NUMBER_OF_AWARDS)
                            .toString()));
        }

        return awards;
    }

    /**
     * Transforms an array of JSON's into a map
     *
     * @param movies array of JSONs
     * @return a map with String as key and Integer as value
     */
    public static Map<String, Integer> watchedMovie(final JSONArray movies) {
        Map<String, Integer> mapVideos = new LinkedHashMap<>();

        if (movies != null) {
            for (Object movie : movies) {
                mapVideos.put((String) ((JSONObject) movie).get(Constants.NAME),
                        Integer.parseInt(((JSONObject) movie).get(Constants.NUMBER_VIEWS)
                                .toString()));
            }
        } else {
            System.out.println("NU ESTE VIZIONAT NICIUN FILM");
        }

        return mapVideos;
    }

    /**
     * Transforms a map which has the value a Integer to a list sorted by Value and Key
     *
     * @param map map with String as key and Integer as Value
     * @param sortType String for how to sort
     * @param nr Integer for how many elements the list should have
     * @return a message with the list
     */
    public static String sortedIntegerAlphabetical(final Map<String, Integer> map,
                                                   final String sortType, final int nr) {
        //Sortez map ul dupa rating, iar daca e egal, dupa nume
        List<String> sortedMap = new ArrayList(map.entrySet().stream().
                sorted(new Comparator<Map.Entry<String, Integer>>() {
                    @Override
                    public int compare(final Map.Entry<String, Integer> elem1,
                                       final Map.Entry<String, Integer> elem2) {
                        if (elem1.getValue() > elem2.getValue()) {
                            return 1;
                        } else if (elem1.getValue() < elem2.getValue()) {
                            return -1;
                        } else {
                            return (elem1.getKey().compareTo(elem2.getKey()));
                        }
                    }
                }).map(Map.Entry::getKey).toList());
        if (sortType.equals("desc")) {
            Collections.reverse(sortedMap);
        }
        while (sortedMap.size() > nr) {
            sortedMap.remove(sortedMap.size() - 1);
        }
        return ("Query result: " + sortedMap);
    }

    /**
     * Transforms a map which has the value a Double to a list sorted by Value and Key
     *
     * @param map map with String as key and Double as Value
     * @param sortType String for how to sort
     * @param nr Integer for how many elements the list should have
     * @return a message with the list
     */
    public static String sortedDoubleAlphabetical(final Map<String, Double> map,
                                                  final String sortType, final int nr) {
        //Sortez map ul dupa rating, iar daca e egal, dupa nume
        List<String> sortedMap = new ArrayList(map.entrySet().stream().
                sorted(new Comparator<Map.Entry<String, Double>>() {
                    @Override
                    public int compare(final Map.Entry<String, Double> elem1,
                                       final Map.Entry<String, Double> elem2) {
                        if (elem1.getValue() > elem2.getValue()) {
                            return 1;
                        } else if (elem1.getValue() < elem2.getValue()) {
                            return -1;
                        } else {
                            return (elem1.getKey().compareTo(elem2.getKey()));
                        }
                    }
                }).map(Map.Entry::getKey).toList());
        if (sortType.equals("desc")) {
            Collections.reverse(sortedMap);
        }
        while (sortedMap.size() > nr) {
            sortedMap.remove(sortedMap.size() - 1);
        }
        return ("Query result: " + sortedMap);
    }

    /**
     * Transforms a map which has the Integer as value to a list sorted by Value
     *
     * @param map map with String as key and Integer as Value
     * @return a list of String
     */
    public static List<String> sortedMapDescendentInteger(final LinkedHashMap<String,
            Integer> map) {
        List<String> mapSorted = new ArrayList(map.entrySet().stream().
                sorted(new Comparator<Map.Entry<String, Integer>>() {
                    @Override
                    public int compare(final Map.Entry<String, Integer> elem1,
                                       final Map.Entry<String, Integer> elem2) {
                        if (elem1.getValue() > elem2.getValue()) {
                            return -1;
                        } else if (elem1.getValue() < elem2.getValue()) {
                            return 1;
                        }
                        return 0;
                    }
                }).map(Map.Entry::getKey).toList());
        return mapSorted;
    }

    /**
     * Transforms a map which has the Double as value to a list sorted by Value
     *
     * @param map map with String as key and Double as Value
     * @return a list of String
     */
    public static List<String> sortedMapDescendentDouble(final LinkedHashMap<String, Double> map) {
        List<String> mapSorted = new ArrayList(map.entrySet().stream().
                sorted(new Comparator<Map.Entry<String, Double>>() {
                    @Override
                    public int compare(final Map.Entry<String, Double> elem1,
                                       final Map.Entry<String, Double> elem2) {
                        if (elem1.getValue() > elem2.getValue()) {
                            return -1;
                        } else if (elem1.getValue() < elem2.getValue()) {
                            return 1;
                        }
                        return 0;
                    }
                }).map(Map.Entry::getKey).toList());
        return mapSorted;
    }

    /**
     * @param movie a Movie
     * @param year Integer in which the movie should be launch
     * @param genre String which contains should contains one of the genres of the movie
     * @return true if the movie respects the condition, or false if not
     */
    public static boolean filterMovie(final Movie movie, final int year, final String genre) {
        if (year != 0 && movie.getYear() != year) {
            return false;
        }
        return genre == null || movie.getGenres().contains(genre);
    }

    /**
     * @param serial a Serial
     * @param year Integer in which the movie should be launch
     * @param genre String which contains should contains one of the genres of the serial
     * @return true if the serial respects the condition, or false if not
     */
    public static boolean filterSerial(final Serial serial, final int year, final String genre) {
        if (year != 0 && serial.getYear() != year) {
            return false;
        }
        return genre == null || serial.getGenres().contains(genre);
    }
}
