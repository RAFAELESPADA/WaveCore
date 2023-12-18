package dev.ojvzinn.pvp.cosmetics;

import dev.ojvzinn.pvp.container.CosmeticContainer;
import dev.ojvzinn.pvp.container.SelectedContainer;
import dev.ojvzinn.pvp.cosmetics.collections.Kit;
import dev.slickcollections.kiwizin.player.Profile;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Cosmetic {

    private static final List<Cosmetic> cosmetics = new ArrayList<>();
    private final String name;
    private final CosmeticType type;
    private final String permission;
    private final String id;
    private final Double value;
    private final ItemStack noHas;
    private final ItemStack noHasPermission;
    private final ItemStack noHasTotalAmount;
    private final ItemStack has;
    private final ItemStack hasAndSelected;

    public static void registerNewCosmetic(Cosmetic cosmetic) {
        cosmetics.add(cosmetic);
    }

    public static List<Cosmetic> listAllCosmetics(CosmeticType type) {
        return cosmetics.stream().filter(cosmetic -> cosmetic.getType().equals(type)).collect(Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    public static <T extends Cosmetic> T findCosmeticSelected(CosmeticType type, Profile profile) {
        return (T) listAllCosmetics(type).stream().filter(cosmetic -> cosmetic.isSelected(profile)).findFirst().orElse(null);
    }

    @SuppressWarnings("unchecked")
    public static <T extends Cosmetic> T findCosmeticSelected(CosmeticType type, Profile profile, Class<T> clazz) {
        return (T) listAllCosmetics(type).stream().filter(cosmetic -> cosmetic.isSelected(profile)).findFirst().orElse(null);
    }

    @SuppressWarnings("unchecked")
    public static <T extends Cosmetic> T findById(String id, CosmeticType type) {
        return (T) listAllCosmetics(type).stream().filter(cosmetic -> cosmetic.getType().equals(type) && cosmetic.getId().equals(id)).findFirst().orElse(null);
    }

    @SuppressWarnings("unchecked")
    public static <T extends Cosmetic> T findById(String id, CosmeticType type, Class<T> cosmeticClass) {
        return (T) listAllCosmetics(type).stream().filter(cosmetic -> cosmetic.getType().equals(type) && cosmetic.getId().equals(id)).findFirst().orElse(null);
    }

    public static List<Cosmetic> listAllCosmetics() {
        return cosmetics;
    }

    public static void setupCosmetics() {
        Kit.setupKits();
    }

    public Cosmetic(String name, CosmeticType type, String permission, String id, Double value, ItemStack noHas, ItemStack noHasPermission, ItemStack noHasTotalAmount, ItemStack has, ItemStack hasAndSelected) {
        this.name = name;
        this.type = type;
        this.permission = permission;
        this.id = id;
        this.value = value;
        this.noHas = noHas;
        this.noHasPermission = noHasPermission;
        this.noHasTotalAmount = noHasTotalAmount;
        this.has = has;
        this.hasAndSelected = hasAndSelected;
    }

    public boolean has(Profile profile) {
        CosmeticContainer container = profile.getAbstractContainer("kCoreKitPvP", "cosmetics", CosmeticContainer.class);
        return this.value == 0.0 || container.hasCosmetic(String.valueOf(this.type.getId()), this.id) || profile.getPlayer().hasPermission("shadowpvp." + this.type.getName().toLowerCase() + "." + this.name.toLowerCase());
    }

    public boolean canSelected(Profile profile) {
        if (this.permission != null && !this.permission.equals("")) {
            return profile.getPlayer().hasPermission(this.permission);
        }

        return true;
    }

    public ItemStack getIcon(Profile profile) {
        if (!has(profile)) {
            if (!canSelected(profile)) {
                return this.noHasPermission;
            }
            if (profile.getCoins("kCoreKitPvP") < this.value) {
                return this.noHasTotalAmount;
            }
            return this.noHas;
        } else {
            if (isSelected(profile)) {
                return this.hasAndSelected;
            }
            return this.has;
        }
    }

    public void giveCosmetic(Profile profile) {
        CosmeticContainer container = profile.getAbstractContainer("kCoreKitPvP", "cosmetics", CosmeticContainer.class);
        container.addCosmetic(String.valueOf(this.type.getId()), this.id);
    }

    public void removeCosmetic(Profile profile) {
        CosmeticContainer container = profile.getAbstractContainer("kCoreKitPvP", "cosmetics", CosmeticContainer.class);
        container.removeCosmetic(String.valueOf(this.type.getId()), this.id);
    }

    public void selectedCosmetic(Profile profile) {
        SelectedContainer container = profile.getAbstractContainer("kCoreKitPvP", "selected", SelectedContainer.class);
        container.setSelectedCosmetic(String.valueOf(this.type.getId()), this.id);
    }
    public void unselectedCosmetic(Profile profile) {
        SelectedContainer container = profile.getAbstractContainer("kCoreKitPvP", "selected", SelectedContainer.class);
        container.removeSelectedCosmetic(String.valueOf(this.type.getId()), this.id);
    }

    public boolean isSelected(Profile profile) {
        SelectedContainer container = profile.getAbstractContainer("kCoreKitPvP", "selected", SelectedContainer.class);
        return container.hasSelectedCosmetic(String.valueOf(this.type.getId()), this.id);
    }

    public CosmeticType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public Double getValue() {
        return value;
    }
}
