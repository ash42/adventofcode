package nl.michielgraat.adventofcode2019.day15;

public enum Status {
    WALL, EMPTY, OXYGEN_SYSTEM;

    public static Status getStatus(final int code) {
        switch (code) {
            case 0:
                return WALL;
            case 1:
                return EMPTY;
            case 2:
                return OXYGEN_SYSTEM;
            default:
                throw new IllegalArgumentException("Unknow status code [" + code + "]");
        }
    }

    public static String toString(final Status code) {
        switch (code) {
            case WALL:
                return "#";
            case EMPTY:
                return ".";
            default:
                return "O";
        }

    }
}
