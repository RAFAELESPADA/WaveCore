package dev.ojvzinn.pvp.lobby.leaderboards;

import dev.ojvzinn.pvp.Language;
import dev.ojvzinn.pvp.lobby.Leaderboard;
import dev.slickcollections.kiwizin.database.Database;
import org.bukkit.Location;

import java.util.Collections;
import java.util.List;

public class LeaguesLeaderboard extends Leaderboard {

  public LeaguesLeaderboard(Location location, String id) {
    super(location, id);
  }
  
  @Override
  public String getType() {
    return "ligas";
  }
  
  @Override
  public List<String[]> getSplitted() {
    List<String[]> list = Database.getInstance().getLeaderBoard("kCoreKitPvP", true, Collections.singletonList("leagues").toArray(new String[0]));
    
    while (list.size() < 10) {
      list.add(new String[]{Language.lobby$leaderboard$empty, "0"});
    }
    return list;
  }
  
  @Override
  public List<String> getHologramLines() {
    return Language.lobby$leaderboard$ranking$hologram;
  }
}