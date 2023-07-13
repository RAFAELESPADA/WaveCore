package bedwarsplugin;


import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class Spawn implements CommandExecutor {
	


	public static ArrayList<String> indo = new ArrayList<String>();
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			return true;
		}
		Player p = (Player) sender;
		p.getInventory().clear();
		p.getInventory().setArmorContents(null);
		Location l = new Location(p.getWorld(), 0.112, 70.00000, 0.819);
		l.setPitch((float)0.2);
		l.setYaw((float)-9.8);
		p.teleport(l);
		return true;
	}
}
