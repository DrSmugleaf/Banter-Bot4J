package com.github.drsmugleaf.deadbydaylight;

import com.github.drsmugleaf.Nullable;

import java.io.InputStream;

/**
 * Created by DrSmugleaf on 07/11/2018
 */
public interface ICharacter {

    String getName();

    InputStream getImage();

    @Nullable
    Double getRating();

}
