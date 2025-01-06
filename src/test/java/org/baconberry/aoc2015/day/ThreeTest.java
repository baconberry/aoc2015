package org.baconberry.aoc2015.day;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ThreeTest {

    @Test
    void solve() {
        var solver = new Three();
        assertEquals("2", solver.solve(List.of(">"), 1));
        assertEquals("4", solver.solve(List.of("^>v<"), 1));
        assertEquals("2", solver.solve(List.of("^v^v^v^v^v"), 1));

        assertEquals("3", solver.solve(List.of("^v"), 2));
        assertEquals("3", solver.solve(List.of("^>v<"), 2));
        assertEquals("11", solver.solve(List.of("^v^v^v^v^v"), 2));
    }
}