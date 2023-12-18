package dev.ojvzinn.pvp.cosmetics.collections.kits;

import dev.ojvzinn.pvp.Main;
import dev.ojvzinn.pvp.cosmetics.collections.Kit;
import dev.ojvzinn.pvp.game.KitPvP;
import dev.slickcollections.kiwizin.player.Profile;
import dev.slickcollections.kiwizin.utils.BukkitUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

public class Grandpa extends Kit {

    public Grandpa(String key, YamlConfiguration CONFIG) {
        super(key, CONFIG);
    }

    @Override
    public void applyKit(Player player) {
        PlayerInventory inventory = player.getInventory();
        Profile.getProfile(player.getName()).setHotbar(null);
        inventory.clear();

        ItemStack sword = BukkitUtils.deserializeItemStack("272 : 1");
        ItemMeta meta = sword.getItemMeta();
        meta.spigot().setUnbreakable(true);
        sword.setItemMeta(meta);
        inventory.setItem(0, sword);
        inventory.setItem(1, BukkitUtils.deserializeItemStack("280 : 1 : nome>&6Grandpa &7(Clique direito) : encantar>" + Enchantment.KNOCKBACK.getName() + ":2"));
        inventory.setItem(8, BukkitUtils.deserializeItemStack("COMPASS : 1 : nome>&aBússola"));
        inventory.setItem(15, new ItemStack(Material.RED_MUSHROOM, 16));
        inventory.setItem(14, new ItemStack(Material.BROWN_MUSHROOM, 16));
        inventory.setItem(13, new ItemStack(Material.BOWL, 16));

        for (int i = 0; i < inventory.getSize(); i++) {
            if (inventory.getItem(i) == null || inventory.getItem(i).getType() == Material.AIR) {
                inventory.setItem(i, new ItemStack(Material.MUSHROOM_SOUP));
            }
        }

        addProtection(player.getName());
        player.sendMessage("§aVocê equipou o kit Fisherman.");
    }

    @Override
    public void setupListeners() {
        Bukkit.getPluginManager().registerEvents(this, Main.getInstance());
    }

    @EventHandler
    public void onPlayerDropAbility(PlayerDropItemEvent event) {
        if (event.isCancelled()) {
            return;
        }

        Player player = event.getPlayer();
        Profile profile = Profile.getProfile(player.getName());
        if (KitPvP.isPlayingKitPvP(player)) {
            ItemStack item = event.getItemDrop().getItemStack();
            if (item != null && item.getType() == Material.STICK && item.getItemMeta() != null && item.getItemMeta().hasDisplayName() && item.getItemMeta().getDisplayName().equals("§6Grandpa §7(Clique direito)") && isSelected(profile)) {
                event.setCancelled(true);
            }
        }
    }
}
