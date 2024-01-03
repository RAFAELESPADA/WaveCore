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

public class Duels extends Kit {

    public Duels() {
        super("1V1", "", "588", 0.0, BukkitUtils.deserializeItemStack(""), BukkitUtils.deserializeItemStack(""), BukkitUtils.deserializeItemStack(""), BukkitUtils.deserializeItemStack(""), BukkitUtils.deserializeItemStack(""));
    }

    @Override
    public void applyKit(Player player) {
        PlayerInventory inventory = player.getInventory();
        Profile.getProfile(player.getName()).setHotbar(null);
        inventory.clear();

        ItemStack item;
        ItemMeta meta;

        item = BukkitUtils.deserializeItemStack("BLAZE_ROD : 1 : nome>&6Desafiar para 1v1 &7(Clique direito)");
        meta = item.getItemMeta();
        meta.spigot().setUnbreakable(true);
        item.setItemMeta(meta);
        inventory.setItem(3, item);

        item = BukkitUtils.deserializeItemStack("REDSTONE : 1 : nome>&cSair");
        meta = item.getItemMeta();
        meta.spigot().setUnbreakable(true);
        item.setItemMeta(meta);
        inventory.setItem(5, item);
        player.sendMessage("§bVocê entrou na warp 1v1.");
    }

    @Override
    public void setupListeners() {}

}
