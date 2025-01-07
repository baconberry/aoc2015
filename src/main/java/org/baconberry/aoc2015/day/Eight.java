package org.baconberry.aoc2015.day;

import org.baconberry.aoc2015.ISolver;

import java.util.List;

public class Eight implements ISolver {
    @Override
    public String solve(List<String> lines, int part) {
        int result = 0;
        for (String line : lines) {
            if (part == 2) {
                var expanded = expand(line);
                result += expanded.length() - line.length();
            } else {
                result += line.length();
                var memRep = toMemoryRepresentation(line);
                result -= memRep.length();
            }
        }
        return String.valueOf(result);
    }

    String replaceWith = "S";

    private String toMemoryRepresentation(String line) {
        var result = line;

        result = result.substring(1);
        result = result.substring(0, result.length() - 1);
        result = result.replaceAll("\\\\\"", replaceWith);
        result = result.replaceAll("\\\\x[0-9abcdef]{2}", replaceWith);
        result = result.replaceAll("\\\\\\\\", replaceWith);
        return result;
    }

    private String expand(String line) {
        var sb = new StringBuilder();
        sb.append("\"");
        for (char c : line.toCharArray()) {
            String val = switch (c) {
                case '"' -> "\\\"";
                case '\\' -> "\\\\";
                default -> "" + c;
            };
            sb.append(val);
        }
        sb.append("\"");
        return sb.toString();
    }
}
