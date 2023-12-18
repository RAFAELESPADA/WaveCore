package dev.ojvzinn.pvp.listeners.collections;

import dev.ojvzinn.pvp.Main;
import dev.ojvzinn.pvp.cosmetics.Cosmetic;
import dev.ojvzinn.pvp.cosmetics.CosmeticType;
import dev.ojvzinn.pvp.cosmetics.collections.Kit;
import dev.ojvzinn.pvp.cosmetics.collections.kits.FPS;
import dev.ojvzinn.pvp.cosmetics.collections.kits.Fenix;
import dev.ojvzinn.pvp.game.KitPvP;
import dev.ojvzinn.pvp.game.object.FpsObject;
import dev.ojvzinn.pvp.listeners.ListenersAbstract;
import dev.ojvzinn.pvp.utils.PlayerUtils;
import dev.slickcollections.kiwizin.player.Profile;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PlayerDeathListener extends ListenersAbstract {

  @EventHandler
  public void onPlayerDeath(PlayerDeathEvent evt) {
    Player player = evt.getEntity();
    evt.setDeathMessage(null);

    Profile profile = Profile.getProfile(player.getName());
    if (profile != null) {
      Location location = evt.getEntity().getLocation();
      PlayerUtils.refreshPlayer(player);
      List<Profile> hitters = profile.getLastHitters();
      Profile killer = hitters.size() > 0 ? hitters.get(0) : null;
      if (KitPvP.findWarpWherePlayer(player) != null) {
        if (Kit.findByKitClass(Fenix.class).isSelected(profile) && Kit.findByKitClass(Fenix.class, true).canRevive(player)) {
          evt.getDrops().clear();
          Kit.findByKitClass(Fenix.class, true).revivePlayer(player);
          Bukkit.getScheduler().runTaskLater(Main.getInstance(), ()-> Cosmetic.findCosmeticSelected(CosmeticType.KIT, profile, Kit.class).applyKit(player), 3L);
          player.updateInventory();
          return;
        }

        KitPvP.findWarpWherePlayer(player).kill(profile, killer);
      }

      List<ItemStack> itemStackList = evt.getDrops();
      List<Item> itemDrop = new ArrayList<>();
      for (ItemStack itemStack : itemStackList.stream().filter(itemStack -> itemStack.getType().equals(Material.MUSHROOM_SOUP) || itemStack.getType().equals(Material.BROWN_MUSHROOM) || itemStack.getType().equals(Material.RED_MUSHROOM) || itemStack.getType().equals(Material.BOWL)).collect(Collectors.toList())) {
        Item item = location.getWorld().dropItem(killer != null ? killer.getPlayer().getLocation() : player.getLocation(), itemStack);
        itemDrop.add(item);
      }

      evt.getDrops().clear();

      new BukkitRunnable() {
        @Override
        public void run() {
          player.setFireTicks(0);
          itemDrop.forEach(Entity::remove);
        }
      }.runTaskLaterAsynchronously(Main.getInstance(), 20L);
    }
  }
}
