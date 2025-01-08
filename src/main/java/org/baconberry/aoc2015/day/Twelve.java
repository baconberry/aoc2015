package org.baconberry.aoc2015.day;

import org.baconberry.aoc2015.ISolver;
import org.baconberry.aoc2015.Utils;

import java.util.List;

public class Twelve implements ISolver {
    @Override
    public String solve(List<String> lines, int part) {
        int result = 0;
        for (String line : lines) {
            if (part == 2) {
                line = removeReds(line);
            }
            for (Integer i : Utils.lineToIntList(line)) {
                result += i;
            }
        }
        return String.valueOf(result);
    }

    private String removeReds(String line) {
        int redIdx = line.indexOf(":\"red\"");
        if (redIdx < 0) {
            return line;
        }
        int bracketIdx = 0;
        int openLevel = 1;
        for (int i = redIdx - 1; i >= 0; i--) {
            char c = line.charAt(i);
            if (c == '}') {
                openLevel++;
            } else if (c == '{') {
                openLevel--;
            }
            if (openLevel == 0) {
                bracketIdx = i;
                break;
            }
        }
        int level = 1;
        for (int i = redIdx; i < line.length(); i++) {
            char c = line.charAt(i);
            if (c == '}') {
                level--;
            } else if (c == '{') {
                level++;
            }
            if (level == 0) {
                return removeReds(line.substring(0, bracketIdx) + line.substring(i + 1));
            }
        }
        return line;
    }
}
