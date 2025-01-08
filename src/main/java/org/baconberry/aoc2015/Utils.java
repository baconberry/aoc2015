package org.baconberry.aoc2015;

import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

public class Utils {
    private Utils() {
    }

    static Pattern singleNumber = Pattern.compile("(-?\\d+)");

    public static List<Integer> lineToIntList(String line) {
        var matcher = singleNumber.matcher(line);
        return matcher.results()
                .map(MatchResult::group)
                .map(Integer::parseInt)
                .toList();
    }
}
