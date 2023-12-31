package net.wavemc.core.bukkit.util;

import java.util.LinkedHashSet;
import java.util.Set;

public class BuildUtil {

    private final static Set<String> buildablePlayers = new LinkedHashSet<>();

    public static void allow(String username) {
        buildablePlayers.add(username);
    }

    public static void deny(String username) {
        buildablePlayers.remove(username);
    }

    public static boolean has(String username) {
        return buildablePlayers.contains(username);
    }
}
