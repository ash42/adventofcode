package nl.michielgraat.adventofcode2022.day16;

import java.util.List;

public record State(Valve valve, int minute, List<Valve> openValves, int nrOfOtherPlayers) {
}
