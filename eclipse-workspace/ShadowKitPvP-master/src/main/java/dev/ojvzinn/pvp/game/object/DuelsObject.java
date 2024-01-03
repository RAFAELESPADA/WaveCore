package dev.ojvzinn.pvp.game.object;


import dev.ojvzinn.pvp.Language;
import dev.ojvzinn.pvp.Main;
import dev.ojvzinn.pvp.container.LeagueContainer;
import dev.ojvzinn.pvp.cosmetics.Cosmetic;
import dev.ojvzinn.pvp.cosmetics.CosmeticType;
import dev.ojvzinn.pvp.cosmetics.collections.Kit;
import dev.ojvzinn.pvp.game.KitPvP;
import dev.ojvzinn.pvp.game.enums.WarpEnum;
import dev.ojvzinn.pvp.game.object.config.DuelsConfig;
import dev.ojvzinn.pvp.game.object.config.FpsConfig;
import dev.ojvzinn.pvp.game.object.config.KitPvPConfig;
import dev.ojvzinn.pvp.league.LeagueManager;
import dev.ojvzinn.pvp.listeners.X1;
import dev.ojvzinn.pvp.utils.PlayerUtils;
import dev.slickcollections.kiwizin.nms.NMS;
import dev.slickcollections.kiwizin.player.Profile;
import dev.slickcollections.kiwizin.player.hotbar.Hotbar;
import dev.slickcollections.kiwizin.utils.BukkitUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DuelsObject extends KitPvP {

    private final Map<String, Kit> oldKits = new HashMap<>();

    public DuelsObject(String configName, WarpEnum warp) {
        super(configName, warp);
    }

    @Override
    public void join(Profile profile) {
        Location spawn = this.getConfig().getSpawnLocation();
        if (spawn == null) {
            profile.getPlayer().sendMessage("§cA arena 1V1 ainda não foi configurada!");
            return;
        }

        Player player = profile.getPlayer();
        for (Player online : Bukkit.getOnlinePlayers()) {
            Bukkit.getScheduler().runTask(Main.getInstance(), () -> {
                online.showPlayer(player);
                if (this.listPlayers().contains(online.getName())) {
                    player.showPlayer(online);
                }
            });
        }

        Bukkit.getScheduler().runTask(Main.getInstance(), () -> {
            player.setAllowFlight(false);
            player.setFlying(false);
        });

        player.teleport(spawn);
        X1.entrar1v1(player);
    }

    @Override
    public void kill(Profile profile, Profile killer) {
        Player player = profile.getPlayer();
        Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
            if (profile.isOnline()) {
                player.teleport(this.getConfig().getSpawnLocation());
                X1.entrar1v1(player);
            }
        }, 3L);

        player.teleport(this.getConfig().getSpawnLocation());
        profile.setStats("kCoreKitPvP", 0L, "killstreaks");
        profile.addStats("kCoreKitPvP", "deaths");
        if (killer == null) {
            broadcast(Language.game$messages$suicide.replace("{player}", player.getName()));
            return;
        }

        killer.addStats("kCoreKitPvP", "killstreaks");
        killer.addStats("kCoreKitPvP", "kills");
        killer.addCoinsWM("kCoreKitPvP", killer.calculateWM(Language.game$options$coins$kill));
        killer.getAbstractContainer("kCoreKitPvP", "leagues", LeagueContainer.class).setLeagueID(LeagueManager.findLeague(profile).getId());

        NMS.sendActionBar(killer.getPlayer(), "§6+ " + killer.calculateWM(Language.game$options$coins$kill) + " coins");
        broadcast(Language.game$messages$kill.replace("{player}", player.getName()).replace("{killer}", killer.getName()));
    }

    @Override
    public void leave(Profile profile) {
        this.listPlayers().remove(profile.getPlayer().getName());
        PlayerUtils.refreshPlayer(profile.getPlayer());
        profile.setHotbar(Hotbar.getHotbarById("lobby"));
        NMS.sendTitle(profile.getPlayer(), "", "", 0, 1, 0);
        try {
            Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
                if (profile.isOnline()) {
                    PlayerUtils.refreshPlayer(profile.getPlayer());
                }
            }, 3L);
        } catch (Exception ignored){}
    }

    @Override
    public KitPvPConfig setupConfig(String configName) throws IOException {
        DuelsConfig config = new DuelsConfig(configName);
        Main.getInstance().getLogger().info(config.getWorldname());
        return config;
    }

    public DuelsConfig getConfig() {
        return getConfig(DuelsConfig.class);
    }
}
