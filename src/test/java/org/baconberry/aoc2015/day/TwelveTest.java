package org.baconberry.aoc2015.day;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TwelveTest {

    @Test
    void solve() {
        var solver = new Twelve();
        assertEquals("4", solver.solve(List.of("[1,{\"c\":\"red\",\"b\":2},3]"), 2));
        assertEquals("0", solver.solve(List.of("{\"d\":\"red\",\"e\":[1,2,3,4],\"f\":5}"), 2));
        assertEquals("6", solver.solve(List.of("[1,\"red\",5]"), 2));
    }
}