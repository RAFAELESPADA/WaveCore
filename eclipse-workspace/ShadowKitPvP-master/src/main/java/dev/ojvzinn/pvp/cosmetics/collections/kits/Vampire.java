package dev.ojvzinn.pvp.cosmetics.collections.kits;

import dev.ojvzinn.pvp.Main;
import dev.ojvzinn.pvp.api.DelayKits;
import dev.ojvzinn.pvp.api.PlayerArenaDeathEvent;
import dev.ojvzinn.pvp.cosmetics.collections.Kit;
import dev.ojvzinn.pvp.game.KitPvP;
import dev.slickcollections.kiwizin.player.Profile;
import dev.slickcollections.kiwizin.utils.BukkitUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Bat;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Objects;
import java.util.Random;

public class Vampire extends Kit {
    public Vampire(String key, YamlConfiguration CONFIG) {
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
        inventory.setItem(1, BukkitUtils.deserializeItemStack("376 : 1 : nome>&6Vampire &7(Clique direito)"));
        inventory.setItem(8, BukkitUtils.deserializeItemStack("COMPASS : 1 : nome>&aBússola"));
        inventory.setItem(15, new ItemStack(Material.RED_MUSHROOM, 16));
        inventory.setItem(14, new ItemStack(Material.BROWN_MUSHROOM, 16));
        inventory.setItem(13, new ItemStack(Material.BOWL, 16));

        for (int i = 0; i < inventory.getSize(); i++) {
            if (inventory.getItem(i) == null || inventory.getItem(i).getType() == Material.AIR) {
                inventory.setItem(i, new ItemStack(Material.MUSHROOM_SOUP));
            }
        }

        DelayKits.createDelayProfile(player, this);
        addProtection(player.getName());
        player.sendMessage("§aVocê equipou o kit Vampire.");
    }

    @Override
    public void setupListeners() {
        Bukkit.getPluginManager().registerEvents(this, Main.getInstance());
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageByEntityEvent event) {
        if (event.isCancelled()) {
            return;
        }

        if (event.getDamager() instanceof Player && event.getEntity() instanceof Player) {
            Profile profile = Profile.getProfile(event.getDamager().getName());
            Profile profileDamager = Profile.getProfile(event.getEntity().getName()) ;
            if (KitPvP.isPlayingKitPvP(profileDamager.getPlayer()) && KitPvP.isPlayingKitPvP(profile.getPlayer())) {
                if (isSelected(profile)) {
                    Player player = profile.getPlayer();
                    Player damagePlayer = profileDamager.getPlayer();
                    damagePlayer.playSound(damagePlayer.getLocation(), Sound.DRINK, 1.0F, 1.0F);
                    player.playSound(player.getLocation(), Sound.DRINK, 1.0F, 1.0F);
                    if (player.getHealth() + 0.5 < player.getMaxHealth()) {
                        player.setHealth(player.getHealth() + 0.5);
                    }

                    damagePlayer.setHealth(damagePlayer.getHealth() - 0.5);
                    player.sendMessage("§a[Vampire] §fVocê drenou um pouco da vida do jogador " + damagePlayer.getName());
                    damagePlayer.sendMessage("§a[Vampire] §f0 jogador " + player.getName() + " drenou um pouco da sua vida!");
                }
            }
        }
    }

    @EventHandler
    public void onPlayerInteractAbility(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Profile profile = Profile.getProfile(player.getName());
        if (KitPvP.isPlayingKitPvP(player)) {
            ItemStack item = event.getItem();
            if (item != null && item.getType() == Material.FERMENTED_SPIDER_EYE && item.getItemMeta() != null && item.getItemMeta().hasDisplayName() && item.getItemMeta().getDisplayName().equals("§6Vampire §7(Clique direito)") && isSelected(profile)) {
                if (!DelayKits.loadDelayProfiles(player).canUse()) {
                    player.sendMessage("§cAguarde " + DelayKits.loadDelayProfiles(player).getDelayTime(true) + "s para utilizar este kit novamente.");
                    return;
                }

                player.setAllowFlight(true);
                player.setFlying(true);
                player.playSound(player.getLocation(), Sound.BAT_LOOP, 1.0F, 1.0F);
                player.sendMessage("§a[Vampire] §fVocê poderá voar por 3 segundos!");

                for (int i = 0; i < 5; i++) {
                    Bat bat = player.getWorld().spawn(player.getLocation().clone().add(Math.random() * 1.0F, Math.random() * 1.0F, Math.random() * 1.0F), Bat.class);
                    bat.setAwake(true);
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            bat.remove();
                        }
                    }.runTaskLater(Main.getInstance(), 20 * 7);
                }

                new BukkitRunnable() {

                    private int ticks;

                    @Override
                    public void run() {
                        if (!KitPvP.isPlayingKitPvP(player)) {
                            super.cancel();
                            return;
                        }

                        if (ticks == 20 * 3) {
                            player.setAllowFlight(false);
                            player.setFlying(false);
                            super.cancel();
                            return;
                        }

                        ticks++;
                    }
                }.runTaskTimer(Main.getInstance(), 1L, 1L);

                if (DelayKits.loadDelayProfiles(player) != null) Objects.requireNonNull(DelayKits.loadDelayProfiles(player)).addDelay(33);
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
            if (item != null && item.getType() == Material.FERMENTED_SPIDER_EYE && item.getItemMeta() != null && item.getItemMeta().hasDisplayName() && item.getItemMeta().getDisplayName().equals("§6Vampire §7(Clique direito)") && isSelected(profile)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerDeathArena(PlayerArenaDeathEvent event) {
        if (isSelected(event.getProfile())) {
            Player player = event.getProfile().getPlayer();
            player.setAllowFlight(false);
            player.setFlying(false);
        }
    }
}
