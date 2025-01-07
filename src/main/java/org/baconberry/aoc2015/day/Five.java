package org.baconberry.aoc2015.day;

import org.apache.commons.lang3.StringUtils;
import org.baconberry.aoc2015.ISolver;

import java.util.List;

public class Five implements ISolver {
    @Override
    public String solve(List<String> lines, int part) {
        int result = 0;
        for (String line : lines) {
            if ((part == 1 && isNice(line)) ||
                    (part == 2 && isNiceImproved(line))) {
                result++;
            }
        }
        return String.valueOf(result);
    }

    boolean isNice(String line) {
        return hasEnoughVowels(line)
                && hasRepeatedChar(line)
                && !containsForbidden(line);
    }

    boolean isNiceImproved(String line) {
        return hasPairAtLeastTwice(line) && hasLetterBetweenRepeats(line);
    }

    private boolean hasLetterBetweenRepeats(String line) {
        for (int i = 0; i < line.toCharArray().length - 2; i++) {
            if (line.charAt(i) == line.charAt(i + 2)) {
                return true;
            }
        }
        return false;
    }

    private static boolean hasPairAtLeastTwice(String line) {
        char past = line.charAt(0);
        for (int i = 1; i < line.toCharArray().length; i++) {
            char c = line.charAt(i);
            if (StringUtils.contains(line.substring(i + 1), "%s%s".formatted(past, c))) {
                return true;
            }
            past = c;
        }
        return false;
    }

    private boolean containsForbidden(String line) {
        return StringUtils.containsAny(line, "ab", "cd", "pq", "xy");
    }

    private boolean hasRepeatedChar(String line) {
        char past = line.charAt(0);
        for (int i = 1; i < line.toCharArray().length; i++) {
            char c = line.charAt(i);
            if (c == past) {
                return true;
            }
            past = c;
        }
        return false;
    }

    private boolean hasEnoughVowels(String line) {
        return line.replaceAll("(?i)[^aeiou]", "").length() >= 3;
    }


}
