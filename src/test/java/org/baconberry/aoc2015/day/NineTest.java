package org.baconberry.aoc2015.day;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NineTest {


    @Test
    void solve() {
        var solver = new Nine();
        var lines = """
                London to Dublin = 464
                London to Belfast = 518
                Dublin to Belfast = 141
                                """.split("\n");
        assertEquals("605", solver.solve(Arrays.asList(lines), 1));
        assertEquals("982", solver.solve(Arrays.asList(lines), 2));
    }
}