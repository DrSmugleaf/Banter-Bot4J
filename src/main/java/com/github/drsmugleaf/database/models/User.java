package com.github.drsmugleaf.database.models;

import com.github.drsmugleaf.database.api.annotations.Column;
import com.github.drsmugleaf.database.api.Model;
import com.github.drsmugleaf.database.api.annotations.Table;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.ReadyEvent;

/**
 * Created by DrSmugleaf on 14/05/2017.
 */
@Table(name = "users")
public class User extends Model<User> {

    @Column(name = "id")
    @Column.Id
    public Long id;

    public User(Long id) {
        this.id = id;
    }

    private User() {}

    @EventSubscriber
    public static void handle(ReadyEvent event) {
        Runnable runnable = () -> {
            event.getClient().getUsers().forEach(user -> {
                Long userID = user.getLongID();
                User userModel = new User(userID);
                userModel.createIfNotExists();
            });
        };

        Thread thread = new Thread(runnable);
        thread.start();
    }

}
