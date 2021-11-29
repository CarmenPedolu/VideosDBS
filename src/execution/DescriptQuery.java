package execution;

import entities.Action;
import entities.Actor;
import main.Database;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class DescriptQuery extends Query {
    private List<String> words;

    public DescriptQuery(final Database database, final Action action) {
        super(database, action);
        this.words = action.getFilters().get(2);
    }

    public List<String> getWords() {
        return words;
    }

    public void setWords(final List<String> words) {
        this.words = words;
    }

    /**
     * Find the actors based on specific words in their description
     *
     * @return a String which contains the actors suitable
     */
    public String description() {
        ArrayList<String> actorsDescription = new ArrayList<>();
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
                actorsDescription.add(actor.getName());
            }
        }
        // Sortez arrayListul alfabetic
        Collections.sort(actorsDescription);
        if (getSortType().equals("desc")) {
            Collections.reverse(actorsDescription);
        }
        return ("Query result: " + actorsDescription);
    }
}
