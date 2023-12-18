package dev.ojvzinn.pvp.listeners.collections;

import dev.ojvzinn.pvp.Language;
import dev.ojvzinn.pvp.Main;
import dev.ojvzinn.pvp.container.LeagueContainer;
import dev.ojvzinn.pvp.game.KitPvP;
import dev.ojvzinn.pvp.game.enums.WarpEnum;
import dev.ojvzinn.pvp.game.object.config.ArenaConfig;
import dev.ojvzinn.pvp.hook.KPCoreHook;
import dev.ojvzinn.pvp.league.LeagueManager;
import dev.ojvzinn.pvp.listeners.ListenersAbstract;
import dev.ojvzinn.pvp.utils.PlayerUtils;
import dev.ojvzinn.pvp.utils.tagger.TagUtils;
import dev.slickcollections.kiwizin.Core;
import dev.slickcollections.kiwizin.nms.NMS;
import dev.slickcollections.kiwizin.player.Profile;
import dev.slickcollections.kiwizin.player.hotbar.Hotbar;
import dev.slickcollections.kiwizin.titles.TitleManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListeners extends ListenersAbstract {

    @EventHandler
    public void onPlayerJoinListeners(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        Profile profile = Profile.getProfile(player.getName());
        KPCoreHook.reloadScoreboard(profile);
        profile.setHotbar(Hotbar.getHotbarById("lobby"));
        PlayerUtils.refreshPlayer(player);
        player.teleport(Core.getLobby());
        TagUtils.sendTeams(player);

        Bukkit.getScheduler().runTaskLaterAsynchronously(Main.getInstance(), () -> {
            TagUtils.setTag(player, profile.getTagContainer().getRoleTag());
            TitleManager.joinLobby(profile);
        }, 5L);

        NMS.sendTitle(player, "", "", 0, 1, 0);
        if (Language.lobby$tab$enabled) {
            NMS.sendTabHeaderFooter(player, Language.lobby$tab$header, Language.lobby$tab$footer);
        }

        profile.getAbstractContainer("kCoreKitPvP", "leagues", LeagueContainer.class).setLeagueID(LeagueManager.findLeague(profile).getId());
        event.setJoinMessage(null);
    }
}
