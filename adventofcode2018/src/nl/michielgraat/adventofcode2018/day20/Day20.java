package nl.michielgraat.adventofcode2018.day20;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import nl.michielgraat.adventofcode2018.FileReader;

public class Day20 {

    private static final String FILENAME = "day20.txt";
    private static final String DIRECTIONS = "NESW";

    private Room getNextRoom(final char c, Room r) {
        if (c == 'N') {
            return new Room(r.x, r.y + 1);
        } else if (c == 'E') {
            return new Room(r.x + 1, r.y);
        } else if (c == 'S') {
            return new Room(r.x, r.y - 1);
        } else {
            return new Room(r.x - 1, r.y);
        }
    }

    private List<Room> getRooms(final String regex) {
        final List<Room> rooms = new ArrayList<>();
        final Deque<Room> stack = new ArrayDeque<>();
        int distance = 0;
        Room room = new Room(0, 0, distance);
        rooms.add(room);
        for (final char c : regex.toCharArray()) {
            if (DIRECTIONS.contains("" + c)) {
                room = getNextRoom(c, room);
                distance++;
                if (rooms.contains(room)) {
                    room = rooms.remove(rooms.indexOf(room));
                }
                room.distance = Math.min(distance, room.distance > 0 ? room.distance : Integer.MAX_VALUE);
                rooms.add(room);
            } else if (c == '(') {
                stack.push(room);
            } else if (c == ')') {
                room = stack.pop();
                distance = room.distance;
            } else if (c == '|') {
                room = stack.peek();
                distance = room.distance;
            }
        }
        return rooms;
    }

    public long runPart2(final List<String> lines) {
        return getRooms(lines.get(0)).stream().filter(r -> r.distance >= 1000).count();
    }

    public int runPart1(final List<String> lines) {
        return getRooms(lines.get(0)).stream().mapToInt(r -> r.distance).max().getAsInt();
    }

    public static void main(final String[] args) {
        final List<String> lines = FileReader.getStringList(FILENAME);
        long start = System.nanoTime();
        System.out.println("Answer to part 1: " + new Day20().runPart1(lines));
        System.out.println("Took: " + (System.nanoTime() - start) / 1000000 + " ms");
        start = System.nanoTime();
        System.out.println("Answer to part 2: " + new Day20().runPart2(lines));
        System.out.println("Took: " + (System.nanoTime() - start) / 1000000 + " ms");
    }
}

class Room {
    int x;
    int y;
    int distance;

    Room(final int x, final int y) {
        this.x = x;
        this.y = y;
    }

    Room(final int x, final int y, final int distance) {
        this.x = x;
        this.y = y;
        this.distance = distance;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
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
        final Room other = (Room) obj;
        if (x != other.x)
            return false;
        if (y != other.y)
            return false;
        return true;
    }

}