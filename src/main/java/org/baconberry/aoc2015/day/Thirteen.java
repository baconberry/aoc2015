package org.baconberry.aoc2015.day;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.ArrayUtils;
import org.baconberry.aoc2015.CollectionUtils;
import org.baconberry.aoc2015.ISolver;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Pattern;

public class Thirteen implements ISolver {

    Pattern pattern = Pattern.compile("(\\w+) would (\\w+) (\\d+) .* next to (\\w+)");

    @Override
    public String solve(List<String> lines, int part) {
        var nodeMap = new HashMap<String, Node>();
        parseNodes(lines, nodeMap);
        if (part == 2) {
            addSelfNode(nodeMap);
        }
        int result = calculateHappinessSeating(nodeMap, new String[0], 0);
        return String.valueOf(result);
    }

    private void addSelfNode(HashMap<String, Node> nodeMap) {
        var selfName = UUID.randomUUID().toString();
        var selfNode = getOrCreateNode(nodeMap, selfName);
        for (Node value : nodeMap.values()) {
            value.edges.add(new Edge(value, selfNode, 0));
            selfNode.edges.add(new Edge(selfNode, value, 0));
        }
        nodeMap.put(selfName, selfNode);
    }

    int calculateHappinessSeating(Map<String, Node> nodeMap, String[] seating, int happinessChange) {
        if (nodeMap.size() == seating.length) {
            return happinessChange + getSeatChange(nodeMap, seating[seating.length - 1], seating[0]);
        }

        int maxChange = Integer.MIN_VALUE;
        for (Node value : nodeMap.values()) {
            if (ArrayUtils.contains(seating, value.name)) {
                continue;
            }
            var newSeating = CollectionUtils.cowAdd(seating, value.name);
            int change = 0;
            if (newSeating.length > 1) {
                change = getSeatChange(nodeMap, newSeating[newSeating.length - 1], newSeating[newSeating.length - 2]);
            }
            int localResult = calculateHappinessSeating(nodeMap, newSeating, happinessChange + change);
            if (localResult > maxChange) {
                maxChange = localResult;
            }
        }
        return maxChange;
    }

    private int getSeatChange(Map<String, Node> nodeMap, String a, String b) {
        int change = 0;
        change += findSeating(nodeMap, a, b)
                .map(Edge::getHappinessChange)
                .orElse(0);
        change += findSeating(nodeMap, b, a)
                .map(Edge::getHappinessChange)
                .orElse(0);
        return change;
    }

    private Optional<Edge> findSeating(Map<String, Node> nodeMap, String src, String dest) {
        if (!nodeMap.containsKey(src)) {
            return Optional.empty();
        }

        for (Edge edge : nodeMap.get(src).edges) {
            if (edge.dst.name.equals(dest)) {
                return Optional.of(edge);
            }
        }
        return Optional.empty();
    }

    private void parseNodes(List<String> lines, HashMap<String, Node> nodeMap) {
        for (String line : lines) {
            var matcher = pattern.matcher(line);
            if (!matcher.find()) {
                throw new IllegalArgumentException();
            }

            var src = getOrCreateNode(nodeMap, matcher.group(1));
            var dest = getOrCreateNode(nodeMap, matcher.group(4));
            int happiness = Integer.parseInt(matcher.group(3));
            if (matcher.group(2).contains("lose")) {
                happiness *= -1;
            }
            var edge = new Edge(src, dest, happiness);
            src.edges.add(edge);
        }
    }

    private Node getOrCreateNode(HashMap<String, Node> nodeMap, String key) {
        return nodeMap.computeIfAbsent(key, k -> new Node(k, new HashSet<>()));
    }

    @AllArgsConstructor
    class Node {
        String name;
        Set<Edge> edges;
    }

    @AllArgsConstructor
    @Getter
    class Edge {
        Node src;
        Node dst;
        int happinessChange;
    }

}
