package me.softsheep6.explorersCore.miscListeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.inventory.ItemStack;

// string duper if it was good ......... dispenser with shears inside dispenses string. Yay.
public class StringDispenser implements Listener {
    @EventHandler void onDispenseString (BlockDispenseEvent event) {
        Location loc = event.getBlock().getLocation();
        loc.setY(loc.getY()+1);
        if (event.getItem().equals(new ItemStack(Material.SHEARS))) {
            event.setCancelled(true);
            Bukkit.getWorlds().getFirst().dropItem(loc, new ItemStack(Material.STRING));
        }
    }

}