package org.baconberry.aoc2015;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class CollectionUtils {
    private CollectionUtils() {
    }

    public static <E> List<E> cowList(List<E> src, E toAdd) {
        return Stream.of(src, List.of(toAdd))
                .flatMap(List::stream)
                .toList();
    }

    public static <K, V> Map<K, V> cowMap(Map<K, V> src, K key, V val) {
        var map = new HashMap<>(src);
        map.put(key, val);
        return Map.copyOf(map);
    }

    public static <E> E[] cowAdd(E[] arr, E e) {
        var newArr = Arrays.copyOf(arr, arr.length + 1);
        newArr[arr.length] = e;
        return newArr;
    }

    public static int[] cowAddInt(int[] arr, int e) {
        var newArr = Arrays.copyOf(arr, arr.length + 1);
        newArr[arr.length] = e;
        return newArr;
    }
}
