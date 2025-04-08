package org.baconberry.aoc2015.day;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SeventeenTest {

    @Test
    void solve() {
        var solver = new Seventeen();
        solver.setLiters(25);
        var text = """
                20
                15
                10
                5
                5
                """;
        assertEquals("4", solver.solve(Arrays.asList(text.split("\n")), 1));
    }
    @Test
    void solve_part2() {
        var solver = new Seventeen();
        solver.setLiters(25);
        var text = """
                20
                15
                10
                5
                5
                """;
        assertEquals("3", solver.solve(Arrays.asList(text.split("\n")), 2));
    }
}