package com.github.drsmugleaf.pokemon.battle;

import com.github.drsmugleaf.Nullable;
import com.github.drsmugleaf.pokemon.moves.DamageTags;
import com.github.drsmugleaf.pokemon.moves.Move;
import com.github.drsmugleaf.pokemon.pokemon.Pokemon;
import com.github.drsmugleaf.pokemon.status.BaseVolatileStatus;
import com.github.drsmugleaf.pokemon.trainer.Trainer;

import java.util.*;

/**
 * Created by DrSmugleaf on 16/09/2017.
 */
public class Action extends Move {

    private final Move MOVE;
    private final Pokemon ATTACKER;
    private final List<Pokemon> DEFENDERS = new ArrayList<>();
    private int TARGET_POSITION;
    private Pokemon TARGET_POKEMON;
    private Trainer TARGET_TEAM;
    private Map<Pokemon, Integer> DAMAGE = new LinkedHashMap<>();
    private Map<Pokemon, Boolean> CRITICAL = new LinkedHashMap<>();
    private Map<Pokemon, Boolean> HIT = new LinkedHashMap<>();
    private final List<BaseVolatileStatus> ATTACKER_VOLATILE_STATUSES = new ArrayList<>();
    private final List<BaseVolatileStatus> TARGET_VOLATILE_STATUSES = new ArrayList<>();
    private final int TURN;
    private final List<DamageTags> TAGS = new ArrayList<>();

    protected Action(Move move, Pokemon attacker, Pokemon target, int turn) {
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

    public Move getMove() {
        return MOVE;
    }

    public Pokemon getAttacker() {
        return ATTACKER;
    }

    public List<Pokemon> getDefenders() {
        return DEFENDERS;
    }

    public void addDefender(Pokemon pokemon) {
        DEFENDERS.add(pokemon);
    }

    public int getTargetPosition() {
        return TARGET_POSITION;
    }

    public Pokemon getTargetPokemon() {
        return TARGET_POKEMON;
    }

    public Trainer getTargetTeam() {
        return TARGET_TEAM;
    }

    public void setTarget(Pokemon target) {
        TARGET_POSITION = target.getTrainer().getActivePokemon(target);
        TARGET_POKEMON = target;
        TARGET_TEAM = target.getTrainer();
    }

    @Nullable
    public Integer getDamage(Pokemon pokemon) {
        return DAMAGE.get(pokemon);
    }

    public void setDamage(Pokemon pokemon, int damage) {
        DAMAGE.put(pokemon, damage);
    }

    @Nullable
    public Boolean wasCritical(Pokemon pokemon) {
        return CRITICAL.get(pokemon);
    }

    public void setCritical(Pokemon pokemon, boolean bool) {
        CRITICAL.put(pokemon, bool);
    }

    public Map<Pokemon, Boolean> hit() {
        return new HashMap<>(HIT);
    }

    @Nullable
    public Boolean hit(Pokemon pokemon) {
        return HIT.get(pokemon);
    }

    public void setHit(Pokemon pokemon, boolean bool) {
        HIT.put(pokemon, bool);
    }

    public List<BaseVolatileStatus> getAttackerVolatileStatuses() {
        return ATTACKER_VOLATILE_STATUSES;
    }

    public boolean attackerHasVolatileStatus(BaseVolatileStatus status) {
        return ATTACKER_VOLATILE_STATUSES.contains(status);
    }

    public List<BaseVolatileStatus> getTargetVolatileStatuses() {
        return TARGET_VOLATILE_STATUSES;
    }

    public boolean targetHasVolatileStatus(BaseVolatileStatus status) {
        return TARGET_VOLATILE_STATUSES.contains(status);
    }

    public Battle getBattle() {
        return ATTACKER.getBattle();
    }

    public int getTurn() {
        return TURN;
    }

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

    public void addTag(DamageTags... tags) {
        Collections.addAll(TAGS, tags);
    }

    public List<DamageTags> getTags() {
        return new ArrayList<>(TAGS);
    }

    public boolean hasTags(DamageTags... tags) {
        return TAGS.containsAll(Arrays.asList(tags));
    }

    @Override
    protected int use(Pokemon attacker, Pokemon defender, Action action) {
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

    public void reflect(Pokemon reflector) {
        Action action = getBattle().replaceAction(this, MOVE, reflector, ATTACKER);
        action.MOVE.useAsReflect(action.ATTACKER, action.TARGET_POKEMON, action.ATTACKER.getBattle(), action);
    }

}
