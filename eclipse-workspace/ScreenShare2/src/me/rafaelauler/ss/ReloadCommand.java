package me.rafaelauler.ss;


import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

public class ReloadCommand extends Command {
  Main plugin;
  
  public ReloadCommand(Main plugin) {
    super("mmn", "movemenow.admin", new String[0]);
    this.plugin = plugin;
  }
  
  public void execute(CommandSender sender, String[] args) {
    if (args.length != 1)
      sender.sendMessage((BaseComponent)new TextComponent("Please use /mmn reload.")); 
    switch (args[0]) {
      case "reload":
        this.plugin.loadConfig();
        break;
    } 
  }
}
