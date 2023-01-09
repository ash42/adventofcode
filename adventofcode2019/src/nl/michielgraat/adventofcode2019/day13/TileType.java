package nl.michielgraat.adventofcode2019.day13;

public enum TileType {
    EMPTY, WALL, BLOCK, HORIZONTAL_PADDLE, BALL;

    public static TileType convert(final int typeNr) {
        switch (typeNr) {
            case 0:
                return EMPTY;
            case 1:
                return WALL;
            case 2:
                return BLOCK;
            case 3:
                return HORIZONTAL_PADDLE;
            case 4:
                return BALL;
            default:
                throw new IllegalArgumentException("Unknown tile type number [" + typeNr + "]");
        }
    }
}
