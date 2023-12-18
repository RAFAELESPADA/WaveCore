package dev.ojvzinn.pvp.commands;

import dev.ojvzinn.pvp.Main;
import dev.ojvzinn.pvp.cosmetics.Cosmetic;
import dev.ojvzinn.pvp.cosmetics.CosmeticType;
import dev.ojvzinn.pvp.cosmetics.collections.Kit;
import dev.ojvzinn.pvp.game.KitPvP;
import dev.ojvzinn.pvp.game.enums.WarpEnum;
import dev.ojvzinn.pvp.game.object.config.FpsConfig;
import dev.ojvzinn.pvp.utils.PlayerUtils;
import dev.slickcollections.kiwizin.Core;
import dev.slickcollections.kiwizin.nms.NMS;
import dev.slickcollections.kiwizin.player.Profile;
import dev.slickcollections.kiwizin.player.hotbar.Hotbar;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpawnCommand extends Commands {

    public SpawnCommand() {
        super("spawn");
    }

    @Override
    public void perform(CommandSender sender, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return;
        }

        Player player = (Player) sender;
        Profile profile = Profile.getProfile(player.getName());
        if (PlayerUtils.isCooldown(player.getName())) {
            player.sendMessage("§cAguarde " + PlayerUtils.getTimeCooldown(player.getName()) + " segundos para utilizar esse comando!");
            return;
        }

        if (KitPvP.isPlayingKitPvP(player)) {
            WarpEnum warp = KitPvP.findWarpWherePlayer(player).getWarpType();
            switch (warp) {
                case FPS: {
                    Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
                        player.teleport(((FpsConfig) KitPvP.findWarp(warp).getConfig()).getSpawnLocation());
                        Cosmetic.findById("1", CosmeticType.KIT, Kit.class).applyKit(player);
                        player.setFireTicks(0);
                    }, 3L);

                    player.teleport(((FpsConfig) KitPvP.findWarp(warp).getConfig()).getSpawnLocation());
                    player.setFireTicks(0);
                    break;
                }

                case ARENA: {
                    Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
                        if (profile.isOnline()) {
                            PlayerUtils.refreshPlayer(profile.getPlayer());
                            player.teleport(Core.getLobby());
                            player.setFireTicks(0);
                        }
                    }, 3L);

                    player.teleport(Core.getLobby());
                    KitPvP.findWarp(warp).leave(profile);
                    player.setFireTicks(0);
                    break;
                }
            }
        } else {
            player.sendMessage("§aVocê já se encontra no spawn!");
        }
    }

}
