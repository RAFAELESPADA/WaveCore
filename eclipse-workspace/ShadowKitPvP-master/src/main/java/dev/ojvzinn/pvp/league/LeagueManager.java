package dev.ojvzinn.pvp.league;

import dev.ojvzinn.pvp.Main;
import dev.ojvzinn.pvp.league.object.League;
import dev.slickcollections.kiwizin.player.Profile;
import dev.slickcollections.kiwizin.plugin.config.KConfig;
import dev.slickcollections.kiwizin.plugin.logger.KLogger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class LeagueManager {

    private static final List<League> LEAGUES_CACHE = new ArrayList<>();
    private static final KLogger logger = new KLogger(Main.getInstance()).getModule("LEAGUES");

    public static void setupLeagues() {
        KConfig config = Main.getInstance().getConfig("league");
        List<String> selection = new ArrayList<>(config.getSection("leagues").getKeys(false));
        for (String key : selection) {
            String name = config.getString("leagues." + key + ".name");
            String tag = config.getString("leagues." + key + ".tag");
            long kills = config.getRawConfig().getLong("leagues." + key + ".kills");
            LEAGUES_CACHE.add(new League(name, tag, kills));
        }

        logger.info("Foram carregados " + LEAGUES_CACHE.size() + " ligas de ranking!");
    }

    public static League findLeague(Profile profile) {
        return LEAGUES_CACHE.stream().filter(league -> profile.getStats("kCoreKitPvP", "kills") >= league.getKillsNecessary()).findFirst().orElse(LEAGUES_CACHE.get(LEAGUES_CACHE.size() - 1));
    }
    public static League lastLeague() {
        return LEAGUES_CACHE.get(LEAGUES_CACHE.size());
    }
    public static League nextLeague(Profile profile) {
        return LEAGUES_CACHE.get(findLeague(profile).getId() + 1) == null ? LEAGUES_CACHE.get(findLeague(profile).getId()) : LEAGUES_CACHE.get(findLeague(profile).getId() + 1);
    }

    public static League findByID(int id) {
        return LEAGUES_CACHE.stream().filter(league -> league.getId() == id).findFirst().orElse(LEAGUES_CACHE.get(LEAGUES_CACHE.size() - 1));
    }

    public static List<League> listLeagues() {
        return LEAGUES_CACHE;
    }
}
