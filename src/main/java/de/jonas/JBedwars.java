package de.jonas;

import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Die Haupt- und Main-Klasse des Plugins. In dieser Klasse wird das gesamte Plugin initialisiert und beim Server
 * registriert. Diese Klasse wird vom Server zur Initialisierung des Plugins aufgerufen, da diese vom {@link JavaPlugin}
 * abgeleitet wird.
 */
public final class JBedwars extends JavaPlugin {

    //<editor-fold desc="STATIC FIELDS">
    /** Der Prefix des {@link JBedwars Plugins}. */
    @Getter
    private static String prefix;
    /** Die Instanz-Variable, womit man auf das {@link JBedwars Plugin} zugreifen kann. */
    @Getter
    private static JBedwars instance;
    //</editor-fold>

    @Override
    public void onEnable() {
        super.onEnable();

        // declare plugin instance
        instance = this;

        // declare plugin prefix
        prefix = getLoadedPrefix();
    }

    /**
     * Kalkuliert den Prefix des Plugins.
     *
     * @return Gibt den prefix des Plugins zurück.
     */
    private String getLoadedPrefix() {
        return ChatColor.DARK_GRAY.toString() + ChatColor.BOLD + "["
            + ChatColor.RED + "JBedwars"
            + ChatColor.DARK_GRAY.toString() + ChatColor.BOLD + "]";
    }
}
