package me.rafaelauler.ss;


import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class HG extends Command {
	public static boolean COPADESATIVADA;
  public HG() {
    super("hg", null, new String[] { "scrim" });
  }
  
  
  public void execute(CommandSender commandSender, String[] args) {
  ServerInfo target = ProxyServer.getInstance().getServerInfo("hg");
  if (target == null) {
	  commandSender.sendMessage("�cServer Invalido");
	return;
  }
  ProxiedPlayer p = (ProxiedPlayer)commandSender;
  if (p.getServer().getInfo()  == target) {
	  commandSender.sendMessage("�cVoc� j� est� nesse servidor");
		return;
  }
  if (!OpenHG.istoggled && !p.hasPermission("kombo.cmd.report")) {
	  p.sendMessage(ChatColor.RED + "Essa sala est� aberta apenas para nossa equipe.");
	  return;
  }
  p.sendMessage("�aConectando...");
  p.connect(target);
  }
}
