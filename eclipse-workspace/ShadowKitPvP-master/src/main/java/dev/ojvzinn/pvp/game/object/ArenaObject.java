package dev.ojvzinn.pvp.game.object;

import dev.ojvzinn.pvp.Language;
import dev.ojvzinn.pvp.Main;
import dev.ojvzinn.pvp.api.DelayKits;
import dev.ojvzinn.pvp.api.PlayerArenaDeathEvent;
import dev.ojvzinn.pvp.container.LeagueContainer;
import dev.ojvzinn.pvp.cosmetics.Cosmetic;
import dev.ojvzinn.pvp.cosmetics.CosmeticType;
import dev.ojvzinn.pvp.cosmetics.collections.Kit;
import dev.ojvzinn.pvp.game.KitPvP;
import dev.ojvzinn.pvp.game.enums.WarpEnum;
import dev.ojvzinn.pvp.game.object.config.ArenaConfig;
import dev.ojvzinn.pvp.game.object.config.KitPvPConfig;
import dev.ojvzinn.pvp.league.LeagueManager;
import dev.ojvzinn.pvp.utils.PlayerUtils;
import dev.slickcollections.kiwizin.Core;
import dev.slickcollections.kiwizin.nms.NMS;
import dev.slickcollections.kiwizin.player.Profile;
import dev.slickcollections.kiwizin.player.hotbar.Hotbar;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.IOException;

public class ArenaObject extends KitPvP {
    public ArenaObject(String configName, WarpEnum warp) {
        super(configName, warp);
    }

    @Override
    public void join(Profile profile) {
        Player player = profile.getPlayer();
        this.listPlayers().add(player.getName());
        for (Player online : Bukkit.getOnlinePlayers()) {
            Bukkit.getScheduler().runTask(Main.getInstance(), ()-> {
                online.showPlayer(player);
                if (this.listPlayers().contains(online.getName())) {
                    player.showPlayer(online);
                }
            });
        }

        Bukkit.getScheduler().runTask(Main.getInstance(), ()-> {
            player.setAllowFlight(false);
            player.setFlying(false);
        });
    }

    @Override
    public void kill(Profile profile, Profile killer) {
        Player player = profile.getPlayer();
        Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> player.teleport(Core.getLobby()), 3L);
        Bukkit.getServer().getPluginManager().callEvent(new PlayerArenaDeathEvent(profile, killer, this));

        player.teleport(Core.getLobby());
        profile.setStats("kCoreKitPvP", 0L, "killstreaks");
        profile.addStats("kCoreKitPvP", "deaths");
        this.leave(profile);
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
        removePlayerPlaying(profile.getPlayer());
        PlayerUtils.refreshPlayer(profile.getPlayer());
        profile.setHotbar(Hotbar.getHotbarById("lobby"));
        NMS.sendTitle(profile.getPlayer(), "", "", 0, 1, 0);
        DelayKits.resetDelay(profile.getPlayer());
        try {
            Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
                if (profile.isOnline()) {
                    PlayerUtils.refreshPlayer(profile.getPlayer());
                }
            }, 3L);
        } catch (Exception ignored) {}
    }

    @Override
    public KitPvPConfig setupConfig(String configName) throws IOException {
        return new ArenaConfig(configName);
    }
}
