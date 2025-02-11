package org.baconberry.aoc2015.day;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TwentyTest extends BaseTest {

    @Test
    void solve() {
        var solver = new Twenty();
        assertEquals("6", solver.solve(List.of("120"), 1));
    }
}