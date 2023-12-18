package dev.ojvzinn.pvp.cosmetics.collections.kits;

import dev.ojvzinn.pvp.Main;
import dev.ojvzinn.pvp.api.DelayKits;
import dev.ojvzinn.pvp.api.PlayerArenaDeathEvent;
import dev.ojvzinn.pvp.cosmetics.Cosmetic;
import dev.ojvzinn.pvp.cosmetics.collections.Kit;
import dev.ojvzinn.pvp.game.KitPvP;
import dev.slickcollections.kiwizin.player.Profile;
import dev.slickcollections.kiwizin.utils.BukkitUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.HashMap;
import java.util.Map;

public class Switcher extends Kit {

    public Switcher(String key, YamlConfiguration CONFIG) {
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
        inventory.setItem(1, BukkitUtils.deserializeItemStack("332 : 1 : nome>&6Switcher &7(Clique direito) : encantar>SILK_TOUCH:1 : esconder>tudo"));
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
        player.sendMessage("§aVocê equipou o kit Switcher.");
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
            if (item != null && item.getType() == Material.SNOW_BALL && item.getItemMeta() != null && item.getItemMeta().hasDisplayName() && item.getItemMeta().getDisplayName().equals("§6Switcher §7(Clique direito)") && isSelected(profile)) {
                event.setCancelled(true);
                player.updateInventory();
                if (!DelayKits.loadDelayProfiles(player).canUse()) {
                    player.sendMessage("§cAguarde " + DelayKits.loadDelayProfiles(player).getDelayTime(true) + "s para utilizar este kit novamente.");
                    return;
                }

                Snowball snowball = player.launchProjectile(Snowball.class);
                snowball.setVelocity(player.getEyeLocation().getDirection().multiply(1.5));
                snowball.setMetadata("switcher", new FixedMetadataValue(Main.getInstance(), player.getName()));
                DelayKits.loadDelayProfiles(player).addDelay(10);
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
            if (item != null && item.getType() == Material.SNOW_BALL && item.getItemMeta() != null && item.getItemMeta().hasDisplayName() && item.getItemMeta().getDisplayName().equals("§6Switcher §7(Clique direito)") && isSelected(profile)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageByEntityEvent event) {
        if (event.isCancelled()) {
            return;
        }

        if (event.getEntity() instanceof Player && event.getDamager() instanceof Snowball) {
            Profile profile = Profile.getProfile(event.getEntity().getName());
            Player player = profile.getPlayer();
            if (KitPvP.isPlayingKitPvP(profile.getPlayer()) && event.getDamager().hasMetadata("switcher") && !Kit.findByKitClass(Neo.class).isSelected(profile)) {
                String name = event.getDamager().getMetadata("switcher").get(0).asString();
                if (Bukkit.getOnlinePlayers().stream().anyMatch(p -> p.getName().equals(name))) {
                    Player damager = Bukkit.getPlayer(name);
                    if (KitPvP.isPlayingKitPvP(damager) && isSelected(Profile.getProfile(damager.getName()))) {
                        Location damagerLocation = damager.getLocation();
                        damager.teleport(player.getLocation());
                        player.teleport(damagerLocation);
                        player.sendMessage("§a[Switcher] §fO jogador " + name + " acaba de trocar de lugar com você!");
                        player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 1.0F, 1.0F);
                        damager.sendMessage("§a[Switcher] §fVocê trocou de lugar com o jogador " + player.getName() + "!");
                        damager.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 1.0F, 1.0F);
                        player.damage(0.0, damager);
                    }
                }
            }
        }
    }

}
