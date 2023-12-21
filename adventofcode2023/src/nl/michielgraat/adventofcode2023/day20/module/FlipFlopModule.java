package nl.michielgraat.adventofcode2023.day20.module;

import static nl.michielgraat.adventofcode2023.day20.Pulse.*;

public class FlipFlopModule extends Module {

    private boolean on = false;
    private boolean sendPulse = false;

    public FlipFlopModule(String name) {
        super(name);
    }

    @Override
    public void sendPulse() {
        PulseInput input = getNextInput();
        sendPulse = false;
        if (input.pulse() == LOW) {
            on = !on;
            destinations.forEach(d -> d.setInput(new PulseInput(this.name, on ? HIGH : LOW)));
            sendPulse = true;
            if (on) {
                nrHighPulsesSend += destinations.size();
            } else {
                nrLowPulsesSend += destinations.size();
            }
        }
    }

    @Override
    public void setInput(PulseInput input) {
        this.inputQueue.add(input);
    }

    @Override
    public boolean hasSendPulse() {
        return sendPulse;
    }
}