package org.baconberry.aoc2015.day;

import org.apache.commons.lang3.tuple.Pair;
import org.baconberry.aoc2015.ISolver;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class Twentyone implements ISolver {
    private final List<Equipment> weapons = List.of(
            new Equipment("Dagger", 8, 4, 0),
            new Equipment("Shortsword", 10, 5, 0),
            new Equipment("Warhammer", 25, 6, 0),
            new Equipment("Longsword", 40, 7, 0),
            new Equipment("Greataxe", 74, 8, 0)
    );

    private final List<Equipment> armors = List.of(
            new Equipment("Nude", 0, 0, 0),
            new Equipment("Leather", 13, 0, 1),
            new Equipment("Chainmail", 31, 0, 2),
            new Equipment("Splintmail", 53, 0, 3),
            new Equipment("Bandedmail", 75, 0, 4),
            new Equipment("Platemail", 102, 0, 5)
    );
    private final List<Equipment> rings = List.of(
            new Equipment("Damage 0", 0, 0, 0),
            new Equipment("Defense 0", 0, 0, 0),
            new Equipment("Damage +1", 25, 1, 0),
            new Equipment("Damage +2", 50, 2, 0),
            new Equipment("Damage +3", 100, 3, 0),
            new Equipment("Defense +1", 20, 0, 1),
            new Equipment("Defense +2", 40, 0, 2),
            new Equipment("Defense +3", 80, 0, 3)
    );
    Player boss = new Player(109, 8, 2, Set.of());

    BiPredicate<Integer, Integer> localComparator= (a,b)->a<b;
    Predicate<Player> playerPredicate = this::defeatsBoss;
    int defaultVal = Integer.MAX_VALUE;

    @Override
    public String solve(List<String> lines, int part) {
        if(part ==2){
            playerPredicate = Predicate.not(this::defeatsBoss);
            localComparator = (a,b) -> a>b;
            defaultVal = Integer.MIN_VALUE;
        }
        var player = new Player(100, 0, 0, Set.of());
        var result = minCostToDefeat(player, weapons, armors, rings, rings);
        return String.valueOf(result.getLeft());
    }

    @SafeVarargs
    final Pair<Integer, Player> minCostToDefeat(Player player, List<Equipment>... sets) {
        if (sets.length == 0) {
            int cost = Optional.of(player)
                    .filter(playerPredicate)
                    .map(this::equipmentCost)
                    .orElse(defaultVal);
            return Pair.of(cost, player);
        }

        var set = sets[0];
        int min = defaultVal;
        Player minPlayer = null;
        for (Equipment equipment : set) {

            var localCost = minCostToDefeat(player.withEquipment(equipment), Arrays.copyOfRange(sets, 1, sets.length));
            if (localComparator.test(localCost.getLeft(), min)) {
                min = localCost.getLeft();
                minPlayer = localCost.getRight();
            }
        }
        return Pair.of(min, minPlayer);
    }

    private int equipmentCost(Player player) {
        return player.equipment().stream()
                .mapToInt(Equipment::cost)
                .sum();
    }

    private boolean defeatsBoss(Player player) {
        int playerDamage = player.damage() - boss.armor();
        if (playerDamage <= 0) {
            return false;
        }
        int bossDamage = boss.damage() - player.armor();
        if (bossDamage <= 0) {
            return true;
        }
        int playerDuration = Math.ceilDiv(player.hp(), bossDamage);
        int bossDuration = Math.ceilDiv(boss.hp(), playerDamage);
        return bossDuration <= playerDuration;
    }

    private record Equipment(String name, int cost, int damage, int armor) {
    }

    private record Player(int hp, int damage, int armor, Set<Equipment> equipment) {

        Player withEquipment(Equipment e) {
            if (equipment.contains(e)) {
                return this;
            }
            var newEq = new HashSet<>(equipment);
            newEq.add(e);
            return new Player(hp, damage + e.damage, armor + e.armor, newEq);
        }
    }

}
