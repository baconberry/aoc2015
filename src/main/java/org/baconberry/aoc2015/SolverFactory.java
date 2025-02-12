package org.baconberry.aoc2015;

import org.baconberry.aoc2015.day.*;

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
            case 5:
                return Five.class;
            case 6:
                return Six.class;
            case 7:
                return Seven.class;
            case 8:
                return Eight.class;
            case 9:
                return Nine.class;
            case 10:
                return Ten.class;
            case 11:
                return Eleven.class;
            case 12:
                return Twelve.class;
            case 13:
                return Thirteen.class;
            case 14:
                return Fourteen.class;
            case 15:
                return Fifteen.class;
            case 16:
                return Sixteen.class;
            case 17:
                return Seventeen.class;
            case 18:
                return Eighteen.class;
            case 20:
                return Twenty.class;
            case 21:
                return Twentyone.class;
            default:
                throw new UnsupportedOperationException();
        }
    }
}
