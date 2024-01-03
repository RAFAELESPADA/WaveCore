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
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class Fisherman extends Kit {

    public Fisherman(String key, YamlConfiguration CONFIG) {
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

        ItemStack fishhook = BukkitUtils.deserializeItemStack("346 : 1 : nome>&6Fisherman &7(Clique direito)");
        meta = fishhook.getItemMeta();
        meta.spigot().setUnbreakable(true);
        fishhook.setItemMeta(meta);

        inventory.setItem(0, sword);
        inventory.setItem(1, fishhook);
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
    public void onPlayerDamage(PlayerFishEvent event) {
        if (event.isCancelled()) {
            return;
        }
        if (event.getCaught() instanceof Player) {
            Profile profile = Profile.getProfile(event.getPlayer().getName());
            Profile profileFishing = Profile.getProfile(event.getCaught().getName());
            if (KitPvP.isPlayingKitPvP(profileFishing.getPlayer()) && KitPvP.isPlayingKitPvP(profile.getPlayer()) && !Kit.findByKitClass(Neo.class).isSelected(profileFishing)) {
                if (isSelected(profile)) {
                    if (event.getCaught().getWorld().equals(Bukkit.getWorld("1v1"))) {
                        return;
                    }
                    Player fishingPlayer = profileFishing.getPlayer();
                    Player player = profile.getPlayer();
                    fishingPlayer.teleport(player.getLocation());
                    player.sendMessage("§a[Fisherman] §fVocê puxou o jogador " + fishingPlayer.getName() + " até você!");
                    fishingPlayer.sendMessage("§a[Fisherman] §fVocê foi puxado para o jogador " + player.getName() + "!");
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
            if (item != null && item.getType() == Material.FISHING_ROD && item.getItemMeta() != null && item.getItemMeta().hasDisplayName() && item.getItemMeta().getDisplayName().equals("§6Fisherman §7(Clique direito)") && isSelected(profile)) {
                event.setCancelled(true);
            }
        }
    }
}
