package nl.michielgraat.adventofcode2019.day20;

public record Portal (String name, boolean inner, int level) {
    
    public boolean isStart() {
        return level == 0 && name.equals("AA");
    }

    public boolean isEnd() {
        return level == 0 && name.equals("ZZ");
    }
    
}
