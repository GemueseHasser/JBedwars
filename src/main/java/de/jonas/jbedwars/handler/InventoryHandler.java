package de.jonas.jbedwars.handler;

import de.jonas.jbedwars.object.Item;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

public final class InventoryHandler {

    //<editor-fold desc="CONSTANTS">

    //<editor-fold desc="waiting">
    /** Das {@link Item}, mit dem man sein Team wählen kann. */
    private static final Item TEAMS = new Item(Material.SANDSTONE, "Team wählen", "team_block");
    //</editor-fold>

    //</editor-fold>


    /**
     * Setzt das Inventar für einen bestimmten Spieler während der Warte-Phase.
     *
     * @param player Der Spieler, dessen Inventar gesetzt wird.
     */
    @SuppressWarnings("checkstyle:MagicNumber")
    public static void setWaitingInventory(@NotNull final Player player) {
        final Inventory inventory = player.getInventory();

        inventory.clear();
        inventory.setItem(4, TEAMS);
    }

}
