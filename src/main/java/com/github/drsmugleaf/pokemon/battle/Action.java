package com.github.drsmugleaf.pokemon.battle;

import com.github.drsmugleaf.pokemon.moves.DamageTags;
import com.github.drsmugleaf.pokemon.moves.Move;
import com.github.drsmugleaf.pokemon.pokemon.Pokemon;
import com.github.drsmugleaf.pokemon.status.BaseVolatileStatus;
import com.github.drsmugleaf.pokemon.trainer.Trainer;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.util.*;

/**
 * Created by DrSmugleaf on 16/09/2017.
 */
public class Action extends Move {

    @NotNull
    private final Move MOVE;

    @NotNull
    private final Pokemon ATTACKER;

    @NotNull
    private final List<Pokemon> DEFENDERS = new ArrayList<>();

    private int TARGET_POSITION;

    @NotNull
    private Pokemon TARGET_POKEMON;

    @NotNull
    private Trainer TARGET_TEAM;

    @NotNull
    private Map<Pokemon, Integer> DAMAGE = new LinkedHashMap<>();

    @NotNull
    private Map<Pokemon, Boolean> CRITICAL = new LinkedHashMap<>();

    @NotNull
    private Map<Pokemon, Boolean> HIT = new LinkedHashMap<>();

    @NotNull
    private final List<BaseVolatileStatus> ATTACKER_VOLATILE_STATUSES = new ArrayList<>();

    @NotNull
    private final List<BaseVolatileStatus> TARGET_VOLATILE_STATUSES = new ArrayList<>();

    private final int TURN;

    @NotNull
    private final List<DamageTags> TAGS = new ArrayList<>();

    protected Action(@NotNull Move move, @NotNull Pokemon attacker, @NotNull Pokemon target, int turn) {
        super(move.BASE_MOVE);

        MOVE = move;
        ATTACKER = attacker;
        TARGET_POSITION = target.getTrainer().getActivePokemon(target);
        TARGET_POKEMON = target;
        TARGET_TEAM = target.getTrainer();

        ATTACKER_VOLATILE_STATUSES.addAll(attacker.STATUSES.getVolatileStatuses().keySet());
        TARGET_VOLATILE_STATUSES.addAll(target.STATUSES.getVolatileStatuses().keySet());

        TURN = turn;
    }

    @NotNull
    public Move getMove() {
        return MOVE;
    }

    @NotNull
    public Pokemon getAttacker() {
        return ATTACKER;
    }

    @NotNull
    public List<Pokemon> getDefenders() {
        return DEFENDERS;
    }

    public void addDefender(@NotNull Pokemon pokemon) {
        DEFENDERS.add(pokemon);
    }

    public int getTargetPosition() {
        return TARGET_POSITION;
    }

    @NotNull
    public Pokemon getTargetPokemon() {
        return TARGET_POKEMON;
    }

    @NotNull
    public Trainer getTargetTeam() {
        return TARGET_TEAM;
    }

    public void setTarget(@NotNull Pokemon target) {
        TARGET_POSITION = target.getTrainer().getActivePokemon(target);
        TARGET_POKEMON = target;
        TARGET_TEAM = target.getTrainer();
    }

    @Nullable
    public Integer getDamage(@NotNull Pokemon pokemon) {
        return DAMAGE.get(pokemon);
    }

    public void setDamage(@NotNull Pokemon pokemon, int damage) {
        DAMAGE.put(pokemon, damage);
    }

    @Nullable
    public Boolean wasCritical(@NotNull Pokemon pokemon) {
        return CRITICAL.get(pokemon);
    }

    public void setCritical(@NotNull Pokemon pokemon, boolean bool) {
        CRITICAL.put(pokemon, bool);
    }

    @NotNull
    public Map<Pokemon, Boolean> hit() {
        return new HashMap<>(HIT);
    }

    @Nullable
    public Boolean hit(@NotNull Pokemon pokemon) {
        return HIT.get(pokemon);
    }

    public void setHit(@NotNull Pokemon pokemon, boolean bool) {
        HIT.put(pokemon, bool);
    }

    @NotNull
    public List<BaseVolatileStatus> getAttackerVolatileStatuses() {
        return ATTACKER_VOLATILE_STATUSES;
    }

    public boolean attackerHasVolatileStatus(@NotNull BaseVolatileStatus status) {
        return ATTACKER_VOLATILE_STATUSES.contains(status);
    }

    @NotNull
    public List<BaseVolatileStatus> getTargetVolatileStatuses() {
        return TARGET_VOLATILE_STATUSES;
    }

    public boolean targetHasVolatileStatus(@NotNull BaseVolatileStatus status) {
        return TARGET_VOLATILE_STATUSES.contains(status);
    }

    @NotNull
    public Battle getBattle() {
        return ATTACKER.getBattle();
    }

    public int getTurn() {
        return TURN;
    }

    @NotNull
    public Generation getGeneration() {
        return ATTACKER.getBattle().GENERATION;
    }

    @Override
    public int getPP() {
        return MOVE.getPP();
    }

    @Override
    public void setPP(int pp) {
        MOVE.setPP(pp);
    }

    @Override
    public void increasePP(int amount) {
        MOVE.increasePP(amount);
    }

    @Override
    public void decreasePP(int amount) {
        MOVE.decreasePP(amount);
    }

    public void addTag(@NotNull DamageTags... tags) {
        Collections.addAll(TAGS, tags);
    }

    @NotNull
    public List<DamageTags> getTags() {
        return new ArrayList<>(TAGS);
    }

    public boolean hasTags(@NotNull DamageTags... tags) {
        return TAGS.containsAll(Arrays.asList(tags));
    }

    @Override
    protected int use(@NotNull Pokemon attacker, @NotNull Pokemon defender, @NotNull Action action) {
        return super.use(attacker, defender, action);
    }

    public void tryUse() {
        Pokemon target = TARGET_TEAM.getActivePokemon(TARGET_POSITION);
        if (target == null) {
            miss(TARGET_POKEMON, this);
            return;
        }

        super.tryUse(ATTACKER, target, this);
    }

    public void reflect(@NotNull Pokemon reflector) {
        Action action = getBattle().replaceAction(this, MOVE, reflector, ATTACKER);
        action.MOVE.useAsReflect(action.ATTACKER, action.TARGET_POKEMON, action.ATTACKER.getBattle(), action);
    }

}
