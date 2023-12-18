package dev.ojvzinn.pvp.league.object;

import dev.ojvzinn.pvp.league.LeagueManager;

public class League {

    private int id;
    private final String name;
    private final String tag;
    private final long killsNecessary;

    public League(String name, String tag, Long killsNecessary) {
        this.id = LeagueManager.listLeagues().size() - 1;
        this.name = name;
        this.tag = tag;
        this.killsNecessary = killsNecessary;
    }

    public String getName() {
        return this.name;
    }

    public long getKillsNecessary() {
        return this.killsNecessary;
    }

    public String getTag() {
        return this.tag;
    }

    public int getId() {
        return this.id;
    }
}
