package dev.ojvzinn.pvp.commands.collections;

import dev.ojvzinn.pvp.api.WarpConfig;
import dev.ojvzinn.pvp.commands.SubCommand;
import dev.ojvzinn.pvp.game.enums.WarpEnum;
import org.bukkit.entity.Player;

public class WarpConfigCommand extends SubCommand {
    public WarpConfigCommand() {
        super("warpconfig", "warpconfig [warp]", "Configure as warps", true);
    }

    @Override
    public void perform(Player player, String[] args) {
        if (args.length == 0) {
            player.sendMessage("§cUtilize /" + this.getUsage());
            return;
        }

        String warp = args[0].toUpperCase();
        if (WarpEnum.findByName(warp).equals(WarpEnum.NENHUM)) {
            player.sendMessage("§cWarp não encontrada");
            return;
        }

        if (WarpConfig.hasSetter(player.getName())) {
            player.sendMessage("§cVocê não pode configurar uma warp enquanto já está configurando uma!");
            return;
        }

        WarpConfig.setPlayer(player.getName(), WarpEnum.findByName(warp));
        player.sendMessage("§aFoi dados os itens necessários para setar os pontos de proteção no spawn!");
    }
}
