package dev.ojvzinn.pvp.listeners.collections;

import dev.ojvzinn.pvp.Main;
import dev.ojvzinn.pvp.api.WarpConfig;
import dev.ojvzinn.pvp.commands.collections.BuildCommand;
import dev.ojvzinn.pvp.cosmetics.Cosmetic;
import dev.ojvzinn.pvp.cosmetics.CosmeticType;
import dev.ojvzinn.pvp.cosmetics.collections.Kit;
import dev.ojvzinn.pvp.game.KitPvP;
import dev.ojvzinn.pvp.game.enums.WarpEnum;
import dev.ojvzinn.pvp.game.object.FpsObject;
import dev.ojvzinn.pvp.game.object.config.ArenaConfig;
import dev.ojvzinn.pvp.game.object.config.FpsConfig;
import dev.ojvzinn.pvp.listeners.ListenersAbstract;
import dev.ojvzinn.pvp.lobby.ArenaNPC;
import dev.ojvzinn.pvp.lobby.DeliveryNPC;
import dev.ojvzinn.pvp.lobby.FPSNPC;
import dev.ojvzinn.pvp.utils.PlayerUtils;
import dev.slickcollections.kiwizin.player.Profile;
import dev.slickcollections.kiwizin.player.enums.BloodAndGore;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

import java.util.HashSet;
import java.util.Set;

public class EntityListener extends ListenersAbstract {

  private static Set<String> NO_DAMAGE = new HashSet<>();

  @EventHandler(priority = EventPriority.HIGHEST)
  public void onEntityDamageByEntity(EntityDamageByEntityEvent evt) {
    if (evt.isCancelled() || DeliveryNPC.listNPCs().stream().anyMatch(deliveryNPC -> evt.getEntity().equals(deliveryNPC.getNpc().getEntity())) || FPSNPC.listNPCs().stream().anyMatch(deliveryNPC -> evt.getEntity().equals(deliveryNPC.getNpc().getEntity())) || ArenaNPC.listNPCs().stream().anyMatch(deliveryNPC -> evt.getEntity().equals(deliveryNPC.getNpc().getEntity()))) {
      evt.setCancelled(true);
      return;
    }

    if (evt.getEntity() instanceof Player) {
      Player player = (Player) evt.getEntity();
      Profile profile = Profile.getProfile(player.getName());
      if (profile != null) {
        if (!KitPvP.isPlayingKitPvP(player)) {
          evt.setCancelled(true);
          return;
        }

        if (KitPvP.isPlayingKitPvP(player) && KitPvP.findWarpWherePlayer(player).getWarpType().equals(WarpEnum.FPS) && ((FpsObject) KitPvP.findWarpWherePlayer(player)).getConfig().getSpawnCubeID().contains(player.getLocation())) {
          evt.setCancelled(true);
          return;
        }

        Player damager = null;
        Profile profile2;
        if (evt.getDamager() instanceof Player) {
          damager = (Player) evt.getDamager();
          profile2 = Profile.getProfile(damager.getName());
          if (profile2 == null) {
            evt.setCancelled(true);
          } else {
            if (profile.getPreferencesContainer().getBloodAndGore() == BloodAndGore.ATIVADO) {
              player.playEffect(player.getLocation(), Effect.STEP_SOUND, Material.REDSTONE_BLOCK);
            }
            if (profile2.getPreferencesContainer().getBloodAndGore() == BloodAndGore.ATIVADO) {
              damager.playEffect(player.getLocation(), Effect.STEP_SOUND, Material.REDSTONE_BLOCK);
            }
          }

          PlayerUtils.addPvPCooldown(player.getName());
          PlayerUtils.addPvPCooldown(damager.getName());
          evt.setDamage(evt.getDamage() / 2);
        }

        if (!evt.isCancelled() && damager != null) {
          profile.setHit(damager.getName());
        }
      }
    }
  }

  @EventHandler(priority = EventPriority.LOWEST)
  public void onEntityDamage(EntityDamageEvent evt) {
    if (evt.isCancelled()) {
      return;
    }

    if (evt.getEntity() instanceof Player) {
      Player player = (Player) evt.getEntity();
      Profile profile = Profile.getProfile(player.getName());
      if (profile != null) {
        if (!KitPvP.isPlayingKitPvP(player)) {
          evt.setCancelled(true);
        } else {
          if (evt.getCause().equals(EntityDamageEvent.DamageCause.FALL) && NO_DAMAGE.contains(player.getName())) {
            evt.setCancelled(true);
            NO_DAMAGE.remove(player.getName());
          }
        }
      }
    }
  }

  @EventHandler(priority = EventPriority.MONITOR)
  public void onPlayerMove(PlayerMoveEvent event) {
    Player player = event.getPlayer();
    Profile profile = Profile.getProfile(player.getName());
    if (profile != null) {
      Block block = event.getTo().getBlock();
      if (block.getType().equals(Material.SPONGE) || block.getRelative(BlockFace.DOWN).getType().equals(Material.SPONGE)) {
        player.setVelocity(new Vector(0, 4, 0));
        player.playSound(player.getLocation(), Sound.FIREWORK_LAUNCH, 2.0F, 0.5F);
        NO_DAMAGE.add(player.getName());
      } else if (block.getType().equals(Material.SLIME_BLOCK) || block.getRelative(BlockFace.DOWN).getType().equals(Material.SLIME_BLOCK)) {
        String getAddLoc = PlayerUtils.getAddForFront(block);
        player.setVelocity(player.getEyeLocation().getDirection().normalize().add(new Vector(Integer.parseInt(getAddLoc.split(", ")[0]), Integer.parseInt(getAddLoc.split(", ")[1]), Integer.parseInt(getAddLoc.split(", ")[2]))));
        player.playSound(player.getLocation(), Sound.FIREWORK_BLAST, 2.0F, 1.0F);
        NO_DAMAGE.add(player.getName());
      }

      if (!player.getGameMode().equals(GameMode.CREATIVE) && !KitPvP.isPlayingKitPvP(player) && !WarpConfig.hasSetter(event.getPlayer().getName()) && !BuildCommand.hasBuilder(player)) {
        ArenaConfig arenaConfig = KitPvP.findWarp(WarpEnum.ARENA).getConfig(ArenaConfig.class);
        if (!arenaConfig.getSpawnCubeID().contains(player.getLocation())) {
          ((Kit) Cosmetic.findCosmeticSelected(CosmeticType.KIT, profile)).applyKit(player);
          KitPvP.findWarp(WarpEnum.ARENA).join(profile);
        }
      }
    }
  }

  @EventHandler(priority = EventPriority.HIGHEST)
  public void onCreatureSpawn(CreatureSpawnEvent evt) {
    evt.setCancelled(evt.getSpawnReason() != SpawnReason.CUSTOM);
  }

  @EventHandler
  public void onFoodLevelChange(FoodLevelChangeEvent evt) {
    evt.setCancelled(true);
  }

  @EventHandler(priority = EventPriority.MONITOR)
  public void onFoodLevelChangeMonitor(FoodLevelChangeEvent evt) {
    if (!evt.isCancelled() && evt.getEntity() instanceof Player) {
      ((Player) evt.getEntity()).setSaturation(5.0f);
    }
  }
}