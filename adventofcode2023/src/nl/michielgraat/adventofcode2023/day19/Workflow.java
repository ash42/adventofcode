package nl.michielgraat.adventofcode2023.day19;

import java.util.List;
import java.util.Optional;

public record Workflow(String name, List<Rule> rules) {

    public String apply(Part part) {
        for (Rule rule : rules) {
            Optional<String> result = rule.apply(part);
            if (result.isPresent()) {
                return result.get();
            }
        }
        throw new IllegalArgumentException("For part " + part + ", this workflow does not work " + this);
    }

    public boolean calls(String name) {
        return rules.stream().filter(r -> r.logic().contains(name)).count() > 0;
    }
}
