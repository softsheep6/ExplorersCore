package me.softsheep6.explorersCore.misc_listeners;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class DisablePlacingInfernoBlock implements Listener {
    // prevents inferno block from being placed by cancelling the event
    @EventHandler
    void onBlockPlace(BlockPlaceEvent event) {
        Block placed = event.getBlockPlaced();

        if (placed.getType() == Material.MAGMA_BLOCK && event.getItemInHand().containsEnchantment(Enchantment.FLAME)) {
            event.setCancelled(true);
        }

    }
}
