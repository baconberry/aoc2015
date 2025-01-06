package org.baconberry.aoc2015.day;

import org.baconberry.aoc2015.ISolver;
import org.baconberry.aoc2015.Utils;

import java.util.List;

public class Two implements ISolver {
    @Override
    public String solve(List<String> lines, int part) {
        int result = 0;
        for (String line : lines) {
            if (line.isBlank()) {
                continue;
            }
            var numbers = Utils.lineToIntList(line);
            int l = numbers.getFirst();
            int w = numbers.get(1);
            int h = numbers.get(2);
            if (part == 1) {
                result += partOne(l, w, h);
            } else {
                result += partTwo(l, w, h);

            }
        }
        return String.valueOf(result);
    }

    private int partOne(int l, int w, int h) {
        int result = 0;
        result += 2 * l * w;
        result += 2 * l * h;
        result += 2 * w * h;
        result += Math.min(Math.min(l * h, w * h), w * l);
        return result;
    }

    private int partTwo(int l, int w, int h) {
        int result = 0;
        result += Math.min(Math.min(l + h, w + h), w + l) * 2;
        result += l * w * h;
        return result;
    }
}
