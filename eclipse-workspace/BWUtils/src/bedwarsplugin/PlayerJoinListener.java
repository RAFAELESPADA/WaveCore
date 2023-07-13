package bedwarsplugin;



import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scoreboard.DisplaySlot;


public class PlayerJoinListener implements Listener {
	private final Main plugin;

	public PlayerJoinListener(Main plugin) {
		this.plugin = plugin;
	}	
	

	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player player = e.getPlayer();
					
				
		player.setFlying(false);
		player.setGameMode(GameMode.SURVIVAL);
		player.setLevel(0);
		player.setFireTicks(0);
		player.getInventory().clear();
		player.getInventory().setArmorContents(null);
		Location l = new Location(player.getWorld(), 0.112, 70.00000, 0.819);
		l.setPitch((float)0.2);
		l.setYaw((float)-9.8);
		e.setJoinMessage(null);
boolean enable = player.getScoreboard().getObjective(DisplaySlot.SIDEBAR) == null;
		
		if (enable) {
			plugin.getScoreboardBuilder().build(player);
		}
		player.teleport(l);
	}
	@EventHandler
	public void onPlayerJoin(PlayerMoveEvent event) {
		Player p = (Player) event.getPlayer();
		boolean enable = event.getPlayer().getScoreboard().getObjective(DisplaySlot.SIDEBAR) == null;
		if (enable) {
			Main.plugin.getScoreboardBuilder().build( event.getPlayer());
		}
	}
	@EventHandler
	public void onJoin(PlayerQuitEvent e) {
		e.setQuitMessage(null);
	}
}
