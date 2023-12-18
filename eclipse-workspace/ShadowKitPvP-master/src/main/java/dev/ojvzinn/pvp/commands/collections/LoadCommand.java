package dev.ojvzinn.pvp.commands.collections;

import dev.ojvzinn.pvp.Main;
import dev.ojvzinn.pvp.commands.SubCommand;
import dev.ojvzinn.pvp.utils.VoidChunkGenerator;
import dev.ojvzinn.pvp.utils.WorldUtils;
import dev.slickcollections.kiwizin.plugin.logger.KLogger;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.command.CommandSender;

import java.io.File;
import java.util.logging.Level;

public class LoadCommand extends SubCommand {
  
  public static final KLogger LOGGER = ((KLogger) Main.getInstance().getLogger()).getModule("LOAD_WORLD");
  
  public LoadCommand() {
    super("load", "load [mundo]", "Carregue um mundo.", false);
  }
  
  @Override
  public void perform(CommandSender sender, String[] args) {
    if (args.length == 0) {
      sender.sendMessage("§cUtilize /kp " + this.getUsage());
      return;
    }
    
    if (Bukkit.getWorld(args[0]) != null) {
      sender.sendMessage("§cMundo já existente.");
      return;
    }
    
    File map = new File(args[0]);
    if (!map.exists() || !map.isDirectory()) {
      sender.sendMessage("§cPasta do Mundo não encontrada.");
      return;
    }
    
    try {
      sender.sendMessage("§aCarregando...");
      Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> {
        new WorldUtils(map.getName()).load();
        sender.sendMessage("§aMundo carregado com sucesso.");
      });
    } catch (Exception ex) {
      LOGGER.log(Level.WARNING, "Cannot load world \"" + args[0] + "\"", ex);
      sender.sendMessage("§cNão foi possível carregar o mundo.");
    }
  }
}
