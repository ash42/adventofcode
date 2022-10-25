package nl.michielgraat.adventofcode2018.day19;

import java.util.List;

import nl.michielgraat.adventofcode2018.FileReader;

public class Day19 {

    private static final String FILENAME = "day19.txt";

    /**
     * So let's reverse engineer this thing. Basically it does a double for loop
     * with two registers (r2 and r5), both until the value in r4 (10551326). Every
     * time their product is equal to r4, add the value of r2 to r0. So, what the
     * program is doing is adding the values of the factors of r4 to r0. So let's
     * just implement that ourselves.
     * 
     * 0 - addi 3 16 3 - goto 17 (INIT)
     * // BIG LOOP
     * // for (r2 = 1; r2 < r4; r2++)
     * 1 - seti 1 3 2
     * // INNER LOOP
     * for (r5 = 1; r5 < r4;r5++)
     * 2 - seti 1 0 5
     * // r1 = r2*r5
     * 3 - mulr 2 5 1
     * // if (r4 == r2*r5) {
     * 4 - eqrr 1 4 1
     * 5 - addr 1 3 3
     * 6 - addi 3 1 3
     * 7 - addr 2 0 0
     * // r5++
     * 8 - addi 5 1 5
     * // if (r5 > r4) {
     * 9 - gtrr 5 4 1
     * 10 - addr 3 1 3
     * // GO back to start loop
     * 11 - seti 2 2 3
     * // r2++ }
     * 12 - addi 2 1 2
     * // if (r2 > r4) GOTO END
     * 13 - gtrr 2 4 1
     * 14 - addr 1 3 3
     * // else goto 1
     * 15 - seti 1 1 3
     * //END
     * 16 - mulr 3 3 3 // r3 = 16*3 = 48 + 1 = 49 -> HALT
     * //INIT
     * // r4 = (2*2)*19 * 11 = 836
     * 17 - addi 4 2 4 r4 = 0+2 = 2, r3 = 18
     * 18 - mulr 4 4 4 r4 = 2*2 = 4, r3 = 19
     * 19 - mulr 3 4 4 r4 = 19*4 = 76, r3 = 20
     * 20 - muli 4 11 4 r4 = 76*11 = 836, r3 = 21
     * // r1 = 4 * 22 + 2 = 90
     * 21 - addi 1 4 1 r1 = 4, r3 = 22
     * 22 - mulr 1 3 1 r1 = 88, r3 = 23
     * 23 - addi 1 2 1 r1 = 90, r3 = 24
     * // r4 = r4 + r1 = 926
     * 24 - addr 4 1 4 r4 = 836+90 = 926, r3 = 25
     * // if (r0 == 0) goto 26 else if (r1 == 1) goto 27, so basically diff between
     * part 1 and 2
     * 25 - addr 3 0 3 r3 = 25 + 1 = 26, r3 = 27
     * // r3 = r0 -> goto 0
     * 26 - seti 0 2 3
     * // r1 = ((((27*28)+756) * 30) * 14) * 32) + 926 = 10551326
     * 27 - setr 3 6 1 r1 = 27, r3 = 28
     * 28 - mulr 1 3 1 r1 = 27*28 = 756, r3 = 29
     * 29 - addr 3 1 1 r1 = 29+756 = 785, r3 = 30
     * 30 - mulr 3 1 1 r1 = 30 * 756 = 23550, r3 = 31
     * 31 - muli 1 14 1 r1 = 23550*14 = 329700, r3 = 32
     * 32 - mulr 1 3 1 r1 = 329700*32 = 10550400, r3 = 33
     * 33 - addr 4 1 4 r4 = 926+10550400 = 10551326, r3 = 34
     * // r0 = 0
     * 34 - seti 0 6 0 r0 = 0, r3 = 35
     * // r1 = 1 -> GOTO 1
     * 35 - seti 0 9 3
     * 
     * 
     * @param lines
     * @return
     */
    private long runPart2(final List<String> lines) {
        final int r4 = 10551326;
        int r0 = 0;
        for (int r2 = 1; r2 <= Math.sqrt(r4); r2++) {
            if (r4 % r2 == 0) {
                if (r2 == (r4 / r2)) {
                    // Both divisors the same, only count once.
                    r0 += r2;
                } else {
                    r0 += (r2 + r4 / r2);
                }
            }
        }
        return r0;
    }

    private long runPart1(final List<String> lines) {
        final Program p = new Program(lines);
        return p.run();
    }

    public static void main(final String[] args) {
        final List<String> lines = FileReader.getStringList(FILENAME);
        long start = System.nanoTime();
        System.out.println("Answer to part 1: " + new Day19().runPart1(lines));
        System.out.println("Took: " + (System.nanoTime() - start) / 1000000 + " ms");
        start = System.nanoTime();
        System.out.println("Answer to part 2: " + new Day19().runPart2(lines));
        System.out.println("Took: " + (System.nanoTime() - start) / 1000000 + " ms");
    }
}