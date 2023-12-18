package dev.ojvzinn.pvp.api;

import dev.ojvzinn.pvp.game.KitPvP;
import dev.ojvzinn.pvp.game.enums.WarpEnum;
import dev.slickcollections.kiwizin.player.Profile;
import dev.slickcollections.kiwizin.player.hotbar.Hotbar;
import dev.slickcollections.kiwizin.utils.BukkitUtils;
import dev.slickcollections.kiwizin.utils.CubeID;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class WarpConfig {

    private static final Map<String, WarpConfig> PLAYERS_CACHE = new HashMap<>();

    public static boolean hasSetter(String player) {
        return PLAYERS_CACHE.containsKey(player);
    }

    public static void setPlayer(String player, WarpEnum warp) {
        PLAYERS_CACHE.put(player, new WarpConfig(warp, player));
        Player playerObj = Bukkit.getPlayer(player);
        playerObj.closeInventory();
        playerObj.setGameMode(GameMode.CREATIVE);
        playerObj.getInventory().clear();
        Profile.getProfile(player).setHotbar(null);
        switch (warp) {
            case ARENA: {
                playerObj.getInventory().setItem(3, BukkitUtils.deserializeItemStack("BLAZE_ROD : 1 : nome>&aSetar Localizações CubeID"));
                playerObj.getInventory().setItem(5, BukkitUtils.deserializeItemStack("WOOL:5 : 1 : nome>&aSalvar Localizações"));
                break;
            }

            case FPS: {
                playerObj.getInventory().setItem(2, BukkitUtils.deserializeItemStack("BLAZE_ROD : 1 : nome>&aSetar Localizações CubeID"));
                playerObj.getInventory().setItem(4, BukkitUtils.deserializeItemStack("WOOL:5 : 1 : nome>&aSalvar Configurações"));
                playerObj.getInventory().setItem(6, BukkitUtils.deserializeItemStack("399 : 1 : nome>&aSetar Spawn"));
                break;
            }
        }

        playerObj.updateInventory();
    }

    public static void setupListeners(PlayerInteractEvent event) {
        if (PLAYERS_CACHE.containsKey(event.getPlayer().getName())) {
            PLAYERS_CACHE.get(event.getPlayer().getName()).setupAction(event);
        }
    }

    public static void clear(String player) {
        PLAYERS_CACHE.remove(player);
    }

    private Location location1;
    private Location location2;
    private Location spawnLocation;
    private final WarpEnum warp;
    private final Hotbar hotbar;

    public WarpConfig(WarpEnum warp, String player) {
        this.location1 = null;
        this.location2 = null;
        this.spawnLocation = null;
        this.warp = warp;
        this.hotbar = Profile.getProfile(player).getHotbar();
    }

    public void setupAction(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();
        if (item != null) {
            if (event.getAction().equals(Action.LEFT_CLICK_BLOCK) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                if (item.getType().equals(Material.BLAZE_ROD)) {
                    if (event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
                        this.location1 = event.getClickedBlock().getLocation();
                        player.sendMessage("§aLocalização 1 salva com sucesso!");
                    } else {
                        this.location2 = event.getClickedBlock().getLocation();
                        player.sendMessage("§aLocalização 2 salva com sucesso!");
                    }
                }
            } else {
                switch (warp) {
                    case FPS: {
                        if (item.getType().equals(Material.WOOL)) {
                            if (this.location1 == null || this.location2 == null || spawnLocation == null) {
                                player.sendMessage("§cÉ necessário setar as duas localizações antes de salvar e o local de spawn!");
                                return;
                            }

                            KitPvP.findWarp(this.warp).getConfig().setValue("spawn-cube-id", makeCubeID().toString());
                            KitPvP.findWarp(this.warp).getConfig().setValue("world-name", this.spawnLocation.getWorld().getName());
                            KitPvP.findWarp(this.warp).getConfig().setValue("spawn-location", BukkitUtils.serializeLocation(this.spawnLocation));
                            KitPvP.findWarp(this.warp).getConfig().reload();
                            player.sendMessage("§aSalvando localizações...");
                            player.setGameMode(GameMode.ADVENTURE);
                            player.closeInventory();
                            player.getInventory().clear();
                            Profile.getProfile(player.getName()).setHotbar(this.hotbar);
                            Profile.getProfile(player.getName()).getHotbar().apply(Profile.getProfile(player.getName()));
                            PLAYERS_CACHE.remove(player.getName());
                        } else if (item.getType().equals(Material.NETHER_STAR)) {
                            this.spawnLocation = player.getLocation();
                            player.sendMessage("§aLocal de spawn da FPS setado com sucesso!");
                        }
                        break;
                    }

                    case ARENA: {
                        if (item.getType().equals(Material.WOOL)) {
                            if (this.location1 == null || this.location2 == null) {
                                player.sendMessage("§cÉ necessário setar as duas localizações antes de salvar!");
                                return;
                            }

                            KitPvP.findWarp(this.warp).getConfig().setValue("spawn-cube-id", makeCubeID().toString());
                            KitPvP.findWarp(this.warp).getConfig().reload();
                            player.sendMessage("§aSalvando localizações...");
                            player.setGameMode(GameMode.ADVENTURE);
                            player.closeInventory();
                            player.getInventory().clear();
                            Profile.getProfile(player.getName()).setHotbar(this.hotbar);
                            Profile.getProfile(player.getName()).getHotbar().apply(Profile.getProfile(player.getName()));
                            PLAYERS_CACHE.remove(player.getName());
                        }
                        break;
                    }
                }
            }
        }
    }

    public CubeID makeCubeID() {
        return new CubeID(this.location1, this.location2);
    }

    public Location getLocation1() {
        return this.location1;
    }

    public Location getLocation2() {
        return this.location2;
    }

    public Location getSpawnLocation() {
        return this.spawnLocation;
    }
}
