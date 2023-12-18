package dev.ojvzinn.pvp.cosmetics.collections.kits;

import dev.ojvzinn.pvp.Main;
import dev.ojvzinn.pvp.api.DelayKits;
import dev.ojvzinn.pvp.cosmetics.collections.Kit;
import dev.ojvzinn.pvp.game.KitPvP;
import dev.slickcollections.kiwizin.player.Profile;
import dev.slickcollections.kiwizin.utils.BukkitUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import java.util.Map;

public class Deshfire extends Kit {

    public Deshfire(String key, YamlConfiguration CONFIG) {
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
        inventory.setItem(1, BukkitUtils.deserializeItemStack("378 : 1 : nome>&6Dashfire &7(Clique direito)"));
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
        DelayKits.createDelayProfile(player, this);
        player.sendMessage("§aVocê equipou o kit Deshfire.");
    }

    @Override
    public void setupListeners() {
        Bukkit.getPluginManager().registerEvents(this, Main.getInstance());
    }

    @EventHandler
    public void onPlayerInteractAbility(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Profile profile = Profile.getProfile(player.getName());
        if (KitPvP.isPlayingKitPvP(player)) {
            ItemStack item = event.getItem();
            if (item != null && item.getType() == Material.MAGMA_CREAM && item.getItemMeta() != null && item.getItemMeta().hasDisplayName() && item.getItemMeta().getDisplayName().equals("§6Dashfire §7(Clique direito)") && isSelected(profile)) {
                if (!DelayKits.loadDelayProfiles(player).canUse()) {
                    player.sendMessage("§cAguarde " + DelayKits.loadDelayProfiles(player).getDelayTime(true) + "s para utilizar este kit novamente.");
                    return;
                }

                for (Entity nearEntity : profile.getPlayer().getNearbyEntities(3, 0, 3)) {
                    if (nearEntity instanceof Player) {
                        nearEntity.setFireTicks(10 * 20);
                        nearEntity.sendMessage("§a[Deshfire] §fVocê foi incendiado pelo jogador " + player.getName() + " por 10 segundos.");
                    }
                }

                player.setVelocity(player.getLocation().getDirection().normalize().multiply(4).setY(0));
                player.sendMessage("§a[Deshfire] §fVocê incendiou " + profile.getPlayer().getNearbyEntities(3, 0, 3).size() + " inimigos!");
                DelayKits.loadDelayProfiles(player).addDelay(30);
            }
        }
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
            if (item != null && item.getType() == Material.MAGMA_CREAM && item.getItemMeta() != null && item.getItemMeta().hasDisplayName() && item.getItemMeta().getDisplayName().equals("§6Dashfire §7(Clique direito)") && isSelected(profile)) {
                event.setCancelled(true);
            }
        }
    }
}
