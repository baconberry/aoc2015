package org.baconberry.aoc2015.day;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class FifteenTest {

    @Test
    void solve() {
        var solver = new Fifteen();
        var lines = """
                Butterscotch: capacity -1, durability -2, flavor 6, texture 3, calories 8
                Cinnamon: capacity 2, durability 3, flavor -2, texture -1, calories 3
                """.split("\n");
        assertEquals("62842880", solver.solve(Arrays.asList(lines), 1));
        assertEquals("57600000", solver.solve(Arrays.asList(lines), 2));
    }
}