package bedwarsplugin;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.viaversion.viaversion.ViaVersionPlugin;
import com.viaversion.viaversion.api.ViaAPI;

import net.helix.core.bukkit.HelixBukkit;
import net.helix.pvp.scoreboard.ScoreboardBuilder;






public class Main extends JavaPlugin {
	private ProtocolManager protocolManager;
	private ViaAPI viaManager;
    public static Main plugin;
    private ScoreboardBuilder scoreboardBuilder;
    @Override
    public void onEnable() {
    	  plugin = this;
    	  this.scoreboardBuilder = new ScoreboardBuilder(this);
registerEvents();
registerComands();

    }
    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage("[FPS] PLUGIN DESLIGADO COM SUCESSO");
    }

    public void registerEvents(){
new BukkitRunnable() {
			
			@Override
			public void run() {
				Bukkit.getWorlds().forEach(world -> world.setTime(1000));
			}
		}.runTaskTimer(this, 0, 30 * 20L);
		
		HelixBukkit.getExecutorService().submit(() -> {
			new BukkitRunnable() {
				@Override
				public void run() {
					Bukkit.getWorlds().forEach(world -> {
						world.getEntities().stream().filter(entity -> entity instanceof Item)
						.forEach(en -> en.remove());
					});
				}
			}.runTaskTimer(this, 0, 7 * 20L);
		});
		
		HelixBukkit.getExecutorService().submit(() -> {
			new BukkitRunnable() {
				@Override
				public void run() {
					Bukkit.getWorlds().forEach(world -> {
						world.getEntities().stream().filter(entity -> entity instanceof Item && entity.getLocation().getBlockX() < 3.560 && entity.getLocation().getBlockX() > -2.896)
						.forEach(en -> en.remove());
					});
				}
			}.runTaskTimer(this, 0, 1 * 1L);
		});
		
	        
	        
		
		ItemStack Resultado = new ItemStack(Material.MUSHROOM_SOUP, 1);
		ItemMeta Cactos = Resultado.getItemMeta();
		Resultado.setItemMeta(Cactos);

		ShapelessRecipe CraftCactos = new ShapelessRecipe(Resultado);
		CraftCactos.addIngredient(1, Material.CACTUS);
		CraftCactos.addIngredient(1, Material.BOWL);
		Bukkit.getServer().addRecipe(CraftCactos);
		ItemMeta Cocoa = Resultado.getItemMeta();
		Resultado.setItemMeta(Cocoa);
		
		ShapelessRecipe CraftCactos2 = new ShapelessRecipe(Resultado);
		CraftCactos2.addIngredient(1, Material.PUMPKIN_SEEDS);
		CraftCactos2.addIngredient(1, Material.BOWL);
		Bukkit.getServer().addRecipe(CraftCactos2);
		ItemMeta Cocoa2 = Resultado.getItemMeta();
		Resultado.setItemMeta(Cocoa2);

		ShapelessRecipe CraftCocoa = new ShapelessRecipe(Resultado);
		CraftCocoa.addIngredient(1, Material.INK_SACK, 3);
		CraftCocoa.addIngredient(1, Material.BOWL);
		Bukkit.getServer().addRecipe(CraftCocoa);
		ItemMeta Flores = Resultado.getItemMeta();
		Resultado.setItemMeta(Flores);
ScoreboardBuilder.init();
		ShapelessRecipe CraftFlores = new ShapelessRecipe(Resultado);
		CraftFlores.addIngredient(1, Material.YELLOW_FLOWER);
		CraftFlores.addIngredient(1, Material.RED_ROSE);
		CraftFlores.addIngredient(1, Material.BOWL);
		Bukkit.getServer().addRecipe(CraftFlores);
    	PluginManager pm = Bukkit.getPluginManager();
    	Bukkit.getConsoleSender().sendMessage("[FPS] EVENTOS INICIANDO");
    	pm.registerEvents(new Eventos(), this);
    	pm.registerEvents(new FPSDEATH(), this);
    	pm.registerEvents(new PlayerDeathListener(), this);
    	pm.registerEvents(new KillStreak(), this);
    	pm.registerEvents(new SignListener(), this);
    	pm.registerEvents(new PlayerJoinListener(this), this);
    	pm.registerEvents(new NoBreakEvent(), this);
    	pm.registerEvents(new Cocoa(), this);
    	pm.registerEvents(new PlayerCombatLogListener(), this);
    	new PlaceHolderAPIHook(this).register();
    	
    }
  
    public void onLoad()
	  {
	    this.protocolManager = ProtocolLibrary.getProtocolManager();
	    viaManager = ViaVersionPlugin.getInstance().getApi();
	    this.protocolManager.addPacketListener(
	      new PacketAdapter(this, ListenerPriority.NORMAL, new PacketType[] { PacketType.Play.Server.TAB_COMPLETE })
	      {
	        public void onPacketSending(PacketEvent event)
	        {
	          if (event.getPacketType() == PacketType.Play.Server.TAB_COMPLETE &&  !event.getPlayer().hasPermission("kitpvp.tab")) {
	        	  if (viaManager.getPlayerVersion(event.getPlayer().getUniqueId()) <= 47)
	            event.setCancelled(true);
	          }
	        }
	      });
	  }
    public ScoreboardBuilder getScoreboardBuilder() {
		return scoreboardBuilder;
	}

    public void registerComands(){
    	getCommand("gkick").setExecutor(new GKick());
    	getCommand("crash").setExecutor(new Crash());
    	getCommand("spawn").setExecutor(new Spawn());
    	getCommand("build").setExecutor(new NoBreakEvent());
    	getCommand("gunkick").setExecutor(new GUnKick());
    }
}
