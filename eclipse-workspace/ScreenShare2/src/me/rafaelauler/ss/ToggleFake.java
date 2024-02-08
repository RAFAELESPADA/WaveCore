package me.rafaelauler.ss;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class ToggleFake extends Command {
  public ToggleFake() {
    super("togglefake", null, new String[] { "togglefakeplayers" });
  }
	public static boolean istoggled = false;
	
  public void execute(CommandSender sender, String[] args) {
    ProxiedPlayer p = (ProxiedPlayer)sender;
    if (!(sender instanceof ProxiedPlayer))
      p.sendMessage("Apenas jogadores podem fazer isto."); 
    if (p.hasPermission("tag.admin")) {
      if (!istoggled) {
        istoggled = true;
        p.sendMessage("FAKE PLAYERS DESATIVADO");
      } 
      else {
    	  istoggled = false;
          p.sendMessage("FAKE PLAYERS ATIVADO");  
      }
    } 
  }
}