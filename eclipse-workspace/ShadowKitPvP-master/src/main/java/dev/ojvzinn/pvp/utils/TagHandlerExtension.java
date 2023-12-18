package dev.ojvzinn.pvp.utils;

import dev.ojvzinn.pvp.utils.tagger.TagUtils;
import dev.slickcollections.kiwizin.player.role.Role;
import dev.slickcollections.kiwizin.player.tag.UpdateTagHandler;
import org.bukkit.entity.Player;

public class TagHandlerExtension extends UpdateTagHandler {

    public static void setupHandler() {
        new TagHandlerExtension();
    }

    @Override
    public void updateTag(Player player, Role role) {
        TagUtils.setTag(player, role);
    }
}
