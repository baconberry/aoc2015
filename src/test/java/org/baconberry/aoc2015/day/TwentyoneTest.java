package org.baconberry.aoc2015.day;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TwentyoneTest {

    @Test
    void solve() {
        var solver = new Twentyone();
        var res = solver.solve(List.of(), 1);
        assertEquals("111", res);
    }

    @Test
    void solveTwo() {
        var solver = new Twentyone();
        var res = solver.solve(List.of(), 2);
        assertEquals("188", res);
    }
}