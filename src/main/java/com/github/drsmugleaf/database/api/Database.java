package com.github.drsmugleaf.database.api;

import java.sql.Connection;

/**
 * Created by DrSmugleaf on 13/05/2017.
 */
public class Database {

    public static final Connection CONNECTION = DatabaseConnection.initialize();



}
