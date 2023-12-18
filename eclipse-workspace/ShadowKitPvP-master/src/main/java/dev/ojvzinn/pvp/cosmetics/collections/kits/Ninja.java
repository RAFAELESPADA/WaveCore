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
import org.bukkit.Sound;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Ninja extends Kit {

    private final Map<String, String> CACHED_PLAYER = new HashMap<>();

    public Ninja(String key, YamlConfiguration CONFIG) {
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
        player.sendMessage("§aVocê equipou o kit Ninja.");
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
                    CACHED_PLAYER.put(profile.getName(), profileDamager.getName());
                    DelayKits.loadDelayProfiles(profile.getPlayer()).setCanSendMessage(true);
                }
            }
        }
    }

    @EventHandler
    public void onPlayerDeathArena(PlayerArenaDeathEvent event) {
        if (isSelected(event.getProfile())) {
            CACHED_PLAYER.remove(event.getProfile().getName());
        }
    }

    @EventHandler
    public void onPlayerSneaking(PlayerToggleSneakEvent event) {
        Player player = event.getPlayer();
        Profile profile = Profile.getProfile(player.getName());
        if (KitPvP.isPlayingKitPvP(player) && profile != null && event.isSneaking() && isSelected(profile)) {
            if (!DelayKits.loadDelayProfiles(player).canUse()) {
                if (DelayKits.loadDelayProfiles(player).canSendMessage()) {
                    player.sendMessage("§cAguarde " + DelayKits.loadDelayProfiles(player).getDelayTime(true) + "s para utilizar o kit novamente!");
                }
                return;
            }

            if (CACHED_PLAYER.containsKey(player.getName())) {
                if (Bukkit.getOnlinePlayers().stream().anyMatch(online -> online.getName().equals(CACHED_PLAYER.get(profile.getName()))) && KitPvP.isPlayingKitPvP(Bukkit.getPlayer(CACHED_PLAYER.get(player.getName())))) {
                    Player target = Bukkit.getPlayer(CACHED_PLAYER.get(player.getName()));
                    player.teleport(target.getLocation());
                    player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 1.0F, 1.0F);
                    player.sendMessage("§a[Ninja] §fVocê se teleportou até o jogador " + target.getName() + "!");
                    DelayKits.loadDelayProfiles(player).addDelay(10);
                    return;
                } else {
                    CACHED_PLAYER.remove(player.getName());
                }
            }

            player.sendMessage("§a[Ninja] §fNenhum jogador foi encontrado!");
            DelayKits.loadDelayProfiles(player).setCanSendMessage(false);
            DelayKits.loadDelayProfiles(player).addDelay(3);
        }
    }

}
