package bedwarsplugin;


import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import net.helix.core.bukkit.api.ActionBarAPI;
import net.helix.core.bukkit.item.ItemBuilder;








public class Eventos implements Listener {

	   public int vida = 7;
	   /* 19 */   public int fome = 7;
	 private ArrayList<String> fall = new ArrayList<>();
@EventHandler
public void onRegionE(PlayerLoginEvent e) {
  Player p = e.getPlayer();
 if (GKick.ban.contains(p.getName())) {
	 e.setKickMessage("Sua conta está proibida de entrar nesse subserver. \nOu Você usou trapaças ou desrespeitou alguma outra regra.");
	 e.disallow(Result.KICK_OTHER, "Sua conta está proibida de entrar nesse subserver. \nOu Você usou trapaças ou desrespeitou alguma outra regra.");
	 Bukkit.getConsoleSender().sendMessage(p.getName() + "tentou entrar mas levou BAN (GKick)");
 }
}




@EventHandler
public void onDano(PlayerDropItemEvent e) {
	if (e.getItemDrop().getItemStack().getType() == Material.DIAMOND_SWORD) {
		e.setCancelled(true);
	}
}
@EventHandler
public void onDano(EntityDamageByEntityEvent e) {
	if (!(e.getDamager() instanceof Player)) {
		return;
	}
	Player t = (Player) e.getDamager();
	if (t.getItemInHand().getType() == Material.STONE_SWORD) {
		e.setDamage(e.getDamage() - 2.5);
	}else if (t.getItemInHand().getType() == Material.STONE_SWORD) {
		if (t.getItemInHand().getEnchantments() == Enchantment.DAMAGE_ALL) {
			e.setDamage(e.getDamage() - 2.5 + (0.6 * t.getItemInHand().getEnchantmentLevel(Enchantment.DAMAGE_ALL)));
		} else if (t.getItemInHand().getEnchantments() == Enchantment.DAMAGE_ALL && t.getFallDistance() > 0) {
			e.setDamage(e.getDamage() - 2.0 + (0.7 * t.getItemInHand().getEnchantmentLevel(Enchantment.DAMAGE_ALL)));
		}
	}else if (t.getItemInHand().getType() == Material.IRON_SWORD) {
			e.setDamage(e.getDamage() - 1.5);
			if (t.getItemInHand().getEnchantments() == Enchantment.DAMAGE_ALL) {
				e.setDamage(e.getDamage() - 1.5 + (0.6 * t.getItemInHand().getEnchantmentLevel(Enchantment.DAMAGE_ALL)));
			}
	} else if (t.getItemInHand().getEnchantments() == Enchantment.DAMAGE_ALL && t.getFallDistance() > 0) {
		e.setDamage(e.getDamage() - 1.5 + (0.7 * t.getItemInHand().getEnchantmentLevel(Enchantment.DAMAGE_ALL)));
		}
else if (t.getItemInHand().getType() == Material.DIAMOND_SWORD) {
	e.setDamage(e.getDamage() - 1.0);
	if (t.getItemInHand().getEnchantments() == Enchantment.DAMAGE_ALL) {
		e.setDamage(e.getDamage() - (1.0) * 0.6 * t.getItemInHand().getEnchantmentLevel(Enchantment.DAMAGE_ALL));
	}
} else if (t.getItemInHand().getEnchantments() == Enchantment.DAMAGE_ALL && t.getFallDistance() > 0) {
	e.setDamage(e.getDamage() - 1.0 + (0.7 * t.getItemInHand().getEnchantmentLevel(Enchantment.DAMAGE_ALL)));
	}
	if (t.getItemInHand().hasItemMeta() && t.getItemInHand().getType() == Material.QUARTZ) {
		e.setDamage(4.5);
	}
}
@EventHandler
public void onEspada(EntityDamageByEntityEvent e) {
	if (!(e.getDamager() instanceof Player)) {
		return;
	}
	
	Player p = (Player) e.getDamager();
	if (p.getItemInHand().getType() == Material.DIAMOND_SWORD) {
		p.getItemInHand().setDurability((short)0);
		p.updateInventory();
	}
      }

   



	
	public void Atirar(Player p) {
		int y = 8;
		Block block = p.getLocation().getBlock().getRelative(0, -1, 0);
		if (block.getType() == Material.SPONGE) {
			Vector vector = new Vector(0, y, 0);
			p.setVelocity(vector);
			this.fall.remove(p.getName());
			 p.playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 4.0F, 4.0F);
			p.getPlayer().getWorld().playEffect(p.getPlayer().getLocation(), Effect.MOBSPAWNER_FLAMES, 10);
			this.fall.add(p.getName());
		}
	}
	public void Atirar2(Player p) {
		final Location loc = p.getEyeLocation();
    
        final Vector sponge = p.getLocation().getDirection().multiply(3.8).setY(0.45);
		Block block = p.getLocation().getBlock().getRelative(0, -1, 0);
		if (block.getType() == Material.SLIME_BLOCK) {
			p.setVelocity(sponge);
		    p.playEffect(loc, Effect.MOBSPAWNER_FLAMES, (Object)null);
			this.fall.remove(p.getName());
			 p.playSound(p.getLocation(), Sound.SLIME_WALK, 4.0F, 4.0F);
			this.fall.add(p.getName());
		}
	}
	

	
	@EventHandler
	public void RemoverDan2o(PlayerPickupItemEvent e) {
		Player entity = e.getPlayer();
		if (entity.getLocation().getX() < 3.560 && entity.getLocation().getX() > -2.896) {
			e.setCancelled(true);
		}
	}
	
	
	   @EventHandler
		public void RemoverDan2o(PlayerMoveEvent e) 
		{
		   
			   Player p = e.getPlayer();
			   for (ItemStack items : p.getInventory().getContents()) {
				   if (items != null) {
				    if (items.getType() != Material.AIR) {
				       return;
				    }
				   }
				}
			   if (p.getLocation().getX() > -170.0D && p.getLocation().getX() < 100.0D && p.getLocation().getZ() > -100.0D && p.getLocation().getZ() < 100.0D && p.getLocation().getY() < 68.0D)  {
			   p.getInventory().setItem(0, new ItemBuilder("§aEspada de diamante", Material.DIAMOND_SWORD)
						.nbt("cancel-drop").addEnchant(Enchantment.DAMAGE_ALL, 1)
						.toStack()
				);

			   p.getInventory().setHelmet(new ItemBuilder("§b§lCAPACETE", Material.IRON_HELMET).toStack());
				p.getInventory().setChestplate(new ItemBuilder("§b§lCOLETE", Material.IRON_CHESTPLATE).toStack());
				p.getInventory().setLeggings(new ItemBuilder("§b§lCALÇA", Material.IRON_LEGGINGS).toStack());
				p.getInventory().setBoots(new ItemBuilder("§b§lBOTA", Material.IRON_BOOTS).toStack());
				
				for (int i = 0; i < 36; i++) {
					p.getInventory().addItem(new ItemStack(Material.MUSHROOM_SOUP));
				}
				p.getInventory().setItem(13, new ItemStack(Material.BOWL, 64));
				p.getInventory().setItem(14, new ItemStack(Material.RED_MUSHROOM, 64));
				p.getInventory().setItem(15, new ItemStack(Material.BROWN_MUSHROOM, 64));
		   }
		}
	  

	   
	
	@EventHandler
	/*    */   public void UsarSopa(PlayerInteractEvent e) {
	/* 27 */     if (e.getItem() == null) {
	/* 28 */       return;
	/*    */     }

	Player p = e.getPlayer();
	/* 33 */       if (p.getHealth() < 20.0D && (p.getItemInHand().getType() == Material.MUSHROOM_SOUP)) {
		e.setCancelled(true);

	/* 35 */        
	/* 36 */         
	/* 37 */         p.setHealth(p.getHealth() + this.vida >= 20.0D ? 20.0D : p.getHealth() + this.vida);
	p.setFoodLevel(20);
	ActionBarAPI.sendActionBar(p, "§c+" + (p.getHealth() + 7 <= 20.0D ? 3.5 : 2) + " §4\u2661");
	    e.getItem().setType(Material.BOWL);
	}
	}
	@EventHandler
	/*    */   public void onFall(FoodLevelChangeEvent e) {   
		if (!e.isCancelled()) {
	      e.setCancelled(true);
		}
	}
	 
	   @EventHandler
		public void RemoverDano(EntityDamageEvent e) 
		{
		   if (!(e.getEntity() instanceof Player)) {
	           return;
	       }
			Player p = (Player) e.getEntity();
			if (e.getCause() == EntityDamageEvent.DamageCause.FALL && this.fall.contains(p.getName())) 
			{
				this.fall.remove(p.getName());
				e.setCancelled(true);

			}
			else if(e.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK)
			{
				this.fall.remove(p.getName());
			}
		}
		
	}


