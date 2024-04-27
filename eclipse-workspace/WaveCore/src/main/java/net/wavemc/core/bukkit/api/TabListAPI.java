package net.wavemc.core.bukkit.api;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerListHeaderFooter;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;

public class TabListAPI {

    public static void send(Player player, String header, String footer) {
        try {
            PacketPlayOutPlayerListHeaderFooter packet = new PacketPlayOutPlayerListHeaderFooter();
            IChatBaseComponent tabHeader = IChatBaseComponent.ChatSerializer.a("{'text': '" + header + "'}");
            IChatBaseComponent tabFooter = IChatBaseComponent.ChatSerializer.a("{'text': '" + footer + "'}");

            Field a = packet.getClass().getDeclaredField("a");
            a.setAccessible(true);
            a.set(packet, tabHeader);

            Field b = packet.getClass().getDeclaredField("b");
            b.setAccessible(true);
            b.set(packet, tabFooter);

            ((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);
        }catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
