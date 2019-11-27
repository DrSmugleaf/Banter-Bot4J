package com.github.drsmugleaf.civilization.vi;

import org.jetbrains.annotations.Contract;

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

    private static final int AMOUNT = Leaders.values().length;

    private final String NAME;

    Leaders(String name) {
        NAME = name;
    }

    public static List<Leaders> random(int amount) {
        List<Leaders> leaders = new ArrayList<>(Arrays.asList(values()));
        Collections.shuffle(leaders);
        return leaders.subList(0, amount);
    }

    @Contract(pure = true)
    public static int getAmount() {
        return AMOUNT;
    }

    @Contract(pure = true)
    public String getName() {
        return NAME;
    }

}
