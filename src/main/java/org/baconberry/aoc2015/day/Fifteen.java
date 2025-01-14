package org.baconberry.aoc2015.day;

import org.baconberry.aoc2015.ISolver;
import org.baconberry.aoc2015.Utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class Fifteen implements ISolver {
    Predicate<Integer> caloriesPredicate = a -> true;

    @Override
    public String solve(List<String> lines, int part) {
        Ingredient[] ingredients = new Ingredient[lines.size()];
        int idx = 0;
        for (String line : lines) {
            var parts = line.split(":");
            var ingredient = Ingredient.of(parts[0], Utils.lineToIntList(parts[1]));
            ingredients[idx++] = ingredient;
        }
        if (part == 2) {
            caloriesPredicate = cal -> cal == 500;
        }
        int[] emptyArr = new int[ingredients.length];
        int maxScore = maxTotalScore(ingredients, emptyArr, 0, 100, new HashMap<>());
        Arrays.toString(emptyArr);
        return String.valueOf(maxScore);
    }

    private int maxTotalScore(Ingredient[] ingredients, int[] recipe, int ingredientCount, int maxIngredients, Map<String, Integer> memo) {
        if (ingredientCount == maxIngredients) {
            return calculateScore(ingredients, recipe);
        }
        var key = Arrays.toString(recipe);
        if (memo.containsKey(key)) {
            return memo.get(key);
        }

        int maxResult = Integer.MIN_VALUE;
        for (int i = 0; i < ingredients.length; i++) {
            int[] newRecipe = Arrays.copyOf(recipe, recipe.length);
            newRecipe[i]++;
            int localResult = maxTotalScore(ingredients, newRecipe,
                    ingredientCount + 1, maxIngredients, memo);
            if (localResult > maxResult) {
                maxResult = localResult;
            }
        }
        memo.put(key, maxResult);
        return maxResult;
    }

    private int calculateScore(Ingredient[] ingredients, int[] recipe) {
        int[] props = new int[5];
        for (int i = 0; i < ingredients.length; i++) {
            props[0] += recipe[i] * ingredients[i].capacity();
            props[1] += recipe[i] * ingredients[i].durability();
            props[2] += recipe[i] * ingredients[i].flavor();
            props[3] += recipe[i] * ingredients[i].texture();
            props[4] += recipe[i] * ingredients[i].calories();
        }
        int sum = 1;
        for (int i = 0; i < props.length - 1; i++) {
            int prop = props[i];
            if (prop < 0) {
                prop = 0;
            }
            sum *= prop;

        }
        if (caloriesPredicate.test(props[4])) {
            return sum;
        }
        return Integer.MIN_VALUE;
    }

    record Ingredient(String name, int capacity, int durability, int flavor, int texture, int calories) {
        static Ingredient of(String name, List<Integer> l) {
            return new Ingredient(name, l.get(0), l.get(1), l.get(2), l.get(3), l.get(4));
        }
    }
}
