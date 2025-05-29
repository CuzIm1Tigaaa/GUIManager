package de.cuzim1tigaaa.guimanager.gui;

import de.cuzim1tigaaa.guimanager.GuiItem;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.*;

@Getter
public abstract class GUI {

	@Getter
	private static final Map<UUID, GUI> pagination = new HashMap<>();

	protected int size;
	@Setter
	protected int page;
	protected GuiItem guiItem;

	public GUI(int size, int page) {
		if(size % 9 != 0)
			throw new IllegalArgumentException("Size must be a multiple of 9");

		this.size = size;
		this.page = page;
	}

	public abstract Inventory getInventory();

	protected final Inventory getInventory(Player player) {
		if(pagination.containsKey(player.getUniqueId())) {
			GUI gui = pagination.get(player.getUniqueId());
			if(gui.equals(this))
				return gui.getGuiItem().inventory();
			return gui.getInventory();
		}
		return this.getInventory();
	}

	protected final boolean alreadyOpen(Player player) {
		if(!pagination.containsKey(player.getUniqueId()))
			return false;

		GUI gui = pagination.get(player.getUniqueId());
		return gui.equals(this);
	}

	public void open(Player player) {
		Inventory inventory = getInventory(player);
		if(!alreadyOpen(player))
			player.openInventory(inventory);

		this.guiItem = new GuiItem(inventory, page, this);
		GUI.getPagination().put(player.getUniqueId(), this);
	}

	protected void setItems() {
		this.guiItem.inventory().clear();
	}

	public void switchPage(int newPage) {
		if(this.guiItem == null)
			return;
		this.page = newPage;
		setItems();
	}

	public boolean click(Player player, int slot) {
		return slot >= 0 && slot < size;
	}

	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof final GUI gui))
			return false;

		if(gui.size != size)
			return false;

		return gui.guiItem.inventory().equals(guiItem.inventory());
	}
}