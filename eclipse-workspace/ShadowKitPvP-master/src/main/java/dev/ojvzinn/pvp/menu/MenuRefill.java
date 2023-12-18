package dev.ojvzinn.pvp.menu;

import dev.ojvzinn.pvp.Main;
import dev.slickcollections.kiwizin.libraries.menu.PlayerMenu;
import dev.slickcollections.kiwizin.player.Profile;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

public class MenuRefill extends PlayerMenu {

    public MenuRefill(Profile profile, String type) {
        super(profile.getPlayer(), "§aMenu de Refill", type.equalsIgnoreCase("soups") ? 6 : 1);

        for (int i = 0; i < getSlots().size(); i++) {
            this.setItem(i, type.equalsIgnoreCase("soups") ? new ItemStack(Material.MUSHROOM_SOUP) : new ItemStack(Material.AIR));
        }

        if (type.equalsIgnoreCase("recraft")) {
            this.setItem(3, new ItemStack(Material.BOWL, 64));
            this.setItem(4, new ItemStack(Material.RED_MUSHROOM, 64));
            this.setItem(5, new ItemStack(Material.BROWN_MUSHROOM, 64));
        }

        this.register(Main.getInstance());
        this.open();
    }

    public void cancel() {
        HandlerList.unregisterAll(this);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent evt) {
        if (evt.getPlayer().equals(this.player)) {
            this.cancel();
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent evt) {
        if (evt.getPlayer().equals(this.player) && evt.getInventory().equals(this.getInventory())) {
            this.cancel();
        }
    }
}
