package queries;

import Entities.Action;
import Entities.User;
import main.Database;
import utils.Utils;

import java.util.*;

public class UserRatingQuery extends Query {
    public UserRatingQuery(Database database, Action action) {
        super(database, action);
    }

    public String Rating_Users() {
        List<User> users = getData().getUsers();
        Map<String, Integer> usersRating = new HashMap<>();
        for (User user : users) {
            if (user.getNr_ratings() != 0) {
                usersRating.put(user.getUsername(), user.getNr_ratings());
            }
        }
        String message = Utils.sortedQuery(usersRating,getSortType(),getNumber());
        return message;
    }
}
