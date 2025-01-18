package org.baconberry.aoc2015.day;

import lombok.Setter;
import org.baconberry.aoc2015.Direction;
import org.baconberry.aoc2015.ISolver;
import org.baconberry.aoc2015.Point;
import org.baconberry.aoc2015.Utils;
import org.baconberry.aoc2015.ds.Grid;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Eighteen implements ISolver {

    char offValue = '.';
    char onValue = '#';
    int part = 1;

    @Setter
    private int iterations = 100;

    @Override
    public String solve(List<String> lines, int part) {
        this.part = part;
        var grid = Utils.parseCharGrid(lines);
        for (int i = 0; i < iterations; i++) {
            grid = activateLights(grid);
        }
        int result = countOn(grid);
        return String.valueOf(result);
    }

    private int countOn(Grid<Character> grid) {
        return (int) grid.stream()
                .flatMap(List::stream)
                .filter(c -> c == onValue)
                .count();
    }

    Grid<Character> activateLights(Grid<Character> grid) {
        setCornersOn(grid);
        var newGrid = grid.duplicate();
        grid.forEachPoint((p, c) -> {
            boolean isOn = c.equals(onValue);
            int countOnAround = countOnAround(grid, p);
            if (isOn && (countOnAround < 2 || countOnAround > 3)) {
                newGrid.setValue(p, offValue);
            } else if (!isOn && countOnAround == 3) {
                newGrid.setValue(p, onValue);
            }
        });
        setCornersOn(newGrid);
        return newGrid;
    }

    private int countOnAround(Grid<Character> grid, Point p) {
        return (int) Arrays.stream(Direction.ALL_DIRS)
                .map(p::plusDirection)
                .map(grid::findValue)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .filter(this::isOn)
                .count();
    }

    boolean isOn(Character c) {
        return c.equals(onValue);
    }

    void setCornersOn(Grid<Character> grid) {
        if(part!=2){
            return;
        }
        grid.setValue(0, 0, onValue);
        grid.setValue(0, grid.getWidth() - 1, onValue);
        grid.setValue(grid.getHeight() - 1, grid.getWidth() - 1, onValue);
        grid.setValue(grid.getHeight() - 1, 0, onValue);
    }
}
