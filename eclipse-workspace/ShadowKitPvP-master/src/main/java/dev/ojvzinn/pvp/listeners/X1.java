package dev.ojvzinn.pvp.listeners;

/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;

import dev.ojvzinn.pvp.Main;

/*     */ import dev.ojvzinn.pvp.cosmetics.Cosmetic;
import dev.ojvzinn.pvp.cosmetics.CosmeticType;
import dev.ojvzinn.pvp.cosmetics.collections.Kit;
import dev.ojvzinn.pvp.game.KitPvP;
import dev.ojvzinn.pvp.game.enums.WarpEnum;
import dev.slickcollections.kiwizin.Core;
import dev.slickcollections.kiwizin.player.Profile;
import org.bukkit.Bukkit;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.Material;
/*     */
import org.bukkit.Sound;
/*     */
import org.bukkit.entity.HumanEntity;
/*     */ import org.bukkit.entity.Player;
/*     */
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.Listener;
/*     */
/*     */ import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
/*     */ import org.bukkit.event.player.*;
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */ import org.bukkit.inventory.ItemStack;
/*     */
/*     */ import org.bukkit.inventory.meta.ItemMeta;
/*     */
/*     */ import org.bukkit.potion.PotionEffect;
/*     */ import org.bukkit.scheduler.BukkitRunnable;
/*     */
import org.bukkit.scheduler.BukkitTask;
/*     */
/*    */ public class X1
        /*    */   implements Listener
        /*    */ {
    /*    */   private Main main;
    /*    */
    /*    */   public X1(Main main)
    /*    */   {
        /* 19 */     this.main = main;
        /*    */   }
    /*    */
    /*     */
    /*     */ public static Map<String, String> convites = new HashMap();
    /*  47 */
    /*  48 */   public static Map<String, String> lutadores = new HashMap();
    /*  49 */   public static ArrayList<Player> hide = new ArrayList();
    /*  50 */   public static ArrayList<Player> inx1 = new ArrayList();
    private BukkitTask runTaskLater;


    /*     */
    /*     */   public static void sair1v1(Player p) {

        inx1.remove(p);
        inx1.remove(p);
        inx1.remove(p);
        inx1.remove(p);
        inx1.remove(p);
        inx1.remove(p);
        inx1.remove(p);
        inx1.remove(p);
        inx1.remove(p);
        inx1.remove(p);
        inx1.remove(p);
        inx1.remove(p);
        inx1.remove(p);
        inx1.remove(p);
        inx1.remove(p);
        inx1.remove(p);
        inx1.remove(p);
        inx1.remove(p);
        inx1.remove(p);
        Profile profile = Profile.getProfile(p.getName());
        KitPvP.clearRegisters(p);

        KitPvP.findWarp(WarpEnum.ONE_VS_ONE).leave(profile);
        p.teleport(Core.getLobby());
    }
    /*     */
    /*     */   public static void entrar1v1(Player p)
    /*     */   {


        /*  97 */     inx1.add(p);
        org.bukkit.World w = Bukkit.getServer().getWorld("1v1");
        /*  98 */     p.teleport(new Location(w,-63.353, 142.0000000, -385.597));
        /* 100 */     p.getInventory().clear();
        /* 101 */     p.getInventory().setArmorContents(null);
        /*     */
        Cosmetic.findById("588", CosmeticType.KIT, Kit.class).applyKit(p);
        /* 115 */
        /* 116 */     p.setHealth(20.0D);
        /* 117 */     p.setExp(0.0F);
        /* 118 */     p.setLevel(0);
        /* 119 */
        /*     */   }
    /*     */
    /*     */
    /*     */   public static void set1v1(Player p)
    /*     */   {
        /* 125 */     p.getInventory().clear();
        /* 126 */     p.getInventory().setArmorContents(null);
        /* 127 */     ItemStack dima = new ItemStack(Material.DIAMOND_SWORD);
        /* 128 */     ItemMeta souperaa = dima.getItemMeta();
        /* 129 */     souperaa.setDisplayName("§cEspada");
        /* 130 */     dima.setItemMeta(souperaa);
        /* 131 */     p.getInventory().addItem(new ItemStack[] { dima });
        /* 132 */     p.getInventory().setHelmet(new ItemStack(Material.IRON_HELMET));
        /* 133 */     p.getInventory().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
        /* 134 */     p.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
        /* 135 */     p.getInventory().setBoots(new ItemStack(Material.IRON_BOOTS));
        /*     */
        /* 137 */     ItemStack sopa = new ItemStack(Material.MUSHROOM_SOUP);
        /* 138 */     ItemMeta sopas = sopa.getItemMeta();
        /* 139 */     sopas.setDisplayName("§6Sopa");
        /* 140 */     sopa.setItemMeta(sopas);

        /* 141 */     for (int i = 0; i <= 7; i++) {
            /* 142 */       p.getInventory().addItem(new ItemStack[] { sopa });
            /* 143 */       p.updateInventory();
            /*     */     }
        /*     */   }
    /*     */   @EventHandler
    /*     */   public void cmdT(PrepareItemCraftEvent  e) {
        for (HumanEntity entity : e.getViewers()) {
            if (entity instanceof Player) {
                Player p = (Player) entity;
                /* 200 */     if (inx1.contains(p)) {
                    ItemStack air = new ItemStack(Material.AIR);
                    e.getInventory().setResult(air);
                    /*     */     }
            }
        }
    }
    /*     */
    /*     */
    /*     */
    /*     */   public static void aceitar(Player p1, Player p2)
    /*     */   {
        Player firstPlayer = p1;
        Player secondPlayer = p2;
        firstPlayer.teleport(new Location(Bukkit.getWorld("1v1"), -44.492D, 109.00000000D, -430.454D));
        firstPlayer.getEyeLocation().setYaw(180.0F);
        secondPlayer.teleport(new Location(Bukkit.getWorld("1v1"), -44.550D, 109.0000000000D, -358.578D));
        secondPlayer.getEyeLocation().setYaw(0.0F);
                /* 154 */
        /* 159 */     set1v1(p1);
        /* 160 */     set1v1(p2);
        /* 161 */     lutadores.put(p1.getName(), p2.getName());
        /* 162 */     lutadores.put(p2.getName(), p1.getName());
        /* 163 */     convites.remove(p1.getName());
        /* 164 */     for (Player pp : Bukkit.getOnlinePlayers()) {
            /* 165 */       p1.hidePlayer(pp);
            /*     */     }
        /* 167 */     hide.add(p1);
        /* 168 */     for (Player pp : Bukkit.getOnlinePlayers()) {
            /* 169 */       p2.hidePlayer(pp);
            /*     */     }
        /* 171 */     hide.add(p2);
        /* 172 */     p1.showPlayer(p2);
        /* 173 */     p2.showPlayer(p1);
        /* 174 */     p1.updateInventory();
        /* 175 */     p2.updateInventory();
        /* 176 */     p2.sendMessage("§cVocê irá batalhar contra " + p1.getName());
        /* 177 */     p1.sendMessage("§cVocê irá batalhar contra " + p2.getName());
        p1.playSound(p1.getLocation(), Sound.GLASS, 5.0F, 5.0F);
        p2.playSound(p2.getLocation(), Sound.GLASS, 5.0F, 5.0F);
        /*     */   }
    /*     */
    /*     */   @EventHandler
    /*     */   public void interact(PlayerInteractEvent e) {
        /* 182 */     if ((e.getItem() != null && e.getItem().getItemMeta() != null && e.getItem().getItemMeta().hasDisplayName() && e.getItem().getItemMeta().getDisplayName().equals("§cSair") ) &&
                /* 183 */       (e.getAction().name().contains("RIGHT_CLICK")) &&
                /* 184 */       (e.getPlayer().getItemInHand().getType().equals(Material.REDSTONE))) {
            /* 185 */       sair1v1(e.getPlayer());
            /*     */     }
        /*     */   }
    /*     */
    /*     */
    /*     */   @EventHandler
    /*     */   public void entrar(PlayerJoinEvent e)
    /*     */   {
        /* 193 */     for (Player p : hide) {
            /* 194 */       p.hidePlayer(e.getPlayer());
            /*     */     }
        /*     */
        /*     */   }

    ////////
    /*     */
    /*     */   @EventHandler
    /*     */   public void cmd(PlayerCommandPreprocessEvent e) {
        /* 200 */     if (lutadores.containsKey(e.getPlayer())) {
            /* 201 */       e.getPlayer().sendMessage("§cSem comandos no 1v1");
            /* 202 */       e.setCancelled(true);
            /* 203 */       return;
            /*     */     }
        /*     */   }
    /*     */
    /*     */   @EventHandler
    /*     */   public void kick(PlayerQuitEvent e) {
        /* 209 */     if (lutadores.containsKey(e.getPlayer().getName())) {
            /* 210 */       Player matou = Bukkit.getServer().getPlayerExact((String)lutadores.get(e.getPlayer().getName()));
            /* 211 */       Player perdedor = e.getPlayer();
            /* 212 */       matou.sendMessage("§aSeu oponente desconectou");
            /* 213 */       hide.remove(perdedor);
            /* 214 */       hide.remove(matou);
            /* 215 */       for (Player pp : Bukkit.getOnlinePlayers()) {
                /* 216 */         matou.showPlayer(pp);
                /* 217 */         perdedor.showPlayer(pp);
                lutadores.remove(e.getPlayer().getName());
                /*     */       }
            /* 219 */       entrar1v1(matou);
            /*     */     }
        /*     */   }

    /*     */
    /*     */   @EventHandler
    /*     */   public void kick(PlayerKickEvent e) {
        /* 225 */     if (lutadores.containsKey(e.getPlayer().getName())) {
            /* 226 */       Player matou = Bukkit.getServer().getPlayerExact((String)lutadores.get(e.getPlayer().getName()));
            /* 227 */       Player perdedor = e.getPlayer();
            /* 228 */       matou.sendMessage("§4Seu oponente foi expulso do servidor!");
            /* 229 */       hide.remove(perdedor);
            /* 230 */       hide.remove(matou);
            /* 231 */       for (Player pp : Bukkit.getOnlinePlayers()) {
                /* 232 */         matou.showPlayer(pp);
                /* 233 */         perdedor.showPlayer(pp);
                lutadores.remove(e.getPlayer().getName());
                /*     */       }
            /* 235 */       entrar1v1(matou);
            /*     */     }
        /*     */   }
    /*     */
    /*     */   @EventHandler
    /*     */   public void morrer(final PlayerDeathEvent e) {
        /* 241 */     Player p = e.getEntity().getPlayer();
        /* 242 */     Player k = p.getKiller();
        if (p == null || k == null) {
            return;
        }
        if (!lutadores.containsKey(e.getEntity().getName()) || !lutadores.containsKey(e.getEntity().getKiller().getName())) {
            return;
        }
        /* 245 */     if ((e.getEntity() instanceof Player)) {
            /* 246 */
            /* 247 */       p.setFireTicks(0);
            /* 248 */       for (PotionEffect effect : p.getActivePotionEffects())
                /* 249 */         p.removePotionEffect(effect.getType());
            /*     */     }
        /* 251 */     if (((p instanceof Player)) && ((k instanceof Player))) {
            /* 252 */
            /* 253 */       p.setFireTicks(0);
            /* 254 */       for (PotionEffect effect : p.getActivePotionEffects())
                /* 255 */         p.removePotionEffect(effect.getType());
            /*     */     }
        /* 257 */
        /*     */
        /*     */
        /*     */
        /*     */
        /*     */
        /*     */
        /*     */
        /*     */
        /*     */
        /*     */
        /*     */
        /*     */
        /*     */
        /*     */
        /*     */
        /*     */
        /*     */
        /*     */
        /*     */
        /*     */
        /*     */
        /* 280 */       new BukkitRunnable()
                /*     */       {
            /*     */         public void run()
            /*     */         {
                /* 262 */           Player matou = Bukkit.getServer()
/* 263 */             .getPlayerExact((String)X1.lutadores.get(e.getEntity().getPlayer().getName()));
                /* 264 */           Player morreu = e.getEntity().getPlayer();
                /* 265 */           morreu.spigot().respawn();
                /* 266 */           X1.lutadores.remove(morreu.getName());
                /* 267 */           X1.lutadores.remove(matou.getName());
                Bukkit.getConsoleSender().sendMessage("§b" + morreu.getName() + " has been killed by " + matou.getName() + " on kitpvp 1v1");
                /* 268 */           X1.hide.remove(matou);
                /* 269 */           X1.hide.remove(morreu);
                /* 270 */           X1.entrar1v1(matou);
                /* 271 */           X1.entrar1v1(morreu);
                /* 272 */           morreu.updateInventory();
                /* 273 */           matou.updateInventory();
                /* 274 */           for (Player online : Bukkit.getOnlinePlayers()) {
                    /* 275 */             morreu.showPlayer(online);
                    /* 276 */             matou.showPlayer(online);
                    /*     */           }
                /*     */
                /*     */         }
            /* 280 */       }.runTaskLater(Main.getInstance(), 5L);
        /*     */     }
    /*     */
    /*     */@EventHandler
    /*     */   public void InteractItem(PlayerDropItemEvent e)
    /*     */ {
        if (!e.getPlayer().getWorld().equals(Bukkit.getWorld("1v1"))) {
            return;
        }
        if (lutadores.containsKey(e.getPlayer())) {
            return;
        }
        e.setCancelled(true);
    }





    @EventHandler
    public void onPlayerDamage(EntityDamageByEntityEvent event) {
        if (event.isCancelled()) {
            return;
        }
        if (!(event.getDamager() instanceof Player)) {
            return;
        }
        if (!(event.getEntity() instanceof Player)) {
            return;
        }
        if (!event.getDamager().getWorld().equals(Bukkit.getWorld("1v1"))) {
            return;
        }
        if (lutadores.containsKey(event.getDamager())) {
            return;
        }
        event.setCancelled(true);

    }
    /*     */   @EventHandler
    /*     */   public void InteractItem(PlayerInteractEntityEvent e)
    /*     */   {

        /* 287 */     if (!(e.getPlayer().getItemInHand() != null && e.getPlayer().getItemInHand().getItemMeta() != null && e.getPlayer().getItemInHand().getItemMeta().hasDisplayName() && e.getPlayer().getItemInHand().getItemMeta().getDisplayName().equals("§6Desafiar para 1v1 §7(Clique direito)")  &&
                /* 288 */       ((e.getRightClicked() instanceof Player)) &&
                /* 289 */       (e.getPlayer().getItemInHand().getType() == Material.BLAZE_ROD))) {
            return;
        }
        {
            /* 290 */       final Player p = e.getPlayer();
            /* 291 */       Player target = (Player)e.getRightClicked();
            /* 292 */       if (convites.containsKey(target.getName())) {
                /* 293 */         if (((String)convites.get(target.getName())).equalsIgnoreCase(p.getName())) {
                    /* 294 */           aceitar(target, p);
                    /* 295 */         } else if (!convites.containsKey(p.getName())) {
                    /* 296 */                           /* 313 */         p.sendMessage("§cVocê convidou o jogador " + target.getName());
                    /* 314 */         target.sendMessage("§cVocê foi convidado pelo jogador " + p.getName());
                    /* 299 */           convites.put(p.getName(), target.getName());
                    /* 300 */           Bukkit.getServer().getScheduler().runTaskLater(Main.getInstance(), new BukkitRunnable()
                            /*     */           {
                        /*     */             public void run()
                        /*     */             {
                            /* 304 */               if (X1.convites.containsKey(p.getName())) {
                                /* 305 */                 X1.convites.remove(p.getName());
                                /*     */               }
                            /*     */             }
                        /* 308 */           }, 200L);
                    /*     */         } else {
                    /* 310 */           p.sendMessage("§cAguarde para desafiar novamente");
                    /*     */         }
                /* 312 */       } else if (!convites.containsKey(p.getName())) {
                /* 313 */         p.sendMessage("§cVocê convidou o jogador " + target.getName());
                /* 314 */         target.sendMessage("§cVocê foi convidado pelo jogador " + p.getName());
                /* 316 */         convites.put(p.getName(), target.getName());
                /* 317 */         BukkitTask runTaskLater = Bukkit.getServer().getScheduler().runTaskLater(Main.getInstance(), new BukkitRunnable()
                        /*     */         {
                    /*     */           public void run()
                    /*     */           {
                        /* 321 */             if (X1.convites.containsKey(p.getName())) {
                            /* 322 */               X1.convites.remove(p.getName());
                            /*     */             }
                        /*     */           }
                    /* 325 */         }, 200L);
                /*     */       } else {
                /* 327 */         p.sendMessage("§cAguarde para desafiar novamente");
                /*     */       }
            /*     */     }
        /*     */   }
    /*     */
    /*     */
    /*     */
    /*     */   private static ItemStack make(Material material, int amount, int shrt, String displayName, List<String> lore)
    /*     */   {
        /* 336 */     ItemStack item = new ItemStack(material, amount, (short)shrt);
        /* 337 */     ItemMeta meta = item.getItemMeta();
        /* 338 */     meta.setDisplayName(displayName);
        /* 339 */     meta.setLore(lore);
        /* 340 */     item.setItemMeta(meta);
        /* 341 */     return item;
        /*     */   }
    /*     */ }


/* Location:              D:\Desktop\video\Minhas Coisas do Desktop\KP-PVPvB12 (1).jar!\me\RafaelAulerDeMeloAraujo\X1\X1.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */
