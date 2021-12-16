package nl.michielgraat.adventofcode2021.day16;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import nl.michielgraat.adventofcode2021.FileReader;

public class Day16 {

    private static final String FILENAME = "day16.txt";
    private static final int MIN_PACKAGE_SIZE = 11; // Minimum package size is 11, 3 bits version, 3 bits typeId and 5
                                                    // bits for typeId=4.

    private String getBinaryString(final String s) {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            switch (s.charAt(i)) {
                case '0':
                    sb.append("0000");
                    break;
                case '1':
                    sb.append("0001");
                    break;
                case '2':
                    sb.append("0010");
                    break;
                case '3':
                    sb.append("0011");
                    break;
                case '4':
                    sb.append("0100");
                    break;
                case '5':
                    sb.append("0101");
                    break;
                case '6':
                    sb.append("0110");
                    break;
                case '7':
                    sb.append("0111");
                    break;
                case '8':
                    sb.append("1000");
                    break;
                case '9':
                    sb.append("1001");
                    break;
                case 'A':
                    sb.append("1010");
                    break;
                case 'B':
                    sb.append("1011");
                    break;
                case 'C':
                    sb.append("1100");
                    break;
                case 'D':
                    sb.append("1101");
                    break;
                case 'E':
                    sb.append("1110");
                    break;
                case 'F':
                    sb.append("1111");
                    break;
                default:
                    throw new IllegalArgumentException(
                            "Non-hexadecimal character '" + s.charAt(i) + "' found in input '" + s + "'");
            }
        }
        return sb.toString();
    }

    private int parseBinary(final String binary, final int start, final int length) {
        return Integer.parseInt(binary.substring(start, length), 2);
    }

    private long getValue(final int typeId, final List<Packet> packets) {
        switch (typeId) {
            case 0:
                return packets.stream().map(p -> p.value).reduce(0L, Long::sum);
            case 1:
                return packets.stream().map(p -> p.value).reduce(1L, (a, b) -> a = a * b);
            case 2:
                return packets.stream().map(p -> p.value).min(Long::compareTo).get();
            case 3:
                return packets.stream().map(p -> p.value).max(Long::compareTo).get();
            case 5:
                return (packets.get(0).value > packets.get(1).value) ? 1 : 0;
            case 6:
                return (packets.get(0).value < packets.get(1).value) ? 1 : 0;
            case 7:
                return (packets.get(0).value == packets.get(1).value) ? 1 : 0;
            default:
                throw new IllegalArgumentException("Unknown type id: " + typeId);
        }
    }

    private Packet createPacket(final String binary, int i) {
        final int startIndex = i;
        final List<Packet> subPackets = new ArrayList<>();
        final int version = parseBinary(binary, i, i + 3);
        final int typeId = parseBinary(binary, i + 3, i + 6);
        long value = 0;
        i += 6;
        if (typeId == 4) {
            final StringBuilder sb = new StringBuilder();
            boolean endReached = false;
            while (!endReached) {
                endReached = binary.charAt(i) == '0';
                sb.append(binary.substring(i + 1, i + 5));
                i += 5;
            }
            value = Long.parseLong(sb.toString(), 2);
        } else {
            final int lengthTypeId = Integer.parseInt(binary.substring(i, ++i), 2);
            if (lengthTypeId == 0) {
                int length = parseBinary(binary, i, i + 15);
                i += 15;
                while (length > 0) {
                    final Packet p = createPacket(binary, i);
                    subPackets.add(p);
                    length -= p.bitLength;
                    i += p.bitLength;
                }
            } else {
                final int nr = parseBinary(binary, i, i + 11);
                i += 11;
                for (int j = 0; j < nr; j++) {
                    final Packet p = createPacket(binary, i);
                    subPackets.add(p);
                    i += p.bitLength;
                }
            }
            value = getValue(typeId, subPackets);
        }
        return new Packet(version, typeId, i - startIndex, subPackets, value);
    }

    private int addPacketNumbers(final String binary) {
        int total = 0;
        int i = 0;
        while (i < binary.length() - MIN_PACKAGE_SIZE) {
            total += Integer.parseInt(binary.substring(i, i + 3), 2);
            final int type = Integer.parseInt(binary.substring(i + 3, i + 6), 2);
            i += 6;
            if (type == 4) {
                boolean endReached = false;
                while (!endReached) {
                    endReached = binary.charAt(i) == '0';
                    i += 5;
                }
            } else {
                final int lengthTypeId = Integer.parseInt(binary.substring(i, ++i), 2);
                i += (lengthTypeId == 0) ? 15 : 11;
            }
        }
        return total;
    }

    private long runPart2(final List<String> lines) {
        return createPacket(getBinaryString(lines.get(0)), 0).value;
    }

    private int runPart1(final List<String> lines) {
        return addPacketNumbers(getBinaryString(lines.get(0)));
    }

    public static void main(final String[] args) {
        final List<String> lines = FileReader.getStringList(FILENAME);
        long start = Calendar.getInstance().getTimeInMillis();
        System.out.println("Answer to part 1: " + new Day16().runPart1(lines));
        System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");
        start = Calendar.getInstance().getTimeInMillis();
        System.out.println("Answer to part 2: " + new Day16().runPart2(lines));
        System.out.println("Took: " + (Calendar.getInstance().getTimeInMillis() - start) + " ms");
    }
}

class Packet {
    int version;
    int typeId;
    List<Packet> subPackets = new ArrayList<>();
    long value;
    int bitLength;

    Packet(final int version, final int typeId, final int bitLength, final List<Packet> subPackets, final long value) {
        this.version = version;
        this.typeId = typeId;
        this.bitLength = bitLength;
        this.subPackets = subPackets;
        this.value = value;
    }

}