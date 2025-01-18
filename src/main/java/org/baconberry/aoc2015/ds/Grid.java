package org.baconberry.aoc2015.ds;

import org.apache.commons.lang3.function.TriConsumer;
import org.baconberry.aoc2015.Point;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.stream.Stream;

public class Grid<E> {
    final int height;
    final int width;

    final ArrayList<ArrayList<E>> arr;

    private Grid(E[][] g) {
        this(arrToList(g));
    }

    private Grid(ArrayList<ArrayList<E>> l) {
        if (l.isEmpty()) {
            height = 0;
            width = 0;
        } else {
            height = l.size();
            width = l.getFirst().size();
        }
        arr = l;
    }

    private static <E> ArrayList<ArrayList<E>> arrToList(E[][] g) {
        var arrList = new ArrayList<ArrayList<E>>(g.length);
        for (E[] es : g) {
            var l = new ArrayList<E>(es.length);
            Collections.addAll(l, es);
            arrList.add(l);
        }
        return arrList;
    }

    public static <T> Grid<T> emptyGrid() {

        return new Grid<>(new ArrayList<>());
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
        arr.get(y).set(x, val);
    }

    public void setValue(Point p, E val) {
        setValue(p.x(), p.y(), val);
    }

    public E getValue(int x, int y) {
        return arr.get(y).get(x);
    }

    public void forEach(TriConsumer<Integer, Integer, E> consumer) {
        if (consumer == null) {
            return;
        }
        for (int y = 0; y < arr.size(); y++) {
            for (int x = 0; x < arr.get(y).size(); x++) {
                consumer.accept(x, y, getValue(x, y));
            }
        }
    }

    public void forEachPoint(BiConsumer<Point, E> consumer) {
        if (consumer == null) {
            return;
        }
        forEach((x, y, c) -> consumer.accept(Point.of(x, y), c));
    }

    public Optional<E> findValue(Point point) {
        if (point.x() < 0 || point.x() >= width
                || point.y() < 0 || point.y() >= height) {
            return Optional.empty();
        }
        return Optional.of(getValue(point.x(), point.y()));
    }

    public Grid<E> duplicate() {
        var r = new ArrayList<ArrayList<E>>(arr.size());
        for (ArrayList<E> es : arr) {
            var l = new ArrayList<E>(es.size());
            l.addAll(es);
            r.add(l);
        }
        return new Grid<>(r);
    }

    public Stream<ArrayList<E>> stream() {
        return arr.stream();
    }
}
