package com.github.drsmugleaf.deadbydaylight;

import org.jetbrains.annotations.NotNull;
import java.io.InputStream;

/**
 * Created by DrSmugleaf on 07/11/2018
 */
public interface ICharacter {

    @NotNull
    String getName();

    @NotNull
    InputStream getImage();

}
