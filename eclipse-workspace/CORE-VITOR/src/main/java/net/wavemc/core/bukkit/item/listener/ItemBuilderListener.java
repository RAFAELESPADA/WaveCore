package net.wavemc.core.bukkit.item.listener;

import net.wavemc.core.bukkit.item.ItemBuilder;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

public class ItemBuilderListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (ItemBuilder.has(event.getCurrentItem(), "cancel-click")) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onDropItem(PlayerDropItemEvent event) {
        if (event.getItemDrop().getItemStack() != null && ItemBuilder.has(event.getItemDrop().getItemStack(), "cancel-drop")) {
            event.setCancelled(true);
        }
    }
}
