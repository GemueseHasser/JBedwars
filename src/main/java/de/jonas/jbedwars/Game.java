package de.jonas.jbedwars;

import de.jonas.JBedwars;
import de.jonas.jbedwars.constant.GameType;
import de.jonas.jbedwars.object.Team;
import de.jonas.jbedwars.task.GameTask;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static de.jonas.jbedwars.constant.GameType.GAME;
import static de.jonas.jbedwars.constant.GameType.POST_GAME;
import static de.jonas.jbedwars.constant.GameType.WAITING;

public final class Game {

    //<editor-fold desc="CONSTANTS">
    /** Die Zeit in Sekunden, die gewartet werden soll, bis das Spiel beginnt. */
    public static final int WAITING_TIME_IN_SECONDS = 20;
    /** Die Zeit in Minuten, die ein Spiel maximal andauern darf. */
    public static final int GAME_TIME_IN_SECONDS = 15 * 60;
    /** Die Anzahl an Ticks, die eine Sekunde beinhaltet. */
    private static final int TICKS_PER_SECOND = 20;
    /** Die Anzahl an Spielern, die insgesamt mitspielen dürfen. */
    private static final int MAX_OVERALL_PLAYERS = 2;
    //</editor-fold>


    //<editor-fold desc="LOCAL FIELDS">
    /** Der Status, den das Spiel hat. */
    @Getter
    @Setter
    private GameType gameType;
    /** Das rote Team. */
    @Getter
    @NotNull
    private final Team teamRed = new Team(MAX_OVERALL_PLAYERS / 2);
    /** Das blaue Team. */
    @Getter
    @NotNull
    private final Team teamBlue = new Team(MAX_OVERALL_PLAYERS / 2);
    /** Alle mitspielenden Spieler. */
    @Getter
    private final List<Player> players = new ArrayList<>();
    /** Der Moment, zu dem das Spiel startet. */
    @Getter
    private Instant gameStartMoment;
    /** Der Moment, zu dem das Spiel überhaupt aktiv wird, also sobald der erste Spieler dem Spiel beitritt. */
    @Getter
    @Setter
    private Instant basicStartMoment;
    /** Der {@link GameTask}, welcher dann läuft, sobald ein Spieler dem Spiel beigetreten ist. */
    private GameTask gameTask;
    /** Die BossBar, die jedem mitspielenden Spieler angezeigt wird und diesem die Zeit anzeigt. */
    @Getter
    @Setter
    private BossBar timeBar;
    //</editor-fold>


    /**
     * Erzeugt eine neue und vollständig unabhängige Instanz eines {@link Game Spiels}. Das {@link Game Spiel} ist das
     * Basis-Konstrukt des gesamten {@link JBedwars Plugins} bzw. dessen Bedwars-Spiels.
     */
    public Game() {
        this.gameType = WAITING;
    }


    /**
     * Lässt einen bestimmten Spieler dem {@link Game Spiel} beitreten.
     *
     * @param player Der Spieler, der dem {@link Game Spiel} beitritt.
     */
    public void join(@NotNull final Player player) {
        if (this.players.isEmpty()) {
            // run game task every second
            this.gameTask = new GameTask();
            this.gameTask.runTaskTimer(
                JBedwars.getInstance(),
                0,
                TICKS_PER_SECOND
            );
        }

        this.players.add(player);
    }

    /**
     * Lässt einen bestimmten Spieler das {@link Game Spiel} verlassen.
     *
     * @param player Der Spieler, der das {@link Game Spiel} verlässt.
     */
    public void leave(@NotNull final Player player) {
        this.players.remove(player);

        if (this.players.isEmpty()) {
            this.gameTask.cancel();
        }
    }

    /**
     * Startet das Spiel, also es wird alles nötige initialisiert.
     */
    public void startGame() {
        // set game start moment to now
        this.gameStartMoment = Instant.now();

        // set game type to game
        this.setGameType(GAME);

        this.timeBar = Bukkit.createBossBar(
            getTimeBarTitle(),
            BarColor.PURPLE,
            BarStyle.SEGMENTED_6,
            BarFlag.CREATE_FOG
        );

        // initialize game for each player
        for (@NotNull final Player player : players) {
            this.timeBar.addPlayer(player);

            if (this.teamRed.getMates().contains(player) || this.teamBlue.getMates().contains(player)) {
                continue;
            }

            if (this.teamRed.isFull()) {
                this.teamBlue.getMates().add(player);
                continue;
            }

            this.teamRed.getMates().add(player);
        }
    }

    /**
     * Stoppt das Spiel und versetzt das Spiel in den Modus {@code POST_GAME}.
     */
    public void stopGame() {
        // set game type to post game
        this.setGameType(POST_GAME);

        // unset time bar
        this.timeBar.removeAll();
    }

    /**
     * Gibt an, ob das Spiel 'voll' ist, also ob sich genug Spieler im Spiel befinden, damit das Spiel starten kann.
     *
     * @return {@code true}, wenn sich genug Spieler im Spiel befinden, ansonsten {@code false}.
     */
    public boolean isFull() {
        return this.players.size() >= MAX_OVERALL_PLAYERS;
    }

    /**
     * Berechnet den Titel mit der aktuell verbleibenden Zeit für die {@link BossBar Time-Bar}.
     *
     * @return Der Titel mit der aktuell verbleibenden Zeit für die {@link BossBar Time-Bar}.
     */
    @SuppressWarnings("checkstyle:MagicNumber")
    public String getTimeBarTitle() {
        return "Verbleibende Zeit: " + ((Game.GAME_TIME_IN_SECONDS - Duration
            .between(gameStartMoment, Instant.now())
            .toSeconds()) / 60D);
    }

    /**
     * Berechnet den aktuellen Fortschritt der {@link BossBar Time-Bar}.
     *
     * @return Der aktuelle Fortschritt der {@link BossBar Time-Bar}.
     */
    public double getTimeBarProgress() {
        return ((double) (1 - (Duration
            .between(gameStartMoment, Instant.now())
            .toSeconds() / GAME_TIME_IN_SECONDS)));
    }

}
