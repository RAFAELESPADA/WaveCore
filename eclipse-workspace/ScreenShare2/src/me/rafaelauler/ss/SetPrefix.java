package me.rafaelauler.ss;


import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;
import net.luckperms.api.node.NodeType;
import net.luckperms.api.node.types.PrefixNode;
import net.luckperms.api.query.QueryOptions;

public class SetPrefix implements CommandExecutor {
    private final BukkitMain plugin;
    private final LuckPerms luckPerms;

    public SetPrefix(BukkitMain plugin, LuckPerms luckPerms) {
        this.plugin = plugin;
        this.luckPerms = luckPerms;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 2) {
            sender.sendMessage(ChatColor.RED + "Especifique um jogador e um prefixo!");
            return true;
        }
if (!sender.hasPermission("cmd.group")) {
	sender.sendMessage(ChatColor.RED + "SEM AUTORIZAÇÃO!");
	return true;
}
        String playerName = args[0];
        String prefix = args[1];

        // Get an OfflinePlayer object for the player
        OfflinePlayer player = this.plugin.getServer().getOfflinePlayer(playerName);

        // Player not known?
        if (player == null ||!player.hasPlayedBefore()) {
            sender.sendMessage(ChatColor.RED + playerName +  " nunca entrou no server!");
            return true;
        }

        // Load, modify & save the user in LuckPerms.
        this.luckPerms.getUserManager().modifyUser(player.getUniqueId(), (User user) -> {

            // Remove all other prefixes the user had before.
            user.data().clear(NodeType.PREFIX::matches);

            // Find the highest priority of their other prefixes
            // We need to do this because they might inherit a prefix from a parent group,
            // and we want the prefix we set to override that!
            Map<Integer, String> inheritedPrefixes = user.getCachedData().getMetaData(QueryOptions.nonContextual()).getPrefixes();
            int priority = inheritedPrefixes.keySet().stream().mapToInt(i -> i + 10).max().orElse(10);

            // Create a node to add to the player.
            Node node = PrefixNode.builder(prefix, priority).build();

            // Add the node to the user.
            user.data().add(node);

            // Tell the sender.
            sender.sendMessage(ChatColor.RED + user.getUsername() + " agora tem o prefixo " + ChatColor.RESET + prefix);
        });

        return true;
    }
}