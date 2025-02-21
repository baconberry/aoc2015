package org.baconberry.aoc2015.day;

import org.baconberry.aoc2015.ISolver;

import java.util.ArrayList;
import java.util.List;

public class Twentythree implements ISolver {
    @Override
    public String solve(List<String> lines, int part) {
        var emu = new Emu();
        emu.code = new ArrayList<>(lines);
        if (part == 2) {
            emu.a = 1;
        }
        emu.execute();

        return String.valueOf(emu.b);
    }

    private class Emu {
        long a;
        long b;
        int pc;

        ArrayList<String> code;

        void execute() {
            String line;
            while ((line = getLineCode()) != null) {
                String[] parts = line.split(" ");
                int offset;
                switch (parts[0]) {
                    case "hlf":
                        if (parts[1].startsWith("a")) {
                            a /= 2;
                        } else {
                            b /= 2;
                        }
                        break;
                    case "tpl":
                        if (parts[1].startsWith("a")) {
                            a *= 3;
                        } else {
                            b *= 3;
                        }
                        break;
                    case "inc":
                        if (parts[1].startsWith("a")) {
                            a++;
                        } else {
                            b++;
                        }
                        break;
                    case "jmp":
                        offset = Integer.parseInt(parts[1]);
                        pc += offset;
                        continue;
                    case "jie":
                        offset = Integer.parseInt(parts[2]);
                        if (parts[1].startsWith("a") && a % 2 == 0) {
                            pc += offset;
                            continue;
                        } else if (parts[1].startsWith("b") && b % 2 == 0) {
                            pc += offset;
                            continue;
                        }
                        break;
                    case "jio":
                        offset = Integer.parseInt(parts[2]);
                        if (parts[1].startsWith("a") && a == 1) {
                            pc += offset;
                            continue;
                        } else if (parts[1].startsWith("b") && b == 1) {
                            pc += offset;
                            continue;
                        }
                        break;
                    default:
                        throw new IllegalArgumentException("Code " + parts[0] + " not supported");
                }
                pc++;
            }
        }

        private String getLineCode() {
            if (pc < 0 || pc >= code.size()) {
                return null;
            }
            return code.get(pc);
        }
    }
}
