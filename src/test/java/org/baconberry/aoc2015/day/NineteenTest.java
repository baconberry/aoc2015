package org.baconberry.aoc2015.day;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NineteenTest {

    @Test
    void solve() {
        var input = """
                H => HO
                H => OH
                O => HH
                                
                HOH
                """;

        var solver = new Nineteen();
        assertEquals("4", solver.solve(Arrays.asList(input.split("\n")), 1));
    }
}