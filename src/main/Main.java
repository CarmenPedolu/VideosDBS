package main;

import Entities.*;
import checker.Checkstyle;
import checker.Checker;
import common.Constants;
import fileio.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import queries.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
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

        //TODO add here the entry point to your implementation
        //System.out.println(input.getCommands().get(0));
        List<ActionInputData> list = input.getCommands();
//        for (ActionInputData actionnn : list) {
//            if (actionnn.getActionType().equals("query"))
//            System.out.println(actionnn.getFilters().get(2));
//        }
        Database data = new Database(input);
        String message;
        for (Action action : data.getActions()) {
            if (action.getActionType().equals("command")) {
                Command command = new Command(data, action.getType(),
                        action.getUsername(), action.getTitle(),
                        action.getGrade(), action.getSeasonNumber());
                if (action.getType().equals("favorite")) {
                    message = command.Favorite();
                    JSONObject object = fileWriter.writeFile(action.getActionId(), null, message);
                    arrayResult.add(object);
                }
                if (action.getType().equals("view")) {
                    message = command.View();
                    JSONObject object = fileWriter.writeFile(action.getActionId(), null, message);
                    arrayResult.add(object);
                }
                if (action.getType().equals("rating")) {
                    message = command.Rating();
                    JSONObject object = fileWriter.writeFile(action.getActionId(), null, message);
                    arrayResult.add(object);
                }
            }
            if (action.getActionType().equals("query")) {
                if (action.getObjectType().equals("actors")) {
                    if (action.getCriteria().equals("average")) {
                        Query actor_query = new Query(data, action);
                        message = actor_query.AverageActor();
                        JSONObject object = fileWriter.writeFile(action.getActionId(), null, message);
                        arrayResult.add(object);
                    }
                    if (action.getCriteria().equals("awards")) {
                        ActorAwardsQuery awards_actors = new ActorAwardsQuery(data, action);
                        message = awards_actors.Awards();
                        JSONObject object = fileWriter.writeFile(action.getActionId(), null, message);
                        arrayResult.add(object);
                    }
                    if (action.getCriteria().equals("filter_description")) {
                        ActorDescriptionQuery description_actors = new ActorDescriptionQuery(data, action);
                        message = description_actors.Description();
                        JSONObject object = fileWriter.writeFile(action.getActionId(), null, message);
                        arrayResult.add(object);
                    }
                }
                if (action.getObjectType().equals("users") && action.getCriteria().equals("num_ratings")) {
                    UserRatingQuery users = new UserRatingQuery(data, action);
                    message = users.Rating_Users();
                    JSONObject object = fileWriter.writeFile(action.getActionId(), null, message);
                    arrayResult.add(object);
                }
                if (action.getCriteria().equals("ratings")) {
                    VideoQuery videoRatings = new VideoQuery(data, action);
                    if (action.getObjectType().equals("movies")) {
                        message = videoRatings.RatingMovies();
                    } else {
                        message = videoRatings.RatingSerials();
                    }
                    JSONObject object = fileWriter.writeFile(action.getActionId(), null, message);
                    arrayResult.add(object);
                }
                if (action.getCriteria().equals("favorite")) {
                    VideoQuery video = new VideoQuery(data, action);
                    if (action.getObjectType().equals("movies")) {
                        message = video.FavoriteMovies();
                    } else {
                        message = video.FavoriteSerials();
                    }
                    JSONObject object = fileWriter.writeFile(action.getActionId(), null, message);
                    arrayResult.add(object);
                }
                if (action.getCriteria().equals("longest")) {
                    VideoQuery video = new VideoQuery(data, action);
                    if (action.getObjectType().equals("movies")) {
                        message = video.LongestMovies();
                    } else {
                        message = video.LongestSerials();
                    }
                    JSONObject object = fileWriter.writeFile(action.getActionId(), null, message);
                    arrayResult.add(object);
                }
                if (action.getCriteria().equals("most_viewed")) {
                    VideoQuery video = new VideoQuery(data, action);
                    if (action.getObjectType().equals("movies")) {
                        message = video.MostViewedMovies();
                    } else {
                        message = video.MostViewedSerials();
                    }
                    JSONObject object = fileWriter.writeFile(action.getActionId(), null, message);
                    arrayResult.add(object);
                }
            }
            if (action.getActionType().equals("recommendation")) {
                if (action.getType().equals("standard")) {
                    Recommendation recommand = new Recommendation(data, action.getUsername());
                    message = recommand.Standard();
                    JSONObject object = fileWriter.writeFile(action.getActionId(), null, message);
                    arrayResult.add(object);
                }
                if (action.getType().equals("best_unseen")) {
                    Recommendation recommand = new Recommendation(data, action.getUsername());
                    message = recommand.BestUnseen();
                    JSONObject object = fileWriter.writeFile(action.getActionId(), null, message);
                    arrayResult.add(object);
                }
                if (action.getType().equals("favorite")) {
                    Recommendation recommand = new Recommendation(data, action.getUsername());
                    message = recommand.Favorite();
                    JSONObject object = fileWriter.writeFile(action.getActionId(), null, message);
                    arrayResult.add(object);
                }
                if (action.getType().equals("search")) {
                    SearchRecommendation recommand = new SearchRecommendation(data, action.getUsername(),action.getGenre());
                    message = recommand.Search();
                    JSONObject object = fileWriter.writeFile(action.getActionId(), null, message);
                    arrayResult.add(object);
                }
                if (action.getType().equals("popular")) {
                    Recommendation recommand = new Recommendation(data, action.getUsername());
                    message = recommand.Popular();
                    JSONObject object = fileWriter.writeFile(action.getActionId(), null, message);
                    arrayResult.add(object);
                }
            }
        }
        fileWriter.closeJSON(arrayResult);
    }
}
