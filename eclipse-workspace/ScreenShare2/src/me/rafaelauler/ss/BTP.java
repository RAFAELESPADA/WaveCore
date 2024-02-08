package me.rafaelauler.ss;


import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.scheduler.ScheduledTask;

public class BTP extends Command {
  public BTP() {
    super("btp", null, new String[0]);
  }
  
  
public void execute(CommandSender sender, String[] args) {
    if (!(sender instanceof ProxiedPlayer)) {
      sender.sendMessage("Apenas jogadores podem fazer isto.");
      return;
    } 
    ProxiedPlayer player = (ProxiedPlayer)sender;
    if (!player.hasPermission("staffchat.use")) {
      player.sendMessage("§cVocê não tem autorização.");
      return;
    } 
    if (args.length < 1) {
      sender.sendMessage("§cUso incorreto. /btp <player>.");
      return;
    } 
    String targetPlayerName = args[0];
    ProxiedPlayer from = (ProxiedPlayer)sender;
    ProxiedPlayer to = ProxyServer.getInstance().getPlayer(args[0]);
    String command = String.join(" ", Arrays.<CharSequence>copyOfRange((CharSequence[])args, 1, args.length));
    ProxiedPlayer targetPlayer = ProxyServer.getInstance().getPlayer(targetPlayerName);
    if (targetPlayer == null) {
      sender.sendMessage("§cNão achei esse jogador.");
      return;
    } 
    teleport(from, to);
  }

public static void teleport(ProxiedPlayer from, ProxiedPlayer to) {
    if (from.getServer().getInfo() != to.getServer().getInfo())
      from.connect(to.getServer().getInfo()); 
    ScheduledTask schedule = ProxyServer.getInstance().getScheduler().schedule(Main.getInstance(), () -> BungeeSend.teleport(from, to), 1L, TimeUnit.SECONDS);
   
  }
}
  