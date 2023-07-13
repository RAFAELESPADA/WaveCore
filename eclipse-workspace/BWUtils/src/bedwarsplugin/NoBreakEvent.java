package bedwarsplugin;

/*    */ 


import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public final class NoBreakEvent
  implements Listener, CommandExecutor
{
  public static ArrayList<Player> embuild = new ArrayList();
  
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
  {
    Player p = (Player)sender;
    if (cmd.getName().equalsIgnoreCase("build")) {
      if (p.hasPermission("tag.admin"))
      {
        if (args.length == 0)
        {
          if (!embuild.contains(p))
          {
            embuild.add(p);
            p.sendMessage(String.valueOf("§c§lBUILD") + "§aAgora você pode editar a fps");
          }
          else
          {
            embuild.remove(p);
            p.sendMessage(String.valueOf("§c§lBUILD") + "§cVocê agora não pode editar a fps");
          }
        }
        else
        {
          Player t = Bukkit.getPlayer(args[0]);
          if (t == null)
          {
            p.sendMessage("§c§lBUILD" + "§cThis player is offline");
            return true;
          }
          if (!embuild.contains(t))
          {
            embuild.add(t);
            p.sendMessage(String.valueOf("§c§lBUILD") + "§aNow: §7" + t.getName() + " §acan edit kitpvp arena");
          }
          else
          {
            embuild.remove(t);
            p.sendMessage(String.valueOf("§c§lBUILD") + "§aPlayer: §7" + t.getName() + " §ano longer can edit kitpvp arena");
          }
        }
      }
      else {
        p.sendMessage(String.valueOf("§c§lBUILD") + "§cVocê não tem acesso a esse comando!");
      }
    }
    return false;
  }
  
  @EventHandler
  public void aoconstruir(BlockPlaceEvent e)
  {
    Player p = e.getPlayer();
    if (!embuild.contains(p)) {
      e.setCancelled(true);
    }
  }
  
  @EventHandler
  public void aoconstruir(BlockBreakEvent e)
  {
    Player p = e.getPlayer();
    if (!embuild.contains(p)) {
      e.setCancelled(true);
    }
  }
}

