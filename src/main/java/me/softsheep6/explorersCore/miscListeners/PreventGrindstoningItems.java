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
        ItemStack clicked = event.getCurrentItem();
        if (clicked == null)
            return;
        // if the inventory click is in a grindstone and is on the result slot, if the result item clicked is the totem or the crown
        // then CANCEL THE EVENT! NO GRINDSTONING FOR U
        if (Objects.requireNonNull(event.getClickedInventory()).getType() == InventoryType.GRINDSTONE && event.getSlotType() == InventoryType.SlotType.RESULT) {
            if (clicked.getType().equals(Material.GOLDEN_HELMET)
                    || (clicked.getType().equals(Material.TOTEM_OF_UNDYING))) {
                event.setCancelled(true);
            }
        }
    }
}
