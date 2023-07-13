package bedwarsplugin;



import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;


public class GUnKick implements Listener, CommandExecutor {
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("Sem console");
			return true;
		}
		Player p = (Player) sender;
		if (args.length == 0) {
			p.sendMessage("§eUse: /gunkick <jogador>");
			return true;
		}	
			if (!p.hasPermission("prismamc.staff")) {
				p.sendMessage("Sem permissao");
				return true;
			}
			GKick.ban.clear();
			p.sendMessage("§e" + args[0] + (" foi permitido a entrar no server novamente"));
			return true;
			
}

	{
	
	}
}

