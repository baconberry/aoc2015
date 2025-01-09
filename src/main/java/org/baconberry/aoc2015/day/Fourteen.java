package org.baconberry.aoc2015.day;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.baconberry.aoc2015.ISolver;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

public class Fourteen implements ISolver {
    Pattern pattern = Pattern.compile("(\\w+).* fly (\\d+) .* for (\\d+) .* (\\d+)");

    @Override
    public String solve(List<String> lines, int part) {
        int totalTime = 2503;
        var map = new HashMap<Reindeer, ReindeerState>();
        parseReindeer(lines, map);
        for (int t = 0; t < totalTime; t++) {
            activateReindeer(map);
            if (part == 2) {
                awardOneToWinners(map);
            }
        }

        Comparator<ReindeerState> cmp = part == 2
                ? Comparator.comparing(ReindeerState::getPoints)
                : Comparator.comparing(ReindeerState::getDistance);
        var e = getWinner(map, cmp);
        return "%s: %d, points: %d".formatted(e.reindeer.name, e.distance, e.points);
    }

    private static void activateReindeer(HashMap<Reindeer, ReindeerState> map) {
        for (ReindeerState state : map.values()) {
            if (state.timeRemaining == 0) {
                state.isResting = !state.isResting;
                if (state.isResting) {
                    state.timeRemaining = state.reindeer.restTime;
                } else {
                    state.timeRemaining = state.reindeer.speedTime;
                }
            }

            if (!state.isResting) {
                state.distance += state.reindeer.speed();
            }
            state.timeRemaining--;
        }
    }


    private ReindeerState getWinner(HashMap<Reindeer, ReindeerState> map, Comparator<ReindeerState> cmp) {
        var max = map.values().stream()
                .max(cmp);
        if (max.isEmpty()) {
            throw new IllegalStateException();
        }
        return max.get();
    }

    private void awardOneToWinners(HashMap<Reindeer, ReindeerState> map) {
        int max = map.values().stream()
                .mapToInt(ReindeerState::getDistance)
                .max()
                .orElse(0);

        map.values().stream()
                .filter(r -> r.distance == max)
                .forEach(r -> r.points++);
    }

    private void parseReindeer(List<String> lines, HashMap<Reindeer, ReindeerState> map) {
        for (String line : lines) {
            var matcher = pattern.matcher(line);
            if (!matcher.find()) {
                throw new IllegalArgumentException();
            }
            var name = matcher.group(1);
            int speed = Integer.parseInt(matcher.group(2));
            int speedTime = Integer.parseInt(matcher.group(3));
            int restTime = Integer.parseInt(matcher.group(4));
            var reindeer = new Reindeer(name, speed, speedTime, restTime);
            map.put(reindeer, new ReindeerState(0, speedTime, false, 0, reindeer));
        }
    }

    record Reindeer(String name, int speed, int speedTime, int restTime) {
    }

    @AllArgsConstructor
    @Getter
    class ReindeerState {
        int distance;
        int timeRemaining;
        boolean isResting;
        int points;
        Reindeer reindeer;
    }

}


