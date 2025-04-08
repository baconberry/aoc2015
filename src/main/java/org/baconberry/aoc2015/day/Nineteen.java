package org.baconberry.aoc2015.day;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.baconberry.aoc2015.ISolver;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.baconberry.aoc2015.CollectionUtils.cowSet;

@Slf4j
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
        if (part == 2) {
            List<Pair<String, String>> invReplacements = replacements.stream()
                    .map(p -> Pair.of(p.getValue(), p.getKey()))
                    .toList();

            return String.valueOf(unbuildMolecule(
                    word, "e", invReplacements, 1,
                    new HashMap<>(), new HashSet<>()));
        }


        Set<String> permutations = new HashSet<>();

        permutate(word, "", replacements, permutations);

        return String.valueOf(permutations.size());
    }

    int unbuildMolecule(String molecule, String target,
                        List<Pair<String, String>> invReplacements, int level,
                        Map<String, Integer> memo, Set<String> ancestors) {
        if (memo.containsKey(molecule)) {
            return memo.get(molecule);
        }

        Set<String> permutations = new HashSet<>();
        permutate(molecule, "", invReplacements, permutations);
        if (permutations.contains(target)) {
            return level;
        }
        List<String> orderedPermutations = new ArrayList<>(permutations);
        orderedPermutations.sort(Comparator.comparingInt(String::length));
        for (String permutation : orderedPermutations) {
            if (permutation.length() > molecule.length()) {
                log.warn("permutation increasing size");
            }
            if (ancestors.contains(permutation)) {
                log.warn("Circular dependency detected, terminating");
                return -1;
            }
            int r = unbuildMolecule(permutation, target, invReplacements, level + 1, memo, cowSet(ancestors, permutation));
            if (r > 0) {
                return r;
            }
            memo.put(permutation, r);
        }
        return -1;
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
