package nl.michielgraat.adventofcode2016.day17;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class Position {
    int x;
    int y;
    String path;

    public Position(final int x, final int y, final String path) {
        this.x = x;
        this.y = y;
        this.path = path;
    }

    public int getPathLength() {
        return this.path.length();
    }

    public boolean isEnd() {
        return x == 3 && y == 3;
    }

    public String getPath() {
        return this.path;
    }

    public boolean isOpen(final int nr) {
        final int result = x * x + 3 * x + 2 * x * y + y + y * y + nr;
        final String binary = Integer.toBinaryString(result);
        final long ones = binary.chars().filter(c -> c == '1').count();
        return ones % 2 == 0;
    }

    private String encodeHexString(final byte[] byteArray) {
        final StringBuilder hexStringBuffer = new StringBuilder();
        for (int i = 0; i < byteArray.length; i++) {
            hexStringBuffer.append(byteToHex(byteArray[i]));
        }
        return hexStringBuffer.toString();
    }

    private String byteToHex(final byte num) {
        final char[] hexDigits = new char[2];
        hexDigits[0] = Character.forDigit((num >> 4) & 0xF, 16);
        hexDigits[1] = Character.forDigit((num & 0xF), 16);
        return new String(hexDigits);
    }

    private String getMD5Hash(final String input) {
        try {
            final MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(input.getBytes());
            final byte[] digest = md.digest();
            return encodeHexString(digest);
        } catch (final NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    private boolean[] getOpenDoors(final String password) {
        // Up - Down - Left - Right
        final boolean[] open = new boolean[4];
        final String hash = getMD5Hash(password + path).substring(0, 4);
        for (int i = 0; i < hash.length(); i++) {
            final char c = hash.charAt(i);
            open[i] = c == 'b' || c == 'c' || c == 'd' || c == 'e' || c == 'f';
        }
        return open;
    }

    public List<Position> getNeighbours(final String password, final int minLength) {
        final List<Position> neighbours = new ArrayList<>();
        final boolean[] openDoors = getOpenDoors(password);
        if (path.length() + 1 < minLength && !isEnd()) {
            // Up
            if (openDoors[0]) {
                final int nx = x;
                final int ny = y - 1;
                if (ny >= 0) {
                    final Position p = new Position(nx, ny, path + "U");
                    neighbours.add(p);
                }
            }
            // Down
            if (openDoors[1]) {
                final int nx = x;
                final int ny = y + 1;
                if (ny <= 3) {
                    final Position p = new Position(nx, ny, path + "D");
                    neighbours.add(p);
                }
            }
            // Left
            if (openDoors[2]) {
                final int nx = x - 1;
                final int ny = y;
                if (nx >= 0) {
                    final Position p = new Position(nx, ny, path + "L");
                    neighbours.add(p);
                }
            }
            // Right
            if (openDoors[3]) {
                final int nx = x + 1;
                final int ny = y;
                if (nx <= 3) {
                    final Position p = new Position(nx, ny, path + "R");
                    neighbours.add(p);
                }
            }
        }
        return neighbours;
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + "), " + path;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((path == null) ? 0 : path.hashCode());
        result = prime * result + x;
        result = prime * result + y;
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final Position other = (Position) obj;
        if (path == null) {
            if (other.path != null)
                return false;
        } else if (!path.equals(other.path))
            return false;
        if (x != other.x)
            return false;
        if (y != other.y)
            return false;
        return true;
    }

}
