package nl.michielgraat.adventofcode2022.day20;

/**
 * Wrapper around long with an added value (I use the index in the original
 * file) to make each value unique. This make getting the element in a list
 * possible, even if there are multiple element with the same val.
 */
public record Number(long val, int idx) {
}