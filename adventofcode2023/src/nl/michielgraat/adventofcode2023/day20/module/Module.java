package nl.michielgraat.adventofcode2023.day20.module;

import java.util.LinkedList;
import java.util.List;

public abstract class Module {
    public static long nrLowPulsesSend = 0;
    public static long nrHighPulsesSend = 0;

    protected String name;
    protected List<Module> destinations;
    protected LinkedList<PulseInput> inputQueue;

    public Module(String name) {
        this.name = name;
        this.inputQueue = new LinkedList<>();
    }

    public void setDestinations(List<Module> destinations) {
        this.destinations = destinations;
    }

    public List<Module> getDestinations() {
        return destinations;
    }

    public abstract void sendPulse();

    public abstract boolean hasSendPulse();

    public abstract void setInput(PulseInput input);

    public String getName() {
        return name;
    }

    protected PulseInput getNextInput() {
        return inputQueue.pop();
    }

    public static void resetPulseCounters() {
        nrLowPulsesSend = 0;
        nrHighPulsesSend = 0;
    }
}
