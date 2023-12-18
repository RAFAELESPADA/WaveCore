package dev.ojvzinn.pvp.api;

import dev.ojvzinn.pvp.cosmetics.collections.Kit;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class DelayKits {

    private static final Map<String, DelayKits> CACHE = new HashMap<>();
    private static final DecimalFormat DF = new DecimalFormat("#.#");

    public static void resetDelay(Player player) {
        CACHE.remove(player.getName());
    }

    public static void createDelayProfile(Player player, Kit kit) {
        CACHE.put(player.getName(), new DelayKits(kit));
    }

    public static DelayKits loadDelayProfiles(Player player) {
        if (!CACHE.containsKey(player.getName())) {
            return null;
        }

        return CACHE.get(player.getName());
    }

    private final Kit kit;
    private Long start;
    private boolean canSendMessage;

    public DelayKits(Kit kit) {
        this.kit = kit;
        this.start = 0L;
        this.canSendMessage = true;
    }

    public void addDelay(int delayTime) {
        this.start = System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(delayTime);
    }

    public void addDelayMilli(int delayTime) {
        this.start = System.currentTimeMillis() + TimeUnit.MILLISECONDS.toMillis(delayTime);
    }

    public boolean canUse() {
        return start == 0 || ((double) (start - System.currentTimeMillis()) / 1000) < 0.1;
    }

    public Kit getKit() {
        return this.kit;
    }

    public double getDelayTime() {
        return ((double) (start - System.currentTimeMillis()) / 1000);
    }

    public String getDelayTime(boolean formated) {
        return DF.format(((double) (start - System.currentTimeMillis()) / 1000));
    }

    public boolean canSendMessage() {
        return canSendMessage;
    }

    public void setCanSendMessage(boolean canSendMessage) {
        this.canSendMessage = canSendMessage;
    }
}
