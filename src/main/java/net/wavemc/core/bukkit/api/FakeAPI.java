package net.wavemc.core.bukkit.api;



import net.wavemc.core.bukkit.WaveBukkit;
import net.wavemc.core.bukkit.account.WavePlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class FakeAPI {

    public static final List<String> randomNicks = Arrays.asList(
            "nomoreLies",
            "nomoreLies",
            "nomoreLies",
            "nomoreLies",
            "nomoreLies",
            "nomoreLies",
            "nomoreLies",
            "nomoreLies",
            "nomoreLies",
            "nomoreLies",
            "nomoreLies",
            "nomoreLies",
            "nomoreLies",
            "nomoreLies",
            "nomoreLies",
            "nomoreLies"
    );

    private static void changeGamerProfileName(String name, Player player) {
        try {
            Method getHandle = player.getClass().getMethod("getHandle",
                    (Class<?>[]) null);
            try {
                Class.forName("com.mojang.authlib.GameProfile");
            } catch (ClassNotFoundException e) {
                return;
            }
            Object profile = getHandle.invoke(player).getClass()
                    .getMethod("getProfile")
                    .invoke(getHandle.invoke(player));
            Field ff = profile.getClass().getDeclaredField("name");
            ff.setAccessible(true);
            ff.set(profile, name);
            for (Player players : Bukkit.getOnlinePlayers()) {
                players.hidePlayer(player);
                players.showPlayer(player);
            }
        } catch (NoSuchMethodException | SecurityException
                | IllegalAccessException | IllegalArgumentException
                | InvocationTargetException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }


    public static boolean isNickValid(String nick) {
        Pattern p = Pattern.compile("[^a-zA-Z0-9_]");
        if (p.matcher(nick).find()) return false;
        return nick.length() >= 3 && nick.length() <= 16;
    }

    public static boolean hasFake(WavePlayer playerData) {
       WavePlayer player = WaveBukkit.getInstance().getPlayerManager().getPlayer(playerData.getName());
        return !playerData.getName().equalsIgnoreCase(player.getName());
    }
    public static String listFake(Player playerData) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            WavePlayer player = WaveBukkit.getInstance().getPlayerManager().getPlayer(p.getName());
            playerData.sendMessage("§cLista de players no fake");
            return hasFake(player) ? "Fake:" + player.getName() + " Nick Real: " + p.getName() : null;
        }
        return null;
    }



}