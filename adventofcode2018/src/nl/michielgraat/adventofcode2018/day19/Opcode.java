package nl.michielgraat.adventofcode2018.day19;

public enum Opcode {
    ADDR,
    ADDI,
    MULR,
    MULI,
    BANR,
    BANI,
    BORR,
    BORI,
    SETR,
    SETI,
    GTIR,
    GTRI,
    GTRR,
    EQIR,
    EQRI,
    EQRR;

    public static Opcode fromString(final String val) {
        return Opcode.valueOf(val.toUpperCase());
    }
}
