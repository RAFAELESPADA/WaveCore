package me.rafaelauler.ss;

import java.util.HashMap;
import java.util.Map;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class LobbyClass extends Command {
  private Map<ProxiedPlayer, Long> cooldowns;
  
  public LobbyClass() {
    super("lobby", "", new String[] { "l", "hub" });
    this.cooldowns = new HashMap<>();
  }
  
  public void execute(CommandSender sender, String[] args) {
    ProxiedPlayer player = (ProxiedPlayer)sender;
    if (!(sender instanceof ProxiedPlayer))
      return; 
    if (player.getServer().getInfo().getName().equalsIgnoreCase("lobby")) {
      player.sendMessage("§cVocê já está conectado neste servidor.");
      return;
    } 
    
    if (this.cooldowns.containsKey(player)) {
      long lastUsage = ((Long)this.cooldowns.get(player)).longValue();
      long currentTime = System.currentTimeMillis();
      long remainingTime = lastUsage + 5000L - currentTime;
      if (remainingTime > 0L) {
        player.sendMessage("§cAguarde " + (remainingTime / 1000L) + " segundos para se conectar novamente.");
        return;
      } 
    }
   // if (player.getServer().getInfo().getName().equalsIgnoreCase("arena")) {
    //	 player.sendMessage("§aVocê voltou ao spawn.");
    	//    player.connect(ProxyServer.getInstance().getServerInfo("academy"));
        //return;
     // } 
    player.sendMessage("§aVocê foi conectado ao lobby com sucesso.");
    player.connect(ProxyServer.getInstance().getServerInfo("lobby"));
    this.cooldowns.put(player, Long.valueOf(System.currentTimeMillis()));
  }
}
