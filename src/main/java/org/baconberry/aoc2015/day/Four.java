package org.baconberry.aoc2015.day;

import org.apache.commons.codec.digest.DigestUtils;
import org.baconberry.aoc2015.ISolver;

import java.util.List;

public class Four implements ISolver {
    @Override
    public String solve(List<String> lines, int part) {
        var line = lines.getFirst();
        final int repeats = part == 1 ? 5 : 6;
        final String startsWith = "0".repeat(repeats);
        for (int i = 0; ; i++) {
            var secret = "%s%d".formatted(line, i);
            if (DigestUtils.md5Hex(secret).startsWith(startsWith)) {
                return String.valueOf(i);
            }
        }
    }


}
