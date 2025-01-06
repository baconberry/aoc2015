package org.baconberry.aoc2015;


import java.util.List;

@FunctionalInterface
public interface ISolver {
    String solve(List<String> lines, int part);
}
