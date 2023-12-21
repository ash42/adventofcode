package nl.michielgraat.adventofcode2023.day20.module;

import static nl.michielgraat.adventofcode2023.day20.Pulse.LOW;

public class ButtonModule extends Module {

    public ButtonModule(String name) {
        super(name);
    }

    @Override
    public void sendPulse() {
        destinations.forEach(d -> d.setInput(new PulseInput(name, LOW)));
        nrLowPulsesSend += destinations.size();
    }

    @Override
    public void setInput(PulseInput input) {
        throw new UnsupportedOperationException("Unimplemented method 'setInput'");
    }

    @Override
    public boolean hasSendPulse() {
        return true;
    }

}
