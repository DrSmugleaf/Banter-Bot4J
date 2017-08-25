package com.github.drsmugbrain.mafia;

import com.github.drsmugbrain.mafia.roles.Categories;
import com.github.drsmugbrain.mafia.roles.Roles;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by DrSmugleaf on 24/08/2017.
 */
public class Setup {

    private final List<Roles> ROLES;

    Setup(@Nonnull List<Roles> roles, List<Categories> categories) {
        this.ROLES = roles;
        for (Categories category : categories) {
            this.ROLES.add(category.random());
        }
    }

    public List<Roles> getRoles() {
        return this.ROLES;
    }

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

        return new Setup(roles, categories);
    }

}
