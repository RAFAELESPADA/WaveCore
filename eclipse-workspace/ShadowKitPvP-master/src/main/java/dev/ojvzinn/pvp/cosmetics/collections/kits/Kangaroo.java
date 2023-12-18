package dev.ojvzinn.pvp.cosmetics.collections.kits;

import dev.ojvzinn.pvp.Main;
import dev.ojvzinn.pvp.api.DelayKits;
import dev.ojvzinn.pvp.cosmetics.Cosmetic;
import dev.ojvzinn.pvp.cosmetics.CosmeticType;
import dev.ojvzinn.pvp.cosmetics.collections.Kit;
import dev.ojvzinn.pvp.game.KitPvP;
import dev.slickcollections.kiwizin.player.Profile;
import dev.slickcollections.kiwizin.utils.BukkitUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class Kangaroo extends Kit {

    public Kangaroo(String key, YamlConfiguration CONFIG) {
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
        inventory.setItem(1, BukkitUtils.deserializeItemStack("401 : 1 : nome>&6Kangaroo &7(Clique direito)"));
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
        player.sendMessage("§aVocê equipou o kit Kangaroo.");
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
            ItemStack item = player.getItemInHand();
            if (item != null && item.getType() == Material.FIREWORK && item.getItemMeta() != null && item.getItemMeta().hasDisplayName() && item.getItemMeta().getDisplayName().equals("§6Kangaroo §7(Clique direito)") && isSelected(profile)) {
                if (!DelayKits.loadDelayProfiles(player).canUse()) {
                    if (DelayKits.loadDelayProfiles(player).canSendMessage()) {
                        player.sendMessage("§cAguarde " + DelayKits.loadDelayProfiles(player).getDelayTime(true) + "s para utilizar o kit novamente!");
                    }

                    event.setCancelled(true);
                    return;
                }

                Location location = player.getLocation();
                if (location.getBlock().getRelative(BlockFace.DOWN).getType() != Material.AIR || location.getBlock().getRelative(BlockFace.DOWN).getRelative(BlockFace.DOWN).getType() != Material.AIR) {
                    player.setVelocity(player.getEyeLocation().getDirection().multiply(player.isSneaking() ? 2.0F : 1.0F).setY(player.isSneaking() ? 0.5F : 1.0F));
                    DelayKits.loadDelayProfiles(player).addDelayMilli(300); //0.5 segundos
                    DelayKits.loadDelayProfiles(player).setCanSendMessage(false);
                }

                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageByEntityEvent event) {
        if (event.isCancelled()) {
            return;
        }

        if (event.getDamager() instanceof Player && event.getEntity() instanceof Player) {
            Profile profile = Profile.getProfile(event.getEntity().getName());
            Profile profileDamager = Profile.getProfile(event.getDamager().getName());
            if (KitPvP.isPlayingKitPvP(profileDamager.getPlayer()) && KitPvP.isPlayingKitPvP(profile.getPlayer())) {
                if (isSelected(profile)) {
                    Player player = profile.getPlayer();
                    DelayKits.loadDelayProfiles(player).addDelay(3);
                    DelayKits.loadDelayProfiles(player).setCanSendMessage(true);
                }
            }
        }
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
                        event.setDamage(event.getDamage() * 0.090);
                    }
                }
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
            if (item != null && item.getType() == Material.FIREWORK && item.getItemMeta() != null && item.getItemMeta().hasDisplayName() && item.getItemMeta().getDisplayName().equals("§6Kangaroo §7(Clique direito)") && isSelected(profile)) {
                event.setCancelled(true);
            }
        }
    }
}
