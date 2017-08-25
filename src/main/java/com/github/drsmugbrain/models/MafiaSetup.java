package com.github.drsmugbrain.models;

import com.github.drsmugbrain.util.Bot;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by DrSmugleaf on 25/08/2017.
 */
public class MafiaSetup {

    private static Connection connection;
    private final long ID;
    private final String NAME;
    private final String ROLES;
    private final long SUBMITTER;

    public MafiaSetup(long id, @Nonnull String name, @Nonnull String roles, long submitter) {
        this.ID = id;
        this.NAME = name;
        this.ROLES = roles;
        this.SUBMITTER = submitter;
    }

    public static void createTable(@Nonnull Connection connection) {
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS mafia_setups (" +
                    "id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY," +
                    "name VARCHAR(64) NOT NULL," +
                    "roles VARCHAR(512) NOT NULL," +
                    "submitter BIGINT UNSIGNED NOT NULL REFERENCES users (id) ON UPDATE CASCADE ON DELETE CASCADE" +
                    ")"
            );
            statement.executeUpdate();
            MafiaSetup.connection = connection;
        } catch (SQLException e) {
            Bot.LOGGER.error("Unable to create mafia_setups database table", e);
            System.exit(1);
        }
    }

    @Nullable
    public static MafiaSetup get(long id) {
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement(
                    "SELECT * FROM mafia_setups " +
                    "WHERE id = ?"
            );
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();
            result.next();
            long resultID = result.getLong("id");
            String resultName = result.getString("name");
            String resultRoles = result.getString("roles");
            long resultSubmitter = result.getLong("submitter");
            return new MafiaSetup(resultID, resultName, resultRoles, resultSubmitter);
        } catch (SQLException e) {
            Bot.LOGGER.error("Error retrieving mafia setup with id " + id, e);
        }

        return null;
    }

    public void save() {
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement("INSERT INTO mafia_setups (id, name, roles, submitter) VALUES (?, ?, ?, ?) ON CONFLICT DO NOTHING");
            statement.setLong(1, this.ID);
            statement.setString(2, this.NAME);
            statement.setString(3, this.ROLES);
            statement.setLong(4, this.SUBMITTER);
            statement.executeUpdate();
        } catch (SQLException e) {
            Bot.LOGGER.error("Error saving mafia setup with id " + this.ID, e);
        }
    }

}
