package com.github.drsmugleaf.civilization.vi;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by DrSmugleaf on 31/08/2018
 */
public enum Leaders {

    ALEXANDER("Alexander"),
    AMANITORE("Amanitore"),
    CATHERINE_DE_MEDICI("Catherine de Medici"),
    CHANDRAGUPTA("Chandragupta"),
    CLEOPATRA("Cleopatra"),
    CYRUS("Cyrus"),
    FREDERICK_BARBAROSSA("Frederick Barbarossa"),
    GANDHI("Gandhi"),
    GENGHIS_KHAN("Genghis Khan"),
    GILGAMESH("Gilgamesh"),
    GITARJA("Gitarja"),
    GORGO("Gorgo"),
    HARALD_HARDRADA("Harald Hardrada"),
    HOJO_TOKIMUNE("Hojo Tokimune"),
    JADWIGA("Jadwiga"),
    JAYAVARMAN_VII("Jayavarman VII"),
    JOHN_CURTIN("John Curtin"),
    LAUTARO("Lautaro"),
    MONTEZUMA("Montezuma"),
    MVEMBA_A_NZINGA("Mvemba a Nzinga"),
    PEDRO_II("Pedro II"),
    PERICLES("Pericles"),
    PETER("Peter"),
    PHILIP_II("Philip II"),
    POUNDMAKER("Poundmaker"),
    QIN_SHI_HUANG("Qin Shi Huang"),
    ROBERT_THE_BRUCE("Robert the Bruce"),
    SALADIN("Saladin"),
    SEONDEOK("Seondeok"),
    SHAKA("Shaka"),
    TAMAR("Tamar"),
    TEDDY_ROOSEVELT("Teddy Roosevelt"),
    TOMYRIS("Tomyris"),
    TRAJAN("Trajan"),
    VICTORIA("Victoria"),
    WILHELMINA("Wilhelmina");

    @Nonnull
    public final String NAME;

    Leaders(@Nonnull String name) {
        NAME = name;
    }

    @Nonnull
    public List<Leaders> random(int amount) {
        List<Leaders> leaders = new ArrayList<>(Arrays.asList(values()));
        Collections.shuffle(leaders);
        return leaders.subList(0, amount - 1);
    }

}
