package de.jonas.jbedwars.task;

import de.jonas.JBedwars;
import de.jonas.jbedwars.Game;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.time.Instant;

import static net.md_5.bungee.api.ChatMessageType.ACTION_BAR;
import static net.md_5.bungee.api.ChatMessageType.SYSTEM;
import static net.md_5.bungee.api.chat.ComponentBuilder.FormatRetention.NONE;

public final class GameTask extends BukkitRunnable {

    @Override
    public void run() {
        // get current game
        final Game game = JBedwars.getInstance().getGame();

        // check current game state
        switch (game.getGameType()) {
            case WAITING:
                final long untilStart;

                // check if teams are full (if game can start)
                if (!(game.getTeamRed().isFull() && game.getTeamBlue().isFull())) {
                    // set waiting time without calculating on default
                    untilStart = Game.WAITING_TIME_IN_SECONDS;
                } else {
                    // calculate time
                    untilStart = Game.WAITING_TIME_IN_SECONDS - Duration
                        .between(game.getBasicStartMoment(), Instant.now())
                        .toSeconds();
                }

                // create waiting time message
                final ComponentBuilder waitingTime = new ComponentBuilder()
                    .append(
                        "Das Spiel startet in ",
                        NONE
                    ).color(ChatColor.GRAY)
                    .append(
                        String.valueOf(untilStart),
                        NONE
                    ).color(ChatColor.GREEN).bold(true)
                    .append(
                        " Sekunden",
                        NONE
                    ).color(ChatColor.GRAY);

                for (@NotNull final Player player : game.getPlayers()) {
                    // check if waiting time is expired
                    if (untilStart <= 0) {
                        // start game
                        game.startGame();

                        return;
                    }

                    // send waiting time message
                    player.spigot().sendMessage(
                        ACTION_BAR,
                        waitingTime.create()
                    );
                }
                break;

            case GAME:
                // get remaining time in minutes
                final long remainingTime = Game.GAME_TIME_IN_MINUTES - Duration
                    .between(game.getGameStartMoment(), Instant.now())
                    .toMinutes();

                // create game time message
                final ComponentBuilder gameTime = new ComponentBuilder()
                    .append(
                        "Das Spiel läuft noch ",
                        NONE
                    ).color(ChatColor.GRAY)
                    .append(
                        String.valueOf(remainingTime),
                        NONE
                    ).color(ChatColor.GREEN).bold(true)
                    .append(
                        " Sekunden",
                        NONE
                    ).color(ChatColor.GRAY);

                for (@NotNull final Player player : game.getPlayers()) {
                    // check if game time is expired
                    if (remainingTime <= 0) {
                        // stop the game
                        game.stopGame();

                        return;
                    }

                    // send game time message
                    player.spigot().sendMessage(
                        SYSTEM,
                        gameTime.create()
                    );
                }
                break;

            case POST_GAME:
                break;

            default:
                break;
        }
    }
}
