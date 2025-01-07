package org.baconberry.aoc2015.day;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EightTest extends BaseTest{

    @Test
    void solve() {
        var solver = new Eight();
        assertEquals("3", solver.solve(List.of("\"aaa\\\"aaa\""), 1));
        assertEquals("2", solver.solve(List.of("\"\""), 1));
        assertEquals("5", solver.solve(List.of("\"\\x27\""), 1));
    }

    @Test
    void solveTwo() {
        var solver = new Eight();
        assertEquals("4", solver.solve(List.of("\"\""), 2));
        assertEquals("6", solver.solve(List.of("\"aaa\\\"aaa\""), 2));
        assertEquals("5", solver.solve(List.of("\"\\x27\""), 2));
        assertEquals("4", solver.solve(List.of("\"abc\""), 2));
    }

    @Disabled
    @Test
    void solveFile() throws IOException {
        solveInput(new Eight());
    }

}