package me.rafaelauler.ss;

import java.util.Arrays;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class BSudo extends Command {
  public BSudo() {
    super("bsudo", null, new String[0]);
  }
  
  
public void execute(CommandSender sender, String[] args) {
    if (!(sender instanceof ProxiedPlayer)) {
      sender.sendMessage("Apenas jogadores podem fazer isto.");
      return;
    } 
    ProxiedPlayer player = (ProxiedPlayer)sender;
    if (!player.hasPermission("kombo.cmd.evento")) {
      player.sendMessage("§cVocê não tem autorização.");
      return;
    } 
    if (args.length < 2) {
      sender.sendMessage("§cUso incorreto. /bsudo <player> <comando>.");
      return;
    } 
    String targetPlayerName = args[0];
    String command = String.join(" ", Arrays.<CharSequence>copyOfRange((CharSequence[])args, 1, args.length));
    ProxiedPlayer targetPlayer = ProxyServer.getInstance().getPlayer(targetPlayerName);
    if (targetPlayer == null) {
      sender.sendMessage("§cNão achei esse jogador.");
      return;
    } 
    player.sendMessage("§4§lSUDO §fVocê forçou " + targetPlayer + " a executar um comando via proxy.");
    ProxyServer.getInstance().getPluginManager().dispatchCommand((CommandSender)targetPlayer, command);
  }
}
