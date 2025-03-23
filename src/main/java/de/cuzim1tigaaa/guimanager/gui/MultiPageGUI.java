package de.cuzim1tigaaa.guimanager.gui;

import de.cuzim1tigaaa.guimanager.GuiItem;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.List;

@Getter
@Setter
public abstract class MultiPageGUI<T> extends GUI {

	protected List<T> items;
	protected int entriesPerPage;

	public MultiPageGUI(int size, int page, int entriesPerPage) {
		super(size, page);
		this.entriesPerPage = entriesPerPage;
	}
}