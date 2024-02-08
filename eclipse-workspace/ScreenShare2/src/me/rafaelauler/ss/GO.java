package me.rafaelauler.ss;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class GO extends Command {
	public static boolean COPADESATIVADA;
  public GO() {
    super("go", null, new String[] { "server" });
  }
  
  
  public void execute(CommandSender commandSender, String[] args) {
	if (!commandSender.hasPermission("staffchat.use")) {
		commandSender.sendMessage("§cNo permission.");
		return;
	}
  
  if (args.length == 0) {
		commandSender.sendMessage("§cUse /server <Server>.");
		commandSender.sendMessage("§eServers: kitpvp , rankup, skywars , bedwars, thebridge");
		return;
  }
  if (args.length >= 1) {
  ServerInfo target = ProxyServer.getInstance().getServerInfo(args[0]);
  if (target == null) {
	  commandSender.sendMessage("§cServer Invalido");
	return;
  }
  ProxiedPlayer p = (ProxiedPlayer)commandSender;
  if (p.getServer().getInfo()  == target) {
	  commandSender.sendMessage("§cVocê já está nesse servidor");
		return;
  }
  p.sendMessage("§aConectando...");
  p.connect(target);
  }
}
}