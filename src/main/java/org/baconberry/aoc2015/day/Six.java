package org.baconberry.aoc2015.day;

import org.baconberry.aoc2015.ISolver;
import org.baconberry.aoc2015.Point;
import org.baconberry.aoc2015.Utils;
import org.baconberry.aoc2015.ds.Grid;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Six implements ISolver {
    @Override
    public String solve(List<String> lines, int part) {
        final AtomicInteger counter = new AtomicInteger(0);
        if (part == 1) {
            partOne(lines, counter);
        } else {
            partTwo(lines, counter);
        }
        return counter.toString();
    }

    private void partTwo(List<String> lines, AtomicInteger counter) {
        var grid = Grid.ofInt(1000, 1000, 0);
        for (String line : lines) {
            processLineTwo(grid, line);
        }
        grid.forEach((x, y, val) -> counter.addAndGet(val));
    }

    private void processLineTwo(Grid<Integer> grid, String line) {
        var numbers = Utils.lineToIntList(line);
        var start = Point.of(numbers.getFirst(), numbers.get(1));
        var end = Point.of(numbers.get(2), numbers.get(3));
        if (line.startsWith("turn")) {
            boolean isOn = line.contains("on");
            add(grid, isOn ? 1 : -1, start, end);
        } else if (line.startsWith("toggle")) {
            add(grid, 2, start, end);
        }
    }

    private void add(Grid<Integer> grid, int toAdd, Point start, Point end) {
        for (int i = start.x(); i <= end.x(); i++) {
            for (int j = start.y(); j <= end.y(); j++) {
                int val = grid.getValue(i, j);
                if (toAdd < 0 && val == 0) {
                    continue;
                }
                grid.setValue(i, j, val + toAdd);
            }
        }
    }

    private void partOne(List<String> lines, AtomicInteger counter) {
        var grid = Grid.ofBool(1000, 1000, false);
        for (String line : lines) {
            processLine(grid, line);
        }
        grid.forEach((x, y, val) -> {
            if (Boolean.TRUE.equals(val)) {
                counter.incrementAndGet();
            }
        });
    }


    private void processLine(Grid<Boolean> grid, String line) {
        var numbers = Utils.lineToIntList(line);
        var start = Point.of(numbers.getFirst(), numbers.get(1));
        var end = Point.of(numbers.get(2), numbers.get(3));
        if (line.startsWith("turn")) {
            boolean isOn = line.contains("on");
            turn(grid, isOn, start, end);
        } else if (line.startsWith("toggle")) {
            toggle(grid, start, end);
        }
    }

    private void toggle(Grid<Boolean> grid, Point start, Point end) {
        for (int i = start.x(); i <= end.x(); i++) {
            for (int j = start.y(); j <= end.y(); j++) {
                grid.setValue(i, j, !grid.getValue(i, j));
            }
        }
    }

    private void turn(Grid<Boolean> grid, boolean val, Point start, Point end) {
        for (int i = start.x(); i <= end.x(); i++) {
            for (int j = start.y(); j <= end.y(); j++) {
                grid.setValue(i, j, val);
            }
        }
    }
}
