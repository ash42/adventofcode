package nl.michielgraat.adventofcode2023.day20.module;

import static nl.michielgraat.adventofcode2023.day20.Pulse.LOW;

public class BroadcasterModule extends Module {

    public BroadcasterModule(String name) {
        super(name);
    }

    @Override
    public void sendPulse() {
        PulseInput input = getNextInput();
        destinations.forEach(d -> d.setInput(new PulseInput(name, input.pulse())));
        if (input.pulse() == LOW) {
            nrLowPulsesSend += destinations.size();
        } else {
            nrHighPulsesSend += destinations.size();
        }
    }

    @Override
    public void setInput(PulseInput input) {
        this.inputQueue.add(input);
    }

    @Override
    public boolean hasSendPulse() {
        return true;
    }

}
