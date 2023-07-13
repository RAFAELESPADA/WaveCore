package bedwarsplugin;


import java.io.IOException;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;



public class RTP {


public static void TeleportArenaRandom(final Player p) {
    final Random dice = new Random();
    final int number = dice.nextInt(4);
    switch (number) {
        case 0: {
        	final World w = p.getWorld();
            final double x = 1564181;
            final double y = 40;
            final double z =  61618;
            final Location lobby = new Location(w, x, y, z);
            lobby.setPitch((float)175.2156);
            lobby.setYaw((float)5.2);
            p.teleport(lobby);
            break;
        }
        case 1: {
        	final World w = p.getWorld();
            final double x = 1564142.244;
            final double y = 37;
            final double z =  61586.515;
            final Location lobby = new Location(w, x, y, z);
            lobby.setPitch((float)-84.5);
            lobby.setYaw((float)4.2);
            p.teleport(lobby);
            break;
        }
    }
}
}