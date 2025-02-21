package org.baconberry.aoc2015.day;

import org.baconberry.aoc2015.ISolver;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseTest {
    protected String solveInput(ISolver solver) throws IOException {
        var lines = readInput();
        return solver.solve(lines, 1);
    }
    protected String solveInput(ISolver solver, int part) throws IOException {
        var lines = readInput();
        return solver.solve(lines, part);
    }

    private List<String> readInput() throws IOException {
        FileReader fr = new FileReader("input.txt");
        BufferedReader br = new BufferedReader(fr);
        String line;
        List<String> result = new ArrayList<>();
        while ((line = br.readLine()) != null) {
            result.add(line);
        }
        return result;
    }
}
