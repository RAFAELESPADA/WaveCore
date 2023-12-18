package dev.ojvzinn.pvp.cosmetics.collections.kits;

import dev.ojvzinn.pvp.Main;
import dev.ojvzinn.pvp.cosmetics.collections.Kit;
import dev.ojvzinn.pvp.game.KitPvP;
import dev.slickcollections.kiwizin.player.Profile;
import dev.slickcollections.kiwizin.utils.BukkitUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Snail extends Kit {

    public Snail(String key, YamlConfiguration CONFIG) {
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
        player.sendMessage("§aVocê equipou o kit Snail.");
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
                    if (Math.random() * 100 < 10) {
                        Player player = profile.getPlayer();
                        Player damagePlayer = profileDamager.getPlayer();
                        damagePlayer.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 3, 1), true);

                        player.sendMessage("§a[Snail] §fVocê deixou o jogador " + damagePlayer.getName() + " lento!");
                        damagePlayer.sendMessage("§a[Snail] §f0 jogador " + player.getName() + " deixou você lento!");
                    }
                }
            }
        }
    }
}
