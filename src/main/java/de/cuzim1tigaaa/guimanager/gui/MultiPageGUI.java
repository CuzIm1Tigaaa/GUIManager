package de.cuzim1tigaaa.guimanager.gui;

import de.cuzim1tigaaa.guimanager.GUIItem;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

import java.util.List;

@Getter
@Setter
public abstract class MultiPageGUI<T> extends GUI {

    protected List<T> items;

    public MultiPageGUI(int size, GUIItem guiItem, int page) {
        super(size, guiItem, page);
    }
}
