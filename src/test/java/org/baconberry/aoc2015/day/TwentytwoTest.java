package org.baconberry.aoc2015.day;

import org.junit.jupiter.api.Test;

import java.util.List;


class TwentytwoTest {

    @Test
    void solve() {
        var solver = new Twentytwo();
        var result = solver.solve(List.of(), 1);
        System.out.println(result);
    }
    @Test
    void solveTwo() {
        var solver = new Twentytwo();
        var result = solver.solve(List.of(), 2);
        System.out.println(result);
    }
}