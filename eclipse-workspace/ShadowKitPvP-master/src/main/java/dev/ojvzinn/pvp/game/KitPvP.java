package dev.ojvzinn.pvp.game;

import dev.ojvzinn.pvp.Main;
import dev.ojvzinn.pvp.game.enums.WarpEnum;
import dev.ojvzinn.pvp.game.object.ArenaObject;
import dev.ojvzinn.pvp.game.object.FpsObject;
import dev.ojvzinn.pvp.game.object.config.KitPvPConfig;
import dev.slickcollections.kiwizin.player.Profile;
import dev.slickcollections.kiwizin.utils.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class KitPvP {

    private static final List<KitPvP> WARPS_LIST = new ArrayList<>();
    private final Set<String> playersList;
    private final WarpEnum warpType;
    private KitPvPConfig config;

    public static void setupArenas() {
        new ArenaObject("arena", WarpEnum.ARENA);
        new FpsObject("fps", WarpEnum.FPS);

        Main.getInstance().getLogger().info("A arena e todas as warps foram carregadas com sucesso!");
    }

    public static KitPvP findWarpWherePlayer(Player player) {
        return WARPS_LIST.stream().filter(kitPvP -> kitPvP.listPlayers().contains(player.getName())).findFirst().orElse(null);
    }

    public static KitPvP findWarp(WarpEnum warp) {
        return WARPS_LIST.stream().filter(kitPvP -> kitPvP.getWarpType().equals(warp)).findFirst().orElse(null);
    }

    public static boolean isPlayingKitPvP(Player player) {
        return WARPS_LIST.stream().anyMatch(kitPvP -> kitPvP.listPlayers().contains(player.getName()));
    }

    public static void clearRegisters(Player player) {
        WARPS_LIST.stream().filter(kitPvP -> kitPvP.playersList.contains(player.getName())).collect(Collectors.toList()).forEach(kitPvP -> kitPvP.removePlayerPlaying(player));
    }

    public KitPvP(String configName, WarpEnum warpType) {
        this.playersList = new HashSet<>();
        this.config = null;
        try {
            this.config = setupConfig(configName);
        } catch (IOException e) {
            Main.getInstance().getLogger().severe("Ocorreu um erro enquanto carregavemos o arquivo '" + configName + ".yml'!");
        }

        System.out.println(this.config.getYamlConfig().getName());
        this.warpType = warpType;
        WARPS_LIST.add(this);
    }

    public abstract void join(Profile profile);
    public abstract void kill(Profile profile, Profile killer);
    public abstract void leave(Profile profile);
    public abstract KitPvPConfig setupConfig(String configName) throws IOException;

    public void broadcast(String message) {
        Bukkit.getOnlinePlayers().forEach(player -> player.sendMessage(StringUtils.formatColors(message)));
    }

    public boolean isPlaying(Player player) {
        return this.playersList.contains(player.getName());
    }

    public Set<String> listPlayers() {
        return this.playersList;
    }

    public KitPvPConfig getConfig() {
        return this.config;
    }

    public WarpEnum getWarpType() {
        return this.warpType;
    }

    @SuppressWarnings("unchecked")
    public <T extends KitPvPConfig> T getConfig(Class<? extends KitPvPConfig> configClass) {
        return (T) this.config;
    }

    public void removePlayerPlaying(Player player) {
        this.playersList.remove(player.getName());
    }

    public void addPlayerPlaying(Player player) {
        this.playersList.add(player.getName());
    }
}
