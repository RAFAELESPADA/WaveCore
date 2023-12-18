package dev.ojvzinn.pvp.cosmetics.collections;

import dev.ojvzinn.pvp.Main;
import dev.ojvzinn.pvp.cosmetics.Cosmetic;
import dev.ojvzinn.pvp.cosmetics.CosmeticType;
import dev.ojvzinn.pvp.cosmetics.collections.kits.*;
import dev.slickcollections.kiwizin.utils.BukkitUtils;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class Kit extends Cosmetic implements Listener {

    private static final List<String> NO_DAMAGE = new ArrayList<>();

    public static Kit findByKitClass(Class<? extends Kit> kitClass) {
        return (Kit) Cosmetic.listAllCosmetics(CosmeticType.KIT).stream().filter(cosmetic -> cosmetic.getClass().equals(kitClass)).findFirst().orElse(null);
    }

    public static <T extends Kit> T findByKitClass(Class<T> kitClass, boolean transformKitClass) {
        return (T) Cosmetic.listAllCosmetics(CosmeticType.KIT).stream().filter(cosmetic -> cosmetic.getClass().equals(kitClass)).findFirst().orElse(null);
    }

    public static void setupKits() {
        File file = new File("plugins/" + Main.getInstance().getDescription().getName() + "/cosmetics/kits.yml");
        if (!file.exists()) {
            File folder = file.getParentFile();
            if (!folder.exists()) {
                folder.mkdirs();
            }

            try {
                Files.copy(Objects.requireNonNull(Kit.class.getResourceAsStream("/kits.yml")), file.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        new PvP("pvp", YamlConfiguration.loadConfiguration(file));
        new Stomper("stomper", YamlConfiguration.loadConfiguration(file));
        new Anchor("anchor", YamlConfiguration.loadConfiguration(file));
        new Steelhead("steelhead", YamlConfiguration.loadConfiguration(file));
        new Deshfire("deshfire", YamlConfiguration.loadConfiguration(file));
        new Fireman("fireman", YamlConfiguration.loadConfiguration(file));
        new Fisherman("fisherman", YamlConfiguration.loadConfiguration(file));
        new Kangaroo("kangaroo", YamlConfiguration.loadConfiguration(file));
        new Magma("magma", YamlConfiguration.loadConfiguration(file));
        new Monk("monk", YamlConfiguration.loadConfiguration(file));
        new Ninja("ninja", YamlConfiguration.loadConfiguration(file));
        new Poseidon("poseidon", YamlConfiguration.loadConfiguration(file));
        new Snail("snail", YamlConfiguration.loadConfiguration(file));
        new Sonic("sonic", YamlConfiguration.loadConfiguration(file));
        new Thor("thor", YamlConfiguration.loadConfiguration(file));
        new Urgal("urgal", YamlConfiguration.loadConfiguration(file));
        new Vampire("vampire", YamlConfiguration.loadConfiguration(file));
        new Viking("viking", YamlConfiguration.loadConfiguration(file));
        new Viper("viper", YamlConfiguration.loadConfiguration(file));
        new Gladiator("gladiator", YamlConfiguration.loadConfiguration(file));
        new Timelord("timelord", YamlConfiguration.loadConfiguration(file));
        new Switcher("switcher", YamlConfiguration.loadConfiguration(file));
        new Grandpa("grandpa", YamlConfiguration.loadConfiguration(file));
        new Neo("neo", YamlConfiguration.loadConfiguration(file));
        new Flash("flash", YamlConfiguration.loadConfiguration(file));
        new Quickdroper("quickdroper", YamlConfiguration.loadConfiguration(file));
        new Fenix("fenix", YamlConfiguration.loadConfiguration(file));
        new Avatar("avatar", YamlConfiguration.loadConfiguration(file));
        new FPS();
    }

    public Kit(String key, YamlConfiguration CONFIG) {
        super(CONFIG.getString("kits." + key + ".name"), CosmeticType.KIT, CONFIG.getString("kits." + key + ".permission"), CONFIG.getString("kits." + key + ".id"), CONFIG.getDouble("kits." + key + ".value"), CONFIG.getString("kits." + key + ".icon.no_has").equals("") ? new ItemStack(Material.AIR) : BukkitUtils.deserializeItemStack(CONFIG.getString("kits." + key + ".icon.no_has")), CONFIG.getString("kits." + key + ".icon.no_has_permission").equals("") ? new ItemStack(Material.AIR) : BukkitUtils.deserializeItemStack(CONFIG.getString("kits." + key + ".icon.no_has_permission")), CONFIG.getString("kits." + key + ".icon.no_has_total_amount").equals("") ? new ItemStack(Material.AIR) : BukkitUtils.deserializeItemStack(CONFIG.getString("kits." + key + ".icon.no_has_total_amount")), BukkitUtils.deserializeItemStack(CONFIG.getString("kits." + key + ".icon.has")), BukkitUtils.deserializeItemStack(CONFIG.getString("kits." + key + ".icon.has_selected")));
        Cosmetic.registerNewCosmetic(this);
        this.setupListeners();
    }

    public Kit(String name, String permission, String id, Double price, ItemStack no_has, ItemStack no_has_permission, ItemStack no_has_total_amount, ItemStack icon_has, ItemStack has_selected) {
        super(name, CosmeticType.KIT, permission, id, price, no_has, no_has_permission, no_has_total_amount, icon_has, has_selected);
        Cosmetic.registerNewCosmetic(this);
        this.setupListeners();
    }

    public abstract void applyKit(Player player);
    public abstract void setupListeners();

    public void addProtection(String player) {
        NO_DAMAGE.add(player);
    }
    @EventHandler
    public void onPlayerFallDamage(EntityDamageEvent event) {
        if (event.getEntity().getType().equals(EntityType.PLAYER)) {
            if (event.getCause().equals(EntityDamageEvent.DamageCause.FALL)) {
                if (NO_DAMAGE.contains(event.getEntity().getName())) {
                    NO_DAMAGE.remove(event.getEntity().getName());
                    event.setCancelled(true);
                }
            }
        }
    }

}
