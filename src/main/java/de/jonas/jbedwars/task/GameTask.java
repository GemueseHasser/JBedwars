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
import static net.md_5.bungee.api.chat.ComponentBuilder.FormatRetention.NONE;

public final class GameTask extends BukkitRunnable {

    @Override
    public void run() {
        // get current game
        final Game game = JBedwars.getInstance().getGame();

        // check current game state
        switch (game.getGameType()) {
            case WAITING:
                final ComponentBuilder message;

                final long untilStartTime;

                // check if teams are full (if game can start)
                if (!game.isFull()) {
                    // set time to default
                    untilStartTime = Game.WAITING_TIME_IN_SECONDS;

                    // create waiting message
                    message = new ComponentBuilder()
                        .append(
                            "Warten auf Spieler..."
                        ).color(ChatColor.GRAY);
                } else {
                    if (game.getBasicStartMoment() == null) {
                        game.setBasicStartMoment(Instant.now());
                    }

                    // calculate time
                    untilStartTime = Game.WAITING_TIME_IN_SECONDS - Duration
                        .between(game.getBasicStartMoment(), Instant.now())
                        .toSeconds();

                    // create waiting time message
                    message = new ComponentBuilder()
                        .append(
                            "Das Spiel startet in ",
                            NONE
                        ).color(ChatColor.GRAY)
                        .append(
                            String.valueOf(untilStartTime),
                            NONE
                        ).color(ChatColor.GREEN).bold(true)
                        .append(
                            " Sekunden",
                            NONE
                        ).color(ChatColor.GRAY);
                }


                // check if waiting time is expired
                if (untilStartTime <= 0) {
                    // start game
                    game.startGame();

                    return;
                }

                for (@NotNull final Player player : game.getPlayers()) {
                    // send waiting time message
                    player.spigot().sendMessage(
                        ACTION_BAR,
                        message.create()
                    );
                }
                break;

            case GAME:
                // get remaining time in minutes
                final long remainingTime = Game.GAME_TIME_IN_SECONDS - Duration
                    .between(game.getGameStartMoment(), Instant.now())
                    .toSeconds();

                // update time bar
                game.getTimeBar().setTitle(game.getTimeBarTitle());
                game.getTimeBar().setProgress(game.getTimeBarProgress());


                // check if game time is expired
                if (remainingTime <= 0) {
                    // stop the game
                    game.stopGame();

                    return;
                }

                for (@NotNull final Player player : game.getPlayers()) {
                    // update scoreboard
                }
                break;

            case POST_GAME:

                break;

            default:
                break;
        }
    }
}
