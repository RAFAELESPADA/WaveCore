package me.rafaelauler.ss;


import java.util.ArrayList;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Plugin;

public class StaffChat extends Command {
  public static ArrayList<String> sc = new ArrayList<>();
  
  public StaffChat(Plugin plugin) {
    super("sc", null, new String[] { "staffchat" });
  }
  
  public void execute(CommandSender sender, String[] args) {
    ProxiedPlayer p = (ProxiedPlayer)sender;
    if (!sender.hasPermission("utils.staffchat.use")) {
      sender.sendMessage((BaseComponent)new TextComponent("§cVocê não é staff."));
      return;
    } 
    if (args.length > 0) {
      String message = String.join(" ", (CharSequence[])args);
      Main.getInstance().scMandarMsg(p, message);
    } else if (args.length == 0 && sc.contains(p.getName().toLowerCase())) {
      sender.sendMessage((BaseComponent)new TextComponent("§cStaffChat desabilitado.."));
      sc.remove(p.getName().toLowerCase());
    } else if (args.length == 0 && !sc.contains(p.getName().toLowerCase())) {
      sender.sendMessage((BaseComponent)new TextComponent("§aStaffChat habilitado.."));
      sc.add(p.getName().toLowerCase());
    } 
  }
}
