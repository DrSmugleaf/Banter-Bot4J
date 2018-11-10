package com.github.drsmugleaf.commands.deadbydaylight;

import sx.blah.discord.api.internal.json.objects.EmbedObject;
import sx.blah.discord.util.EmbedBuilder;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by DrSmugleaf on 10/11/2018
 */
public class LargeEmbedBuilder extends EmbedBuilder {

    @Nonnull
    private final List<EmbedBuilder> builders = new ArrayList<>();

    public LargeEmbedBuilder() {
        super();
        builders.add(this);
    }

    @Nonnull
    private EmbedBuilder getCurrentBuilder() {
        return builders.get(builders.size() - 1);
    }

    @Override
    public EmbedBuilder appendField(String title, String content, boolean inline) {
        EmbedBuilder builder = getCurrentBuilder();

        if (builder.getFieldCount() >= FIELD_COUNT_LIMIT) {
            EmbedBuilder newBuilder = new EmbedBuilder();
            builders.add(newBuilder);
            builder = newBuilder;
        }

        if (builders.size() == 1) {
            return super.appendField(title, content, inline);
        }

        return builder.appendField(title, content, inline);
    }

    @Nonnull
    public List<EmbedObject> buildAll() {
        List<EmbedObject> embeds = new ArrayList<>();

        for (EmbedBuilder builder : builders) {
            embeds.add(builder.build());
        }

        return embeds;
    }

}
