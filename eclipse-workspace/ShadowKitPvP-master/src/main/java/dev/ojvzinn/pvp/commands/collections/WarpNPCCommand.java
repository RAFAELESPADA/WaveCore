package dev.ojvzinn.pvp.commands.collections;

import dev.ojvzinn.pvp.commands.SubCommand;
import dev.ojvzinn.pvp.game.enums.WarpEnum;
import dev.ojvzinn.pvp.lobby.ArenaNPC;
import dev.ojvzinn.pvp.lobby.FPSNPC;
import dev.ojvzinn.pvp.lobby.trait.DUELSNPC;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class WarpNPCCommand extends SubCommand {

    public WarpNPCCommand() {
        super("warpnpc", "warpnpc [id] [warp]", "Setar o npc de teleporte para warp", true);
    }

    @Override
    public void perform(Player player, String[] args) {
        if (args.length == 0) {
            player.sendMessage(" \n§eAjuda\n \n§6/kp warpnpc adicionar [id] [warp] §f- §7Adicionar NPC.\n§6/kp warpnpc remover [id] §f- §7Remover NPC.\n ");
            return;
        }

        String action = args[0];
        if (action.equalsIgnoreCase("adicionar")) {
            if (args.length <= 2) {
                player.sendMessage("§cUtilize /kp npcentregas adicionar [id] [warp]");
                return;
            }

            String id = args[1];
            WarpEnum warp = WarpEnum.findByName(args[2]);
            if (warp == WarpEnum.NENHUM) {
                player.sendMessage("Essa warp não foi registrada em nosso banco de dados!");
                return;
            }

            switch (warp) {
                case FPS: {
                    if (FPSNPC.getById(id) != null) {
                        player.sendMessage("§cJá existe um NPC utilizando \"" + id + "\" como ID.");
                        return;
                    }

                    Location location = player.getLocation().getBlock().getLocation().add(0.5, 0, 0.5);
                    location.setYaw(player.getLocation().getYaw());
                    location.setPitch(player.getLocation().getPitch());
                    FPSNPC.add(id, location);
                    player.sendMessage("§aNPC adicionado com sucesso.");
                    break;
                }
                case ONE_VS_ONE: {
                    if (DUELSNPC.getById(id) != null) {
                        player.sendMessage("§cJá existe um NPC utilizando \"" + id + "\" como ID.");
                        return;
                    }

                    Location location = player.getLocation().getBlock().getLocation().add(0.5, 0, 0.5);
                    location.setYaw(player.getLocation().getYaw());
                    location.setPitch(player.getLocation().getPitch());
                    DUELSNPC.add(id, location);
                    player.sendMessage("§aNPC adicionado com sucesso.");
                    break;
                }
                case ARENA: {
                    if (ArenaNPC.getById(id) != null) {
                        player.sendMessage("§cJá existe um NPC utilizando \"" + id + "\" como ID.");
                        return;
                    }

                    Location location = player.getLocation().getBlock().getLocation().add(0.5, 0, 0.5);
                    location.setYaw(player.getLocation().getYaw());
                    location.setPitch(player.getLocation().getPitch());
                    ArenaNPC.add(id, location);
                    player.sendMessage("§aNPC adicionado com sucesso.");
                    break;
                }
            }
        } else if (action.equalsIgnoreCase("remover")) {
            if (args.length <= 2) {
                player.sendMessage("§cUtilize /kp warpnpc remover [id] [warp]");
                return;
            }

            String id = args[1];
            WarpEnum warp = WarpEnum.findByName(args[2]);
            if (warp == WarpEnum.NENHUM) {
                player.sendMessage("Essa warp não foi registrada em nosso banco de dados!");
                return;
            }

            switch (warp) {
                case FPS: {
                    FPSNPC npc = FPSNPC.getById(id);
                    if (npc == null) {
                        player.sendMessage("§cNão existe um NPC utilizando \"" + id + "\" como ID.");
                        return;
                    }

                    FPSNPC.remove(npc);
                    player.sendMessage("§cNPC removido com sucesso.");
                    break;
                }
                case ONE_VS_ONE: {
                    DUELSNPC npc2 = DUELSNPC.getById(id);
                    if (npc2 == null) {
                        player.sendMessage("§cNão existe um NPC utilizando \"" + id + "\" como ID.");
                        return;
                    }

                    DUELSNPC.remove(npc2);
                    player.sendMessage("§cNPC removido com sucesso.");
                    break;
                }
                case ARENA: {
                    ArenaNPC npc = ArenaNPC.getById(id);
                    if (npc == null) {
                        player.sendMessage("§cNão existe um NPC utilizando \"" + id + "\" como ID.");
                        return;
                    }

                    ArenaNPC.remove(npc);
                    player.sendMessage("§cNPC removido com sucesso.");
                    break;
                }
            }
        } else {
            player.sendMessage(" \n§eAjuda\n \n§6/kp npcentregas adicionar [id] [warp] §f- §7Adicionar NPC.\n§6/kp npcentregas remover [id] [warp] §f- §7Remover NPC.\n ");
        }
    }
}
