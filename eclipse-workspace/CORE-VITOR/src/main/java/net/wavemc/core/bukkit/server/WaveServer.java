package net.wavemc.core.bukkit.server;

import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public enum WaveServer {

    ALL(25565,  "global", "network"),
    LOGIN(25567),
    LOBBY(25568, "hub"),
    KITPVP(25569),
    GLADIATOR(20012, "glad"),
    EVENTO(25571, "event"),
    RANKUP(25570, "rank");



    public static Optional<WaveServer> findServer(String serverName) {
        return Arrays.stream(values()).filter(server ->
                server.toString().equalsIgnoreCase(serverName)
                        || server.getAliases().contains(serverName.toLowerCase())
        ).findFirst();
    }

    @Getter private final int port;
    @Getter @Setter private int playerCount = 0;
    @Getter private final List<String> aliases;

    WaveServer(int port, String... aliases) {
        this.port = port;
        this.aliases = Arrays.asList(aliases);
    }
}
