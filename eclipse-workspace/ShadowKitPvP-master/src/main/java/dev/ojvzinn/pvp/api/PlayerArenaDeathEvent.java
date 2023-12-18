package dev.ojvzinn.pvp.api;

import dev.ojvzinn.pvp.game.KitPvP;
import dev.slickcollections.kiwizin.player.Profile;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerArenaDeathEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlerList() {
        return handlers;
    }

    private final Profile profile;
    private final Profile killer;
    private final KitPvP game;

    public PlayerArenaDeathEvent(Profile profile, Profile killer, KitPvP game) {
        this.profile = profile;
        this.killer = killer;
        this.game = game;
    }

    public Profile getKiller() {
        return this.killer;
    }

    public Profile getProfile() {
        return this.profile;
    }

    public KitPvP getGame() {
        return this.game;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}
