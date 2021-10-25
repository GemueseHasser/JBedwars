package de.jonas.jbedwars.task;

import de.jonas.JBedwars;
import de.jonas.jbedwars.Game;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.time.Instant;

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

                if (!(game.getTeamRed().isFull() && game.getTeamBlue().isFull())) {
                    untilStart = Game.WAITING_TIME_IN_SECONDS;
                } else {
                    untilStart = Game.WAITING_TIME_IN_SECONDS - Duration
                        .between(game.getBasicStartMoment(), Instant.now())
                        .toSeconds();
                }

                final ComponentBuilder builder = new ComponentBuilder()
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
                    // check if waiting time is completed
                    if (untilStart <= 0) {
                        // start game
                        game.startGame();

                        return;
                    }

                    player.spigot().sendMessage(
                        ChatMessageType.ACTION_BAR,
                        builder.create()
                    );
                }
                break;

            case GAME:
                // get remaining time in minutes
                final long remainingTime = Game.GAME_TIME_IN_MINUTES - Duration
                    .between(game.getGameStartMoment(), Instant.now())
                    .toMinutes();
                break;

            case POST_GAME:
                break;

            default:
                break;
        }
    }
}
