package dev.ojvzinn.pvp.cosmetics.collections.kits;

import dev.ojvzinn.pvp.Main;
import dev.ojvzinn.pvp.cosmetics.collections.Kit;
import dev.slickcollections.kiwizin.player.Profile;
import dev.slickcollections.kiwizin.utils.BukkitUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

public class FPS extends Kit {

    public FPS() {
        super("FPS", "", "1", 0.0, BukkitUtils.deserializeItemStack(""), BukkitUtils.deserializeItemStack(""), BukkitUtils.deserializeItemStack(""), BukkitUtils.deserializeItemStack(""), BukkitUtils.deserializeItemStack(""));
    }

    @Override
    public void applyKit(Player player) {
        PlayerInventory inventory = player.getInventory();
        Profile.getProfile(player.getName()).setHotbar(null);
        inventory.clear();

        ItemStack item;
        ItemMeta meta;

        item = BukkitUtils.deserializeItemStack("276 : 1 : encantar>DAMAGE_ALL:1");
        meta = item.getItemMeta();
        meta.spigot().setUnbreakable(true);
        item.setItemMeta(meta);
        inventory.setItem(0, item);

        item = BukkitUtils.deserializeItemStack("306 : 1");
        meta = item.getItemMeta();
        item.setItemMeta(meta);
        inventory.setHelmet(item);

        item = BukkitUtils.deserializeItemStack("307 : 1");
        meta = item.getItemMeta();
        item.setItemMeta(meta);
        inventory.setChestplate(item);

        item = BukkitUtils.deserializeItemStack("308 : 1");
        meta = item.getItemMeta();
        item.setItemMeta(meta);
        inventory.setLeggings(item);

        item = BukkitUtils.deserializeItemStack("309 : 1");
        meta = item.getItemMeta();
        item.setItemMeta(meta);
        inventory.setBoots(item);

        inventory.setItem(15, new ItemStack(Material.RED_MUSHROOM, 16));
        inventory.setItem(14, new ItemStack(Material.BROWN_MUSHROOM, 16));
        inventory.setItem(13, new ItemStack(Material.BOWL, 16));

        for (int i = 0; i < inventory.getSize(); i++) {
            if (inventory.getItem(i) == null || inventory.getItem(i).getType() == Material.AIR) {
                inventory.setItem(i, new ItemStack(Material.MUSHROOM_SOUP));
            }
        }

        addProtection(player.getName());
        player.sendMessage("§aVocê equipou o kit FPS.");
    }

    @Override
    public void setupListeners() {}
}
