package nl.michielgraat.adventofcode2023.day20.module;

public class OutputModule extends Module {

    public OutputModule(String name) {
        super(name);
    }

    @Override
    public void sendPulse() {
        return;
    }

    @Override
    public boolean hasSendPulse() {
        return false;
    }

    @Override
    public void setInput(PulseInput input) {
    }
}