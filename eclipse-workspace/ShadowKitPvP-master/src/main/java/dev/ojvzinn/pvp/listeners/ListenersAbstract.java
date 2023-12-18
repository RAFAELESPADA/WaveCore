package dev.ojvzinn.pvp.listeners;

import dev.ojvzinn.pvp.Main;
import dev.ojvzinn.pvp.listeners.collections.*;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

public abstract class ListenersAbstract implements Listener {

    public static void setupListeners() {
        new PlayerJoinListeners();
        new AsyncPlayerChatListener();
        new EntityListener();
        new InventoryClickListener();
        new PlayerDeathListener();
        new PlayerInteractListener();
        new PlayerQuitListener();
        new PlayerRestListener();
        new ServerListener();
    }

    public ListenersAbstract() {
        Bukkit.getPluginManager().registerEvents(this, Main.getInstance());
    }
}
