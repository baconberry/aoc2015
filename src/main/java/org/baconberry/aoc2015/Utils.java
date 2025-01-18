package org.baconberry.aoc2015;

import org.baconberry.aoc2015.ds.Grid;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
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

    public static <E> Grid<E> parseGrid(List<String> lines, Function<String, E[]> fn, Function<List<String>, E[][]> initFn) {
        if (lines.isEmpty()) {
            return Grid.emptyGrid();
        }
        E[][] g = initFn.apply(lines);

        for (int i = 0; i < lines.size(); i++) {
            g[i] = fn.apply(lines.get(i));
        }
        return Grid.of(g);
    }

    public static Grid<Character> parseCharGrid(List<String> lines) {
        lines = trimEmptyLines(lines);
        Function<String, Character[]> fn = line -> {
            var chars = line.toCharArray();
            var arr = new Character[chars.length];
            for (int i = 0; i < chars.length; i++) {
                arr[i] = chars[i];
            }
            return arr;
        };
        Function<List<String>, Character[][]> initFn = ls -> new Character[ls.size()][ls.getFirst().length()];
        return parseGrid(lines, fn, initFn);
    }

    private static List<String> trimEmptyLines(List<String> lines) {
        return lines.stream()
                .filter(Predicate.not(String::isBlank))
                .toList();
    }
}
