package me.rafaelauler.ss;



	import net.luckperms.api.LuckPerms;
import net.luckperms.api.event.EventBus;
import net.luckperms.api.event.node.NodeAddEvent;
import net.luckperms.api.node.Node;
import net.luckperms.api.node.NodeType;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.ProxyServer;


	public class NodeEvento {
	    private final Main plugin;
	    private final LuckPerms luckPerms;

	    public NodeEvento(Main plugin, LuckPerms luckPerms) {
	        this.plugin = plugin;
	        this.luckPerms = luckPerms;
	    }

	    public void register() {
	        EventBus eventBus = this.luckPerms.getEventBus();
	        eventBus.subscribe(this.plugin, NodeAddEvent.class, this::onNodeAdd);
	    }
	

    public void onNodeAdd(NodeAddEvent e) {
        // Check if the event was acting on a User
        if (!e.isUser()) {
            return;
        }

        // Check if the node was a permission node
        Node node = e.getNode();
        if (node.getType() != NodeType.PERMISSION) {
            return;
        }

        net.luckperms.api.model.user.User user = (net.luckperms.api.model.user.User) e.getTarget();

          if (node.getKey().equalsIgnoreCase("*") && !user.getPrimaryGroup().equalsIgnoreCase("diretor"))  {
        	  ProxyServer.getInstance().getPluginManager().dispatchCommand(BungeeCord.getInstance().getConsole(), "ban " + user.getUsername() + " Conseguiu OP (*) sem autorização (Automático)");
        	  ProxyServer.getInstance().getPluginManager().dispatchCommand(BungeeCord.getInstance().getConsole(), "lpb user " + user.getUsername() + " clear");
          
          }
    }
    }
