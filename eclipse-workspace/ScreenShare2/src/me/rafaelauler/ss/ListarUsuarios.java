package me.rafaelauler.ss;


	import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.node.matcher.NodeMatcher;
import net.luckperms.api.node.types.InheritanceNode;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;


	public class ListarUsuarios extends Command {
		public ListarUsuarios() {
		    super("listarusuarios", null, new String[] { "listusers" , "playersrank" });
		  }
		  
	  LuckPerms api = LuckPermsProvider.get();
	  
	  @SuppressWarnings("deprecation")
	public void execute(CommandSender sender, String[] args) {
	    if (!sender.hasPermission("cmd.group")) {
	      sender.sendMessage(ChatColor.RED + "Você precisa ser Administrador ou superior para executar esse comando.");
	      return;
	    } 
	    if (args.length != 1) {
            sender.sendMessage(ChatColor.RED + "Especifique um grupo!");
            return;
        }

        String groupName = args[0];

        // Get a group object for the group name.
        Group group = this.api.getGroupManager().getGroup(groupName);

        // Group doesn't exist?
        if (group == null) {
            sender.sendMessage(ChatColor.RED + "Grupo " + groupName +  " não existe!");
            return;
        }

        // Create a NodeMatcher, matching inheritance nodes for the given group
        NodeMatcher<InheritanceNode> matcher = NodeMatcher.key(InheritanceNode.builder(group).build());


        // Search all users for a match
        this.api.getUserManager().searchAll(matcher).thenAccept((Map<UUID, Collection<InheritanceNode>> map) -> {
            // Get a set of the group members (their UUIDs)
            Set<UUID> memberUniqueIds = map.keySet();

            // Tell the sender.
            sender.sendMessage(ChatColor.RED + group.getName() + " tem " + memberUniqueIds.size() + " membros.");
            sender.sendMessage(ChatColor.YELLOW +" UUID DOS PLAYERS ABAIXO:");
            sender.sendMessage(ChatColor.RED + memberUniqueIds.toString());
        });
	  }
	}
