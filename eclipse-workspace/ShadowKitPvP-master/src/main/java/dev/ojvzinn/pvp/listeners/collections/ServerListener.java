package dev.ojvzinn.pvp.listeners.collections;

import dev.ojvzinn.pvp.listeners.ListenersAbstract;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

public class ServerListener extends ListenersAbstract {
  
  @EventHandler
  public void onBlockIgnite(BlockIgniteEvent evt) {
    evt.setCancelled(true);
  }
  
  @EventHandler
  public void onBlockBurn(BlockBurnEvent evt) {
    evt.setCancelled(true);
  }
  
  @EventHandler
  public void onLeavesDecay(LeavesDecayEvent evt) {
    evt.setCancelled(true);
  }
  
  @EventHandler
  public void onEntityExplode(EntityExplodeEvent evt) {
    evt.setCancelled(true);
  }
  
  @EventHandler
  public void onWeatherChange(WeatherChangeEvent evt) {
    evt.setCancelled(evt.toWeatherState());
  }

  @EventHandler
  public void onSignEditing(SignChangeEvent event) {
    if (event.getLine(0).equalsIgnoreCase("<KITPVP>")) {
      String type = event.getLine(1);

      if (type.equalsIgnoreCase("<SOUPS>")) {
        event.setLine(0, "§b§lKITPVP");
        event.setLine(1, "§eSoups");
        event.setLine(2, "§6>------<");
      } else if (type.equalsIgnoreCase("<RECRAFT>")) {
        event.setLine(0, "§b§lKITPVP");
        event.setLine(1, "§eRecraft");
        event.setLine(2, "§6>------<");
      }
    }
  }
}