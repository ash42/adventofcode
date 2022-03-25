package nl.michielgraat.adventofcode2017.day25;

class StateRule {
    boolean zeroWriteZero;
    Direction zeroDirection;
    char zeroNextState;

    boolean oneWriteZero;
    Direction oneDirection;
    char oneNextState;

    public StateRule(final boolean zeroWriteZero, final Direction zeroDirection, final char zeroNextState, final boolean oneWriteZero,
            final Direction oneDirection, final char oneNextState) {
        this.zeroWriteZero = zeroWriteZero;
        this.zeroDirection = zeroDirection;
        this.zeroNextState = zeroNextState;
        this.oneWriteZero = oneWriteZero;
        this.oneDirection = oneDirection;
        this.oneNextState = oneNextState;
    }

}