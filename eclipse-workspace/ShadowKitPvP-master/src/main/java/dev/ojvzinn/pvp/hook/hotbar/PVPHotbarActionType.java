package dev.ojvzinn.pvp.hook.hotbar;

import dev.ojvzinn.pvp.menu.MenuKits;
import dev.ojvzinn.pvp.menu.MenuLobbies;
import dev.slickcollections.kiwizin.Core;
import dev.slickcollections.kiwizin.player.Profile;
import dev.slickcollections.kiwizin.player.hotbar.HotbarActionType;

public class PVPHotbarActionType extends HotbarActionType {
  
  @Override
  public void execute(Profile profile, String action) {
    switch (action.toLowerCase()) {
      case "kits": {
        new MenuKits(profile.getPlayer());
        break;
      }

      case "lobbies": {
        new MenuLobbies(profile);
        break;
      }

      case "lobby": {
        Core.sendServer(profile, "lobby");
        break;
      }
    }
  }
}
