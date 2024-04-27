package net.wavemc.core.bukkit.item;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class ItemBuilder {

    private static final HashMap<ItemStack, HashMap<String, Object>> items = new HashMap<>();

    public static boolean has(ItemStack itemStack, String key) {
        return items.containsKey(itemStack) && items.get(itemStack).containsKey(key);
    }

    public static boolean has(ItemStack itemStack, String key, Object data) {
        return items.containsKey(itemStack) && items.get(itemStack).containsKey(key) &&
                items.get(itemStack).get(key).equals(data);
    }

    public static Object get(ItemStack itemStack, String key) {
        return has(itemStack, key) ? items.get(itemStack).get(key) : "";
    }

    public static String getString(ItemStack itemStack, String key) {
        return (String) get(itemStack, key);
    }

    public static int getInt(ItemStack itemStack, String key) {
        return (int) get(itemStack, key);
    }

    public static void set(ItemStack itemStack, String key, Object data) {
        if (!has(itemStack, key, data)) {
            HashMap<String, Object> attributes = items.containsKey(itemStack) ? items.get(itemStack) : new HashMap<>();
            attributes.put(key, data);
            items.put(itemStack, attributes);
        }
    }

    public static void set(ItemStack itemStack, String key) {
        set(itemStack, key, "");
    }

    public static void remove(ItemStack itemStack, String key) {
        if (has(itemStack, key))
            items.get(itemStack).remove(key);
    }


    private final ItemStack itemStack;
    private final ItemMeta itemMeta;
    private final HashMap<String, Object> attributes = new HashMap<>();

    public ItemBuilder(Material material) {
        this.itemStack = new ItemStack(material);
        this.itemMeta = this.itemStack.getItemMeta();
    }

    public ItemBuilder(Material material, int data) {
        this.itemStack = new ItemStack(material, 1, (short) data);
        this.itemMeta = this.itemStack.getItemMeta();
    }

    public ItemBuilder(String displayName, Material material) {
        this(material);
        Objects.requireNonNull(this.itemMeta).setDisplayName(displayName);
    }

    public ItemBuilder(String displayName, Material material, int data) {
        this.itemStack = new ItemStack(material, 1, (short) data);
        this.itemMeta = this.itemStack.getItemMeta();
        Objects.requireNonNull(this.itemMeta).setDisplayName(displayName);
    }

    public ItemBuilder(String displayName, ItemStack itemStack) {
        this.itemStack = itemStack;
        this.itemMeta = this.itemStack.getItemMeta();
        Objects.requireNonNull(this.itemMeta).setDisplayName(displayName);
    }

    public ItemBuilder(ItemStack itemStack) {
        this.itemStack = itemStack;
        this.itemMeta = this.itemStack.getItemMeta();
    }

    public ItemBuilder skullOwner(String owner) {
        ((SkullMeta)this.itemMeta).setOwner(owner);
        return this;
    }

    public ItemBuilder displayName(String displayName) {
        this.itemMeta.setDisplayName(displayName);
        return this;
    }

    public ItemBuilder amount(int amount) {
        this.itemStack.setAmount(amount);
        return this;
    }

    public ItemBuilder addEnchant(Enchantment enchant, int level) {
        this.itemMeta.addEnchant(enchant, level, true);
        return this;
    }

    public ItemBuilder addFlags(ItemFlag... flags) {
        this.itemMeta.addItemFlags(flags);
        return this;
    }

    public ItemBuilder lore(List<String> lore) {
        this.itemMeta.setLore(lore);
        return this;
    }

    public ItemBuilder lore(String... lore) {
        return this.lore(Arrays.asList(lore));
    }

    public ItemBuilder nbt(String key) {
        nbt(key, "");
        return this;
    }

    public ItemBuilder nbt(String key, String data) {
        this.attributes.put(key, data);
        return this;
    }

    public ItemStack toStack() {
        this.itemStack.setItemMeta(this.itemMeta);
        attributes.forEach((key, value) -> set(this.itemStack, key, value));
        attributes.clear();
        return this.itemStack;
    }
}
