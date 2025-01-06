package org.baconberry.aoc2015.day;

import org.baconberry.aoc2015.Direction;
import org.baconberry.aoc2015.ISolver;
import org.baconberry.aoc2015.Point;

import java.util.HashSet;
import java.util.List;

public class Three implements ISolver {
    @Override
    public String solve(List<String> lines, int part) {
        var currentPoint = Point.of(0, 0);
        var santaSet = new HashSet<Point>();
        var santaPoint = currentPoint;
        var roboSantaPoint = currentPoint;
        var roboSantaSet = new HashSet<Point>();
        var currentSet = roboSantaSet;
        santaSet.add(currentPoint);
        roboSantaSet.add(currentPoint);

        for (String line : lines) {
            for (char c : line.toCharArray()) {
                var direction = Direction.parseChar(c);
                if (part == 2) {
                    if (currentSet == santaSet) {
                        currentSet = roboSantaSet;
                        roboSantaPoint = roboSantaPoint.plusDirection(direction);
                        currentSet.add(roboSantaPoint);
                    } else {
                        currentSet = santaSet;
                        santaPoint = santaPoint.plusDirection(direction);
                        currentSet.add(santaPoint);
                    }
                } else {
                    currentPoint = currentPoint.plusDirection(direction);
                    santaSet.add(currentPoint);
                }
            }
        }
        if (part == 2) {
            santaSet.addAll(roboSantaSet);
        }
        return String.valueOf(santaSet.size());
    }


}
