package org.baconberry.aoc2015.day;

import org.baconberry.aoc2015.ISolver;

import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

public class Ten implements ISolver {
    @Override
    public String solve(List<String> lines, int part) {
        var line = lines.getFirst();
        String result;
        if (part == 2) {
            result = lookAndSay(line, 50);
        } else {
            result = lookAndSay(line, 40);
        }

        return String.valueOf(result.length());
    }

    Pattern pattern = Pattern.compile("(\\d)\\1*");

    String lookAndSay(String line, int level) {
        if (level == 0) {
            return line;
        }
        var matcher = pattern.matcher(line);
        var groups = matcher.results()
                .map(MatchResult::group)
                .toList();
        var sb = new StringBuilder();
        for (String group : groups) {
            sb.append("%s%s".formatted(group.length(), group.charAt(0)));
        }
        return lookAndSay(sb.toString(), level - 1);
    }
}
