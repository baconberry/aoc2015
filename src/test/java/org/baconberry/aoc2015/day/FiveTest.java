package org.baconberry.aoc2015.day;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FiveTest {

    @Test
    void solve() {
        var solver = new Five();
        assertEquals("1", solver.solve(List.of("ugknbfddgicrmopn"), 1));
        assertEquals("1", solver.solve(List.of("aaa"), 1));
        assertEquals("0", solver.solve(List.of("jchzalrnumimnmhp",
                "haegwjzuvuyypxyu",
                "dvszwmarrgswjxmb"), 1));

        assertEquals("1", solver.solve(List.of("qjhvhtzxzqqjkmpb"), 2));
        assertEquals("1", solver.solve(List.of("xxyxx"), 2));
        assertEquals("0", solver.solve(List.of("uurcxstgmygtbstg"), 2));
        assertEquals("0", solver.solve(List.of("ieodomkazucvgmuy"), 2));
    }
}