package me.rafaelauler.ss;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class PingCommand extends Command {
  public PingCommand() {
    super("ping");
  }
  
  public void execute(CommandSender sender, String[] args) {
    if (!(sender instanceof ProxiedPlayer)) {
      sender.sendMessage("jogadores podem fazer isto.");
      return;
    } 
    ProxiedPlayer player = (ProxiedPlayer)sender;
    if (args.length == 1) {
      String targetPlayerName = args[0];
      ProxiedPlayer targetPlayer = ProxyServer.getInstance().getPlayer(targetPlayerName);
      if (targetPlayer == null) {
        sender.sendMessage("§cNão achei esse jogador.");
        return;
      } 
      int ping = targetPlayer.getPing();
      if (ping <= 80) {
        sender.sendMessage("§eO ping de " + targetPlayerName + " é de " + ping + "ms §a(BOM)");
      } else if (ping <= 170) {
    	  sender.sendMessage("§eO ping de " + targetPlayerName + " é de " + ping + "ms §e(MÉDIO)");
      } else {
    	  sender.sendMessage("§eO ping de " + targetPlayerName + " é de " + ping + "ms §c(RUIM)");
      } 
    } else {
      int ping = player.getPing();
      if (ping <= 80) {
        sender.sendMessage("§eSeu ping é de: " + ping + "ms §a(BOM)");
      } else if (ping <= 170) {
    	   sender.sendMessage("§eSeu ping é de: " + ping + "ms §e(MÉDIO)");
      } else {
    	   sender.sendMessage("§eSeu ping é de: " + ping + "ms §c(RUIM)");
      } 
    } 
  }
}
