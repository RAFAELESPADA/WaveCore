package net.wavemc.core.bukkit.util;

import net.wavemc.core.bukkit.WaveBukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginIdentifiableCommand;
import org.bukkit.plugin.Plugin;
@SuppressWarnings("all")
public abstract class CCommand extends Command implements PluginIdentifiableCommand {
    CommandSender sender;//So you can mess with this inside this class
    WaveBukkit plugin = WaveBukkit.getInstance();

    protected CCommand(String name) {
        super(name);
    }

    @Override//Sets the plugin to our plugin so it shows up in /help
    public Plugin getPlugin() {
        return plugin;
    }

    public abstract void run(CommandSender sender, String commandLabel, String[] arguments);//Just simpler and allows 'return;' instead of 'return true/false;'

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] arguments) {
        this.sender = sender;
        run(sender, commandLabel, arguments);//actually run the command.
        return true;
    }
}