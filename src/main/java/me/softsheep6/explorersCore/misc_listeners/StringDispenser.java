package me.softsheep6.explorersCore.misc_listeners;

import me.softsheep6.explorersCore.ExplorersCore;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.Objects;

// string duper if it was good ......... dispenser with shears inside dispenses string. Yay.
public class StringDispenser implements Listener {
    @EventHandler void onDispenseString (BlockDispenseEvent event) {
        Location loc = event.getBlock().getLocation();
        loc.setY(loc.getY()+1);
        if (event.getItem().equals(new ItemStack(Material.SHEARS))
                && Boolean.FALSE.equals(Bukkit.getWorlds().getFirst().getPersistentDataContainer().get(new NamespacedKey(ExplorersCore.getPlugin(), "stringduper"), PersistentDataType.BOOLEAN))) {
            event.setCancelled(true);
            Objects.requireNonNull(event.getBlock().getLocation().getWorld()).dropItem(loc, new ItemStack(Material.STRING));
        }
    }
}