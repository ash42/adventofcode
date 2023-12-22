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

    private long lcm(long x, long y) {
        long max = Math.max(x, y);
        long min = Math.min(x, y);
        long lcm = max;
        while (lcm % min != 0) {
            lcm += max;
        }
        return lcm;
    }

    private List<Module> getBinaryCounterGroup(Module source) {
        List<Module> group = new ArrayList<>();
        group.add(source);
        for (Module destination : source.getDestinations()) {
            if (destination instanceof FlipFlopModule) {
                group.addAll(getBinaryCounterGroup(destination));
            }
        }
        return group;
    }

    private String buildBinaryString(List<Module> group) {
        StringBuilder sb = new StringBuilder();
        for (Module module : group) {
            boolean foundConjunction = false;
            for (Module destination : module.getDestinations()) {
                if (destination instanceof ConjunctionModule) {
                    foundConjunction = true;
                    break;
                }
            }
            sb.append(foundConjunction ? "1" : "0");
        }
        return sb.reverse().toString();
    }

    private List<Integer> getNrsTillPulse(Map<String, Module> nameToModule) {
        Module broadcaster = nameToModule.get("broadcaster");
        List<Module> bcDestinations = broadcaster.getDestinations();
        List<Integer> nrsTillPulse = new ArrayList<>();
        for (Module bcDestination : bcDestinations) {
            List<Module> group = getBinaryCounterGroup(bcDestination);
            String binaryString = buildBinaryString(group);
            nrsTillPulse.add(Integer.parseInt(binaryString, 2));
        }
        return nrsTillPulse;
    }

    @Override
    protected String runPart2(final List<String> input) {
        Map<String, Module> nameToModule = readModules(input);
        Module.resetPulseCounters();
        /*
         * This took some massive analysis of the input. First, after a hint in
         * /r/adventofcode, I used GraphViz to make a visual representation of my input.
         * Analysing this, I saw that there are four groups of modules containing of a
         * bunch of flip-flops and one "gateway" conjunction module which each act as
         * a binary counter. I also understood that the number of button presses needed
         * until 'rx' got a low pulse should be the lcm of the number of presses it
         * takes for each group to send a pulse. It took me a while (and some hints in
         * /r/adventofcode) to see that you can fabricate a binary number based upon
         * which flip-flops send a pulse to the conjunction module (1 in the binary
         * number) and which do not (a 0). Order is from last to first flip-flop in the
         * chain starting immediately after the broadcaster module.
         */

        // For all groups described above how many button presses it takes until they
        // send a pulse.
        List<Integer> nrsTillPulse = getNrsTillPulse(nameToModule);
        // Calculate the lcm of all these numbers and we're done.
        long nrTillLowPulse = 1;
        for (int nrTillPulse : nrsTillPulse) {
            nrTillLowPulse = lcm(nrTillLowPulse, nrTillPulse);
        }

        return String.valueOf(nrTillLowPulse);
    }

    @Override
    protected String runPart1(final List<String> input) {
        Map<String, Module> nameToModule = readModules(input);
        for (int i = 1; i <= 1000; i++) {
            pushButton(nameToModule.get("button"), nameToModule, new LinkedList<>());
        }
        return String.valueOf(Module.nrLowPulsesSend * Module.nrHighPulsesSend);
    }

    public static void main(String... args) {
        new Day20("day20.txt");
    }
}
