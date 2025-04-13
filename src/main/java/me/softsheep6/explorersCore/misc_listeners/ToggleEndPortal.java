package me.softsheep6.explorersCore.misc_listeners;

import me.softsheep6.explorersCore.ExplorersCore;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.persistence.PersistentDataType;

public class ToggleEndPortal implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {

        // if interaction isnt clicking a block, or if end is enabled, return
        if (event.getClickedBlock() == null
                || Boolean.FALSE.equals(Bukkit.getWorlds().getFirst().getPersistentDataContainer().get(new NamespacedKey(ExplorersCore.getPlugin(), "end"), PersistentDataType.BOOLEAN)))
            return;

        // otherwise, if interaction is a right click with an eye of ender on an end portal frame, cancel it to disable going to the end!
        if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)
                && event.getClickedBlock().getType().equals(Material.END_PORTAL_FRAME)
                && (event.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.ENDER_EYE) || (event.getPlayer().getInventory().getItemInOffHand().getType().equals(Material.ENDER_EYE)))) {
            event.setCancelled(true);
        }
    }
}
