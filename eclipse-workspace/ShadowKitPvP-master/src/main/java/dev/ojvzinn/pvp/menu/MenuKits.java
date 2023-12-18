package dev.ojvzinn.pvp.menu;

import dev.ojvzinn.pvp.Language;
import dev.ojvzinn.pvp.cosmetics.Cosmetic;
import dev.ojvzinn.pvp.cosmetics.CosmeticType;
import dev.ojvzinn.pvp.cosmetics.collections.Kit;
import dev.slickcollections.kiwizin.Core;
import dev.slickcollections.kiwizin.libraries.menu.PagedPlayerMenu;
import dev.slickcollections.kiwizin.player.Profile;
import dev.slickcollections.kiwizin.utils.BukkitUtils;
import dev.slickcollections.kiwizin.utils.enums.EnumSound;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class MenuKits extends PagedPlayerMenu {

    private Profile profile;
    private Map<ItemStack, Kit> kitForSlot = new HashMap<>();

    public MenuKits(Player player) {
        super(player, "Menu de Kits", 6);
        this.previousPage = (this.rows * 9) - 9;
        this.nextPage = (this.rows * 9) - 1;
        this.onlySlots(Arrays.asList(10, 11, 12, 13, 14, 15, 16, 19, 20, 21, 22, 23, 24, 25, 28, 29, 30, 31, 32, 33, 34));
        this.profile = Profile.getProfile(player.getName());
        List<ItemStack> itens = new ArrayList<>();

        for (Cosmetic kit : Cosmetic.listAllCosmetics(CosmeticType.KIT)) {
            kitForSlot.put(kit.getIcon(profile), (Kit) kit);
            itens.add(kit.getIcon(profile));
        }

        this.removeSlotsWith(BukkitUtils.deserializeItemStack("351:1 : 1 : nome>&cFechar"), 49);

        this.setItems(itens);
        this.register(Core.getInstance());
        this.open();
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent evt) {
        if (evt.getInventory().equals(this.getCurrentInventory())) {
            evt.setCancelled(true);

            if (evt.getWhoClicked().equals(this.player)) {
                Profile profile = Profile.getProfile(this.player.getName());
                if (profile == null) {
                    this.player.closeInventory();
                    return;
                }

                if (evt.getClickedInventory() != null && evt.getClickedInventory().equals(this.getCurrentInventory())) {
                    ItemStack item = evt.getCurrentItem();

                    if (item != null && item.getType() != Material.AIR) {
                        if (evt.getSlot() == this.previousPage) {
                            EnumSound.CLICK.play(this.player, 0.5F, 2.0F);
                            this.openPrevious();
                        } else if (evt.getSlot() == this.nextPage) {
                            EnumSound.CLICK.play(this.player, 0.5F, 2.0F);
                            this.openNext();
                        } else if (evt.getSlot() == 49) {
                            player.closeInventory();
                            EnumSound.CLICK.play(this.player, 0.5F, 2.0F);
                        } else {
                            Cosmetic cosmetic = this.kitForSlot.get(evt.getCurrentItem());
                            if (cosmetic != null) {
                                if (!cosmetic.canSelected(profile)) {
                                    player.sendMessage(Language.cosmetic$nohas$permission);
                                    EnumSound.ENDERMAN_TELEPORT.play(this.player, 0.5F, 1.0F);
                                    return;
                                }

                                if (!cosmetic.has(profile)) {
                                    if (profile.getCoins("kCoreKitPvP") < cosmetic.getValue()) {
                                        player.sendMessage(Language.cosmetic$nohas$totalmoney);
                                        EnumSound.ENDERMAN_TELEPORT.play(this.player, 0.5F, 1.0F);
                                        return;
                                    }

                                    player.sendMessage(Language.cosmetic$buy$sucess);
                                    EnumSound.LEVEL_UP.play(this.player, 0.5F, 1.0F);
                                    cosmetic.giveCosmetic(profile);
                                    new MenuKits(player);
                                    return;
                                }

                                EnumSound.ITEM_PICKUP.play(this.player, 0.5F, 2.0F);
                                if (cosmetic.isSelected(profile)) {
                                    cosmetic.unselectedCosmetic(profile);
                                } else {
                                    cosmetic.selectedCosmetic(profile);
                                }

                                new MenuKits(player);
                            }
                        }
                    }
                }
            }
        }
    }

    public void cancel() {
        HandlerList.unregisterAll(this);
        this.kitForSlot.clear();
        this.kitForSlot = null;
        this.profile = null;
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent evt) {
        if (evt.getPlayer().equals(this.player)) {
            this.cancel();
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent evt) {
        if (evt.getPlayer().equals(this.player) && evt.getInventory().equals(this.getCurrentInventory())) {
            this.cancel();
        }
    }

}
