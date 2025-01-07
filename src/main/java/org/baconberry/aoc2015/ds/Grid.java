package org.baconberry.aoc2015.ds;

import org.apache.commons.lang3.function.TriConsumer;

public class Grid<E> {
    E[][] arr;

    private Grid(E[][] g) {
        this.arr = g;
    }

    public static <T> Grid<T> of(T[][] g) {
        return new Grid<>(g);
    }

    public static Grid<Boolean> ofBool(int rows, int cols, boolean initValue) {
        var g = new Boolean[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                g[i][j] = initValue;
            }
        }
        return of(g);
    }

    public static Grid<Integer> ofInt(int rows, int cols, int initValue) {
        var g = new Integer[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                g[i][j] = initValue;
            }
        }
        return of(g);
    }

    public void setValue(int x, int y, E val) {
        arr[y][x] = val;
    }

    public E getValue(int x, int y) {
        return arr[y][x];
    }

    public void forEach(TriConsumer<Integer, Integer, E> consumer) {
        if (consumer == null) {
            return;
        }
        for (int y = 0; y < arr.length; y++) {
            for (int x = 0; x < arr[y].length; x++) {
                consumer.accept(x, y, arr[y][x]);
            }
        }

    }
}
