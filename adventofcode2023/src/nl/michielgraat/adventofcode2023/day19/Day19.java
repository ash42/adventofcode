package nl.michielgraat.adventofcode2023.day19;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.michielgraat.adventofcode2023.AocSolver;

public class Day19 extends AocSolver {

    protected Day19(String filename) {
        super(filename);
    }

    private List<Rule> parseRules(String ruleString) {
        List<Rule> rules = new ArrayList<>();
        String[] rulesArray = ruleString.split(",");
        for (String rule : rulesArray) {
            rules.add(new Rule(rule));
        }
        return rules;
    }

    private Map<String, Workflow> readWorkflows(List<String> input) {
        Map<String, Workflow> workflows = new HashMap<>();
        for (String line : input) {
            if (line.isBlank()) {
                break;
            }
            String name = line.substring(0, line.indexOf("{"));
            List<Rule> rules = parseRules(line.substring(line.indexOf("{") + 1, line.indexOf("}")));
            workflows.put(name, new Workflow(name, rules));
        }
        return workflows;
    }

    private Part parsePart(String input) {
        String[] parts = input.split(",");
        return new Part(Integer.valueOf(parts[0].substring(parts[0].indexOf("=") + 1)),
                Integer.valueOf(parts[1].substring(parts[1].indexOf("=") + 1)),
                Integer.valueOf(parts[2].substring(parts[2].indexOf("=") + 1)),
                Integer.valueOf(parts[3].substring(parts[3].indexOf("=") + 1)));
    }

    private List<Part> readParts(List<String> input) {
        List<Part> parts = new ArrayList<>();
        boolean started = false;
        for (String line : input) {
            if (started) {
                parts.add(parsePart(line.substring(1, line.length() - 1)));
            }
            if (line.isBlank()) {
                started = true;
            }
        }
        return parts;
    }

    private List<Part> getAcceptedParts(List<Part> parts, Map<String, Workflow> workflows) {
        List<Part> accepted = new ArrayList<>();
        for (Part part : parts) {
            Workflow workflow = workflows.get("in");
            String result = workflow.apply(part);
            while (!(result.equals("A") || result.equals("R"))) {
                result = workflows.get(result).apply(part);
            }
            if (result.equals("A")) {
                accepted.add(part);
            }
        }
        return accepted;
    }

    private void getAcceptedRanges(Range range, Workflow workflow, Map<String, Workflow> workflows,
            List<Range> acceptedRanges) {
        for (Rule rule : workflow.rules()) {
            if (rule.isOperation()) {
                String destination = rule.getDestination().get();
                Range[] adjustedRanges = range.split(rule);
                if (destination.equals("A")) {
                    // Reached A, save the range
                    acceptedRanges.add(adjustedRanges[0]);
                } else if (!destination.equals("R")) {
                    // We will recursively continue with the destination, eg. the workflow which is
                    // named in the rule
                    getAcceptedRanges(adjustedRanges[0], workflows.get(destination), workflows, acceptedRanges);
                }
                // We continue checking rules with the complement of the range used in the
                // conditional above
                range = adjustedRanges[1];
            } else if (rule.logic().equals("A")) {
                // Reached A at the end of the workflow, save the range
                acceptedRanges.add(range);
            } else if (!rule.logic().equals("R")) {
                // We are at the end of the workflow, continue with the workflow named there (if
                // not R).
                getAcceptedRanges(range, workflows.get(rule.logic()), workflows, acceptedRanges);
            }
        }
    }

    @Override
    protected String runPart2(final List<String> input) {
        Map<String, Workflow> workflows = readWorkflows(input);
        List<Range> acceptedRanges = new ArrayList<>();
        getAcceptedRanges(new Range(), workflows.get("in"), workflows, acceptedRanges);
        return String.valueOf(acceptedRanges.stream().mapToLong(Range::getCombinations).sum());
    }

    @Override
    protected String runPart1(final List<String> input) {
        Map<String, Workflow> workflows = readWorkflows(input);
        List<Part> parts = readParts(input);
        return String.valueOf(getAcceptedParts(parts, workflows).stream().mapToLong(x -> x.getRating()).sum());
    }

    public static void main(String... args) {
        new Day19("day19.txt");
    }
}
