package nl.michielgraat.adventofcode2019.day17;

public enum SpaceType {
    SCAFFOLD, EMPTY, LEFT, RIGHT, UP, DOWN;

    public static SpaceType toSpaceType(final long input) {
        switch((int) input) {
            case 46: return EMPTY;
            case 35: return SCAFFOLD;
            case 60: return LEFT;
            case 62: return RIGHT;
            case 94: return UP;
            case 118: return DOWN;
            default: throw new IllegalArgumentException("Unknown space type [" + input + "]");
        }
    }

    public String getPrintable() {
        switch (this) {
            case SCAFFOLD: return "#";
            case EMPTY: return ".";
            case LEFT: return "<";
            case RIGHT: return ">";
            case UP: return "^";
            default: return "v";
        }
    }

    public int getRobotDirection() {
        switch (this) {
            case LEFT: return 0;
            case DOWN: return 1;
            case RIGHT: return 2;
            case UP: return 3;
            default: throw new IllegalArgumentException("No robot direction [" + this + "]");
        }
    }
}
