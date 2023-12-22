package nl.michielgraat.adventofcode2023.day20.module;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.michielgraat.adventofcode2023.day20.Pulse;

import static nl.michielgraat.adventofcode2023.day20.Pulse.*;

public class ConjunctionModule extends Module {

    private Map<String, Pulse> inputMap;

    public ConjunctionModule(String name) {
        super(name);
        inputMap = new HashMap<>();
    }

    public void setSources(List<Module> sources) {
        for (Module s : sources) {
            inputMap.put(s.getName(), LOW);
        }
    }

    @Override
    public void sendPulse() {
        if (inputMap.values().stream().allMatch(p -> p == HIGH)) {
            destinations.forEach(d -> d.setInput(new PulseInput(name, LOW)));
            nrLowPulsesSend += destinations.size();
        } else {
            destinations.forEach(d -> d.setInput(new PulseInput(name, HIGH)));
            nrHighPulsesSend += destinations.size();
        }
    }

    @Override
    public void setInput(PulseInput input) {
        inputMap.put(input.name(), input.pulse());
    }

    @Override
    public boolean hasSendPulse() {
        return true;
    }

}
