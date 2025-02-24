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
        return switch (day) {
            case 1 -> One.class;
            case 2 -> Two.class;
            case 3 -> Three.class;
            case 4 -> Four.class;
            case 5 -> Five.class;
            case 6 -> Six.class;
            case 7 -> Seven.class;
            case 8 -> Eight.class;
            case 9 -> Nine.class;
            case 10 -> Ten.class;
            case 11 -> Eleven.class;
            case 12 -> Twelve.class;
            case 13 -> Thirteen.class;
            case 14 -> Fourteen.class;
            case 15 -> Fifteen.class;
            case 16 -> Sixteen.class;
            case 17 -> Seventeen.class;
            case 18 -> Eighteen.class;
            case 20 -> Twenty.class;
            case 21 -> Twentyone.class;
            case 22 -> Twentytwo.class;
            case 23 -> Twentythree.class;
            case 24 -> Twentyfour.class;
            default -> throw new UnsupportedOperationException();
        };
    }
}
