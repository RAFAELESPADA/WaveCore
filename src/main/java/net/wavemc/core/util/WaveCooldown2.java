package net.wavemc.core.util;

import net.wavemc.core.bukkit.api.ActionBar;
import net.wavemc.core.bukkit.api.HelixActionBar;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class WaveCooldown2 {

    private final static HashMap<String, HashMap<String, Long>> cooldowns = new HashMap<>();
    public static final Map<UUID, List<WaveCooldownAPI>> map = new ConcurrentHashMap<>();

    public static void removeAllCooldowns(Player player) {
        if (map.containsKey(player.getUniqueId())) {
            List<WaveCooldownAPI> list = map.get(player.getUniqueId());
            Iterator<WaveCooldownAPI> it = list.iterator();
            while (it.hasNext()) {
                WaveCooldownAPI cooldown = it.next();
                it.remove();
            }
        }
    }

    public static void sendMessage(Player player, String name) {
        if (map.containsKey(player.getUniqueId())) {
            List<WaveCooldownAPI> list = map.get(player.getUniqueId());
            for (WaveCooldownAPI cooldown : list)
                if (cooldown.getName().equals(name)) {
                    display(player , cooldown);
                }
        }
    }

    public static void addCooldown(Player player, WaveCooldownAPI cooldown) {
        CooldownStartEvent event = new CooldownStartEvent(player, cooldown);
        Bukkit.getServer().getPluginManager().callEvent(event);
        if (!event.isCancelled()) {
            List<WaveCooldownAPI> list = map.computeIfAbsent(player.getUniqueId(), v -> new ArrayList<>());
            list.add(cooldown);
        }
    }

    public static boolean removeCooldown(Player player, String name) {
        if (map.containsKey(player.getUniqueId())) {
            List<WaveCooldownAPI> list = map.get(player.getUniqueId());
            Iterator<WaveCooldownAPI> it = list.iterator();
            while (it.hasNext()) {
                WaveCooldownAPI cooldown = it.next();
                if (cooldown.getName().equals(name)) {
                    it.remove();
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean hasCooldown(Player player, String name) {
        if (map.containsKey(player.getUniqueId())) {
            List<WaveCooldownAPI> list = map.get(player.getUniqueId());
            for (WaveCooldownAPI cooldown : list)
                if (cooldown.getName().equals(name)) {
                    return true;
                }
        }
        return false;
    }

    public static boolean inCooldown(Player player, String name) {
        if (map.containsKey(player.getUniqueId())) {
            List<WaveCooldownAPI> list = map.get(player.getUniqueId());
            for (WaveCooldownAPI cooldown : list)
                if (cooldown.getName().equals(name)) {
                    if (cooldown.expired()) {
                        return false;
                    } else {
                        return true;
                    }
                }
        }
        return false;
    }


    @EventHandler
    public void onUpdate(UpdateEvent event) {
        if (event.getType() != UpdateEvent.UpdateType.SEGUNDO)
            return;

        for (UUID uuid : map.keySet()) {
            Player player = Bukkit.getPlayer(uuid);
            if (player != null) {
                List<WaveCooldownAPI> list = map.get(uuid);
                Iterator<WaveCooldownAPI> it = list.iterator();

                /* Found Cooldown */
                WaveCooldownAPI found = null;
                while (it.hasNext()) {
                    WaveCooldownAPI cooldown = it.next();
                    if (!cooldown.expired()) {
                        if (cooldown instanceof ItemCooldown) {
                            ItemStack hand = player.getInventory().getItemInHand();
                            if (hand != null && hand.getType() != Material.AIR) {
                                ItemCooldown item = (ItemCooldown) cooldown;
                                if (hand.equals(item.getItem())) {
                                    item.setSelected(true);
                                    found = item;
                                    break;
                                }
                            }
                            continue;
                        }
                        found = cooldown;
                        continue;
                    }
                    it.remove();
                    ActionBar.sendActionBar(player, "");

                    CooldownFinishEvent e = new CooldownFinishEvent(player, cooldown);
                    Bukkit.getServer().getPluginManager().callEvent(e);
                }

                /* Display Cooldown */
                if (found != null) {
                    display(player, found);
                } else if (list.isEmpty()) {
                    map.remove(uuid);
                } else {
                    WaveCooldownAPI cooldown = list.get(0);
                    if (cooldown instanceof ItemCooldown) {
                        ItemCooldown item = (ItemCooldown) cooldown;
                        if (item.isSelected()) {
                            item.setSelected(false);
                        }
                    }
                }
            }
        }
    }


    public static void display(Player player, WaveCooldownAPI cooldown) {
        StringBuilder bar = new StringBuilder();
        double percentage = cooldown.getPercentage();
        double remaining = cooldown.getRemaining();
        double count = 20 - Math.max(percentage > 0D ? 1 : 0, percentage / 5);

        for (int a = 0; a < count; a++)
            bar.append("§a§l:");

        for (int a = 0; a < 20 - count; a++)
            bar.append("§c§l:");

        String name = cooldown.getName();
        HelixActionBar.send(player, name + " " + bar.toString() + "§f " + StringUtils.toMillis(cooldown.getRemaining()));
    }
}
