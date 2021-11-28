package queries;

import Entities.Action;
import Entities.Actor;
import main.Database;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class ActorDescriptionQuery extends Query {
    private List<String> words;

    public ActorDescriptionQuery(Database database, Action action) {
        super(database, action);
        this.words = action.getFilters().get(2);
    }

    public List<String> getWords() {
        return words;
    }

    public void setWords(List<String> words) {
        this.words = words;
    }

    public String Description() {
        ArrayList<String> sorted_actors_description = new ArrayList<>();
        List<Actor> actors = getData().getActors();
        for (Actor actor : actors) {
            int add = 0;
            String description = actor.getCareerDescription();
            description = description.replaceAll(",", " ");
            description = description.replaceAll("\\.", " ");
            description = description.replaceAll("\\n", " ");
            description = description.replaceAll("-", " ");
            for (String word : this.words) {
                String wordFind = " " + word.toLowerCase() + " ";
                if (!description.toLowerCase().contains(wordFind)) {
                    add = 0;
                    break;
                } else {
                    add = 1;
                }
            }
            if (add == 1) {
                sorted_actors_description.add(actor.getName());
            }
        }
        // Sortez arrayListul alfabetic
        Collections.sort(sorted_actors_description);
        if (getSortType().equals("desc")) {
            Collections.reverse(sorted_actors_description);
        }
        return ("Query result: " + sorted_actors_description);
    }
}
