package de.cuzim1tigaaa.guimanager.gui;

import de.cuzim1tigaaa.guimanager.GUIItem;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class SinglePageGUI<T> extends GUI {

    protected T object;

    public SinglePageGUI(int size, GUIItem guiItem, int page) {
        super(size, guiItem, page);
    }
}
