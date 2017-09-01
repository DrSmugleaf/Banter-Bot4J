package com.github.drsmugbrain.mafia;

import com.github.drsmugbrain.mafia.roles.Role;
import com.github.drsmugbrain.mafia.roles.RoleStatuses;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by DrSmugleaf on 24/08/2017.
 */
public class Player {

    private final Long ID;
    private final String NAME;
    private Role ROLE = null;
    private boolean bot = false;
    private final List<RoleStatuses> STATUSES = new ArrayList<>();
    private Player target = null;
    private PlayerStates state = null;

    public Player(@Nonnull Long id, @Nonnull String name) {
        this.ID = id;
        this.NAME = name;
    }

    public Long getID() {
        return this.ID;
    }

    public String getName() {
        return this.NAME;
    }

    @Nullable
    public Role getRole() {
        return this.ROLE;
    }

    protected void setRole(@Nonnull Role role) {
        this.ROLE = role;
    }

    public boolean isBot() {
        return this.bot;
    }

    public void setBot(boolean bool) {
        this.bot = bool;
    }

    public List<RoleStatuses> getStatuses() {
        return new ArrayList<>(this.STATUSES);
    }

    protected void addStatuses(@Nonnull RoleStatuses... statuses) {
        Collections.addAll(this.STATUSES, statuses);
    }

    protected void resetStatuses() {
        this.STATUSES.clear();
    }

    public Player getTarget() {
        return this.target;
    }

    protected void setTarget(@Nonnull Player target) {
        this.target = target;
    }

    protected void resetTarget() {
        this.target = null;
    }

    public PlayerStates getState() {
        return this.state;
    }

    protected void setState(@Nonnull PlayerStates state) {
        this.state = state;
    }

    protected void resetState() {
        this.state = null;
    }

}
