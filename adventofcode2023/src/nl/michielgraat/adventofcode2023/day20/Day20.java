package nl.michielgraat.adventofcode2023.day20;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import nl.michielgraat.adventofcode2023.AocSolver;
import nl.michielgraat.adventofcode2023.day20.module.Module;
import nl.michielgraat.adventofcode2023.day20.module.OutputModule;
import nl.michielgraat.adventofcode2023.day20.module.BroadcasterModule;
import nl.michielgraat.adventofcode2023.day20.module.ButtonModule;
import nl.michielgraat.adventofcode2023.day20.module.ConjunctionModule;
import nl.michielgraat.adventofcode2023.day20.module.FlipFlopModule;

public class Day20 extends AocSolver {

    private static final Pattern MODULE_PATTERN = Pattern.compile("(\\S+)(\\s->\\s)(.*)");

    protected Day20(String filename) {
        super(filename);
    }

    private Map<String, Module> readModules(List<String> input) {
        // First read all modules that are present
        Map<String, Module> modules = new HashMap<>();
        // Also keep a map from the modules to the names of their pulse destinations.
        Map<Module, List<String>> moduleToDestinations = new HashMap<>();
        for (String line : input) {
            Matcher matcher = MODULE_PATTERN.matcher(line);
            matcher.find();
            String module = matcher.group(1);
            List<String> outputs = Arrays.asList(matcher.group(3).split(", "));

            if (module.equals("broadcaster")) {
                Module bModule = new BroadcasterModule(module);
                moduleToDestinations.put(bModule, outputs);
                modules.put(module, bModule);
            } else if (module.startsWith("%")) {
                module = module.substring(1);
                Module flipModule = new FlipFlopModule(module);
                moduleToDestinations.put(flipModule, outputs);
                modules.put(module, flipModule);
            } else if (module.startsWith("&")) {
                module = module.substring(1);
                Module cModule = new ConjunctionModule(module);
                moduleToDestinations.put(cModule, outputs);
                modules.put(module, cModule);
            }
        }

        // Now add process to moduleToDestinations map and set the 'real' Module objects
        // as destinations.
        // Also keep track of all sources (inputs) for conjunction modules.
        Map<Module, List<Module>> conjModuleToSources = new HashMap<>();
        for (Entry<Module, List<String>> entry : moduleToDestinations.entrySet()) {
            Module module = entry.getKey();
            List<String> outputs = entry.getValue();
            List<Module> outputList = new ArrayList<>();
            for (String o : outputs) {
                if (!modules.containsKey(o)) {
                    // We have found the name of a module which does not exist. This must be an
                    // "end-of-line" sort of output module (as in the examples).
                    modules.put(o, new OutputModule(o));
                }
                Module dest = modules.get(o);
                outputList.add(dest);
                if (dest instanceof ConjunctionModule) {
                    conjModuleToSources.putIfAbsent(dest, new ArrayList<>());
                    List<Module> inputs = conjModuleToSources.get(dest);
                    inputs.add(module);
                    conjModuleToSources.put(dest, inputs);
                }
            }
            module.setDestinations(outputList);
        }

        // Add all sources (inputs) to their corresponding conjunction modules.
        for (Entry<Module, List<Module>> entry : conjModuleToSources.entrySet()) {
            ConjunctionModule cModule = (ConjunctionModule) entry.getKey();
            cModule.setSources(entry.getValue());
        }

        // Finally add a button module with broadcaster as destination.
        Module buttonModule = new ButtonModule("button");
        List<Module> buttonDest = new ArrayList<>();
        buttonDest.add(modules.get("broadcaster"));
        buttonModule.setDestinations(buttonDest);
        modules.put(buttonModule.getName(), buttonModule);

        return modules;
    }

    private void pushButton(Module module, Map<String, Module> nameToModule, LinkedList<Module> queue) {
        queue.add(module);
        while (!queue.isEmpty()) {
            Module current = queue.pop();
            current.sendPulse();
            if (!(current instanceof OutputModule) && current.hasSendPulse()) {
                queue.addAll(current.getDestinations());
            }
        }
    }

    @Override
    protected String runPart2(final List<String> input) {
        return "part 2";
    }

    @Override
    protected String runPart1(final List<String> input) {
        Map<String, Module> nameToModule = readModules(input);
        for (int i = 1; i <= 1000; i++) {
            pushButton(nameToModule.get("button"), nameToModule, new LinkedList<>());
        }
        return String.valueOf(nameToModule.get("button").nrLowPulsesSend * nameToModule.get("button").nrHighPulsesSend);
    }

    public static void main(String... args) {
        new Day20("day20.txt");
    }
}
