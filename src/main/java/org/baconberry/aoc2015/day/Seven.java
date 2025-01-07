package org.baconberry.aoc2015.day;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.baconberry.aoc2015.ISolver;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Seven implements ISolver {
    @Override
    public String solve(List<String> lines, int part) {
        var valueMap = new HashMap<String, Integer>();
        var gateQueue = new LinkedList<Gate>();
        var initQueue = new LinkedList<Gate>();
        for (String line : lines) {
            if (line.isBlank()) {
                continue;
            }
            var parts = line.split("->");
            var firstPart = parts[0].trim().split(" ");
            if (firstPart.length == 1) {
                var gate = new DirectWire(firstPart[0].trim(), parts[1].trim());
                gateQueue.addLast(gate);
                if (part == 2 && parts[1].trim().equals("b")) {
                    continue;
                }
                initQueue.addLast(gate);
            } else {
                var gate = parseGate(parts[1].trim(), firstPart);
                gateQueue.addLast(gate);
                initQueue.addLast(gate);
            }
        }
        activateWires(gateQueue, valueMap);
        if (part == 2) {
            var aVal = valueMap.get("a");
            valueMap.clear();
            initQueue.addFirst(new DirectWire("%d".formatted(aVal), "b"));
            activateWires(initQueue, valueMap);
        }
        return String.valueOf(valueMap.get("a"));
    }

    private static void activateWires(LinkedList<Gate> gateQueue, HashMap<String, Integer> valueMap) {
        while (!gateQueue.isEmpty()) {
            var gate = gateQueue.pop();
            if (!gate.isCompleteWires(valueMap)) {
                gateQueue.addLast(gate);
                continue;
            }
            gate.activate(valueMap);
        }
    }

    private Gate parseGate(String out, String[] firstPart) {
        if (firstPart[0].equals("NOT")) {
            return new NotGate(firstPart[1].trim(), out);
        }
        var gateKey = firstPart[1].trim();
        var a = firstPart[0].trim();
        var b = firstPart[2].trim();
        return switch (gateKey) {
            case "AND" -> new AndGate(a, b, out);
            case "OR" -> new OrGate(a, b, out);
            case "LSHIFT" -> new LShift(a, out, Integer.parseInt(b));
            case "RSHIFT" -> new RShift(a, out, Integer.parseInt(b));
            default -> throw new UnsupportedOperationException();
        };
    }

    interface Gate {
        boolean isCompleteWires(Map<String, Integer> wireMap);

        void activate(Map<String, Integer> wireMap);

        default Integer getVal(Map<String, Integer> wireMap, String key) {
            if (StringUtils.isNumeric(key)) {
                return Integer.parseInt(key);
            }
            return wireMap.get(key);
        }
    }

    @AllArgsConstructor
    class DirectWire implements Gate {
        String in, out;

        @Override
        public boolean isCompleteWires(Map<String, Integer> wireMap) {
            return StringUtils.isNumeric(in) || wireMap.containsKey(in);
        }

        @Override
        public void activate(Map<String, Integer> wireMap) {
            if (StringUtils.isNumeric(in)) {
                wireMap.put(out, Integer.parseInt(in));
            } else {
                wireMap.put(out, wireMap.get(in));
            }
        }
    }

    @AllArgsConstructor
    class AndGate implements Gate {
        String inA, inB, out;

        @Override
        public boolean isCompleteWires(Map<String, Integer> wireMap) {
            boolean aComplete = StringUtils.isNumeric(inA) || wireMap.containsKey(inA);
            boolean bComplete = StringUtils.isNumeric(inB) || wireMap.containsKey(inB);
            return aComplete && bComplete;
        }

        @Override
        public void activate(Map<String, Integer> wireMap) {
            var aVal = getVal(wireMap, inA);
            var bVal = getVal(wireMap, inB);
            var val = aVal & bVal;
            wireMap.put(out, val);
        }
    }

    class OrGate extends AndGate {
        public OrGate(String inA, String inB, String out) {
            super(inA, inB, out);
        }

        @Override
        public void activate(Map<String, Integer> wireMap) {
            var aVal = getVal(wireMap, inA);
            var bVal = getVal(wireMap, inB);
            var val = aVal | bVal;
            wireMap.put(out, val);
        }
    }

    @AllArgsConstructor
    class LShift implements Gate {
        String in, out;
        int shift;

        @Override
        public boolean isCompleteWires(Map<String, Integer> wireMap) {
            return StringUtils.isNumeric(in) || wireMap.containsKey(in);
        }

        @Override
        public void activate(Map<String, Integer> wireMap) {
            var val = getVal(wireMap, in) << shift;
            wireMap.put(out, val);
        }
    }

    class RShift extends LShift {
        public RShift(String in, String out, int shift) {
            super(in, out, shift);
        }

        @Override
        public void activate(Map<String, Integer> wireMap) {
            var val = getVal(wireMap, in) >> shift;
            wireMap.put(out, val);
        }
    }

    class NotGate extends LShift {
        public NotGate(String in, String out) {
            super(in, out, 0);
        }

        @Override
        public void activate(Map<String, Integer> wireMap) {
            var val = ~getVal(wireMap, in);
            wireMap.put(out, val);
        }
    }
}
