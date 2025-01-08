package org.baconberry.aoc2015.day;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ElevenTest {

    @Test
    void solve() {
        var solver = new Eleven();
        assertEquals("abcdffaa", solver.solve(List.of("abcdefgh"), 1));
    }
}