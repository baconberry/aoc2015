package org.baconberry.aoc2015;

public enum Direction {
    N, S, E, W, NONE;


    public static Direction parseChar(char c) {
        return switch (c) {
            case '^' -> N;
            case 'v' -> S;
            case '<' -> W;
            case '>' -> E;
            default -> NONE;
        };
    }

    public Point cardinalDiff() {
        return switch (this) {
            case N -> Point.of(0, 1);
            case S -> Point.of(0, -1);
            case E -> Point.of(1, 0);
            case W -> Point.of(-1, 0);
            case NONE -> Point.of(0, 0);
        };
    }
}
