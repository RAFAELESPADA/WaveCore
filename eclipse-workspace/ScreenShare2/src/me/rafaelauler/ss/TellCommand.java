package me.rafaelauler.ss;


import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class TellCommand extends Command {
  private final Map<ProxiedPlayer, ProxiedPlayer> lastMessageMap;
  
  public TellCommand() {
    super("tell", "", new String[] { "msg", "w" });
    this.lastMessageMap = new HashMap<>();
  }
  
  public void execute(CommandSender sender, String[] args) {
    if (!(sender instanceof ProxiedPlayer)) {
      sender.sendMessage(TextComponent.fromLegacyText("§cApenas jogadores podem fazer isso."));
      return;
    } 
    ProxiedPlayer player = (ProxiedPlayer)sender;
    if (args.length < 2) {
      sender.sendMessage(TextComponent.fromLegacyText("§cComando incorreto. Use /tell <player> <mensagem>."));
      return;
    } 
    String targetPlayerName = args[0];
    String message = String.join(" ", Arrays.<CharSequence>copyOfRange((CharSequence[])args, 1, args.length));
    if (targetPlayerName.equalsIgnoreCase(player.getName())) {
      sender.sendMessage(TextComponent.fromLegacyText("§cVocê não pode mandar mensagem para você mesmo."));
      return;
    } 
    ProxiedPlayer targetPlayer = ProxyServer.getInstance().getPlayer(targetPlayerName);
    if (targetPlayer == null) {
      sender.sendMessage(TextComponent.fromLegacyText("§cNão achei esse jogador."));
      return;
    } 
    targetPlayer.sendMessage(TextComponent.fromLegacyText("§c§lTELL RECEBIDO §7" + player.getName() + ": §f" + message));
    sender.sendMessage(TextComponent.fromLegacyText("§c§lTELL ENVIADO §7" + targetPlayer.getName() + ": §f" + message));
    this.lastMessageMap.put(player, targetPlayer);
    this.lastMessageMap.put(targetPlayer, player);
  }
  
  public ProxiedPlayer getLastMessageRecipient(ProxiedPlayer player) {
    return this.lastMessageMap.get(player);
  }
}
