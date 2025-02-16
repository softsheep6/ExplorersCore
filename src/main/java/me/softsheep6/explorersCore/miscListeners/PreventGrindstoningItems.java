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
        if (Objects.requireNonNull(event.getClickedInventory()).getType() == InventoryType.GRINDSTONE) {
            // this causes NullPointerException sometimes but as far as i know no earth shattering consequences occur soooo whatever
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
