package dev.ojvzinn.pvp.listeners.collections;
import dev.ojvzinn.pvp.commands.collections.BuildCommand;
import dev.ojvzinn.pvp.game.KitPvP;
import dev.ojvzinn.pvp.listeners.ListenersAbstract;
import dev.slickcollections.kiwizin.player.Profile;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClickListener extends ListenersAbstract {
  
  @EventHandler(priority = EventPriority.LOWEST)
  public void onInventoryClick(InventoryClickEvent evt) {
    if (evt.getWhoClicked() instanceof Player) {
      Player player = (Player) evt.getWhoClicked();
      Profile profile = Profile.getProfile(player.getName());
      
      if (profile != null) {
        if (!KitPvP.isPlayingKitPvP(player)) {
          evt.setCancelled(!BuildCommand.hasBuilder(player));
        }
      }
    }
  }
}
