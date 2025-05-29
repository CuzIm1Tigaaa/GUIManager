package de.cuzim1tigaaa.guimanager;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;

/**
 * This class provides some useful methods for GUIs
 */
public class GuiUtils {

    /**
     * Calculate the amount of pages a gui will have
     * @param entries The amount of entries, for example how many teams are in a list
     * @param perPage The amount of entries per page that should be displayed
     * @return Returns the amount of pages needed to display all entries
     */
    public static int calcMaxPage(int entries, int perPage) {
        return entries / perPage + ((entries % perPage == 0) ? 0 : 1);
    }

    /**
     * Set the navigation items
     * @param inventory The inventory to set the items
     * @param last whether the last item is needed
     * @param reload whether the reload item is needed
     * @param next whether the next item is needed
     */
    public static void setNavigationItems(Inventory inventory, boolean last, boolean reload, boolean next) {
        if(last)
            inventory.setItem(inventory.getSize() - 9, ItemUtils.getNavigationLast());
        if(reload)
            inventory.setItem(inventory.getSize() - 5, ItemUtils.getNavigationRefresh());
        if(next)
            inventory.setItem(inventory.getSize() - 1, ItemUtils.getNavigationNext());
    }

    /**
     * Set placeholders (glass panes) inside a GUI
     * Placeholders can be light or dark, depending where they are:
     * if they are on an edge of the inventory the placeholder is dark
     * Otherwise it is light
     *
     * @param inventory     The inventory the placeholders should go into
     */
    public static void setPlaceholders(Inventory inventory) {
        int slots = inventory.getSize();

        for(int i = 0; i < slots; i++) {
            boolean light = true;
            if(i < 9 || i > slots - 9)
                light = false;
            else if(i % 9 == 0 || (i + 1) % 9 == 0)
                light = false;
            inventory.setItem(i, ItemUtils.getPlaceholder(light));
        }
    }

    public static Integer nextPage(Inventory inventory) {
        int slot = inventory.getSize();

        if(inventory.getItem(slot - 1).getType() == Material.PLAYER_HEAD)
            return 1;

        if(inventory.getItem(slot - 5).getType() == Material.PLAYER_HEAD)
            return 0;

        if(inventory.getItem(slot - 9).getType() == Material.PLAYER_HEAD)
            return -1;

        return null;
    }
}