package dev.ojvzinn.pvp.cosmetics.collections.kits;

import dev.ojvzinn.pvp.Main;
import dev.ojvzinn.pvp.api.DelayKits;
import dev.ojvzinn.pvp.cosmetics.Cosmetic;
import dev.ojvzinn.pvp.cosmetics.CosmeticType;
import dev.ojvzinn.pvp.cosmetics.collections.Kit;
import dev.ojvzinn.pvp.game.KitPvP;
import dev.slickcollections.kiwizin.player.Profile;
import dev.slickcollections.kiwizin.utils.BukkitUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Monk extends Kit {

    public Monk(String key, YamlConfiguration CONFIG) {
        super(key, CONFIG);
    }

    @Override
    public void applyKit(Player player) {
        PlayerInventory inventory = player.getInventory();
        Profile.getProfile(player.getName()).setHotbar(null);
        inventory.clear();

        ItemStack sword = BukkitUtils.deserializeItemStack("272 : 1");
        ItemMeta meta = sword.getItemMeta();
        meta.spigot().setUnbreakable(true);
        sword.setItemMeta(meta);
        inventory.setItem(0, sword);
        inventory.setItem(1, BukkitUtils.deserializeItemStack("369 : 1 : nome>&6Monk &7(Clique direito)"));
        inventory.setItem(8, BukkitUtils.deserializeItemStack("COMPASS : 1 : nome>&aBússola"));
        inventory.setItem(15, new ItemStack(Material.RED_MUSHROOM, 16));
        inventory.setItem(14, new ItemStack(Material.BROWN_MUSHROOM, 16));
        inventory.setItem(13, new ItemStack(Material.BOWL, 16));

        for (int i = 0; i < inventory.getSize(); i++) {
            if (inventory.getItem(i) == null || inventory.getItem(i).getType() == Material.AIR) {
                inventory.setItem(i, new ItemStack(Material.MUSHROOM_SOUP));
            }
        }

        DelayKits.createDelayProfile(player, this);
        addProtection(player.getName());
        player.sendMessage("§aVocê equipou o kit Monk.");
    }

    @Override
    public void setupListeners() {
        Bukkit.getPluginManager().registerEvents(this, Main.getInstance());
    }

    @EventHandler
    public void onPlayerInteractAbility(PlayerInteractAtEntityEvent event) {
        Player player = event.getPlayer();
        Profile profile = Profile.getProfile(player.getName());
        if (KitPvP.isPlayingKitPvP(player) && event.getRightClicked() instanceof Player) {
            ItemStack item = player.getItemInHand();
            Player clickedPlayer = (Player) event.getRightClicked();
            if (KitPvP.isPlayingKitPvP(clickedPlayer)) {
                if (item != null && item.getType() == Material.BLAZE_ROD && item.getItemMeta() != null && item.getItemMeta().hasDisplayName() && item.getItemMeta().getDisplayName().equals("§6Monk §7(Clique direito)") && isSelected(profile)) {
                    if (!DelayKits.loadDelayProfiles(player).canUse()) {
                        player.sendMessage("§cAguarde " + DelayKits.loadDelayProfiles(player).getDelayTime(true) + "s para utilizar o kit novamente!");
                        return;
                    }

                    Inventory inventory = clickedPlayer.getInventory();
                    ItemStack[] items = inventory.getContents();
                    List<ItemStack> itemList = new ArrayList<>();
                    for (ItemStack itemInv : items) {
                        if (itemInv != null) {
                            itemList.add(itemInv.clone());
                        } else {
                            itemList.add(null);
                        }
                    }

                    Collections.shuffle(itemList);
                    for (int i = 0; i < items.length; i++) {
                        ItemStack newItem = itemList.get(i);
                        if (newItem != null) {
                            inventory.setItem(i, newItem);
                        }
                    }

                    clickedPlayer.updateInventory();
                    player.sendMessage("§a[Monk] §fVocê bagunçou o inventário do jogador " + clickedPlayer.getName() + "!");
                    clickedPlayer.sendMessage("§a[Monk] §fSeu inventário foi bagunçado pelo jogador " + player.getName() + "!");
                    DelayKits.loadDelayProfiles(player).addDelay(30);
                }
            }
        }
    }

    @EventHandler
    public void onPlayerDropAbility(PlayerDropItemEvent event) {
        if (event.isCancelled()) {
            return;
        }

        Player player = event.getPlayer();
        Profile profile = Profile.getProfile(player.getName());
        if (KitPvP.isPlayingKitPvP(player)) {
            ItemStack item = event.getItemDrop().getItemStack();
            if (item != null && item.getType() == Material.BLAZE_ROD && item.getItemMeta() != null && item.getItemMeta().hasDisplayName() && item.getItemMeta().getDisplayName().equals("§6Monk §7(Clique direito)") && isSelected(profile)) {
                event.setCancelled(true);
            }
        }
    }
}
