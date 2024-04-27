package me.rafaelauler.ss;


import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public enum PlayerGroup {

    DONO("Dono", 0, "Dono", "ydiscordhook.dono", ChatColor.DARK_RED, 1),
    SUBDONO("SubDono", 1, "SubDono", "ydiscordhook.subdono", ChatColor.DARK_RED, 2),
    DIRETOR("Diretor", 1, "Diretor", "ydiscordhook.diretor", ChatColor.DARK_AQUA, 3),
    SUBDIRETOR("SubDiretor", 1, "SubDiretor", "ydiscordhook.subdiretor", ChatColor.DARK_BLUE, 4),
    COORD("Coord", 4, "Coord", "ydiscordhook.coord", ChatColor.DARK_PURPLE, 5),
    GERENTE("Gerente", 2, "Gerente", "ydiscordhook.gerente", ChatColor.RED, 6),
    ADMIN("Admin", 3, "Admin", "ydiscordhook.admin", ChatColor.RED, 7),
    MODPLUS("Mod+", 5, "Mod+", "ydiscordhook.mod+", ChatColor.DARK_GREEN, 8),
    MOD("Mod", 5, "Mod", "ydiscordhook.mod", ChatColor.DARK_GREEN, 9),
    BUILDER("Construtor", 6, "Construtor", "ydiscordhook.construtor", ChatColor.BLUE, 10),
    TRIAL("Trial", 7, "Trial", "ydiscordhook.trial", ChatColor.LIGHT_PURPLE, 11),
    AJUDANTE("Ajudante", 7, "Ajudante", "ydiscordhook.ajudante", ChatColor.YELLOW, 12),
    ESTAGIARIO("Estagiario", 8, "Estagiário", "ydiscordhook.estagiario", ChatColor.LIGHT_PURPLE, 13),
    FAMOSO("Famoso", 9, "Famoso", "ydiscordhook.famoso", ChatColor.DARK_PURPLE, 14),
    YT("YT", 10, "YT", "ydiscordhook.yt", ChatColor.RED, 15),
    STREAMER("Streamer", 11, "Streamer", "ydiscordhook.streamer", ChatColor.DARK_AQUA, 16),
    TIKTOKER("TikToker", 12, "TikToker", "ydiscordhook.tiktoker", ChatColor.BLUE, 17),
    MINIYT("MiniYT", 10, "MiniYT", "ydiscordhook.miniyt", ChatColor.RED, 18),
    INVESTIDORPLUSPLUS("Invest++", 13, "Invest++", "ydiscordhook.investidor++", ChatColor.GREEN , 19),
    INVESTIDORPLUS("Invest+", 14, "Invest+", "ydiscordhook.investidor+", ChatColor.GREEN , 20),
    INVEST("Invest", 15, "Invest", "ydiscordhook.invest", ChatColor.GREEN , 21),
    IMPERADOR("Imperador", 16, "Imperador", "ydiscordhook.imperador", ChatColor.AQUA , 22),
    SUPREMO("Supremo", 17, "Supremo", "ydiscordhook.supremo", ChatColor.DARK_RED , 23),
    LENDARIO("Lendario", 17, "Lendario", "ydiscordhook.lendario", ChatColor.GOLD , 24),
    HEROI("Heroi", 17, "Heroi", "ydiscordhook.heroi", ChatColor.DARK_PURPLE , 25),
    CAMPEAO("Campeao", 17, "Campeao", "ydiscordhook.campeao", ChatColor.DARK_AQUA , 26),
    BUGHUNTER("BugHunter", 17, "BugHunter", "ydiscordhook.bughunter", ChatColor.DARK_GRAY , 27),
    APOIADOR("Apoiador", 17, "Apoiador", "ydiscordhook.apoiador", ChatColor.YELLOW , 28),
    NITRO("Nitro", 17, "Nitro", "ydiscordhook.nitro", ChatColor.LIGHT_PURPLE , 29),
    MEMBRO("Membro", 23, "Membro", "ydiscordhook.membro", ChatColor.GRAY, 30);

    private final String name;
    private final String permission;
    private final ChatColor color;
    private final int priority;

    PlayerGroup(final String s, final int n, final String name, final String permission, final ChatColor color, final int priority) {
        this.name = name;
        this.permission = permission;
        this.color = color;
        this.priority = priority;
    }

    public String getName() {
        return this.name;
    }

    public String getPermission() {
        return this.permission;
    }

    public ChatColor getColor() {
        return this.color;
    }

    public String getColoredName() {
        return this.getColor() + this.getName();
    }

    public int getPriority() {
        return this.priority;
    }

    public String getBoldColoredName() {
        return this.getColor() + "§l" + this.getName();
    }

    public static PlayerGroup getByName(final String name) {
        for (PlayerGroup group : PlayerGroup.values()) {
            if (group.name().equalsIgnoreCase(name)) {
                return group;
            }
        }
        return null;
    }

    public static PlayerGroup getGroup(final Player player) {
        for (PlayerGroup group : PlayerGroup.values()) {
            if (player.hasPermission(group.getPermission())) {
                return group;
            }
        }
        return PlayerGroup.MEMBRO;
    }

    public static String getPlayerNameWithGroup(Player player) {
        PlayerGroup group = getGroup(player);
        String prefix = group.getBoldColoredName().toUpperCase();
        return prefix + group.getColor() + " " + player.getName();
    }

}
