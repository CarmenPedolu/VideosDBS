package queries;

import Entities.Action;
import Entities.Actor;
import actor.ActorsAwards;
import main.Database;

import java.util.*;

import static utils.Utils.sortedQuery;
import static utils.Utils.stringToAwards;

public class ActorAwardsQuery extends Query {
    private List<String> awards;

    public ActorAwardsQuery(Database database, Action action) {
        super(database, action);
        this.awards = action.getFilters().get(3);
    }

    public List<String> getAwards() {
        return awards;
    }

    public void setAwards(List<String> awards) {
        this.awards = awards;
    }

    public String Awards() {
        List<Actor> actors = getData().getActors();
        // verific daca fiecare actor are acele award-uri
        // daca le are pe toate
        // adaug intr-un map numele lui si cate award-uri are
        Map<String, Integer> actors_awards = new HashMap<>();
        for (Actor actor : actors) {
            Map<ActorsAwards, Integer> actor_award = actor.getAwards();
            int add = 1;
            for (String award : this.awards) {
                if (!actor_award.containsKey(stringToAwards(award))) {
                    add = 0;
                    break;
                }
            }
            if (add == 1) {
                int nrAwards = 0;
                for (Map.Entry<ActorsAwards, Integer> entry : actor_award.entrySet()) {
                    nrAwards += entry.getValue();
                }
                actors_awards.put(actor.getName(), nrAwards);
            }
        }
        String message = sortedQuery(actors_awards, getSortType(), actors_awards.size());
        return message;
    }
}
