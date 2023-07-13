package bedwarsplugin;


import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;


public class GKick implements Listener, CommandExecutor {
	public static ArrayList<String> ban = new ArrayList();
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("Sem console");
			return true;
		}
		Player p = (Player) sender;
		
			if (args.length == 0) {
				p.sendMessage("§eUse: /gkick <jogador>");
				return true;
			}
			Player target = Bukkit.getServer().getPlayer(args[0]);
			if (target == null) {
				p.sendMessage("§eJogador offline ou invalido!");
				return true;
			}
			if (ban.contains(target.getName())) {
				p.sendMessage("§eEsse jogador já está impedido de entrar");
				return true;
			}
	
			if (!p.hasPermission("prismamc.staff")) {
				p.sendMessage("Sem permissao");
				return true;
			}
			
			if (target.hasPermission("prismamc.immune")) {
				p.sendMessage("§cEsse jogador tem um cargo superior ao seu e você não pode expulsar ele!");
				return true;
			}
			ban.add(target.getName());
			p.sendMessage("§e" + target.getName()  + (" (") + target.getAddress().getHostName() + ")" + " foi banido do Servidor.");
			p.sendMessage("§eLembre-se de usar esse comando apenas se o /ban falhar");
			target.kickPlayer("Sua conta está proibida de entrar nesse subserver. \nOu Você usou trapaças ou desrespeitou alguma outra regra.");
			Bukkit.broadcast("§e[GKick] " + p.getName() + " proibiu " + target.getName() + " de entrar no Servidor", "prismamc.staff");
			return true;
			
}

	{
	
	}
}
