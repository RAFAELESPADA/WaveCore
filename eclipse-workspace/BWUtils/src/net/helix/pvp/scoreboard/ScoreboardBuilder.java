package net.helix.pvp.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import bedwarsplugin.Main;
import net.helix.core.bukkit.HelixBukkit;
import net.helix.core.bukkit.account.HelixPlayer;
import net.helix.core.bukkit.account.provider.PlayerPvP;
import net.helix.core.bukkit.format.HelixDecimalFormat;

public class ScoreboardBuilder {

	public ScoreboardBuilder(Main plugin) {
		new ScoreboardTask(this).runTaskTimer(plugin, 0, 3 * 20L);
	}
private static String text = "";
private static WaveAnimation waveAnimation;


public static void init() {
  waveAnimation = new WaveAnimation("FPS", "§6§l", "§f§l", "§e§l");
  text = "FPS";
  Bukkit.getScheduler().runTaskTimer(Main.plugin, new Runnable() {
        public void run() {
          ScoreboardBuilder.text = ScoreboardBuilder.waveAnimation.next();
          for (Player onlines : Bukkit.getOnlinePlayers()) {
            if (onlines == null)
              continue; 
            if (!onlines.isOnline())
              continue; 
            if (onlines.isDead())
              continue; 
            Scoreboard score = onlines.getScoreboard();
            if (score == null)
              continue; 
            Objective objective = score.getObjective(DisplaySlot.SIDEBAR);
            if (objective == null)
              continue; 
            objective.setDisplayName(ScoreboardBuilder.text);
          } 
        }
      },  20L, 1L);
}
	public void build(Player player) {
		{
		player.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
		
		Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
		Objective objective = scoreboard.registerNewObjective("pvp", "dummy");
		
		objective.setDisplayName("§b§lFPS");
		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
		
		String l0 = "§3";
		String l1 = " §fKills: §7";
		String l2 = " §fDeaths: §7";
		String l3 = " §fKillstreak: §a";
		String l4 = " §fCoins: §6";
		String ll = "§0";
		String l6 = "§esladepvp.com";
		
		scoreboard.registerNewTeam("coins").addEntry(l4);
		scoreboard.registerNewTeam("kills").addEntry(l1);
		scoreboard.registerNewTeam("deaths").addEntry(l2);
		scoreboard.registerNewTeam("ks").addEntry(l3);
	    objective.getScore(l6).setScore(0);
		objective.getScore(ll).setScore(1);
		objective.getScore(l4).setScore(2);
		objective.getScore(l3).setScore(3);
		objective.getScore(l2).setScore(4);
		objective.getScore(l1).setScore(5);
		objective.getScore(l0).setScore(6);
		player.setScoreboard(scoreboard);
		update(player);
		}
	}
	
	public void update(Player player) {
		if (player.getScoreboard().getObjective("pvp") == null) {
			return;
		}
		HelixPlayer helixPlayer = HelixBukkit.getInstance().getPlayerManager()
				.getPlayer(player.getName());
		PlayerPvP pvp = helixPlayer.getPvp();
		Scoreboard scoreboard = player.getScoreboard();
		scoreboard.getTeam("coins").setSuffix(HelixDecimalFormat.format(pvp.getCoins()));
		scoreboard.getTeam("kills").setSuffix(HelixDecimalFormat.format(pvp.getKills()));
		scoreboard.getTeam("deaths").setSuffix(HelixDecimalFormat.format(pvp.getDeaths()));
		scoreboard.getTeam("ks").setSuffix(HelixDecimalFormat.format(pvp.getKillstreak()));
	}
}
