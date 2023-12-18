package dev.ojvzinn.pvp.utils;

import dev.slickcollections.kiwizin.player.Profile;
import dev.slickcollections.kiwizin.player.role.Role;
import dev.slickcollections.kiwizin.utils.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class PlayerUtils {

    private static Map<String, Long> PVPCOOLDOWN = new HashMap<>();

    public static boolean isCooldown(String name) {
        if (PVPCOOLDOWN.containsKey(name)) {
            long start = PVPCOOLDOWN.get(name);
            return ((start - System.currentTimeMillis()) / 1000.0) > 0.1;
        }

        return false;
    }

    public static void addPvPCooldown(String name) {
        PVPCOOLDOWN.remove(name);
        PVPCOOLDOWN.put(name, System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(10));
    }

    public static String getTimeCooldown(String name) {
        long start = PVPCOOLDOWN.get(name);
        return String.valueOf(((start - System.currentTimeMillis()) / 1000.0));
    }

    public static void refreshPlayer(Player player) {
        Profile profile = Profile.getProfile(player.getName());
        player.setMaxHealth(20.0);
        player.setHealth(20.0);
        player.setFoodLevel(20);
        player.setExhaustion(0.0F);
        player.setExp(0.0F);
        player.setLevel(0);
        player.setAllowFlight(false);
        player.setFlying(false);
        player.closeInventory();
        player.spigot().setCollidesWithEntities(true);
        player.getInventory().setHeldItemSlot(0);
        for (PotionEffect pe : player.getActivePotionEffects()) {
            player.removePotionEffect(pe.getType());
        }

        player.setGameMode(GameMode.SURVIVAL);
        profile.getDataContainer("kCoreProfile", "role").set(StringUtils.stripColors(Role.getPlayerRole(player, true).getName()));

        if (profile.getHotbar() != null) {
            profile.getHotbar().apply(profile);
        }

        for (Player online : Bukkit.getOnlinePlayers()) {
            player.showPlayer(online);
            online.showPlayer(player);
        }
    }

    public static String getAddForFront(Block block) {
        Block blockFront;
        if (block.getRelative(BlockFace.DOWN).getRelative(BlockFace.NORTH).getType().equals(Material.AIR)) {
            blockFront = block.getRelative(BlockFace.NORTH);
        } else if (block.getRelative(BlockFace.DOWN).getRelative(BlockFace.SOUTH).getType().equals(Material.AIR)) {
            blockFront = block.getRelative(BlockFace.DOWN).getRelative(BlockFace.SOUTH);
        } else if (block.getRelative(BlockFace.DOWN).getRelative(BlockFace.WEST).getType().equals(Material.AIR)) {
            blockFront = block.getRelative(BlockFace.WEST);
        } else {
            blockFront = block.getRelative(BlockFace.EAST);
        }

        if (block.getZ() == blockFront.getZ()) {
            if (blockFront.getX() > block.getX()) {
                return "3, 0, 0";
            } else {
                return "-3, 0, 0";
            }
        } else {
            if (blockFront.getZ() > block.getZ()) {
                return "0, 0, 3";
            } else {
                return "0, 0, -3";
            }
        }
    }

}
