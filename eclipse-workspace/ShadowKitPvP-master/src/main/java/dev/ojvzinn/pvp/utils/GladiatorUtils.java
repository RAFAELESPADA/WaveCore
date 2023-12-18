package dev.ojvzinn.pvp.utils;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class GladiatorUtils {

    private final List<Location> LOCATIONS;
    private final Location playerLocation;
    private final Player player;
    private final Location clickedPlayerLocation;
    private final Player playerClicked;

    public GladiatorUtils(Location playerLocation, Location clickedPlayerLocation, Player player, Player playerClicked) {
        this.playerLocation = playerLocation;
        this.clickedPlayerLocation = clickedPlayerLocation;
        this.LOCATIONS = new ArrayList<>();
        this.player = player;
        this.playerClicked = playerClicked;
    }

    public Player getPlayerClicked() {
        return this.playerClicked;
    }

    public Player getPlayer() {
        return this.player;
    }

    public void addLocation(Location location) {
        this.LOCATIONS.add(location);
    }

    public List<Location> listLocations() {
        return this.LOCATIONS;
    }

    public Location getClickedPlayerLocation() {
        return this.clickedPlayerLocation;
    }

    public Location getPlayerLocation() {
        return this.playerLocation;
    }
}
