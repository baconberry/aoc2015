package org.baconberry.aoc2015.day;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.ToString;
import lombok.With;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.baconberry.aoc2015.ISolver;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class Twentytwo implements ISolver {
    List<Effect> effects = createEffects();
    int globalMin = Integer.MAX_VALUE;


    @Override
    public String solve(List<String> lines, int part) {
        var player = new Player(50, 500, 0, 0, List.of());
        var boss = new Player(51, 0, 9, 0, List.of());

        var min = calculateMinManaCost(player, boss, 0, Integer.MAX_VALUE, 0, Integer.MAX_VALUE, List.of());

        return String.valueOf(min);
    }

    private Result calculateMinManaCost(Player player, Player boss, int manaCost, int maxMana, int level, int maxLevel, List<Effect> playerEffects) {
        if (player.hp() <= 0 || manaCost >= player.mp || manaCost >= maxMana || level >= maxLevel || globalMin <= manaCost) {
            return new Result(Integer.MAX_VALUE, level);
        }
        if (boss.hp() <= 0) {
            return new Result(manaCost, level);
        }
        var min = new Result(Integer.MAX_VALUE, Integer.MAX_VALUE);

        for (Effect effect : effects) {
            var effectClone = effect.toBuilder().build();
            var effectList = cow(playerEffects, effectClone);
            var localPlayers = Pair.of(player, boss);
            for (Effect eff : effectList) {
                localPlayers = eff.apply(localPlayers.getLeft(), localPlayers.getRight());
            }
            var playerClone = localPlayers.getLeft();
            int bossDamage = Math.max(1, boss.damage - player.armor());
            playerClone = playerClone.withHp(playerClone.hp - bossDamage);
            var localResult = calculateMinManaCost(playerClone, localPlayers.getRight(), manaCost + effectClone.cost, min.cost, level + 1, maxLevel, effectList);
            if (localResult.cost < min.cost) {
                min = localResult;
                log.info("Current min: {}", min);
            }
            if (globalMin > localResult.cost) {
                globalMin = localResult.cost;
            }
        }
        return min;
    }

    private List<Effect> cow(List<Effect> pEffects, Effect toAdd) {
        var list = new ArrayList<Effect>(pEffects.size() + 1);
        for (Effect effect : pEffects) {
            if (effect.turns <= 0) {
                continue;
            }
            list.add(effect.toBuilder().build());
        }
        list.add(toAdd);
        return list;
    }

    record Result(int cost, int level) {
    }

    @With
    record Player(int hp, int mp, int damage, int armor, List<Effect> effects) {
    }


    @AllArgsConstructor
    @Builder(toBuilder = true)
    @ToString(onlyExplicitlyIncluded = true)
    static class Effect {
        @ToString.Include
        String name;
        int cost;
        int damage;
        int heal;
        int armor;
        int mana;
        @ToString.Include
        int turns;


        /**
         * @return true if effect is ended
         */
        Pair<Player, Player> apply(Player player, Player challenger) {
            if (turns-- <= 0) {
                return Pair.of(player, challenger);
            }
            challenger = challenger.withHp(challenger.hp - damage);
            player = player.withHp(player.hp + heal)
                    .withArmor(player.armor + armor)
                    .withMp(player.mp + mana);
            return Pair.of(player, challenger);
        }
    }

    static class Shield extends Effect {

        boolean applied;

        public Shield(String name, int cost, int damage, int heal, int armor, int mana, int turns) {
            super(name, cost, damage, heal, armor, mana, turns);
        }

        @Override
        Pair<Player, Player> apply(Player player, Player challenger) {
            if (applied) {
                armor = 0;
            }
            try {
                if (turns == 0) {
                    return super.apply(player.withArmor(player.armor() - 7), challenger);
                }
                return super.apply(player, challenger);
            } finally {
                applied = true;
            }
        }
    }

    private List<Effect> createEffects() {
        return List.of(
                new Effect("Magic Missile", 53, 4, 0, 0, 0, 1),
                new Effect("Drain", 73, 2, 2, 0, 0, 1),
                new Shield("Shield", 113, 0, 0, 7, 0, 6),
                new Effect("Poison", 173, 3, 0, 0, 0, 6),
                new Effect("Recharge", 229, 0, 0, 0, 101, 5)
        );
    }
}
