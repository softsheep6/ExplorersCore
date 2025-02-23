package me.softsheep6.explorersCore.miscListeners;

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
            ItemStack grindstoned = Objects.requireNonNull(event.getClickedInventory()).getItem(0);
            if (clicked == null || grindstoned == null)
                return;
            // if the inventory click is in a grindstone and is on the result slot, if the result item clicked is an item that shouldnt be unenchanted,
            // then CANCEL THE EVENT! NO GRINDSTONING FOR U
            if (event.getSlotType() == InventoryType.SlotType.RESULT) {
                if (grindstoned.getEnchantmentLevel(Enchantment.LOYALTY) == 1 || grindstoned.getEnchantmentLevel(Enchantment.MENDING) == 1 || grindstoned.getEnchantmentLevel(Enchantment.CHANNELING) == 1) {
                    if (clicked.getType().equals(Material.GOLDEN_HELMET)
                            || clicked.getType().equals(Material.TOTEM_OF_UNDYING)
                            || clicked.getType().equals(Material.DIAMOND_SWORD)) {
                        event.setCancelled(true);
                    }
                }
            }
        }
    }
}
