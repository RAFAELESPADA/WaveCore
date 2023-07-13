package bedwarsplugin;



import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import net.helix.core.bukkit.HelixBukkit;
import net.helix.core.bukkit.account.HelixPlayer;

public class FPSDEATH implements Listener {
	
	@EventHandler
	public void onPlayerDieInArena(HelixPlayerDeathEvent event) {
		if (event.getReason() != HelixPlayerDeathEvent.Reason.FPS) {
			return;
		}
		Player player = event.getPlayer();
		List<ItemStack> drops = new ArrayList<>(event.getDrops());
		Location deathLocation = event.getDeathLocation();
		deathLocation.getWorld().playEffect(deathLocation, Effect.EXPLOSION_LARGE, 4);
		player.spigot().respawn();
		event.getDrops().clear();
		drops.clear();
		new BukkitRunnable() {
			@Override
			public void run() {
				Location l = new Location(player.getWorld(), 0.112, 70.00000, 0.819);
				l.setPitch((float)0.2);
				l.setYaw((float)-9.8);
				player.teleport(l);
			}
		}.runTaskLater(Main.plugin, 20);
		
		if (event.hasKiller()) {
			Player killer = event.getKiller();
			Random random = new Random();
			killer.getInventory().setHelmet(new ItemStack(Material.IRON_HELMET));
			killer.getInventory().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
			killer.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
			killer.getInventory().setBoots(new ItemStack(Material.IRON_BOOTS));
			HelixPlayer killerHelixPlayer = HelixBukkit.getInstance().getPlayerManager().getPlayer(killer.getName());
			killer.playSound(killer.getLocation(), Sound.LEVEL_UP, 10.0f, 10.0f);
			killer.sendMessage(("§3Você matou " + player.getName() + ". §8(" + (event.isValidKill() ? "Conta" : "Não conta")));
			if (event.isValidKill()) {
				int killerAddCoins = random.nextInt(80 + 1 - 25) + 25;
				killerHelixPlayer.getPvp().addKills(1);
				killerHelixPlayer.getPvp().addKillstreak(1);
				killerHelixPlayer.getPvp().addCoins(killerAddCoins);
				killer.sendMessage("§6§l[+] §6" + killerAddCoins + " coins");
			}
			HelixPlayer victimHelixPlayer = HelixBukkit.getInstance().getPlayerManager().getPlayer(player.getName());
			int victimWithdrawnCoins = random.nextInt(20 + 1 - 8) + 8;
			victimHelixPlayer.getPvp().addDeaths(1);
			if ((victimHelixPlayer.getPvp().getCoins() - victimWithdrawnCoins) >= 0) {
				victimHelixPlayer.getPvp().removeCoins(victimWithdrawnCoins);
				player.sendMessage("§c§l[-] §c" + victimWithdrawnCoins + " coins");
			}else {
				victimHelixPlayer.getPvp().setCoins(0);
			}
			
			player.sendMessage("§cVocê morreu para " + killer.getName());
					HelixBukkit.getInstance().getPlayerManager().getController().save(victimHelixPlayer);
					HelixBukkit.getInstance().getPlayerManager().getController().save(killerHelixPlayer);
		}else {
			player.sendMessage("§cVocê morreu.");
		}
		
		Location l = new Location(player.getWorld(), 0.112, 70.00000, 0.819);
		l.setPitch((float)0.2);
		l.setYaw((float)-9.8);
		player.teleport(l);
	}

}

