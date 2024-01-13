package dev.wplugins.waze.gerementions.listeners;

import dev.wplugins.waze.gerementions.BukkitMain;
import dev.wplugins.waze.gerementions.Main;
import dev.wplugins.waze.gerementions.database.MySQLDatabase;
import dev.wplugins.waze.gerementions.punish.Punish;
import dev.wplugins.waze.gerementions.punish.dao.PunishDao;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerLoginEvent;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

public class BukkitListener implements Listener {
    private PunishDao punishDao;

    public BukkitListener() {
        punishDao = BukkitMain.getPlugin().getPunishDao();
    }
    SimpleDateFormat SDF = new SimpleDateFormat("dd/MM/yyyy 'às' HH:mm");
    SimpleDateFormat SDF2 = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    @EventHandler
    public void login(PlayerLoginEvent event) {
        String name = event.getPlayer().getName();
BukkitMain.getPlugin().getLogger().info("[WPunish] " + name +  " entrou");
        punishDao.clearPunishes2(name);
        try {

            Statement statement2 = MySQLDatabase.getInstance().getConnection().createStatement();
            ResultSet resultSet2 = statement2.executeQuery("SELECT * FROM wPunish2 WHERE playerName= '" + name + "' AND type='BAN'");
        if (resultSet2.next()) {
            BukkitMain.getPlugin().getLogger().info(name + " está banido do servidor até " + SDF.format(System.currentTimeMillis() +  (resultSet2.getLong("expires"))));
            String proof = (resultSet2.getString("proof") == null ? "Indisponível" : resultSet2.getString("proof"));
            String message =  "\n§cVocê está banido do servidor." + "\n§cExpira em: §7" + (resultSet2.getLong("expires") == 0 ? "Nunca" : SDF2.format(System.currentTimeMillis() + (resultSet2.getLong("expires"))));

            event.disallow(PlayerLoginEvent.Result.KICK_BANNED, message);
            event.setResult(PlayerLoginEvent.Result.KICK_BANNED);

    }
    } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
        @EventHandler
    public void chat(PlayerChatEvent event) {

            Bukkit.getServer().getOnlinePlayers().stream().filter(player -> player.getAddress().equals(event.getPlayer().getAddress())).findAny().ifPresent(player -> {
                punishDao.clearPunishes(player.getName());

                Statement statement2 = null;
                try {
                    statement2 = MySQLDatabase.getInstance().getConnection().createStatement();
                    ResultSet resultSet2 = statement2.executeQuery("SELECT * FROM wPunish2 WHERE playerName='" + player.getName() + "' AND type='MUTE'");


                        List<String> commands = Arrays.asList("/tell", "/g", "/r", "/c", "/lobby", "/p", "/s");

                        String message = event.getMessage();

                        if (event.getMessage().startsWith("/")) {
                            if (commands.stream().noneMatch(s -> message.startsWith(s) || message.startsWith(s.toUpperCase()) || message.equalsIgnoreCase(s))) {
                                event.setCancelled(false);
                                return;
                            }
                            if (message.startsWith("/report") || message.startsWith("/s") || message.startsWith("/c") || message.startsWith("/reportar") ||
                                    message.equalsIgnoreCase("/lobby") ||
                                    message.startsWith("/logar") || message.startsWith("/login") ||
                                    message.startsWith("/registrar") || message.startsWith("/register") || message.equalsIgnoreCase("/rejoin") ||
                                    message.equalsIgnoreCase("/reentrar") || message.equalsIgnoreCase("/leave") || message.equalsIgnoreCase("/loja") || message.equalsIgnoreCase("/party aceitar")) {
                                event.setCancelled(false);
                                return;

                            }
                        }
                        if (resultSet2.next()) {
                            event.setMessage(ChatColor.RED + "Você está mutado!" + ChatColor.RED + " Tempo: " + "\n§cExpira em: §7" + (resultSet2.getLong("expires") == 0 ? "Nunca" : SDF2.format(System.currentTimeMillis() + (resultSet2.getLong("expires")))));
                            event.setCancelled(true);


                        }
                } catch (SQLException e1){
                    throw new RuntimeException(e1);
                }});
}}











