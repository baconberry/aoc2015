package org.baconberry.aoc2015.day;

import lombok.AllArgsConstructor;
import org.baconberry.aoc2015.ISolver;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Nine implements ISolver {
    @Override
    public String solve(List<String> lines, int part) {
        var nodeMap = new HashMap<String, Node>();
        var pattern = Pattern.compile("(\\w+)\\s+to\\s+(\\w+)\\s+=\\s+(\\d+)");
        for (String line : lines) {
            var matcher = pattern.matcher(line);
            if (matcher.matches()) {
                var source = matcher.group(1);
                var dest = matcher.group(2);
                var distance = Integer.parseInt(matcher.group(3));
                var sourceNode = getOrCreateNode(nodeMap, source);
                var destNode = getOrCreateNode(nodeMap, dest);
                sourceNode.edges.add(new Edge(distance, destNode));
                destNode.edges.add(new Edge(distance, sourceNode));
            }
        }
        int initValue = Integer.MAX_VALUE;
        if (part == 2) {
            initValue = Integer.MIN_VALUE;
        }
        int currentVal = initValue;
        BiPredicate<Integer, Integer> matchPredicate = (local, current) -> local < current;
        if (part == 2) {
            matchPredicate = (local, current) -> local > current;
        }
        for (Node value : nodeMap.values()) {
            int localDist = walkFromNode(nodeMap, value, List.of(value), 0, matchPredicate, initValue);
            if (matchPredicate.test(localDist, currentVal)) {
                currentVal = localDist;
            }
        }
        return String.valueOf(currentVal);
    }

    private int walkFromNode(HashMap<String, Node> nodeMap, Node value, List<Node> path, int totalDist, BiPredicate<Integer, Integer> matchPredicate, int initValue) {
        if (path.size() == nodeMap.size()) {
            return totalDist;
        }
        int current = initValue;
        for (Edge edge : value.edges) {
            if (path.contains(edge.dest)) {
                continue;
            }
            int localDist = walkFromNode(nodeMap, edge.dest, cowList(path, edge.dest), totalDist + edge.distance, matchPredicate, initValue);
            if (matchPredicate.test(localDist, current)) {
                current = localDist;
            }
        }
        return current;
    }

    private <E> List<E> cowList(List<E> src, E toAdd) {
        return Stream.of(src, List.of(toAdd))
                .flatMap(List::stream)
                .toList();
    }

    private Node getOrCreateNode(HashMap<String, Node> nodeMap, String key) {
        return nodeMap.computeIfAbsent(key, k -> new Node(k, new HashSet<>()));
    }

    @AllArgsConstructor
    class Node {
        String id;
        Set<Edge> edges;
    }

    @AllArgsConstructor
    class Edge {
        int distance;
        Node dest;
    }
}
