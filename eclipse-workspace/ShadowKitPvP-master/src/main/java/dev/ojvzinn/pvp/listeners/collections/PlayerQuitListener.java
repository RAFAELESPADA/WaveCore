package dev.ojvzinn.pvp.listeners.collections;

import dev.ojvzinn.pvp.api.WarpConfig;
import dev.ojvzinn.pvp.commands.collections.BuildCommand;
import dev.ojvzinn.pvp.game.KitPvP;
import dev.ojvzinn.pvp.listeners.ListenersAbstract;
import dev.ojvzinn.pvp.utils.tagger.TagUtils;
import dev.slickcollections.kiwizin.player.Profile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener extends ListenersAbstract {
  
  @EventHandler
  public void onPlayerQuit(PlayerQuitEvent evt) {
    if (KitPvP.isPlayingKitPvP(evt.getPlayer())) {
      KitPvP.findWarpWherePlayer(evt.getPlayer()).leave(Profile.getProfile(evt.getPlayer().getName()));
    }
    TagUtils.reset(evt.getPlayer().getName());
    BuildCommand.remove(evt.getPlayer());
    WarpConfig.clear(evt.getPlayer().getName());
    evt.setQuitMessage(null);
  }
}
