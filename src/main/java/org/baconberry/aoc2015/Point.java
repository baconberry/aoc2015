package org.baconberry.aoc2015;

public record Point(int x, int y) {

    public static Point of(int pX, int pY) {
        return new Point(pX, pY);
    }

    public Point plusDirection(Direction direction) {
        var diff = direction.cardinalDiff();
        return Point.of(this.x() + diff.x(), this.y() + diff.y());
    }
}
