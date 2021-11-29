package execution;

import entities.Action;
import entities.User;
import main.Database;
import utils.Utils;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

public final class UserRatingQuery extends Query {
    public UserRatingQuery(final Database database, final Action action) {
        super(database, action);
    }

    /**
     * Find the users based on the ratings which they gave
     *
     * @return a String which contains the actors suitable
     */
    public String ratingUsers() {
        List<User> users = getData().getUsers();
        Map<String, Integer> usersRating = new HashMap<>();
        for (User user : users) {
            if (user.getNrRatings() != 0) {
                usersRating.put(user.getUsername(), user.getNrRatings());
            }
        }
        return Utils.sortedIntegerAlphabetical(usersRating, getSortType(), getNumber());
    }
}

