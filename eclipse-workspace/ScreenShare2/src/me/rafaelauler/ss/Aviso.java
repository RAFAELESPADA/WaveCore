package me.rafaelauler.ss;


import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class Aviso extends Command {
  public Aviso() {
    super("aviso", null, new String[] { "alerta" });
  }
  
  public void execute(CommandSender sender, String[] args) {
    ProxiedPlayer p = (ProxiedPlayer)sender;
    if (!(sender instanceof ProxiedPlayer))
      p.sendMessage("Apenas jogadores podem fazer isto."); 
    if (p.hasPermission("kombo.cmd.evento")) {
      if (args.length == 0) {
        sender.sendMessage("§cUso incorreto. Use /alerta <mensagem>.");
        return;
      } 
      String msg = "§b§lAVISO §f" + String.join(" ", (CharSequence[])args);
      for (ProxiedPlayer player : ProxyServer.getInstance().getPlayers())
        player.sendMessage(TextComponent.fromLegacyText(msg)); 
    } else {
      p.sendMessage("§cVocê não tem autorização para isso.");
    } 
  }
}
