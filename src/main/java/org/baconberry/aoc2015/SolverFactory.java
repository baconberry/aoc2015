package org.baconberry.aoc2015;

import org.baconberry.aoc2015.day.Four;
import org.baconberry.aoc2015.day.One;
import org.baconberry.aoc2015.day.Three;
import org.baconberry.aoc2015.day.Two;

public final class SolverFactory {
    private SolverFactory() {
    }

    public static ISolver create(int day) {
        var czz = getDayClass(day);
        try {
            return czz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static Class<? extends ISolver> getDayClass(int day) {
        switch (day) {
            case 1:
                return One.class;
            case 2:
                return Two.class;
            case 3:
                return Three.class;
            case 4:
                return Four.class;
            default:
                throw new UnsupportedOperationException();
        }
    }
}
