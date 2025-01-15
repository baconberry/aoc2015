package org.baconberry.aoc2015.day;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
}