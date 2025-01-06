package org.baconberry.aoc2015;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


@Slf4j
public class Main {
    public static void main(String[] args) throws IOException {
        int day = Integer.parseInt(args[0]);
        int part = Integer.parseInt(args[1]);
        var solver = SolverFactory.create(day);
        var lines = readLines();
        var result = solver.solve(lines, part);
        log.info("Day {} part {} result: [{}]", day, part, result);
    }

    private static List<String> readLines() throws IOException {
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(isr);
        var result = new ArrayList<String>();
        String line;
        while ((line = reader.readLine()) != null) {
            result.add(line);
        }
        return result;
    }
}