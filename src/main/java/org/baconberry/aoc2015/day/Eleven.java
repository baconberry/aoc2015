package org.baconberry.aoc2015.day;

import org.apache.commons.lang3.StringUtils;
import org.baconberry.aoc2015.ISolver;

import java.util.List;

public class Eleven implements ISolver {
    @Override
    public String solve(List<String> lines, int part) {
        var pwd = lines.getFirst();
        var next = nextPassword(pwd, pwd.length() - 1);
        while (isNotSafePassword(next)) {
            next = nextPassword(next, pwd.length() - 1);
        }
        if (part == 2) {
            next = nextPassword(next, pwd.length() - 1);
            while (isNotSafePassword(next)) {
                next = nextPassword(next, pwd.length() - 1);
            }
        }
        return next;
    }

    private boolean isNotSafePassword(String pwd) {
        return !hasContiguousChars(pwd) || containsIllegalChars(pwd) || !hasAtLeastTwoPairs(pwd, "");
    }

    private boolean hasAtLeastTwoPairs(String pwd, String prevPair) {
        if (pwd.isBlank()) {
            return false;
        }
        char prev = pwd.charAt(0);
        for (int i = 1; i < pwd.toCharArray().length; i++) {
            char c = pwd.charAt(i);
            if (c == prev) {
                var pair = "" + c + c;
                prev = c;
                if (prevPair.equals(pair)) {
                    continue;
                }
                if (!prevPair.isBlank()) {
                    return true;
                }
                if (hasAtLeastTwoPairs(pwd.substring(i + 1), pair)) {
                    return true;
                }
            }
            prev = c;
        }
        return false;
    }

    private boolean containsIllegalChars(String pwd) {
        return StringUtils.containsAny(pwd, "i", "l", "o");
    }

    private boolean hasContiguousChars(String pwd) {
        for (int i = 0; i < pwd.toCharArray().length - 2; i++) {
            char a = pwd.charAt(i);
            char b = pwd.charAt(i + 1);
            char c = pwd.charAt(i + 2);
            if (nextChar(a) == b && nextChar(b) == c && a < b && b < c) {
                return true;
            }
        }
        return false;
    }

    String validChars = "abcdefghjkmnpqrstuvwxyz";

    String nextPassword(String pwd, int idx) {
        if (idx == 0 && pwd.charAt(0) == 'z') {
            return "aa" + pwd.substring(1);
        }
        char c = nextChar(pwd.charAt(idx));
        var left = pwd.substring(0, idx);
        var right = pwd.substring(idx + 1);
        if (c == 'a') {
            //wrapped
            return nextPassword(left + c + right, idx - 1);
        }
        return left + c + right;
    }

    char nextChar(char c) {
        int idx = validChars.indexOf(c);
        if (idx == validChars.length() - 1) {
            return validChars.charAt(0);
        }
        return validChars.charAt(idx + 1);
    }
}
