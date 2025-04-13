package me.softsheep6.explorersCore.misc_listeners;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public class PreventGrindstoningItems implements Listener {

    @EventHandler void onInventoryClick (InventoryClickEvent event) {
        if (event.getClickedInventory() == null)
            return; // YAY NO MORE NULLPOINTEREXCEPTION YAYYYY
        if (event.getClickedInventory().getType() == InventoryType.GRINDSTONE) {
            ItemStack clicked = event.getCurrentItem();
            ItemStack grindstoned = Objects.requireNonNull(event.getClickedInventory()).getItem(0); // 2 variables cause grindstone has 2 slots!
            ItemStack grindstoned2 = Objects.requireNonNull(event.getClickedInventory()).getItem(1);
            if (clicked == null || (grindstoned == null && grindstoned2 == null))
                return;
            // if the inventory click is in a grindstone and is on the result slot, if the result item clicked is an item that shouldnt be unenchanted,
            // then CANCEL THE EVENT! NO GRINDSTONING FOR U
            // idek what i did to get it to stop nullpointerexceptioning but this works i guess IDKKKKKKKKKKKKKKKKKKKKKKKKKKK
            if (event.getSlotType() == InventoryType.SlotType.RESULT) {
                if (grindstoned != null && (grindstoned.getType() == Material.DRAGON_EGG
                        || (grindstoned.getType() == Material.TOTEM_OF_UNDYING && grindstoned.containsEnchantment(Enchantment.MENDING))
                        || (grindstoned.getType() == Material.GOLDEN_HELMET && grindstoned.containsEnchantment(Enchantment.LOYALTY))
                        || (grindstoned.getType() == Material.DIAMOND_SWORD && grindstoned.containsEnchantment(Enchantment.CHANNELING))
                        || (grindstoned.getType() == Material.DIAMOND_AXE && grindstoned.containsEnchantment(Enchantment.SWIFT_SNEAK))))
                    event.setCancelled(true);
                else {
                    assert grindstoned2 != null;
                    if (grindstoned2.getType() == Material.DRAGON_EGG
                            || (grindstoned2.getType() == Material.TOTEM_OF_UNDYING && grindstoned2.containsEnchantment(Enchantment.MENDING))
                            || (grindstoned2.getType() == Material.GOLDEN_HELMET && grindstoned2.containsEnchantment(Enchantment.LOYALTY))
                            || (grindstoned2.getType() == Material.DIAMOND_SWORD && grindstoned2.containsEnchantment(Enchantment.CHANNELING))
                            || (grindstoned2.getType() == Material.DIAMOND_AXE && grindstoned2.containsEnchantment(Enchantment.SWIFT_SNEAK)))
                        event.setCancelled(true);
                }
            }
        }
    }
}
