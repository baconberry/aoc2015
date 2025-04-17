package org.baconberry.aoc2015.day;

import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.With;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.baconberry.aoc2015.ISolver;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class Twentytwo implements ISolver {
    List<IEffect> effects = createEffects();
    int globalMin = Integer.MAX_VALUE;

    int startTurnHandicap = 0;


    @Override
    public String solve(List<String> lines, int part) {
        var player = new Player(50, 500, 0, 0, List.of());
        var boss = new Player(51, 0, 9, 0, List.of());
        if (part == 2) {
            startTurnHandicap = 1;
        }

        var min = playerTurn(player, boss, 0, List.of());

        return String.valueOf(min);
    }

    private int playerTurn(Player player, Player boss, int manaCost, List<IEffect> srcplayerEffects) {
        player = player.takeDamage(startTurnHandicap);
        if (player.isDead() || globalMin < manaCost) {
            return Integer.MAX_VALUE;
        }
        var playerEffects = clone(srcplayerEffects);
        final var players = applyEffects(player, boss, playerEffects);
        if (players.getRight().isDead()) {
            return manaCost;
        }
        int min = Integer.MAX_VALUE;
        for (IEffect effect : effects) {
            var effectClone = effect.cloneEffect();
            int manaUsed = manaCost + effectClone.cost();
            if (manaUsed > player.mp() || effectInList(effectClone, playerEffects)) {
                continue;
            }

            var localPlayers = effectClone.apply(players.getLeft(), players.getRight());
            var effectList = cow(playerEffects, effectClone);
            int localResult = bossTurn(localPlayers.getLeft(), localPlayers.getRight(), manaUsed, effectList);
            if (localResult < min) {
                min = localResult;
            }
            if (globalMin > localResult) {
                globalMin = localResult;
            }
        }

        return min;
    }

    private List<IEffect> clone(List<IEffect> playerEffects) {
        return playerEffects.stream()
                .map(IEffect::cloneEffect)
                .toList();
    }

    private Pair<Player, Player> applyEffects(Player player, Player boss, List<IEffect> playerEffects) {
        var players = Pair.of(player, boss);
        for (IEffect effect : playerEffects) {
            players = effect.apply(players.getLeft(), players.getRight());
        }
        return players;
    }

    private int bossTurn(Player player, Player boss, int manaCost, List<IEffect> playerEffects) {
        var players = applyEffects(player, boss, playerEffects);
        if (players.getRight().isDead()) {
            return manaCost;
        }
        var damagedPlayer = players.getRight().hit(players.getLeft());
        return playerTurn(damagedPlayer, players.getRight(), manaCost, playerEffects);
    }

    private boolean effectInList(IEffect effectClone, List<IEffect> effects) {
        return effects.stream()
                .filter(IEffect::isActive)
                .anyMatch(effect -> effect.name().equals(effectClone.name()));
    }

    private List<IEffect> cow(List<IEffect> pEffects, IEffect toAdd) {
        var list = new ArrayList<IEffect>(pEffects.size() + 1);
        list.addAll(clone(pEffects));
        list.add(toAdd);
        return list;
    }

    @With
    record Player(int hp, int mp, int damage, int armor, List<Effect> effects) {


        boolean isDead() {
            return hp() <= 0;
        }

        Player takeDamage(int damage) {
            if (damage > 0) {
                return withHp(hp - damage);
            }
            return this;
        }

        Player hit(Player otherPlayer) {
            int damage = Math.max(1, damage() - otherPlayer.armor());
            return otherPlayer.takeDamage(damage);
        }
    }


    interface IEffect {
        Pair<Player, Player> apply(Player player, Player challenger);

        IEffect cloneEffect();

        boolean isActive();

        int cost();

        String name();
    }

    @AllArgsConstructor
    @SuperBuilder(toBuilder = true)
    @ToString(onlyExplicitlyIncluded = true)
    static class Effect implements IEffect {
        @ToString.Include
        String name;
        int cost;
        int damage;
        int heal;
        int armor;
        int mana;
        @ToString.Include
        int turns;


        public Pair<Player, Player> apply(Player player, Player challenger) {
            if (!isActive()) {
                return Pair.of(player, challenger);
            }
            turns--;
            var boss = challenger.takeDamage(damage);
            var effectedPlayer = player.withHp(player.hp + heal)
                    .withArmor(player.armor + armor)
                    .withMp(player.mp + mana);
            return Pair.of(effectedPlayer, boss);
        }

        public Effect cloneEffect() {
            return this.toBuilder().build();
        }

        public boolean isActive() {
            return turns > 0;
        }

        @Override
        public int cost() {
            return cost;
        }

        @Override
        public String name() {
            return name;
        }
    }

    static class Shield extends Effect {

        boolean reverted;

        public Shield(String name, int cost, int damage, int heal, int armor, int mana, int turns) {
            super(name, cost, damage, heal, armor, mana, turns);
        }

        @Override
        public Pair<Player, Player> apply(Player player, Player challenger) {
            try {
                if (turns == 1 && !reverted) {
                    reverted = true;
                    return super.apply(player.withArmor(player.armor() - armor), challenger);
                }
                return super.apply(player, challenger);
            } finally {
                super.armor = 0;
            }
        }

        @Override
        public Effect cloneEffect() {
            return new Shield(name, cost, damage, heal, armor, mana, turns);
        }
    }

    static class DelayedByOne implements IEffect {

        final IEffect delegate;
        boolean canApply;

        public DelayedByOne(IEffect wrapper) {
            delegate = wrapper;
            canApply = false;
        }

        private DelayedByOne(IEffect wrapper, boolean canApply) {
            delegate = wrapper;
            this.canApply = canApply;
        }

        public Pair<Player, Player> apply(Player player, Player challenger) {
            if (canApply) {
                return delegate.apply(player, challenger);
            }
            canApply = true;
            return Pair.of(player, challenger);
        }

        @Override
        public IEffect cloneEffect() {
            return new DelayedByOne(delegate.cloneEffect(), canApply);
        }

        @Override
        public boolean isActive() {
            return delegate.isActive();
        }

        @Override
        public int cost() {
            return delegate.cost();
        }

        @Override
        public String name() {
            return delegate.name();
        }

        @Override
        public String toString() {
            return "Delayed[%s]".formatted(delegate.toString());
        }
    }

    private List<IEffect> createEffects() {
        return List.of(
                new Effect("Magic Missile", 53, 4, 0, 0, 0, 1),
                new Effect("Drain", 73, 2, 2, 0, 0, 1),
                new DelayedByOne(new Shield("Shield", 113, 0, 0, 7, 0, 6)),
                new DelayedByOne(new Effect("Poison", 173, 3, 0, 0, 0, 6)),
                new DelayedByOne(new Effect("Recharge", 229, 0, 0, 0, 101, 5))
        );
    }
}
