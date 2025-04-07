package org.baconberry.aoc2015.day;

import org.baconberry.aoc2015.ISolver;
import org.baconberry.aoc2015.Utils;

import java.util.List;

public class TwentyFive implements ISolver {
    @Override
    public String solve(List<String> lines, int part) {
        List<Integer> numbers = Utils.lineToIntList(lines.getFirst());
        return getCode(numbers.getFirst(), numbers.getLast());
    }

    public String getCode(int row, int col) {
        var ip = new InfinitePaper(row, col);
        return String.valueOf(ip.getCode(col, row));
    }


    static class InfinitePaper {
        final int rows;
        final int columns;
        final long[][] data;

        InfinitePaper(int rowToCalculate, int colToCalculate) {
            rows = rowToCalculate + colToCalculate;
            columns = rows;
            data = new long[rows + 1][columns + 1];
            fillData();
        }

        long getCode(int col, int row) {
            return data[row - 1][col - 1];
        }

        void fillData() {
            long n = 20151125;
            int col;
            int row;
            for (int i = 0; i <= columns; i++) {
                row = i;
                col = 0;
                while (row >= 0) {
                    data[row][col] = n;
                    n = (n * 252533) % 33554393;
                    col++;
                    row--;
                }
            }

        }
    }
}
