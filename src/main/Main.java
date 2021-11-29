package main;

import entities.Action;

import checker.Checkstyle;
import checker.Checker;
import common.Constants;

import fileio.Input;
import fileio.InputLoader;
import fileio.Writer;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import execution.AwardsQuery;
import execution.Command;
import execution.DescriptQuery;
import execution.Query;
import execution.Recommendation;
import execution.SearchRecommendation;
import execution.UserRatingQuery;
import execution.VideoQuery;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * The entry point to this homework. It runs the checker that tests your implentation.
 */
public final class Main {
    /**
     * for coding style
     */
    private Main() {
    }

    /**
     * Call the main checker and the coding style checker
     *
     * @param args from command line
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void main(final String[] args) throws IOException {
        File directory = new File(Constants.TESTS_PATH);
        Path path = Paths.get(Constants.RESULT_PATH);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }

        File outputDirectory = new File(Constants.RESULT_PATH);

        Checker checker = new Checker();
        checker.deleteFiles(outputDirectory.listFiles());

        for (File file : Objects.requireNonNull(directory.listFiles())) {

            String filepath = Constants.OUT_PATH + file.getName();
            File out = new File(filepath);
            boolean isCreated = out.createNewFile();
            if (isCreated) {
                action(file.getAbsolutePath(), filepath);
            }
        }

        checker.iterateFiles(Constants.RESULT_PATH, Constants.REF_PATH, Constants.TESTS_PATH);
        Checkstyle test = new Checkstyle();
        test.testCheckstyle();
    }

    /**
     * @param filePath1 for input file
     * @param filePath2 for output file
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void action(final String filePath1,
                              final String filePath2) throws IOException {
        InputLoader inputLoader = new InputLoader(filePath1);
        Input input = inputLoader.readData();

        Writer fileWriter = new Writer(filePath2);
        JSONArray arrayResult = new JSONArray();

        Database data = new Database(input);
        String message = null;
        for (Action action : data.getActions()) {
            if (action.getActionType().equals("command")) {
                Command command = new Command(data, action.getType(),
                        action.getUsername(), action.getTitle(),
                        action.getGrade(), action.getSeasonNumber());
                if (action.getType().equals("favorite")) {
                    message = command.favorite();
                }
                if (action.getType().equals("view")) {
                    message = command.view();
                }
                if (action.getType().equals("rating")) {
                    message = command.rating();
                }
            }
            if (action.getActionType().equals("query")) {
                if (action.getObjectType().equals("actors")) {
                    if (action.getCriteria().equals("average")) {
                        Query actorQuery = new Query(data, action);
                        message = actorQuery.averageActor();
                    }
                    if (action.getCriteria().equals("awards")) {
                        AwardsQuery awardsActors = new AwardsQuery(data, action);
                        message = awardsActors.awards();
                    }
                    if (action.getCriteria().equals("filter_description")) {
                        DescriptQuery descriptionActors = new DescriptQuery(data, action);
                        message = descriptionActors.description();
                    }
                }
                if (action.getObjectType().equals("users")
                        && action.getCriteria().equals("num_ratings")) {
                    UserRatingQuery users = new UserRatingQuery(data, action);
                    message = users.ratingUsers();
                }
                if (action.getCriteria().equals("ratings")) {
                    VideoQuery videoRatings = new VideoQuery(data, action);
                    if (action.getObjectType().equals("movies")) {
                        message = videoRatings.ratingMovies();
                    } else {
                        message = videoRatings.ratingSerials();
                    }
                }
                if (action.getCriteria().equals("favorite")) {
                    VideoQuery video = new VideoQuery(data, action);
                    if (action.getObjectType().equals("movies")) {
                        message = video.favoriteMovies();
                    } else {
                        message = video.favoriteSerials();
                    }
                }
                if (action.getCriteria().equals("longest")) {
                    VideoQuery video = new VideoQuery(data, action);
                    if (action.getObjectType().equals("movies")) {
                        message = video.longestMovies();
                    } else {
                        message = video.longestSerials();
                    }
                }
                if (action.getCriteria().equals("most_viewed")) {
                    VideoQuery video = new VideoQuery(data, action);
                    if (action.getObjectType().equals("movies")) {
                        message = video.mostViewedMovies();
                    } else {
                        message = video.mostViewedSerials();
                    }
                }
            }
            if (action.getActionType().equals("recommendation")) {
                if (action.getType().equals("search")) {
                    SearchRecommendation recommand = new SearchRecommendation(data,
                            action.getUsername(), action.getGenre());
                    message = recommand.search();
                } else {
                    Recommendation recommand = new Recommendation(data, action.getUsername());
                    if (action.getType().equals("standard")) {
                        message = recommand.standard();
                    }
                    if (action.getType().equals("best_unseen")) {
                        message = recommand.bestUnseen();
                    }
                    if (action.getType().equals("favorite")) {
                        message = recommand.favorite();
                    }
                    if (action.getType().equals("popular")) {
                        message = recommand.popular();
                    }
                }
            }
            JSONObject object = fileWriter.writeFile(action.getActionId(), null, message);
            arrayResult.add(object);
        }
        fileWriter.closeJSON(arrayResult);
    }
}
