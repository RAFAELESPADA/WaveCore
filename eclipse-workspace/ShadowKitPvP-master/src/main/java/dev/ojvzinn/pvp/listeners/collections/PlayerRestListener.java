package dev.ojvzinn.pvp.listeners.collections;

import dev.ojvzinn.pvp.commands.collections.BuildCommand;
import dev.ojvzinn.pvp.game.KitPvP;
import dev.ojvzinn.pvp.listeners.ListenersAbstract;
import dev.slickcollections.kiwizin.player.Profile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class PlayerRestListener extends ListenersAbstract {

  @EventHandler
  public void onPlayerPickup(PlayerPickupItemEvent event) {
    Profile profile = Profile.getProfile(event.getPlayer().getName());
    if (profile != null) {
      if (!KitPvP.isPlayingKitPvP(profile.getPlayer())) {
        event.setCancelled(true);
      }
    }
  }

  @EventHandler
  public void onPlayerDropItem(PlayerDropItemEvent evt) {
    Profile profile = Profile.getProfile(evt.getPlayer().getName());
    if (profile != null) {
      if (!KitPvP.isPlayingKitPvP(profile.getPlayer())) {
        evt.setCancelled(true);
      }
    }
  }
  
  @EventHandler
  public void onBlockBreak(BlockBreakEvent evt) {
    evt.setCancelled(!BuildCommand.hasBuilder(evt.getPlayer()));
  }
  
  @EventHandler
  public void onBlockPlace(BlockPlaceEvent evt) {
    evt.setCancelled(!BuildCommand.hasBuilder(evt.getPlayer()));
  }
}
