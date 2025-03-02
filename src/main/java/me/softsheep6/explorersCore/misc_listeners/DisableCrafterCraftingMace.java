package me.softsheep6.explorersCore.misc_listeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.CrafterCraftEvent;
import org.bukkit.inventory.ItemStack;

public class DisableCrafterCraftingMace implements Listener {
    @EventHandler void onCrafterCrafts (CrafterCraftEvent event) {
        if (event.getResult().equals(new ItemStack(Material.MACE))) {
            event.setCancelled(true);
        }
    }
}