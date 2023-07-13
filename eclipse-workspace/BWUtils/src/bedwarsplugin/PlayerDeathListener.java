package bedwarsplugin;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import net.helix.core.bukkit.HelixBukkit;
import net.helix.core.bukkit.account.HelixPlayer;


public class PlayerDeathListener implements Listener {
	
	private final static HashMap<String, List<String>> lastKillsMap = new HashMap<>();

	@EventHandler
	public void onDeath(PlayerDeathEvent event) {
		Player player = event.getEntity();
		Player killer = event.getEntity().getKiller();
		Location deathLocation = player.getLocation().clone();
		boolean validKill = false;

		player.getActivePotionEffects().forEach(it -> player.removePotionEffect(it.getType()));
		
		player.spigot().respawn();
		event.setDeathMessage(null);
		event.setDroppedExp(0);
		event.getDrops().clear();
		if (killer != null) {
			List<String> lastKills = lastKillsMap.containsKey(killer.getName()) ?
					lastKillsMap.get(killer.getName()) : new ArrayList<>();
			
			
			int repeatedKills = (int) lastKills.stream().filter(
					username -> username.equalsIgnoreCase(player.getName())
			).count() + 1;
			
			int allowRepeatedKills = 2;
			if (repeatedKills <= allowRepeatedKills) {
				validKill = true;
				lastKills.add(player.getName());
				
				if (lastKills.size() >= 3) {
					lastKills.clear();
				}
			}
			lastKillsMap.put(killer.getName(), lastKills);
			   HelixPlayer helixPlayer = HelixBukkit.getInstance().getPlayerManager().getPlayer(killer.getName());

			
		}
		

		HelixPlayerDeathEvent helixPlayerDeathEvent2 = new HelixPlayerDeathEvent(
				player, killer, deathLocation,
				new ArrayList<>(event.getDrops()),
				HelixPlayerDeathEvent.Reason.FPS,
				validKill
		);
			 Bukkit.getPluginManager().callEvent(helixPlayerDeathEvent2);	 
		 }
	
	

		            		
		 
					    
			 

	 
	 
				
	 
	
	@EventHandler
	public void onRespawn(PlayerRespawnEvent e) {
		Player p = e.getPlayer();
		p.setFireTicks(0);
		p.setNoDamageTicks(0);
		Location l = new Location(p.getWorld(), 0.112, 67.00000, 0.819);
		l.setPitch((float)179.5);
		l.setYaw((float)-6.6);
		p.teleport(l);
		Location spawnLocation = l;
		p.teleport(spawnLocation);
	}
}

