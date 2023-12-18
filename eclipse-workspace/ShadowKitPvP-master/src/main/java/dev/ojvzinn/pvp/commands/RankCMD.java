package dev.ojvzinn.pvp.commands;



import dev.ojvzinn.pvp.Main;
import dev.ojvzinn.pvp.cosmetics.Cosmetic;
import dev.ojvzinn.pvp.cosmetics.CosmeticType;
import dev.ojvzinn.pvp.cosmetics.collections.Kit;
import dev.ojvzinn.pvp.game.KitPvP;
import dev.ojvzinn.pvp.game.enums.WarpEnum;
import dev.ojvzinn.pvp.game.object.config.FpsConfig;
import dev.ojvzinn.pvp.league.LeagueManager;
import dev.ojvzinn.pvp.league.object.League;
import dev.ojvzinn.pvp.utils.PlayerUtils;

import java.util.ArrayList;
import java.util.List;
import dev.slickcollections.kiwizin.Core;
import dev.slickcollections.kiwizin.nms.NMS;
import dev.slickcollections.kiwizin.player.Profile;
import dev.slickcollections.kiwizin.player.hotbar.Hotbar;
import dev.slickcollections.kiwizin.utils.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RankCMD extends Commands {

    public RankCMD() {
        super("rank" , "ranks");
    }

    @Override
    public void perform(CommandSender sender, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return;
        }

        Player player = (Player) sender;
        Profile profile = Profile.getProfile(player.getName());
        Player helixPlayer = profile.getPlayer();


        if (profile == null) {
            player.kickPlayer("RELOGUE");
            return;
        }

        player.sendMessage("§2SISTEMAS - LIGAS");
        player.sendMessage("§3Deus §f- §a900 kills");
        player.sendMessage("§eLendário §f- §a750 kills");
        player.sendMessage("§cRuby III a RubyI §f- §a600 a 700 kills");
        player.sendMessage("§1Safira III a SafiraI §f- §a450 a 600 kills");
        player.sendMessage("§bDiamond III a DiamondI §f- §a300 a 450 kills");
        player.sendMessage("§6Gold III a GoldI §f- §a180 a 300 kills");
        player.sendMessage("§fIron III a IronI §f- §a90 a 180 kills");
        player.sendMessage("§7Novato III a NovatoI §f- §a0 a 90 kills");
        player.sendMessage("§fSua Liga Atual: " + LeagueManager.findLeague(profile).getName());


    }}



