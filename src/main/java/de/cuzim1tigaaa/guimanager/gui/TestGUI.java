package de.cuzim1tigaaa.guimanager.gui;

import org.bukkit.inventory.Inventory;

class TestGUI extends GUI {

	public TestGUI(int size, int page) {
		super(size, page);
	}

	@Override
	public Inventory getInventory() {
		return null;
	}

	@Override
	public void switchPage(int newPage) {

	}

	@Override
	public void setItems() {

	}
}