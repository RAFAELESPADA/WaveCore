package dev.ojvzinn.pvp.lobby;

import dev.ojvzinn.pvp.Language;
import dev.ojvzinn.pvp.Main;
import dev.ojvzinn.pvp.game.KitPvP;
import dev.ojvzinn.pvp.game.enums.WarpEnum;
import dev.ojvzinn.pvp.lobby.trait.NPCSkinTrait;
import dev.slickcollections.kiwizin.libraries.holograms.HologramLibrary;
import dev.slickcollections.kiwizin.libraries.holograms.api.Hologram;
import dev.slickcollections.kiwizin.libraries.npclib.NPCLibrary;
import dev.slickcollections.kiwizin.libraries.npclib.api.npc.NPC;
import dev.slickcollections.kiwizin.plugin.config.KConfig;
import dev.slickcollections.kiwizin.utils.BukkitUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class FPSNPC {

  private static final KConfig CONFIG = Main.getInstance().getConfig("npcs");
  private static final List<FPSNPC> NPCS = new ArrayList<>();
  private String id;
  private Location location;
  private NPC npc;
  private Hologram hologram;

  public FPSNPC(Location location, String id) {
    this.location = location;
    this.id = id;
    if (!this.location.getChunk().isLoaded()) {
      this.location.getChunk().load(true);
    }
    
    this.spawn();
  }
  
  public static void setupNPCs() {
    if (!CONFIG.contains("fps")) {
      CONFIG.set("fps", new ArrayList<>());
    }

    for (String serialized : CONFIG.getStringList("fps")) {
      if (serialized.split("; ").length > 6) {
        String id = serialized.split("; ")[6];
        
        NPCS.add(new FPSNPC(BukkitUtils.deserializeLocation(serialized), id));
      }
    }
    
    Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), () -> listNPCs().forEach(FPSNPC::update), 20, 20);
  }
  
  public static void add(String id, Location location) {
    NPCS.add(new FPSNPC(location, id));
    List<String> list = CONFIG.getStringList("fps");
    list.add(BukkitUtils.serializeLocation(location) + "; " + id);
    CONFIG.set("fps", list);
  }
  
  public static void remove(FPSNPC npc) {
    NPCS.remove(npc);
    List<String> list = CONFIG.getStringList("fps");
    list.remove(BukkitUtils.serializeLocation(npc.getLocation()) + "; " + npc.getId());
    CONFIG.set("fps", list);
    
    npc.destroy();
  }
  
  public static FPSNPC getById(String id) {
    return NPCS.stream().filter(npc -> npc.getId().equals(id)).findFirst().orElse(null);
  }

  public static Collection<FPSNPC> listNPCs() {
    return NPCS;
  }
  
  public void spawn() {
    if (this.npc != null) {
      this.npc.destroy();
      this.npc = null;
    }
    
    if (this.hologram != null) {
      HologramLibrary.removeHologram(this.hologram);
      this.hologram = null;
    }
    
    this.hologram = HologramLibrary.createHologram(this.location.clone().add(0, 0.5, 0));
    for (int index = Language.lobby$npc$fps$hologram.size(); index > 0; index--) {
      this.hologram.withLine(Language.lobby$npc$fps$hologram.get(index - 1));
    }
    
    this.npc = NPCLibrary.createNPC(EntityType.PLAYER, "§8[NPC] ");
    this.npc.data().set("fps-npc", true);
    this.npc.data().set(NPC.HIDE_BY_TEAMS_KEY, true);
    this.npc.addTrait(new NPCSkinTrait(this.npc, Language.lobby$npc$fps$skin$value, Language.lobby$npc$fps$skin$signature));
    this.npc.spawn(this.location);
  }
  
  public void update() {
    int size = Language.lobby$npc$fps$hologram.size();
    for (int index = size; index > 0; index--) {
      this.hologram.updateLine(size - (index - 1), Language.lobby$npc$fps$hologram.get(index - 1).replace("{online}", String.valueOf(KitPvP.findWarp(WarpEnum.FPS).listPlayers().size())));
    }
  }
  
  public void destroy() {
    this.id = null;
    this.location = null;
    
    this.npc.destroy();
    this.npc = null;
    HologramLibrary.removeHologram(this.hologram);
    this.hologram = null;
  }
  
  public String getId() {
    return id;
  }
  
  public Location getLocation() {
    return this.location;
  }

  public NPC getNpc() {
    return this.npc;
  }
}