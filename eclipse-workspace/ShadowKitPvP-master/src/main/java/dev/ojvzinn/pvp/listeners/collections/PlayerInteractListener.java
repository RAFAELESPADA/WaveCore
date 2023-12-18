package dev.ojvzinn.pvp.listeners.collections;

import dev.ojvzinn.pvp.Main;
import dev.ojvzinn.pvp.api.WarpConfig;
import dev.ojvzinn.pvp.commands.collections.BuildCommand;
import dev.ojvzinn.pvp.cosmetics.collections.Kit;
import dev.ojvzinn.pvp.cosmetics.collections.kits.Quickdroper;
import dev.ojvzinn.pvp.game.KitPvP;
import dev.ojvzinn.pvp.game.enums.WarpEnum;
import dev.ojvzinn.pvp.game.object.FpsObject;
import dev.ojvzinn.pvp.listeners.ListenersAbstract;
import dev.ojvzinn.pvp.menu.MenuRefill;
import dev.slickcollections.kiwizin.Core;
import dev.slickcollections.kiwizin.libraries.npclib.api.event.NPCRightClickEvent;
import dev.slickcollections.kiwizin.libraries.npclib.api.npc.NPC;
import dev.slickcollections.kiwizin.menus.MenuDeliveries;
import dev.slickcollections.kiwizin.nms.NMS;
import dev.slickcollections.kiwizin.player.Profile;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.DecimalFormat;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PlayerInteractListener extends ListenersAbstract {

  private static final DecimalFormat format = new DecimalFormat("#.#");
  private static final Map<Player, Player> compassTarget = new HashMap<>();
  @EventHandler
  public void onNPCRightClick(NPCRightClickEvent evt) {
    Player player = evt.getPlayer();
    Profile profile = Profile.getProfile(player.getName());

    if (profile != null) {
      NPC npc = evt.getNPC();
      if (npc.data().has("delivery-npc")) {
        new MenuDeliveries(profile);
      } else {
        if (npc.data().has("fps-npc")) {
          KitPvP.clearRegisters(player);
          FpsObject fps = (FpsObject) KitPvP.findWarp(WarpEnum.FPS);
          fps.addPlayerPlaying(player);
          fps.join(profile);
        } else if (npc.data().has("arena-npc")) {
          KitPvP.clearRegisters(player);
          KitPvP.findWarp(WarpEnum.FPS).leave(profile);
          player.teleport(Core.getLobby());
        }
      }
    }
  }

  @EventHandler(priority = EventPriority.LOWEST)
  public void onPlayerInteract(PlayerInteractEvent evt) {
    Player player = evt.getPlayer();
    Profile profile = Profile.getProfile(player.getName());

    if (profile != null) {
      WarpConfig.setupListeners(evt);
      if (!KitPvP.isPlayingKitPvP(player)) {
        evt.setCancelled(!BuildCommand.hasBuilder(player));
      } else {
        ItemStack itemStack = player.getItemInHand();
        if (itemStack.getType().equals(Material.MUSHROOM_SOUP)) {
          double vidaNecessery = player.getMaxHealth() - player.getHealth();
          if (vidaNecessery == 0) {
            return;
          }


          int slot = player.getInventory().getHeldItemSlot();
          player.getInventory().setItem(slot, new ItemStack(Material.BOWL));
          player.updateInventory();
          Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
            if (Kit.findByKitClass(Quickdroper.class).isSelected(profile)) {
              player.getInventory().setItem(slot, new ItemStack(Material.AIR));
            } else {
              player.getInventory().setItem(slot, new ItemStack(Material.BOWL));
            }

            player.updateInventory();
          }, 1L);

          if (vidaNecessery >= 7) {
            player.setHealth(player.getHealth() + 7);
            NMS.sendActionBar(player, "§a+3.5 §4❤");
          } else {
            player.setHealth(player.getHealth() + vidaNecessery);
            NMS.sendActionBar(player, "§a+" + format.format(vidaNecessery / 2) + " §4❤");
          }
        }

        if (itemStack.getType().equals(Material.COMPASS)) {
          ItemMeta meta = itemStack.getItemMeta();
          if (meta != null && meta.hasDisplayName() && meta.getDisplayName().equals("§aBússola")) {
            List<Player> onlines = player.getWorld().getPlayers().stream().filter(online -> player != online && player.getLocation().distance(online.getLocation()) <= 64).sorted(Comparator.comparingDouble(o -> player.getLocation().distance(o.getLocation()))).collect(Collectors.toList());
            if (onlines.isEmpty()) {
              player.sendMessage("§cNão há nenhum jogador perto de você!");
            } else {
              Player localized = onlines.get(0);
              player.sendMessage("§aRastreando o jogador " + localized.getName() + ", ele está a " + format.format(player.getLocation().distance(localized.getLocation())) + " blocos de você!");
              compassTarget.remove(player);
              compassTarget.put(player, localized);
              new BukkitRunnable() {
                @Override
                public void run() {
                  if (!localized.isOnline() || !player.isOnline() || !compassTarget.containsKey(player)) {
                    this.cancel();
                    return;
                  }

                  player.setCompassTarget(localized.getLocation());
                }
              }.runTaskTimerAsynchronously(Main.getInstance(), 0L, 10L);
            }
          }
        }

        if (evt.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
          Block block = evt.getClickedBlock();

          if (block.getType() == Material.WALL_SIGN || block.getType() == Material.SIGN_POST) {
            Sign sign = (Sign) block.getState();

            if (sign.getLine(1).equals("§eRecraft")) {
              new MenuRefill(Profile.getProfile(player.getName()), "recraft");
            } else if (sign.getLine(1).equals("§eSoups")) {
              new MenuRefill(Profile.getProfile(player.getName()), "soups");
            }
          }
        }
      }
    }
  }

  @EventHandler
  public void onPlayerMove(PlayerMoveEvent evt) {
    if (evt.getTo().getBlockY() != evt.getFrom().getBlockY() && evt.getTo().getBlockY() < 0) {
      Player player = evt.getPlayer();
      Profile profile = Profile.getProfile(player.getName());

      if (profile != null) {
        if (KitPvP.isPlayingKitPvP(player)) {
          KitPvP.findWarpWherePlayer(player).kill(profile, null);
          player.damage(20.0D);
          return;
        }

        player.teleport(Core.getLobby());
      }
    }
  }

  @EventHandler
  public void onPlayerDrop(PlayerDropItemEvent event) {
    Player player = event.getPlayer();
    Profile profile = Profile.getProfile(player.getName());
    if (profile != null) {
      if (KitPvP.isPlayingKitPvP(player)) {
        Item item = event.getItemDrop();
        if (item.getItemStack().getType().name().contains("SWORD") || item.getItemStack().getType().name().contains("AXE") || item.getItemStack().getType().equals(Material.COMPASS)) {
          event.setCancelled(true);
        }
      }
    }
  }
}