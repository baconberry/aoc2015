package org.baconberry.aoc2015.day;

import org.baconberry.aoc2015.ISolver;
import org.baconberry.aoc2015.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

public class Sixteen implements ISolver {
    Map<String, Integer> mustMap = Map.of(
            "children", 3,
            "cats", 7,
            "samoyeds", 2,
            "pomeranians", 3,
            "akitas", 0,
            "vizslas", 0,
            "goldfish", 5,
            "trees", 3,
            "cars", 2,
            "perfumes", 1
    );

    Set<String> greaterThanSet = Set.of("cats", "trees");
    Set<String> fewerThan = Set.of("pomeranians", "goldfish");

    Pattern pattern = Pattern.compile("([a-z]+):");

    @Override
    public String solve(List<String> lines, int part) {
        var auntMap = parseAuntMap(lines);
        var validAunts = new ArrayList<Aunt>();
        for (Aunt aunt : auntMap.values()) {
            if (isCorrectAunt(aunt, part)) {
                validAunts.add(aunt);
            }
        }
        if (validAunts.size() == 1) {
            return String.valueOf(validAunts.getFirst().number);
        }
        return "NOT_FOUND";
    }

    private boolean isCorrectAunt(Aunt aunt, int part) {
        for (Map.Entry<String, Integer> e : aunt.things().entrySet()) {
            var k = e.getKey();
            var mustValue = mustMap.get(k);
            if (part == 2 && greaterThanSet.contains(k)) {
                if (mustValue.compareTo(e.getValue()) >= 0) {
                    return false;
                }
            } else if (part == 2 && fewerThan.contains(k)) {
                if (mustValue.compareTo(e.getValue()) <= 0) {
                    return false;
                }
            } else if (!mustValue.equals(e.getValue())) {
                return false;
            }
        }
        return true;
    }

    private HashMap<Integer, Aunt> parseAuntMap(List<String> lines) {
        var auntMap = new HashMap<Integer, Aunt>(lines.size());

        for (String line : lines) {
            var matcher = pattern.matcher(line);
            var numbers = Utils.lineToIntList(line);
            var things = matcher.results().map(MatchResult::group)
                    .map(s -> s.replace(":", ""))
                    .map(String::trim)
                    .toList();
            var auntThings = new HashMap<String, Integer>(3);
            for (int i = 0; i < things.size(); i++) {
                auntThings.put(things.get(i), numbers.get(i + 1));
            }
            auntMap.put(numbers.getFirst(), new Aunt(numbers.getFirst(), auntThings));
        }
        return auntMap;
    }

    record Aunt(int number, Map<String, Integer> things) {
    }
}
