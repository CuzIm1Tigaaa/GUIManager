package de.cuzim1tigaaa.guimanager;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.*;

import java.util.*;

/**
 * Class for custom itemStacks used in a GUI
 */
public class ItemManager {

	/**
	 * Enum for leather armor types
	 */
	public enum Type {
		HELMET, CHESTPLATE, LEGGINGS, BOOTS
	}


	/**
	 * Calculate the amount of pages of a GUI
	 * @param entries The amount of entries, for example how many teams are in a list
	 * @param perPage The amount of entries per page that should be displayed
	 * @return Returns the amount of pages needed to display all entries
	 */
	public static int calcMaxPage(int entries, int perPage) {
		return entries / perPage + ((entries % perPage == 0) ? 0 : 1);
	}

	/**
	 * Set the navigation items
	 *
	 * @param inventory The inventory to set the items
	 * @param next whether the next item is needed
	 * @param last whether the last item is needed
	 */
	public static void setNavigationItems(Inventory inventory, boolean last, boolean reload, boolean next) {
		if(last) inventory.setItem(inventory.getSize() - 9, getNavigationLast());
		if(reload) inventory.setItem(inventory.getSize() - 5, getNavigationRefresh());
		if(next) inventory.setItem(inventory.getSize() - 1, getNavigationNext());
	}
	/**
	 * Get the navigation item to go to the next page
	 *
	 * @return              Returns the itemStack of the navigation item
	 * @see CustomHead
	 */
	public static ItemStack getNavigationNext() {
		return getCustomHead(CustomHead.BLACK_ARROW_RIGHT, ChatColor.GREEN + ChatColor.BOLD.toString() + "Weiter", ChatColor.GRAY + "Klicke hier, um eine", ChatColor.GRAY + "Seite weiter zu gehen");
	}
	/**
	 * Get the navigation item to go to the previous page
	 *
	 * @return              Returns the itemStack of the navigation item
	 * @see CustomHead
	 */
	public static ItemStack getNavigationLast() {
		return getCustomHead(CustomHead.BLACK_ARROW_LEFT, ChatColor.RED + ChatColor.BOLD.toString() + "Zurück", ChatColor.GRAY + "Klicke hier, um eine", ChatColor.GRAY + "Seite zurück zu gehen");
	}
	/**
	 * Get the refresh item to refresh the current page
	 *
	 * @return              Returns the itemStack of the refresh item
	 * @see CustomHead
	 */
	public static ItemStack getNavigationRefresh() {
		return getCustomHead(CustomHead.BLACK_EXCLAMATION, ChatColor.BLUE + ChatColor.BOLD.toString() + "Reload", ChatColor.GRAY + "Klicke hier, um diese", ChatColor.GRAY + "Seite neu zu laden");
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

		boolean light;
		for(int i = 0; i < slots; i++) {
			if(i < 9 || i > slots - 9) light = false;
			else light = !((i + 1) % 9 == 0 || i % 9 == 0);
			inventory.setItem(i, getPlaceholder(light));
		}
	}
	/**
	 * Get a placeholder itemStack
	 *
	 * @param light         Whether it should be a light or dark placeholder
	 *                      (Black or Dark_Gray Glass pane)
	 * @return              Returns the itemStack of the placeholder
	 */
	public static ItemStack getPlaceholder(boolean light) {
		ItemStack placeholder;
		if(light) placeholder = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
		else placeholder = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
		ItemMeta placeholderMeta = placeholder.getItemMeta();
		placeholderMeta.setDisplayName("");
		placeholder.setItemMeta(placeholderMeta);
		return placeholder;
	}

	/**
	 * Get an itemStack with a modified size. If the size is greater than the
	 * max stack size of the current inventory, modify the inventory max size
	 *
	 * @param inventory     The inventory to modify
	 * @param itemStack     The itemStack which should be in the inventory
	 * @param amount        The stack size of the itemStack
	 * @return              Return the itemStack with the new stack size
	 */
	public static ItemStack getAmountItem(final Inventory inventory, final ItemStack itemStack, final int amount) {
		inventory.setMaxStackSize(Math.max(amount, inventory.getMaxStackSize()));
		itemStack.setAmount(amount);
		return itemStack;
	}

	/**
	 * Get a player head
	 *
	 * @param player        The player who is the owner of the heads texture
	 * @param display       The display-name of the itemStack
	 * @param loreItems     The lore the head should have, every String is a new line
	 * @return              Returns the itemStack skull with the players skin texture
	 */
	public static ItemStack getPlayerHead(final OfflinePlayer player, final String display, final String... loreItems) {
		ItemStack itemStack = new ItemStack(Material.PLAYER_HEAD);
		SkullMeta meta = (SkullMeta) itemStack.getItemMeta();
		meta.setOwningPlayer(player);
		itemStack.setItemMeta(meta);

		itemStack = addNameLore(itemStack, display, loreItems);
		return itemStack;
	}
	/**
	 * Get a player head
	 *
	 * @param inventory     The inventory to modify
	 * @param player        The player who is the owner of the heads texture
	 * @param amount        The size of the stack
	 * @param display       The display-name of the itemStack
	 * @param loreItems     The lore the head should have, every String is a new line
	 * @return              Returns the itemStack skull with the players skin texture and a custom stack size
	 */
	public static ItemStack getPlayerHead(final Inventory inventory, final OfflinePlayer player, final int amount, final String display, final String... loreItems) {
		ItemStack itemStack = getPlayerHead(player, display, loreItems);
		return getAmountItem(inventory, itemStack, amount);
	}


	/**
	 * Get a custom head as ItemStack
	 *
	 * @param head          The enum of the head
	 * @param displayName   The displayName the head should have
	 * @param loreItems     The lore the head should have, every String is a new line
	 * @return              Returns the itemStack skull with the custom texture
	 * @see CustomHead
	 */
	public static ItemStack getCustomHead(final CustomHead head, final String displayName, boolean useAsGPName, final String... loreItems) {
		ItemStack customHead = new ItemStack(Material.PLAYER_HEAD, 1);
		SkullMeta customHeadMeta = (SkullMeta) customHead.getItemMeta();
		customHeadMeta.setOwnerProfile(CustomHead.getHead(head));
		customHead.setItemMeta(customHeadMeta);
		customHead = addNameLore(customHead, useAsGPName ? null : displayName, loreItems);
		return customHead;
	}
	/**
	 * Get a custom head as ItemStack
	 *
	 * @param head          The enum of the head
	 * @param displayName   The displayName the head should have
	 * @param loreItems     The lore the head should have, every String is a new line
	 * @return              Returns the itemStack skull with the custom texture
	 * @see CustomHead
	 */
	public static ItemStack getCustomHead(final CustomHead head, final String displayName, final String... loreItems) {
		return getCustomHead(head, displayName, false, loreItems);
	}

	/**
	 * Get a custom item as ItemStack
	 *
	 * @param material      The type of the ItemStack
	 * @param displayName   The displayName the item should have
	 * @param loreItems     The lore the item should have, every String is a new line
	 * @return              Returns the item as ItemStack
	 */
	public static ItemStack getCustomItem(final Material material, final String displayName, final String... loreItems) {
		return getCustomItem(material, false, displayName, loreItems);
	}
	/**
	 * Get a custom item as ItemStack
	 *
	 * @param material      The type of the ItemStack
	 * @param displayName   The displayName the item should have
	 * @param enchanted     Should this item be enchanted?
	 * @param loreItems     The lore the item should have, every String is a new line
	 * @return              Returns the item as ItemStack
	 */
	public static ItemStack getCustomItem(final Material material, boolean enchanted, final String displayName, final String... loreItems) {
		ItemStack custom = new ItemStack(material);
		custom = addNameLore(custom, displayName, loreItems);
		if(enchanted) custom = getEnchantedItem(custom);
		return custom;
	}

	/**
	 * Get a colored piece of leather armor as an ItemStack
	 *
	 * @param type          The type of the piece
	 * @see Type
	 * @param color         The color of the piece
	 * @see Color
	 * @param displayName   The displayName the item should have
	 * @param loreItems     The lore the item should have, every String is a new line
	 * @return              Returns the leather armor piece as ItemStack
	 */
	public static ItemStack getColoredLeatherArmor(final Type type, final Color color, final String displayName, final String... loreItems) {
		return getColoredLeatherArmor(type, color, false, displayName, loreItems);
	}
	/**
	 * Get a colored piece of leather armor as an ItemStack
	 *
	 * @param type          The type of the piece
	 * @see Type
	 * @param color         The color of the piece
	 * @see Color
	 * @param enchanted     Should this item be enchanted?
	 * @param displayName   The displayName the item should have
	 * @param loreItems     The lore the item should have, every String is a new line
	 * @return              Returns the leather armor piece as ItemStack
	 */
	public static ItemStack getColoredLeatherArmor(final Type type, final Color color, boolean enchanted, final String displayName, final String... loreItems) {
		ItemStack leather = switch(type) {
			case HELMET -> new ItemStack(Material.LEATHER_HELMET);
			case CHESTPLATE -> new ItemStack(Material.LEATHER_CHESTPLATE);
			case LEGGINGS -> new ItemStack(Material.LEATHER_LEGGINGS);
			case BOOTS -> new ItemStack(Material.LEATHER_BOOTS);
		};

		LeatherArmorMeta meta = (LeatherArmorMeta) leather.getItemMeta();
		meta.setColor(color);
		leather.setItemMeta(meta);

		leather = addNameLore(leather, displayName, loreItems);
		if(enchanted) leather = getEnchantedItem(leather);
		return leather;
	}

	/**
	 * Enchant an ItemStack
	 *
	 * @param itemStack     The ItemStack to enchant
	 * @return              Returns the item as enchanted ItemStack
	 */
	public static ItemStack getEnchantedItem(final ItemStack itemStack) {
		ItemMeta meta = itemStack.getItemMeta();
		meta.addEnchant(Enchantment.UNBREAKING, 1, false);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		itemStack.setItemMeta(meta);
		return itemStack;
	}
	/**
	 * Add a displayName and an itemLore to an ItemStack
	 *
	 * @param itemStack     The ItemStack to modify
	 * @param displayName   The displayName the item should have
	 * @param loreItems     The lore the item should have, every String is a new line
	 * @return              Returns the modified ItemStack
	 */
	private static ItemStack addNameLore(final ItemStack itemStack, final String displayName, final String... loreItems) {
		ItemMeta meta = itemStack.getItemMeta();
		meta.setDisplayName(displayName);

		if(loreItems != null && loreItems.length > 0) {
			List<String> lore = new ArrayList<>();
			Collections.addAll(lore, loreItems);
			lore.removeIf(Objects::isNull);
			meta.setLore(lore);
		}

		itemStack.setItemMeta(meta);
		return itemStack;
	}
}