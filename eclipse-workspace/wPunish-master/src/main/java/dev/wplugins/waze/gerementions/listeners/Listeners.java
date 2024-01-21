package dev.wplugins.waze.gerementions.listeners;

import dev.wplugins.waze.gerementions.BukkitMain;
import dev.wplugins.waze.gerementions.Main;
import dev.wplugins.waze.gerementions.database.MySQLDatabase;
import dev.wplugins.waze.gerementions.enums.reason.Reason;
import dev.wplugins.waze.gerementions.punish.dao.PunishDao;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.event.PreLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;

import java.net.InetSocketAddress;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

public class Listeners implements Listener {

    private PunishDao punishDao;

    public Listeners() {
        punishDao = Main.getInstance().getPunishDao();
    }

    SimpleDateFormat SDF = new SimpleDateFormat("dd/MM/yyyy 'às' HH:mm");
    SimpleDateFormat SDF2 = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    @EventHandler
    public void login(PreLoginEvent event) {
        String name = event.getConnection().getName();

        InetSocketAddress ip = event.getConnection().getAddress();
        BungeeCord.getInstance().getConsole().sendMessage("Jogador " + name + " está entrando (PRELOGIN)...");


        Main.getInstance().getLogger().log(Level.FINE
                , "Jogador " + name + " está entrando...");
        BungeeCord.getInstance().getScheduler().schedule(Main.getInstance(), new Runnable() {
            public void run() {

                {
                    try {

                        Statement statement3 = MySQLDatabase.getInstance().getConnection().createStatement();
                        ResultSet resultSet3 = statement3.executeQuery("SELECT * FROM wPunish WHERE playerName='" + name + "'");


                        Statement statement4 = MySQLDatabase.getInstance().getConnection().createStatement();


                        Statement statement2 = MySQLDatabase.getInstance().getConnection().createStatement();
                        ResultSet resultSet2 = statement2.executeQuery("SELECT * FROM wPunish WHERE playerName='" + name + "' AND (type='BAN' OR type='TEMPBAN' OR type='Banimento temporário')");
                        ResultSet resultSetIP = statement4.executeQuery("SELECT * FROM wPunish WHERE ip='" + ip.getAddress() + "'");

                        if ((resultSet2.next() && resultSet3.next())) {
                            Reason r = Reason.valueOf(resultSet3.getString("reason"));
                            event.setCancelled(true);
                            BungeeCord.getInstance().getConsole().sendMessage("Jogador " + name + " (" + ip.getAddress() + ") tentou entrar mas está banido");

                            String proof = (resultSet2.getString("proof") == null ? "Indisponível" : resultSet2.getString("proof"));
                            event.setCancelReason(TextComponent.fromLegacyText(Main.getInstance().getConfig().getString("Prefix").replace("&", "§") + "\n\n§cVocê está banido do servidor.\n" +
                                    "\n§cMotivo: " + r.getText() + " - " + proof +
                                    "\n§cAutor da punição: §7" + resultSet2.getString("stafferName") + "\n§cExpira em: §7" + (resultSet2.getLong("expires") == 0 ? "Nunca" : SDF2.format(resultSet2.getLong("expires")) +
                                    "\n§cID da punição: §e#" + resultSet2.getString("id") +
                                    "\n\n§cUse o ID §e#" + resultSet2.getString("id") + " §cpara criar uma revisão em " + Main.getInstance().getConfig().getString("AppealSite").replace("&", "§"))));
                            event.getConnection().disconnect(TextComponent.fromLegacyText(Main.getInstance().getConfig().getString("Prefix").replace("&", "§") + "\n\n§cVocê está banido do servidor.\n" +
                                    "\n§cMotivo: " + r.getText() + " - " + proof +
                                    "\n§cAutor da punição: §7" + resultSet2.getString("stafferName") + "\n§cExpira em: §7" + (resultSet2.getLong("expires") == 0 ? "Nunca" : SDF2.format(resultSet2.getLong("expires")) +
                                    "\n§cID da punição: §e#" + resultSet2.getString("id") +
                                    "\n\n§cUse o ID §e#" + resultSet2.getString("id") + " §cpara criar uma revisão em " + Main.getInstance().getConfig().getString("AppealSite").replace("&", "§"))));
                            return;
                        } else if (resultSetIP.next()) {

                                Reason r = Reason.valueOf(resultSetIP.getString("reason"));
                                event.setCancelled(true);
                                BungeeCord.getInstance().getConsole().sendMessage("Jogador " + name + " (" + ip.getAddress() + ") tentou entrar mas está banido");
                                String proof = (resultSetIP.getString("proof") == null ? "Indisponível" : resultSetIP.getString("proof"));
                                event.setCancelReason(TextComponent.fromLegacyText(Main.getInstance().getConfig().getString("Prefix").replace("&", "§") + "\n\n§eSeu IP está banido do servidor.\n" +
                                        "\n§cMotivo: " + r.getText() + " - " + proof +
                                        "\n§cAutor da punição: §7" + resultSetIP.getString("stafferName") + "\n§cExpira em: §7" + (resultSetIP.getLong("expires") == 0 ? "Nunca" : SDF2.format(resultSetIP.getLong("expires")) +
                                        "\n§cID da punição: §e#" + resultSetIP.getString("id") +
                                        "\n\n§cUse o ID §e#" + resultSetIP.getString("id") + " §cpara criar uma revisão em " + Main.getInstance().getConfig().getString("AppealSite").replace("&", "§"))));
                                event.getConnection().disconnect(TextComponent.fromLegacyText(Main.getInstance().getConfig().getString("Prefix").replace("&", "§") + "\n\n§eSeu IP está banido do servidor.\n" +
                                        "\n§cMotivo: " + r.getText() + " - " + proof +
                                        "\n§cAutor da punição: §7" + resultSetIP.getString("stafferName") + "\n§cExpira em: §7" + (resultSetIP.getLong("expires") == 0 ? "Nunca" : SDF2.format(resultSetIP.getLong("expires")) +
                                        "\n§cID da punição: §e#" + resultSetIP.getString("id") +
                                        "\n\n§cUse o ID §e#" + resultSetIP.getString("id") + " §cpara criar uma revisão em " + Main.getInstance().getConfig().getString("AppealSite").replace("&", "§"))));

                            } else {
                            Main.getInstance().getLogger().log(Level.FINE
                                    , "Jogador " + name + " não está banido");
                            BungeeCord.getInstance().getConsole().sendMessage("Jogador " + name + " não está banido...");
                        }
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }


                };
            }

            ;

        }, 1, TimeUnit.MILLISECONDS);
    }



    @EventHandler
    public void login(LoginEvent event) {
        String name = event.getConnection().getName();

        BungeeCord.getInstance().getConsole().sendMessage("Jogador " + name + " está entrando (LOGIN)...");


        InetSocketAddress ip = event.getConnection().getAddress();
        Main.getInstance().getLogger().log(Level.FINE
                , "Jogador " + name + " está entrando...");
        BungeeCord.getInstance().getScheduler().schedule(Main.getInstance(), new Runnable() {
            public void run() {

                {
                    try {


                        Statement statement2 = MySQLDatabase.getInstance().getConnection().createStatement();

                        Statement statement3 = MySQLDatabase.getInstance().getConnection().createStatement();
                        Statement statement4 = MySQLDatabase.getInstance().getConnection().createStatement();

                        ResultSet resultSet3 = statement3.executeQuery("SELECT * FROM wPunish WHERE playerName='" + name + "'");

                        ResultSet resultSetIP = statement4.executeQuery("SELECT * FROM wPunish WHERE ip='" + ip.getAddress() + "'");
                        ResultSet resultSet2 = statement2.executeQuery("SELECT * FROM wPunish WHERE playerName='" + name + "' AND (type='BAN' OR type='TEMPBAN' OR type='Banimento temporário')");

                        if ((resultSet2.next() && resultSet3.next())) {

                            Reason r = Reason.valueOf(resultSet3.getString("reason"));
                            event.setCancelled(true);
                            BungeeCord.getInstance().getConsole().sendMessage("Jogador " + name + " (" + ip.getAddress() + ") tentou entrar mas está banido");
                            String proof = (resultSet2.getString("proof") == null ? "Indisponível" : resultSet2.getString("proof"));
                            event.setCancelReason(TextComponent.fromLegacyText(Main.getInstance().getConfig().getString("Prefix").replace("&", "§") + "\n\n§cVocê está banido do servidor.\n" +
                                    "\n§cMotivo: " + r.getText() + " - " + proof +
                                    "\n§cAutor da punição: §7" + resultSet2.getString("stafferName") + "\n§cExpira em: §7" + (resultSet2.getLong("expires") == 0 ? "Nunca" : SDF2.format(resultSet2.getLong("expires")) +
                                    "\n§cID da punição: §e#" + resultSet2.getString("id") +
                                    "\n\n§cUse o ID §e#" + resultSet2.getString("id") + " §cpara criar uma revisão em " + Main.getInstance().getConfig().getString("AppealSite").replace("&", "§"))));
                            event.getConnection().disconnect(TextComponent.fromLegacyText(Main.getInstance().getConfig().getString("Prefix").replace("&", "§") + "\n\n§cVocê está banido do servidor.\n" +
                                    "\n§cMotivo: " + r.getText() + " - " + proof +
                                    "\n§cAutor da punição: §7" + resultSet2.getString("stafferName") + "\n§cExpira em: §7" + (resultSet2.getLong("expires") == 0 ? "Nunca" : SDF2.format(resultSet2.getLong("expires")) +
                                    "\n§cID da punição: §e#" + resultSet2.getString("id") +
                                    "\n\n§cUse o ID §e#" + resultSet2.getString("id") + " §cpara criar uma revisão em " + Main.getInstance().getConfig().getString("AppealSite").replace("&", "§"))));

                        }  else if (resultSetIP.next()) {

                            Reason r = Reason.valueOf(resultSetIP.getString("reason"));
                            event.setCancelled(true);
                            BungeeCord.getInstance().getConsole().sendMessage("Jogador " + name + " (" + ip.getAddress() + ") tentou entrar mas está banido por IP");
                            String proof = (resultSetIP.getString("proof") == null ? "Indisponível" : resultSetIP.getString("proof"));
                            event.setCancelReason(TextComponent.fromLegacyText(Main.getInstance().getConfig().getString("Prefix").replace("&", "§") + "\n\n§eSeu IP está banido do servidor.\n" +
                                    "\n§cMotivo: " + r.getText() + " - " + proof +
                                    "\n§cAutor da punição: §7" + resultSetIP.getString("stafferName") + "\n§cExpira em: §7" + (resultSetIP.getLong("expires") == 0 ? "Nunca" : SDF2.format(resultSetIP.getLong("expires")) +
                                    "\n§cID da punição: §e#" + resultSetIP.getString("id") +
                                    "\n\n§cUse o ID §e#" + resultSetIP.getString("id") + " §cpara criar uma revisão em " + Main.getInstance().getConfig().getString("AppealSite").replace("&", "§"))));
                            event.getConnection().disconnect(TextComponent.fromLegacyText(Main.getInstance().getConfig().getString("Prefix").replace("&", "§") + "\n\n§eSeu IP está banido do servidor.\n" +
                                    "\n§cMotivo: " + r.getText() + " - " + proof +
                                    "\n§cAutor da punição: §7" + resultSetIP.getString("stafferName") + "\n§cExpira em: §7" + (resultSetIP.getLong("expires") == 0 ? "Nunca" : SDF2.format(resultSetIP.getLong("expires")) +
                                    "\n§cID da punição: §e#" + resultSetIP.getString("id") +
                                    "\n\n§cUse o ID §e#" + resultSetIP.getString("id") + " §cpara criar uma revisão em " + Main.getInstance().getConfig().getString("AppealSite").replace("&", "§"))));

                            } else {
                            Main.getInstance().getLogger().log(Level.FINE
                                    , "Jogador " + name + " não está banido");
                            BungeeCord.getInstance().getConsole().sendMessage("Jogador " + name + " não está banido...");
                        }
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }


                }
            }

            ;

        }, 1, TimeUnit.MILLISECONDS);
    }




    @EventHandler
    public void chat(ChatEvent event) {
        String evmessage = event.getMessage();

        InetSocketAddress ip = event.getSender().getAddress();
        if (evmessage.contains("/report") || evmessage.startsWith("/report") && event.isCommand()) {
            return;
        }

        if (evmessage.contains("/glist") || evmessage.startsWith("/glist") && event.isCommand()) {
            return;
        }
        if (evmessage.contains("/rejoin") || evmessage.startsWith("/rejoin") && event.isCommand()) {
            return;
        }
        if (evmessage.contains("/rewards") || evmessage.startsWith("/reward") && event.isCommand()) {
            return;
        }
        ProxiedPlayer player = (ProxiedPlayer) event.getSender();
        try {
            BungeeCord.getInstance().getConsole().sendMessage("[SERVER CHAT] " + player.getName() + " -> " + event.getMessage());
            Main.getInstance().getLogger().log(Level.FINE, "[SERVER CHAT] " + player.getName() + " -> " + event.getMessage());
            Statement statement2 = MySQLDatabase.getInstance().getConnection().createStatement();
            ResultSet resultSet2 = statement2.executeQuery("SELECT * FROM wPunish WHERE playerName='" + player.getName() + "' AND (type='MUTE' OR type='Mute temporário' OR type='TEMPMUTE')");
            String message = event.getMessage();

            Statement statement3 = MySQLDatabase.getInstance().getConnection().createStatement();
            Statement statement4 = MySQLDatabase.getInstance().getConnection().createStatement();

            ResultSet resultSet3 = statement3.executeQuery("SELECT * FROM wPunish WHERE playerName='" + player.getName() + "'");
            ResultSet resultSetip = statement4.executeQuery("SELECT * FROM wPunish WHERE ip='" + BungeeCord.getInstance().getPlayer(player.getName()).getAddress().getAddress() + "'");
            boolean ipbanido = statement3.executeQuery("SELECT * FROM wPunish WHERE ip='" + ip.getAddress() + "'").next();

            List<String> commands = Arrays.asList("/tell", "/g", "/r");

            if ((resultSet2.next() && resultSet3.next())) {


                if (event.isCommand()) {

                    if (commands.stream().noneMatch(s -> message.startsWith(s) || message.startsWith(s.toUpperCase()) || message.equalsIgnoreCase(s))) {
                        event.setCancelled(false);
                        return;
                    }
                } else if (message.startsWith("/report") || message.startsWith("/s") || message.startsWith("/c") || message.startsWith("/reportar") ||
                        message.equalsIgnoreCase("/lobby") ||
                        message.startsWith("/logar") || message.startsWith("/login") || message.equalsIgnoreCase("/rejoin") ||
                        message.equalsIgnoreCase("/reentrar") || message.equalsIgnoreCase("/leave") || message.equalsIgnoreCase("/loja") || message.equalsIgnoreCase("/party aceitar")) {
                    event.setCancelled(false);
                    return;

                }
                String proof = (resultSet2.getString("proof") == null ? "Indisponível" : resultSet2.getString("proof"));
                String pf2 = (resultSetip.getString("proof") == null ? "Indisponível" : resultSetip.getString("proof"));


                BungeeCord.getInstance().getConsole().sendMessage("[SERVER CHAT] " + player.getName() + " is muted");
                Reason r = Reason.valueOf(resultSet3.getString("reason"));
                player.sendMessage(TextComponent.fromLegacyText("\n§c* Você está silenciado " + (resultSet2.getLong("expires") > 0 ? "até o dia " + SDF.format(resultSet2.getLong("expires")) : "permanentemente") +
                        "\n\n§c* Motivo: " + r.getText() + " - " + proof +
                        "\n§c* Autor: " + resultSet2.getString("stafferName") +
                        "\n§c* Use o ID §e#" + String.valueOf(resultSet2.getString("id")) + " §cpara criar uma revisão em " + Main.getInstance().getConfig().getString("AppealSite").replace("&", "§") +
                        "\n"));


                event.setCancelled(true);
                BungeeCord.getInstance().getConsole().sendMessage("[SERVER CHAT] " + player.getName() + " gets message canceled because is muted.");
                if (resultSetip.next()) {


                    BungeeCord.getInstance().getConsole().sendMessage("[SERVER CHAT] " + player.getName() + " is IP muted");
                    player.sendMessage(TextComponent.fromLegacyText("\n§c* Você está silenciado " + (resultSetip.getLong("expires") > 0 ? "até o dia " + SDF.format(resultSetip.getLong("expires")) : "permanentemente") +
                            "\n\n§c* Motivo: " + resultSetip.getString("reason") + " - " + pf2 +
                            "\n§c* Autor: " + resultSetip.getString("stafferName") +
                            "\n§c* Use o ID §e#" + String.valueOf(resultSetip.getString("id")) + " §cpara criar uma revisão em " + Main.getInstance().getConfig().getString("AppealSite").replace("&", "§") +
                            "\n"));


                    event.setCancelled(true);
                    BungeeCord.getInstance().getConsole().sendMessage("[SERVER CHAT] " + player.getName() + " gets message canceled because is muted.");
                }
                if (!event.isCommand()) {


                    BungeeCord.getInstance().getConsole().sendMessage("[SERVER CHAT] " + player.getName() + " is muted");
                    player.sendMessage(TextComponent.fromLegacyText("\n§c* Você está silenciado " + (resultSet2.getLong("expires") > 0 ? "até o dia " + SDF.format(resultSet2.getLong("expires")) : "permanentemente") +
                            "\n\n§c* Motivo: " + r.getText() + " - " + proof +
                            "\n§c* Autor: " + resultSet2.getString("stafferName") +
                            "\n§c* Use o ID §e#" + String.valueOf(resultSet2.getString("id")) + " §cpara criar uma revisão em " + Main.getInstance().getConfig().getString("AppealSite").replace("&", "§") +
                            "\n"));


                    event.setCancelled(true);
                    BungeeCord.getInstance().getConsole().sendMessage("[SERVER CHAT] " + player.getName() + " gets message canceled because is muted.");

                } else {
                    Main.getInstance().getLogger().log(Level.FINE
                            , "Jogador " + player.getName() + " não está MUTADO");
                    BungeeCord.getInstance().getConsole().sendMessage("Jogador " + player.getName() + " não está MUTADO");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }




    }}


















