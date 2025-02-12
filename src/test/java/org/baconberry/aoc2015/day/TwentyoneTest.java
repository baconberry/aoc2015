package org.baconberry.aoc2015.day;

import org.junit.jupiter.api.Test;

import java.util.List;

class TwentyoneTest {

    @Test
    void solve() {
        var solver = new Twentyone();
        var res = solver.solve(List.of(), 1);
        System.out.println(res);
    }
}