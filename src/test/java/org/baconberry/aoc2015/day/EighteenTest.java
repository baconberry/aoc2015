package org.baconberry.aoc2015.day;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class EighteenTest extends BaseTest{

    @Test
    void solve() {
        var solver = new Eighteen();
        var text = """
                .#.#.#
                ...##.
                #....#
                ..#...
                #.#..#
                ####..
                """;
        solver.setIterations(4);
        assertEquals("4", solver.solve(Arrays.asList(text.split("\n")), 1));
    }

//    @Test
//    void ignore() throws IOException {
//        solveInput(new Eighteen());
//    }
}