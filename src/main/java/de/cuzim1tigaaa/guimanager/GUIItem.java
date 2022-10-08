package de.cuzim1tigaaa.guimanager;

import org.bukkit.inventory.Inventory;

/**
 * Useful for managing guis with multiple pages
 * Can store all types of objects for executing different tasks
 */
public record GUIItem(Inventory inventory, int page, Object... objects) {}