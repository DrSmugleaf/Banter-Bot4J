package com.github.drsmugbrain.mafia;

import com.github.drsmugbrain.mafia.roles.Categories;
import com.github.drsmugbrain.mafia.roles.Roles;
import com.github.drsmugbrain.mafia.settings.Settings;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by DrSmugleaf on 24/08/2017.
 */
public class Setup {

    private final List<Roles> ROLES;
    private final Settings SETTINGS;

    public Setup(@Nonnull List<Roles> roles, @Nonnull List<Categories> categories, @Nonnull Settings settings) {
        this.ROLES = roles;
        for (Categories category : categories) {
            this.ROLES.add(category.random());
        }
        this.SETTINGS = settings;
    }

    @Nonnull
    public List<Roles> getRoles() {
        return this.ROLES;
    }

    @Nonnull
    public Settings getSettings() {
        return this.SETTINGS;
    }

    @Nonnull
    public static Setup random() {
        List<Roles> roles = new ArrayList<>();
        List<Categories> categories = new ArrayList<>();

        categories.add(Categories.TOWN_INVESTIGATIVE);
        categories.add(Categories.TOWN_INVESTIGATIVE);
        categories.add(Categories.TOWN_PROTECTIVE);
        categories.add(Categories.TOWN_PROTECTIVE);
        categories.add(Categories.TOWN_GOVERNMENT);
        categories.add(Categories.TOWN_KILLING);
        categories.add(Categories.TOWN_KILLING);
        categories.add(Categories.TOWN_POWER);
        categories.add(Categories.TOWN_POWER);
        roles.add(Roles.GODFATHER);
        categories.add(Categories.MAFIA_DECEPTION);
        categories.add(Categories.MAFIA_SUPPORT);
        categories.add(Categories.NEUTRAL_BENIGN);
        categories.add(Categories.NEUTRAL_EVIL);
        categories.add(Categories.NEUTRAL_KILLING);

        return new Setup(roles, categories, Settings.getDefault());
    }

    @Nonnull
    public static Setup importSetup(String string) throws ParseException {
        String[] stringArray = string.split("\r?\n");
        List<Roles> roles = new ArrayList<>();
        List<Categories> categories = new ArrayList<>();

        for (String roleName : stringArray) {
            Categories category = Categories.getCategory(roleName);
            Roles role = Roles.getRole(roleName);

            if (category != null) {
                categories.add(category);
            } else if (role != null) {
                roles.add(role);
            } else {
                throw new ParseException("Invalid role: " + roleName);
            }
        }

        return new Setup(roles, categories, Settings.getDefault());
    }

}
