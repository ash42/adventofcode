package nl.michielgraat.adventofcode2022.day16;

import java.util.List;

public class State {
    private Valve valve;
    private int minute;
    private List<Valve> openValves;

    public State(Valve valve, int minute, List<Valve> openValves) {
        this.valve = valve;
        this.minute = minute;
        this.openValves = openValves;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((valve == null) ? 0 : valve.hashCode());
        result = prime * result + minute;
        result = prime * result + ((openValves == null) ? 0 : openValves.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        State other = (State) obj;
        if (valve == null) {
            if (other.valve != null)
                return false;
        } else if (!valve.equals(other.valve))
            return false;
        if (minute != other.minute)
            return false;
        if (openValves == null) {
            if (other.openValves != null)
                return false;
        } else if (!openValves.equals(other.openValves))
            return false;
        return true;
    }


}
