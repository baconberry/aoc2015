package org.baconberry.aoc2015.day;

import org.apache.commons.lang3.tuple.Pair;
import org.baconberry.aoc2015.ISolver;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Nineteen implements ISolver {
    @Override
    public String solve(List<String> lines, int part) {

        String word = null;
        List<Pair<String, String>> replacements = new ArrayList<>();
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if (line.isBlank()) {
                word = lines.get(i + 1);
                break;
            }

            var parts = line.split("=>");
            replacements.add(Pair.of(parts[0].trim(), parts[1].trim()));
        }
        assert word != null;


        Set<String> permutations = new HashSet<>();

        permutate(word, "", replacements, permutations);

        return String.valueOf(permutations.size());
    }

    void permutate(String word, String prefix, List<Pair<String, String>> replacements, Set<String> permutations) {
        if (word.isEmpty()) {
            return;
        }
        for (Pair<String, String> rep : replacements) {
            var k = rep.getKey();
            var v = rep.getValue();
            if (word.startsWith(k)) {
                permutations.add("%s%s%s".formatted(
                        prefix,
                        v,
                        word.substring(k.length())
                ));
            }
        }

        permutate(
                word.substring(1),
                "%s%s".formatted(prefix, word.substring(0, 1)),
                replacements,
                permutations
        );
    }
}
