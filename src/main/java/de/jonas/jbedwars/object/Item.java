package de.jonas.jbedwars.object;

import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Ein {@link Item} ist ein modifizierter {@link ItemStack}, welcher als Zusatz noch einen {@code key} hat, womit man
 * den {@link ItemStack} leichter identifizieren kann.
 */
@NotNull
public final class Item extends ItemStack {

    /** Der Anzeige-Name des Items. */
    @NotNull
    private final String name;
    /** Der Key des Items, welches die Identifizierung vereinfachen soll. */
    @Getter
    @Nullable
    private String key;


    //<editor-fold desc="CONSTRUCTORS">

    /**
     * Erzeugt einen neuen {@link ItemStack} ohne weitere modifizierung aus einem {@link Material} und einem Namen.
     *
     * @param material Das {@link Material}, asu dem der {@link ItemStack} bestehen soll.
     * @param name     Der Anzeige-Name, den der Item-Stack haben soll.
     */
    public Item(
        @NotNull final Material material,
        @NotNull final String name
    ) {
        super(material);

        this.name = name;
        setMeta();
    }


    /**
     * Erzeugt einen neuen {@link ItemStack}, welcher als weitere modifizierung mit einem Key ausgestattet wird, womit
     * er dann leichter identifiziert werden kann.
     *
     * @param material Das {@link Material}, asu dem der {@link ItemStack} bestehen soll.
     * @param name     Der Anzeige-Name, den der Item-Stack haben soll.
     * @param key      Der Key, der diesem {@link Item} hinzugefügt werden soll.
     */
    public Item(
        @NotNull final Material material,
        @NotNull final String name,
        @NotNull final String key
    ) {
        super(material);

        this.name = name;
        this.key = key;
        setMeta();
    }
    //</editor-fold>


    /**
     * Setzt die {@link ItemMeta Meta} des {@link Item Items}, damit der Anzeige-Name oder ähnliches realisiert werden
     * kann.
     */
    private void setMeta() {
        final ItemMeta meta = this.getItemMeta();

        assert meta != null;
        meta.setDisplayName(this.name);

        this.setItemMeta(meta);
    }

}
