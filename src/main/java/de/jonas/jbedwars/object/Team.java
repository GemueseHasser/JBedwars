package de.jonas.jbedwars.object;

import de.jonas.jbedwars.Game;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import java.util.ArrayList;
import java.util.List;

/**
 * Ein {@link Team}, stellt ein Team in einem {@link Game Bedwars-Spiel} dar, wovon es mehrere in einem Spiel geben
 * kann. Ein Team kann eine beliebige Größe haben und demnach auch beliebig viele Spieler beinhalten. Auch in demselben
 * Spiel können verschiedene Teams verschiedene Größen haben.
 */
@Getter
@NotNull
@RequiredArgsConstructor
public final class Team {

    //<editor-fold desc="LOCAL FIELDS">
    /** Die Größe des Teams (Die Anzahl an Spielern, die in dieses Team passen). */
    @Range(from = 0, to = Integer.MAX_VALUE)
    private final int size;
    /** Die {@link ChatColor Farbe}, die das Team haben soll. */
    @NotNull
    private final ChatColor teamColor;
    /** Der Anzeige-Name des Teams. */
    @NotNull
    private final String name;
    /** Die Liste, die alle Spieler beinhaltet, die sich in diesem Team befinden. */
    @NotNull
    private final List<Player> mates = new ArrayList<>();
    //</editor-fold>

    /**
     * Prüft, ob dieses Team voll ist, oder ob noch weitere Spieler in dieses Team passen würden.
     *
     * @return Wenn das Team voll ist, also die Anzahl an Spielern, welche sich in diesem Team befinden, die Größe des
     *     Teams ({@code size}) erreicht hat, {@code true}, ansonsten (zufalls noch Spieler in das Team passen) {@code
     *     false}.
     */
    public boolean isFull() {
        return mates.size() >= size;
    }
}
