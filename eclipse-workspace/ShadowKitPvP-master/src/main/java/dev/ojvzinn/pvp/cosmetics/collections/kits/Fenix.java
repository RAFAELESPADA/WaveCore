package dev.ojvzinn.pvp.cosmetics.collections.kits;

import dev.ojvzinn.pvp.Main;
import dev.ojvzinn.pvp.api.DelayKits;
import dev.ojvzinn.pvp.api.PlayerArenaDeathEvent;
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
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Fenix extends Kit {

    private List<String> ACTIVED_KIT = new ArrayList<>();
    private Map<String, Integer> KILL_SEQUENCE = new HashMap<>();
    private List<String> BYPASS = new ArrayList<>();

    public Fenix(String key, YamlConfiguration CONFIG) {
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
        inventory.setItem(8, BukkitUtils.deserializeItemStack("COMPASS : 1 : nome>&aBússola"));
        inventory.setItem(15, new ItemStack(Material.RED_MUSHROOM, 16));
        inventory.setItem(14, new ItemStack(Material.BROWN_MUSHROOM, 16));
        inventory.setItem(13, new ItemStack(Material.BOWL, 16));

        for (int i = 0; i < inventory.getSize(); i++) {
            if (inventory.getItem(i) == null || inventory.getItem(i).getType() == Material.AIR) {
                inventory.setItem(i, new ItemStack(Material.MUSHROOM_SOUP));
            }
        }

        if (!BYPASS.contains(player.getName())) {
            addProtection(player.getName());
            KILL_SEQUENCE.put(player.getName(), 0);
            player.sendMessage("§aVocê equipou o kit Fênix.");
        } else {
            BYPASS.remove(player.getName());
        }
    }

    @Override
    public void setupListeners() {
        Bukkit.getPluginManager().registerEvents(this, Main.getInstance());
    }

    @EventHandler
    public void onPlayerDeathArena(PlayerArenaDeathEvent event) {
        Profile profile = event.getProfile();
        Player player = profile.getPlayer();
        if (event.getKiller() != null) {
            Profile killer = event.getKiller();
            Player killerP = killer.getPlayer();
            if (isSelected(profile)) {
                ACTIVED_KIT.remove(player.getName());
                KILL_SEQUENCE.remove(player.getName());
            }
            if (event.getKiller().getPlayer().getWorld().equals(Bukkit.getWorld("1v1"))) {
                return;
            }
            if (isSelected(killer)) {
                if (KILL_SEQUENCE.containsKey(killerP.getName())) {
                    KILL_SEQUENCE.replace(killerP.getName(), KILL_SEQUENCE.get(killerP.getName()) + 1);
                    if (KILL_SEQUENCE.get(killerP.getName()) == 3) {
                        killerP.sendMessage("§e[Fênix] §fVocê ativou sua habilidade com sucesso!");
                        ACTIVED_KIT.add(killerP.getName());
                        KILL_SEQUENCE.remove(killerP.getName());
                    } else if (KILL_SEQUENCE.get(killerP.getName()) < 3) {
                        killerP.sendMessage("§e[Fênix] §fVocê ainda precisa matar " + (3 - KILL_SEQUENCE.get(killerP.getName())) + " jogadores para ativar sua habilidade.");
                    }
                }
            }
        }
    }

    public boolean canRevive(Player player) {
        return ACTIVED_KIT.contains(player.getName());
    }

    public void revivePlayer(Player player) {
        player.setHealth(player.getMaxHealth());
        player.sendMessage("§e[Fênix] §fVocê ressurgiu da cinzas!");
        ACTIVED_KIT.remove(player.getName());
        BYPASS.add(player.getName());
        for (Player online : Bukkit.getOnlinePlayers().stream().filter(player1 -> !player1.equals(player)).collect(Collectors.toList())) {
            online.sendMessage("§e[Fênix] §fO jogador " + player.getName() + " acaba de ressurgir da cinzas!");
        }
    }
}
