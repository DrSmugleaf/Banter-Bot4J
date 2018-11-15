package com.github.drsmugleaf.deadbydaylight;

import javax.annotation.Nonnull;
import java.io.InputStream;

/**
 * Created by DrSmugleaf on 07/11/2018
 */
public interface ICharacter {

    @Nonnull
    String getName();

    @Nonnull
    InputStream getImage();

}
