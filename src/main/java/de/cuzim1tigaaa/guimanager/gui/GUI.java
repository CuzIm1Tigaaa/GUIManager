package de.cuzim1tigaaa.guimanager.gui;

import de.cuzim1tigaaa.guimanager.GUIItem;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
public abstract class GUI {

    @Getter
    private static final Map<UUID, GUI> pagination = new HashMap<>();

    protected int size;
    protected int page;
    protected GUIItem guiItem;

    public GUI(int size, GUIItem guiItem, int page) {
        if(size % 9 != 0)
            throw new IllegalArgumentException("Size must be a multiple of 9");

        this.size = size;
        this.guiItem = guiItem;
        this.page = page;
    }

    public abstract void open(Player player);
    public abstract void switchPage(int newPage);
    public abstract void setItems();
}
