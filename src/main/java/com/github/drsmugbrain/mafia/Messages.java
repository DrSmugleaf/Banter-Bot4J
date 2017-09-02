package com.github.drsmugbrain.mafia;

import com.github.drsmugbrain.mafia.roles.Crime;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * Created by DrSmugleaf on 31/08/2017.
 */
public enum Messages {

    CORONER("Coroner") {
        @Nonnull
        @Override
        public String getMessage(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, @Nonnull Player target2) {
            return String.format("%s's role was %s", target1.getName(), target1.getRole());
        }
    },
    DETECTIVE("Detective") {
        @Nonnull
        @Override
        public String getMessage(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, @Nonnull Player target2) {
            if (target1.getTarget() == null) {
                return String.format("%s didn't visit anyone today", target1.getName());
            }

            return String.format("%s visited %s today", target1.getName(), target1.getTarget().getName());
        }
    },
    INVESTIGATOR("Investigator") {
        @Nonnull
        @Override
        public String getMessage(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, @Nonnull Player target2) {
            List<Crime> crimes = target1.getRole().getCrimes();
            StringBuilder message = new StringBuilder();

            if (crimes.isEmpty() || (crimes.size() == 1 && crimes.contains(Crime.NO_CRIME))) {
                return String.format("%s has committed no crimes.", target1.getName());
            }

            message.append(String.format("%s's crimes are ", target1.getName()));
            for (int i = 0; i < crimes.size(); i++) {
                if (crimes.size() == i) {
                    message.append(crimes.get(i).getName());
                    message.append(".");
                    continue;
                }

                if (crimes.size() == i - 1) {
                    message.append(crimes.get(i).getName());
                    message.append(" and ");
                    continue;
                }

                message.append(crimes.get(i).getName());
                message.append(", ");
            }

            return message.toString();
        }
    };

    private final String NAME;

    Messages(@Nonnull String name) {
        this.NAME = name;
    }

    @Nonnull
    public String getName() {
        return this.NAME;
    }

    @Nonnull
    public abstract String getMessage(@Nonnull Game game, @Nonnull Player player, @Nonnull Player target1, @Nonnull Player target2);

}
