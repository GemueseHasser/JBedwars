package de.jonas.jbedwars.object;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public final class Team {
    @Getter
    private final int size;
    @Getter
    private final List<Player> mates = new ArrayList<>();

    public boolean isFull() {
        return mates.size() >= size;
    }
}
