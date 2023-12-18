package dev.ojvzinn.pvp.cosmetics.collections.kits;

import dev.ojvzinn.pvp.Main;
import dev.ojvzinn.pvp.cosmetics.Cosmetic;
import dev.ojvzinn.pvp.cosmetics.CosmeticType;
import dev.ojvzinn.pvp.cosmetics.collections.Kit;
import dev.ojvzinn.pvp.game.KitPvP;
import dev.slickcollections.kiwizin.player.Profile;
import dev.slickcollections.kiwizin.utils.BukkitUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

public class Stomper extends Kit {

    public Stomper(String key, YamlConfiguration CONFIG) {
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
        player.sendMessage("§aVocê equipou o kit Stomper.");
    }

    @Override
    public void setupListeners() {
        Bukkit.getPluginManager().registerEvents(this, Main.getInstance());
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent event) {
        if (event.isCancelled()) {
            return;
        }

        if (event.getCause().equals(EntityDamageEvent.DamageCause.FALL)) {
            if (event.getEntity() instanceof Player) {
                Profile profile = Profile.getProfile(event.getEntity().getName());
                if (KitPvP.isPlayingKitPvP(profile.getPlayer())) {
                    if (isSelected(profile)) {
                        if (profile.getPlayer().getFallDistance() >= 4.0F) {
                            for (Entity nearEntity : profile.getPlayer().getNearbyEntities(4, 0, 4)) {
                                if (nearEntity instanceof Player) {
                                    if (KitPvP.isPlayingKitPvP((Player) nearEntity)) {
                                        if (!Kit.findByKitClass(Steelhead.class).isSelected(Profile.getProfile(nearEntity.getName()))) {
                                            if (!((Player) nearEntity).isSneaking()) {
                                                ((Player) nearEntity).damage(event.getDamage(), profile.getPlayer());
                                                ((Player) nearEntity).playSound(nearEntity.getLocation(), Sound.ANVIL_LAND, 1.0F, 1.0F);
                                            } else {
                                                ((Player) nearEntity).damage(event.getDamage() * 0.075, profile.getPlayer());
                                                ((Player) nearEntity).playSound(nearEntity.getLocation(), Sound.ANVIL_LAND, 1.0F, 1.0F);
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        event.setDamage(0.0F);
                    }
                }
            }
        }
    }
}
