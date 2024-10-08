package net.wavemc.core.bukkit.listener;


import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import net.wavemc.core.bukkit.WaveBukkit;
import net.wavemc.core.bukkit.account.WavePlayer;
import org.bukkit.entity.Player;


/**
 * This class will be registered through the register-method in the
 * plugins onEnable-method.
 */
public class PlaceHolderAPIHook extends PlaceholderExpansion {

    /*
    /*    */   static WaveBukkit plugin;

    /**
     * Since we register the expansion inside our own plugin, we
     * can simply use this method here to get an instance of our
     * plugin.
     *
     *        The instance of our plugin.
     */
    /*    */   public PlaceHolderAPIHook(WaveBukkit main)
    /*    */   {
        /* 20 */     this.plugin = main;
        /* 21 */     plugin = main;
        /*    */   }
    /*    */

    /**
     * Because this is an internal class,
     * you must override this method to let PlaceholderAPI know to not unregister your expansion class when
     * PlaceholderAPI is reloaded
     *
     * @return true to persist through reloads
     */
    @Override
    public boolean persist(){
        return true;
    }

    /**
     * Because this is a internal class, this check is not needed
     * and we can simply return {@code true}
     *
     * @return Always true since it's an internal class.
     */
    @Override
    public boolean canRegister(){
        return true;
    }

    /**
     * The name of the person who created this expansion should go here.
     * <br>For convienience do we return the author from the plugin.yml
     *
     * @return The name of the author as a String.
     */
    @Override
    public String getAuthor(){
        return plugin.getDescription().getAuthors().toString();
    }

    /**
     * The placeholder identifier should go here.
     * <br>This is what tells PlaceholderAPI to call our onRequest
     * method to obtain a value if a placeholder starts with our
     * identifier.
     * <br>This must be unique and can not contain % or _
     *
     * @return The identifier in {@code %<identifier>_<value>%} as String.
     */
    @Override
    public String getIdentifier(){
        return "kpcore";
    }

    /**
     * This is the version of the expansion.
     * <br>You don't have to use numbers, since it is set as a String.
     *
     * For convienience do we return the version from the plugin.yml
     *
     * @return The version as a String.
     */
    @Override
    public String getVersion(){
        return plugin.getDescription().getVersion();
    }

    /**
     * This is the method called when a placeholder with our identifier
     * is found and needs a value.
     * <br>We specify the value identifier in this method.
     * <br>Since version 2.9.1 can you use OfflinePlayers in your requests.
     *
     *         A String containing the identifier/value.
     *
     * @return possibly-null String of the requested identifier.
     */


    public String onPlaceholderRequest(Player p, String indentifier) {
        WavePlayer wavePlayer = WaveBukkit.getInstance().getPlayerManager()
                .getPlayer(p.getName());
        if (wavePlayer == null) {
            return "";
        }
        if (indentifier.equals("player_uniqueid")) {
            return wavePlayer.getUuid().toString();
        }
        return null;
    }
}



