package org.baconberry.aoc2015.day;

import org.baconberry.aoc2015.ISolver;

import java.util.List;

public class Twenty implements ISolver {
    @Override
    public String solve(List<String> lines, int part) {
        int minPresents = Integer.parseInt(lines.getFirst());
        int c = 0;
        int n = 0;
        while(c<minPresents){
            c = calculatePresents(++n);
        }
        return String.valueOf(n);
    }

    int calculatePresents(int houseNumber){
        int sum = houseNumber*10;
        for (int i = 1; i <= houseNumber/2; i++) {
            if(houseNumber%i==0){
                sum += i*10;
            }
        }
        return sum;
    }
}
