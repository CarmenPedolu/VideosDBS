package execution;

import entities.Action;
import entities.Actor;
import actor.ActorsAwards;
import main.Database;
import utils.Utils;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

import static utils.Utils.sortedIntegerAlphabetical;
import static utils.Utils.stringToAwards;

public final class AwardsQuery extends Query {
    private List<String> awards;

    public AwardsQuery(final Database database, final Action action) {
        super(database, action);
        this.awards = action.getFilters().get(Utils.AWARDSINDEX);
    }

    public List<String> getAwards() {
        return awards;
    }

    public void setAwards(final List<String> awards) {
        this.awards = awards;
    }

    /**
     * Find the actors based on specific awards which they wins
     *
     * @return a String which contains the actors suitable
     */
    public String awards() {
        List<Actor> actors = getData().getActors();
        // Verific daca fiecare actor are acele award-uri,
        // daca le are pe toate, il adaug intr-un HashMap numele lui si cate award-uri are
        Map<String, Integer> actorsAwards = new HashMap<>();
        for (Actor actor : actors) {
            Map<ActorsAwards, Integer> actorAward = actor.getAwards();
            int add = 1;
            for (String award : this.awards) {
                if (!actorAward.containsKey(stringToAwards(award))) {
                    add = 0;
                    break;
                }
            }
            if (add == 1) {
                int nrAwards = 0;
                for (Map.Entry<ActorsAwards, Integer> entry : actorAward.entrySet()) {
                    nrAwards += entry.getValue();
                }
                actorsAwards.put(actor.getName(), nrAwards);
            }
        }
        return sortedIntegerAlphabetical(actorsAwards, getSortType(), actorsAwards.size());
    }
}
