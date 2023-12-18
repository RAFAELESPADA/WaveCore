package dev.ojvzinn.pvp;

import dev.ojvzinn.pvp.commands.Commands;
import dev.ojvzinn.pvp.cosmetics.Cosmetic;
import dev.ojvzinn.pvp.cosmetics.collections.kits.Gladiator;
import dev.ojvzinn.pvp.game.KitPvP;
import dev.ojvzinn.pvp.hook.KPCoreHook;
import dev.ojvzinn.pvp.league.LeagueManager;
import dev.ojvzinn.pvp.listeners.ListenersAbstract;
import dev.ojvzinn.pvp.lobby.*;
import dev.ojvzinn.pvp.utils.TagHandlerExtension;
import dev.slickcollections.kiwizin.Core;
import dev.slickcollections.kiwizin.player.Profile;
import dev.slickcollections.kiwizin.plugin.KPlugin;
import dev.slickcollections.kiwizin.utils.BukkitUtils;
import org.bukkit.Bukkit;

public class Main extends KPlugin {

    private static Main instance;
    public static String currentServerName;

    public static Main getInstance() {
        return instance;
    }

    @Override
    public void start() {}

    @Override
    public void load() {
        saveDefaultConfig();
        currentServerName = getConfig().getString("lobby");

        instance = this;
    }

    @Override
    public void enable() {
        if (this.getConfig().getString("spawn") != null) {
            Core.setLobby(BukkitUtils.deserializeLocation(this.getConfig().getString("spawn")));
        }

        KPCoreHook.setupHook();
        ListenersAbstract.setupListeners();
        Commands.setupCommands();

        Cosmetic.setupCosmetics();
        KitPvP.setupArenas();
        LeagueManager.setupLeagues();
        Lobby.setupLobbies();
        TagHandlerExtension.setupHandler();

        Bukkit.getScheduler().runTaskLater(Main.getInstance(), ()-> {
                    FPSNPC.setupNPCs();
                    ArenaNPC.setupNPCs();
                    DeliveryNPC.setupNPCs();
                }, 20L);

        KPCoreHook.setupHook();
        Language.setupLanguage();
        Leaderboard.setupLeaderboards();

        getLogger().info("o plugin iniciou com sucesso!");
    }

    @Override
    public void disable() {
        Gladiator.destroyAll();
        Bukkit.getOnlinePlayers().forEach(player -> KitPvP.findWarpWherePlayer(player).leave(Profile.getProfile(player.getName())));
        getLogger().info("o plugin desligou com sucesso!");
    }
}
