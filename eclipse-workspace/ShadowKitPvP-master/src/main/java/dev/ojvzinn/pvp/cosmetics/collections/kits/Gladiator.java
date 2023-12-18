package dev.ojvzinn.pvp.cosmetics.collections.kits;

import dev.ojvzinn.pvp.Main;
import dev.ojvzinn.pvp.api.DelayKits;
import dev.ojvzinn.pvp.api.PlayerArenaDeathEvent;
import dev.ojvzinn.pvp.cosmetics.collections.Kit;
import dev.ojvzinn.pvp.game.KitPvP;
import dev.ojvzinn.pvp.utils.GladiatorUtils;
import dev.slickcollections.kiwizin.player.Profile;
import dev.slickcollections.kiwizin.utils.BukkitUtils;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Bat;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Gladiator extends Kit {

    private static final Map<String, GladiatorUtils> JAILS = new HashMap<>();

    public static void destroyAll() {
        JAILS.values().forEach(utils -> destroyJail(utils.getPlayer().getName()));
    }

    private static void destroyJail(String name) {
        for (Location location : JAILS.get(name).listLocations()) {
            location.getBlock().setType(Material.AIR);
            location.getBlock().getState().update();
        }

        JAILS.remove(name);
    }

    public Gladiator(String key, YamlConfiguration CONFIG) {
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
        inventory.setItem(1, BukkitUtils.deserializeItemStack("101 : 1 : nome>&6Gladiator &7(Clique direito)"));
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
        player.sendMessage("§aVocê equipou o kit Gladiator.");
    }

    @Override
    public void setupListeners() {
        Bukkit.getPluginManager().registerEvents(this, Main.getInstance());
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractAtEntityEvent event) {
        if (event.getRightClicked() instanceof Player) {
            Profile profile = Profile.getProfile(event.getPlayer().getName());
            Profile profileClicked = Profile.getProfile(event.getRightClicked().getName());
            if (profileClicked != null && profile != null && KitPvP.isPlayingKitPvP(profileClicked.getPlayer()) && KitPvP.isPlayingKitPvP(profile.getPlayer())) {
                if (isSelected(profile)) {
                    Player player = profile.getPlayer();
                    Player playerClicked = profileClicked.getPlayer();
                    ItemStack item = player.getItemInHand();
                    if (item != null && item.getType() == Material.IRON_FENCE && item.getItemMeta() != null && item.getItemMeta().hasDisplayName() && item.getItemMeta().getDisplayName().equals("§6Gladiator §7(Clique direito)") &&  !Kit.findByKitClass(Neo.class).isSelected(profileClicked)) {
                        event.setCancelled(true);
                        if (JAILS.containsKey(playerClicked.getName()) || JAILS.values().stream().anyMatch(utils -> utils.getPlayerClicked().equals(playerClicked))) {
                            player.sendMessage("§a[Gladiator] §fNão é possível puxar esse jogador por já estar em uma jaula!");
                            return;
                        }

                        if (255 - ((player.getLocation().getY() + 50) + 8) < 0) {
                            player.sendMessage("§cNão é possível criar uma jaula aqui!");
                            return;
                        }

                        if (!createJailGlass(player.getWorld(), player.getLocation().getX(), player.getLocation().getY() + 50, player.getLocation().getZ(), 8, Material.GLASS, player, new GladiatorUtils(player.getLocation(), playerClicked.getLocation(), player, playerClicked))) {
                            player.sendMessage("§a[Gladiator] §fNão é possível criar uma jaula aqui!");
                        }
                    }
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
            if (item != null && item.getType() == Material.IRON_FENCE && item.getItemMeta() != null && item.getItemMeta().hasDisplayName() && item.getItemMeta().getDisplayName().equals("§6Gladiator §7(Clique direito)") && isSelected(profile)) {
                event.setCancelled(true);
                player.updateInventory();
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
            if (item != null && item.getType() == Material.IRON_FENCE && item.getItemMeta() != null && item.getItemMeta().hasDisplayName() && item.getItemMeta().getDisplayName().equals("§6Gladiator §7(Clique direito)") && isSelected(profile)) {
                event.setCancelled(true);
                player.updateInventory();
            }
        }
    }

    private boolean createJailGlass(World world, double centerX, double centerY, double centerZ, int size, Material material, Player player, GladiatorUtils utils) {
        List<Location> locs = new ArrayList<>();
        for (double x = centerX - size; x <= centerX + size; x++) {
            for (double y = centerY - size; y <= centerY + size; y++) {
                for (double z = centerZ - size; z <= centerZ + size; z++) {
                    Block block = world.getBlockAt((int) x, (int) y, (int) z);
                    if (block.getType() != Material.AIR) {
                        return false;
                    }

                    locs.add(block.getLocation());
                }
            }
        }

        size--;
        for (double x = centerX - size; x <= centerX + size; x++) {
            for (double y = centerY - size; y <= centerY + size; y++) {
                for (double z = centerZ - size; z <= centerZ + size; z++) {
                    Block block = world.getBlockAt((int) x, (int) y, (int) z);
                    locs.remove(block.getLocation());
                }
            }
        }

        locs.forEach(location -> {
            location.getBlock().setType(material);
            location.getBlock().setMetadata("atribute", new FixedMetadataValue(Main.getInstance(), "indestrutible"));
            location.getBlock().getState().update();
            utils.addLocation(location);
        });

        Location firstLocation = locs.get(0).clone();
        firstLocation.setY(centerY - size);
        firstLocation.add(1.5, 0, 1.5);
        firstLocation.setYaw(-45.0f);
        firstLocation.setPitch(0.0f);

        Location lastLocation = locs.get(locs.size() - 1).clone();
        lastLocation.setY(centerY - size);
        lastLocation.add(-0.5, 0, -0.5);
        lastLocation.setYaw(135.0f);
        lastLocation.setPitch(0.0f);

        utils.getPlayer().teleport(firstLocation);
        utils.getPlayerClicked().teleport(lastLocation);
        JAILS.put(player.getName(), utils);
        return true;
    }

    @EventHandler
    public void onPlayerDeathArena(PlayerArenaDeathEvent event) {
        Profile profile = event.getProfile();
        Profile killer = event.getKiller();
        if (isSelected(profile)) {
            if (JAILS.containsKey(profile.getName())) {
                JAILS.get(profile.getName()).getPlayerClicked().teleport(JAILS.get(profile.getName()).getClickedPlayerLocation());
                destroyJail(profile.getName());
            }
        }

        if (killer != null && isSelected(killer)) {
            if (JAILS.containsKey(killer.getName())) {
                JAILS.get(killer.getName()).getPlayer().teleport(JAILS.get(killer.getName()).getPlayerLocation());
                destroyJail(killer.getName());
            }
        }
    }

}
