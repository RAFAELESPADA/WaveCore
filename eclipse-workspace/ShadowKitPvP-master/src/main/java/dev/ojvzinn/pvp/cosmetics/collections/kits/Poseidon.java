package dev.ojvzinn.pvp.cosmetics.collections.kits;

import dev.ojvzinn.pvp.Main;
import dev.ojvzinn.pvp.api.DelayKits;
import dev.ojvzinn.pvp.api.PlayerArenaDeathEvent;
import dev.ojvzinn.pvp.cosmetics.Cosmetic;
import dev.ojvzinn.pvp.cosmetics.CosmeticType;
import dev.ojvzinn.pvp.cosmetics.collections.Kit;
import dev.ojvzinn.pvp.game.KitPvP;
import dev.slickcollections.kiwizin.player.Profile;
import dev.slickcollections.kiwizin.utils.BukkitUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Poseidon extends Kit {

    private Set<String> POTIONS_EFFECT = new HashSet<>();

    public Poseidon(String key, YamlConfiguration CONFIG) {
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
        DelayKits.createDelayProfile(player, this);
        player.sendMessage("§aVocê equipou o kit Poseidon.");
    }

    @Override
    public void setupListeners() {
        Bukkit.getPluginManager().registerEvents(this, Main.getInstance());
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (event.isCancelled()) {
            return;
        }

        Profile profile = Profile.getProfile(event.getPlayer().getName());
        if (KitPvP.isPlayingKitPvP(profile.getPlayer())) {
            if (isSelected(profile)) {
                Player player = profile.getPlayer();
                if (player.getLocation().getBlock().getType() == Material.WATER || player.getLocation().getBlock().getRelative(BlockFace.DOWN).getType() == Material.WATER || player.getLocation().getBlock().getType() == Material.STATIONARY_WATER || player.getLocation().getBlock().getRelative(BlockFace.DOWN).getType() == Material.STATIONARY_WATER) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20, 1), true);
                    player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20, 1), true);
                    if (POTIONS_EFFECT.contains(player.getName())) {
                        POTIONS_EFFECT.add(player.getName());
                    }

                    if (DelayKits.loadDelayProfiles(player).canUse()) {
                        player.sendMessage("§a[Poseidon] §fVocê recebeu velocidade II e força II por estar na água!");
                        DelayKits.loadDelayProfiles(player).addDelay(30);
                    }
                } else {
                    if (POTIONS_EFFECT.contains(player.getName())) {
                        player.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
                        player.removePotionEffect(PotionEffectType.SPEED);
                        POTIONS_EFFECT.remove(player.getName());
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPlayerDeathArena(PlayerArenaDeathEvent event) {
        if (isSelected(event.getProfile())) {
            POTIONS_EFFECT.remove(event.getProfile().getName());
        }
    }

}
