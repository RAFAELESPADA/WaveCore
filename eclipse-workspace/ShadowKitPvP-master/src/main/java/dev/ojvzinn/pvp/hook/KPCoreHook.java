package dev.ojvzinn.pvp.hook;

import com.comphenix.protocol.ProtocolLibrary;
import dev.ojvzinn.pvp.Language;
import dev.ojvzinn.pvp.Main;
import dev.ojvzinn.pvp.cosmetics.Cosmetic;
import dev.ojvzinn.pvp.cosmetics.CosmeticType;
import dev.ojvzinn.pvp.hook.hotbar.PVPHotbarActionType;
import dev.ojvzinn.pvp.hook.protocollib.HologramAdapter;
import dev.ojvzinn.pvp.league.LeagueManager;
import dev.slickcollections.kiwizin.Core;
import dev.slickcollections.kiwizin.player.Profile;
import dev.slickcollections.kiwizin.player.hotbar.Hotbar;
import dev.slickcollections.kiwizin.player.hotbar.HotbarAction;
import dev.slickcollections.kiwizin.player.hotbar.HotbarActionType;
import dev.slickcollections.kiwizin.player.hotbar.HotbarButton;
import dev.slickcollections.kiwizin.player.scoreboard.KScoreboard;
import dev.slickcollections.kiwizin.player.scoreboard.scroller.ScoreboardScroller;
import dev.slickcollections.kiwizin.plugin.config.KConfig;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.logging.Level;

public class KPCoreHook {

    public static void setupHook() {
        Core.minigame = "Kit PvP";

        setupHotbars();
        new BukkitRunnable() {
            @Override
            public void run() {
                Profile.listProfiles().forEach(profile -> {
                    if (profile.getScoreboard() != null) {
                        profile.getScoreboard().scroll();
                    }
                });
            }
        }.runTaskTimerAsynchronously(Main.getInstance(), 0, Language.scoreboards$scroller$every_tick);

        new BukkitRunnable() {
            @Override
            public void run() {
                Profile.listProfiles().forEach(profile -> {
                    if (!profile.playingGame() && profile.getScoreboard() != null) {
                        profile.update();
                    }
                });
            }
        }.runTaskTimerAsynchronously(Main.getInstance(), 0, 20);

        ProtocolLibrary.getProtocolManager().addPacketListener(new HologramAdapter());
    }

    public static void reloadScoreboard(Profile profile) {
        Player player = profile.getPlayer();
        List<String> lines = Language.scoreboards$main;

        profile.setScoreboard(new KScoreboard() {
            @Override
            public void update() {
                for (int index = 0; index < Math.min(lines.size(), 15); index++) {
                    String line = lines.get(index);
                    line = PlaceholderAPI.setPlaceholders(player, line);
                    line = line.replace("{kit}", Cosmetic.findCosmeticSelected(CosmeticType.KIT, profile).getName())
                            .replace("{league}", LeagueManager.findLeague(profile).getName());

                    this.add(15 - index, line);
                }
            }
        }.scroller(new ScoreboardScroller(Language.scoreboards$scroller$titles)).to(player).build());

        profile.getScoreboard().health().updateHealth();
        profile.update();
        profile.getScoreboard().scroll();
    }

    private static void setupHotbars() {
        HotbarActionType.addActionType("pvp", new PVPHotbarActionType());

        KConfig config = Main.getInstance().getConfig("hotbar");
        for (String id : new String[]{"lobby"}) {
            Hotbar hotbar = new Hotbar(id);

            for (String button : config.getSection(id).getKeys(false)) {
                try {
                    hotbar.getButtons().add(new HotbarButton(config.getInt(id + "." + button + ".slot"), new HotbarAction(config.getString(id + "." + button + ".execute")), config.getString(id + "." + button + ".icon")));
                } catch (Exception ex) {
                    Main.getInstance().getLogger().log(Level.WARNING, "Falha ao carregar o botao \"" + button + "\" da hotbar \"" + id + "\": ", ex);
                }
            }

            Hotbar.addHotbar(hotbar);
        }
    }

}
