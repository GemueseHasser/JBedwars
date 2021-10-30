package de.jonas;

import de.jonas.jbedwars.Game;
import de.jonas.jbedwars.object.Team;
import lombok.Getter;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import static net.md_5.bungee.api.chat.ComponentBuilder.FormatRetention.NONE;

/**
 * Die Haupt- und Main-Klasse des Plugins. In dieser Klasse wird das gesamte Plugin initialisiert und beim Server
 * registriert. Diese Klasse wird vom Server zur Initialisierung des Plugins aufgerufen, da diese vom {@link JavaPlugin}
 * abgeleitet wird.
 */
public final class JBedwars extends JavaPlugin implements Listener {

    //<editor-fold desc="STATIC FIELDS">
    /** Der Prefix des {@link JBedwars Plugins}. */
    @Getter
    private static BaseComponent[] prefix;
    /** Die Instanz-Variable, womit man auf das {@link JBedwars Plugin} zugreifen kann. */
    @Getter
    private static JBedwars instance;
    //</editor-fold>

    //<editor-fold desc="LOCAL FIELDS">
    /** Das {@link Game Spiel} bzw. dessen Basis-Konstrukt. */
    @Getter
    private Game game;
    //</editor-fold>


    @Override
    public void onEnable() {
        super.onEnable();

        // declare plugin instance
        instance = this;

        // declare plugin prefix
        prefix = getLoadedPrefix();

        // create game
        this.game = new Game(
            new Team[] {
                new Team(1),
                new Team(1),
            }
        );

        Bukkit.getPluginManager().registerEvents(this, this);
    }

    /**
     * Kalkuliert den Prefix des Plugins.
     *
     * @return Gibt den prefix des Plugins zurück.
     */
    private BaseComponent[] getLoadedPrefix() {
        return new ComponentBuilder()
            .append(
                "[",
                NONE
            ).color(ChatColor.DARK_GRAY).bold(true)
            .append(
                "JBedwars",
                NONE
            ).color(ChatColor.RED)
            .append(
                "]",
                NONE
            ).color(ChatColor.DARK_GRAY).bold(true)
            .create();
    }

    @EventHandler
    public void onJoin(@NotNull final PlayerJoinEvent e) {
        game.join(e.getPlayer());
    }
}
