package de.cuzim1tigaaa.guimanager;

import de.cuzim1tigaaa.colorlib.ColorLib;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.*;

import java.util.*;

/**
 * This class provides some useful methods for itemstacks and their metadata
 */
public class ItemUtils {

    /**
     * Get the navigation item to go to the next page
     * Default is set to a custom head with an arrow to the right
     *
     * @return Returns the itemStack of the navigation item
     * @see CustomHead
     */
    public static ItemStack getNavigationNext() {
        return getCustomHead(CustomHead.BLACK_ARROW_RIGHT,
                "&a&lWeiter",
                "&7Klicke hier, um eine",
                "&7Seite weiter zu gehen");
    }

    /**
     * Get the navigation item to go to the previous page
     * Default is set to a custom head with an arrow to the left
     *
     * @return Returns the itemStack of the navigation item
     * @see CustomHead
     */
    public static ItemStack getNavigationLast() {
        return getCustomHead(CustomHead.BLACK_ARROW_LEFT,
                "&c&lZurück",
                "&7Klicke hier, um eine",
                "&7Seite zurück zu gehen");
    }

    /**
     * Get the refresh item to refresh the current page
     * Default is set to a custom head with an exclamation mark
     *
     * @return Returns the itemStack of the refresh item
     * @see CustomHead
     */
    public static ItemStack getNavigationRefresh() {
        return getCustomHead(CustomHead.BLACK_EXCLAMATION,
                "&9&lReload",
                "&7Klicke hier, um diese", "&7Seite neu zu laden");
    }

    /**
     * Get a placeholder itemStack
     *
     * @param light Whether it should be a light or dark placeholder
     *              (Black or Dark_Gray Glass pane)
     * @return Returns the itemStack of the placeholder
     */
    public static ItemStack getPlaceholder(boolean light) {
        Material material = light ? Material.GRAY_STAINED_GLASS_PANE : Material.BLACK_STAINED_GLASS_PANE;
	    return getCustomItem(material, "&c");
    }

    /**
     * Get an itemStack with a modified size. If the size is greater than the
     * max stack size of the current inventory, modify the inventory max size
     *
     * @param inventory The inventory to modify
     * @param itemStack The itemStack which should be in the inventory
     * @param amount    The stack size of the itemStack
     * @return Return the itemStack with the new stack size
     */
    public static ItemStack getAmountItem(final Inventory inventory, final ItemStack itemStack, final int amount) {
        inventory.setMaxStackSize(Math.max(amount, inventory.getMaxStackSize()));
        itemStack.setAmount(amount);
        return itemStack;
    }

    /**
     * Get a player head
     *
     * @param player    The player who is the owner of the heads texture
     * @param display   The display-name of the itemStack
     * @param loreItems The lore the head should have, every String is a new line
     * @return Returns the itemStack skull with the players skin texture
     */
    public static ItemStack getPlayerHead(final OfflinePlayer player, final String display, final String... loreItems) {
        ItemStack itemStack = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) itemStack.getItemMeta();

        if(player.isOnline())
            meta.setOwningPlayer(player);
        else
            meta.setOwnerProfile(GuiManager.getPlayerProfiles().get(player.getUniqueId()));

        itemStack.setItemMeta(meta);
        itemStack = addNameLore(itemStack, display, loreItems);
        return itemStack;
    }

    /**
     * Get a player head
     *
     * @param inventory The inventory to modify
     * @param player    The player who is the owner of the heads texture
     * @param amount    The size of the stack
     * @param display   The display-name of the itemStack
     * @param loreItems The lore the head should have, every String is a new line
     * @return Returns the itemStack skull with the players skin texture and a custom stack size
     */
    public static ItemStack getPlayerHead(final Inventory inventory, final OfflinePlayer player, final int amount, final String display, final String... loreItems) {
        ItemStack itemStack = getPlayerHead(player, display, loreItems);
        return getAmountItem(inventory, itemStack, amount);
    }

    /**
     * Get a custom head as ItemStack
     *
     * @param head              The enum of the head
     * @param customDisplayName If the custom display name should be used or if it should be added to the lore
     *                          Can be used if the name of the head owner should be displayed instead
     * @param displayName       The displayName the head should have
     * @param loreItems         The lore the head should have, every String is a new line
     * @return Returns the itemStack skull with the custom texture
     * @see CustomHead
     */
    public static ItemStack getCustomHead(final CustomHead head, boolean customDisplayName, final String displayName, final String... loreItems) {
        ItemStack customHead = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta customHeadMeta = (SkullMeta) customHead.getItemMeta();
        customHeadMeta.setOwnerProfile(CustomHead.getHead(head));
        customHead.setItemMeta(customHeadMeta);

        if(!customDisplayName) {
            String[] loreItemsUpdated = loreItems;
            if(displayName != null) {
                loreItemsUpdated = new String[loreItems.length + 1];
                System.arraycopy(loreItems, 0, loreItems, 1, loreItems.length - 1);
                loreItemsUpdated[0] = displayName;
            }

            customHead = addNameLore(customHead, null, loreItemsUpdated);
            return customHead;
        }

        customHead = addNameLore(customHead, displayName, loreItems);
        return customHead;
    }

    /**
     * Get a custom head as ItemStack
     *
     * @param head        The enum of the head
     * @param displayName The displayName the head should have
     * @param loreItems   The lore the head should have, every String is a new line
     * @return Returns the itemStack skull with the custom texture
     * @see CustomHead
     */
    public static ItemStack getCustomHead(final CustomHead head, final String displayName, final String... loreItems) {
        return getCustomHead(head, true, displayName, loreItems);
    }

    /**
     * Get a custom item as ItemStack
     *
     * @param material    The type of the ItemStack
     * @param displayName The displayName the item should have
     * @param loreItems   The lore the item should have, every String is a new line
     * @return Returns the item as ItemStack
     */
    public static ItemStack getCustomItem(final Material material, final String displayName, final String... loreItems) {
        return getCustomItem(material, false, displayName, loreItems);
    }

    /**
     * Get a custom item as ItemStack
     *
     * @param material    The type of the ItemStack
     * @param displayName The displayName the item should have
     * @param enchanted   If the item should be enchanted
     * @param loreItems   The lore the item should have, every String is a new line
     * @return Returns the item as ItemStack
     */
    public static ItemStack getCustomItem(final Material material, boolean enchanted, final String displayName, final String... loreItems) {
        ItemStack custom = new ItemStack(material);
        custom = addNameLore(custom, displayName, loreItems);
        if(enchanted) custom = addEnchantments(custom);
        return custom;
    }

    /**
     * Get a colored piece of leather armor as an ItemStack
     *
     * @param slot        The slot of the piece, only armor pieces! Default is HEAD for helmet
     * @param color       The color of the piece
     * @param displayName The displayName the item should have
     * @param loreItems   The lore the item should have, every String is a new line
     * @return Returns the leather armor piece as ItemStack
     * @see EquipmentSlot
     * @see Color
     */
    public static ItemStack getColoredLeatherArmor(final EquipmentSlot slot, final Color color, final String displayName, final String... loreItems) {
        return getColoredLeatherArmor(slot, color, false, displayName, loreItems);
    }

    /**
     * Get a colored piece of leather armor as an ItemStack
     *
     * @param slot        The slot of the piece, only armor pieces! Default is HEAD for helmet
     * @param color       The color of the piece
     * @param enchanted   If the armor piece should be enchanted
     * @param displayName The displayName the item should have
     * @param loreItems   The lore the item should have, every String is a new line
     * @return Returns the leather armor piece as ItemStack
     * @see EquipmentSlot
     * @see Color
     */
    public static ItemStack getColoredLeatherArmor(final EquipmentSlot slot, final Color color, boolean enchanted, final String displayName, final String... loreItems) {
        ItemStack leatherArmor = switch(slot) {
            case CHEST -> new ItemStack(Material.LEATHER_CHESTPLATE);
            case LEGS -> new ItemStack(Material.LEATHER_LEGGINGS);
            case FEET -> new ItemStack(Material.LEATHER_BOOTS);
            default -> new ItemStack(Material.LEATHER_HELMET);
        };

        leatherArmor = addNameLore(leatherArmor, displayName, loreItems);
        if(enchanted)
            leatherArmor = addEnchantments(leatherArmor);

        LeatherArmorMeta meta = (LeatherArmorMeta) leatherArmor.getItemMeta();
        meta.setColor(color);
        leatherArmor.setItemMeta(meta);
        return leatherArmor;
    }

    /**
     * Enchant an ItemStack with Unbreaking I and hidden
     * Useful if the enchantment is just needed visually
     * @param itemStack The ItemStack to enchant
     * @return Returns the item as enchanted ItemStack
     */
    public static ItemStack addEnchantments(final ItemStack itemStack) {
        Enchantment enchantment = Registry.ENCHANTMENT.get(NamespacedKey.minecraft("unbreaking"));
        return addEnchantments(itemStack, Collections.singletonMap(enchantment, 1), true);
    }

    /**
     * Enchant an ItemStack with multiple enchantments
     * @param itemStack The ItemStack to enchant
     * @param enchantments Enchantments with their level that should be added
     * @param hideEnchantments If the enchantments should be hidden, useful if just needed visually
     * @return Returns the given item with the specified enchantments
     */
    public static ItemStack addEnchantments(final ItemStack itemStack, Map<Enchantment, Integer> enchantments, boolean hideEnchantments) {
        ItemMeta meta = itemStack.getItemMeta();
        enchantments.forEach((enchantment, integer) ->
                meta.addEnchant(enchantment, integer, false));
        if(hideEnchantments)
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    /**
     * Add a displayName and an itemLore to an existing ItemStack
     * can be formatted using hex and normal chat color codes
     * @param itemStack   The ItemStack to modify
     * @param displayName The displayName the item should have
     * @param loreItems   The lore the item should have, every String is a new line, nulls get removed
     * @return Returns the modified ItemStack
     */
    public static ItemStack addNameLore(final ItemStack itemStack, final String displayName, final String... loreItems) {
        ItemMeta meta = itemStack.getItemMeta();
        if(displayName != null)
            meta.setDisplayName(ColorLib.format(displayName));

        if(loreItems != null && loreItems.length > 0) {
            List<String> lore = new ArrayList<>();
            Arrays.stream(loreItems).filter(Objects::nonNull)
                    .forEach(loreItem -> lore.add(ColorLib.format(loreItem)));
            meta.setLore(lore);
        }

        itemStack.setItemMeta(meta);
        return itemStack;
    }

    /**
     * Translates the colors of the displayName and lore of an ItemStack
     * @param itemStack   The ItemStack to modify
     * @return Returns the modified ItemStack
     */
    public static ItemStack translateNameLore(final ItemStack itemStack) {
        ItemMeta meta = itemStack.getItemMeta();
        if(meta.hasDisplayName())
            meta.setDisplayName(ColorLib.format(meta.getDisplayName()));

        if(meta.hasLore()) {
            List<String> lore = new ArrayList<>();
            meta.getLore().forEach(loreItem -> lore.add(ColorLib.format(loreItem)));
            meta.setLore(lore);
        }
        itemStack.setItemMeta(meta);
        return itemStack;
    }
}