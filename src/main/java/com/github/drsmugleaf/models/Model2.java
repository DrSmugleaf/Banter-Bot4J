package com.github.drsmugleaf.models;

import com.github.drsmugleaf.BanterBot4J;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by DrSmugleaf on 21/03/2018.
 */
public abstract class Model2<T extends Model2<T, E>, E extends Enum<E>> {

    private final Class<T> clazz;
    private final Class<E> eEnum;
    public final EnumMap<E, Object> PROPERTIES;

    protected Model2(Class<T> clazz, Class<E> eEnum) {
        this.clazz = clazz;
        this.eEnum = eEnum;
        PROPERTIES = new EnumMap<>(eEnum);
    }

    public List<T> get() throws SQLException {
        List<T> models = new ArrayList<>();

        PreparedStatement statement = Database.conn.prepareStatement("SELECT * FROM ?");
        Table tableAnnotation = clazz.getAnnotation(Table.class);
        statement.setString(1, tableAnnotation.name());
        String fixedQuery = statement.toString().replaceFirst("'(.+)'", "$1");

        StringBuilder sBuilder = new StringBuilder(fixedQuery);
        Iterator<Map.Entry<E, Object>> iterator = PROPERTIES.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<E, Object> entry = iterator.next();

            sBuilder
                    .append(entry.getKey().name())
                    .append(" = ?");

            if (iterator.hasNext()) {
                sBuilder.append(" AND ");
            }
        }

        statement = Database.conn.prepareStatement(sBuilder.toString());

        int i = 1;
        for (Object o : PROPERTIES.values()) {
            statement.setObject(i, o);
        }

        ResultSet result = statement.executeQuery();
        while (result.next()) {
            try {
                T row = clazz.newInstance();
                for (E property : eEnum.getEnumConstants()) {
                    row.PROPERTIES.put(property, result.getObject(property.name()));
                }
                models.add(row);
            } catch (InstantiationException | IllegalAccessException e) {
                BanterBot4J.LOGGER.error("Error creating new model instance" , e);
            }
        }

        return models;
    }

}
