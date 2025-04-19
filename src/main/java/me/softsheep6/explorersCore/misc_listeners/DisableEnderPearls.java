package me.softsheep6.explorersCore.misc_listeners;

import me.softsheep6.explorersCore.ExplorersCore;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.persistence.PersistentDataType;

public class DisableEnderPearls implements Listener {
    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent event) {

        // if pearling is enabled, return
        if (Boolean.FALSE.equals(Bukkit.getWorlds().getFirst().getPersistentDataContainer().get(new NamespacedKey(ExplorersCore.getPlugin(), "enderpearls"), PersistentDataType.BOOLEAN)))
            return;

        // otherwise, cancel event when ender pearl teleport happens yea
        if (event.getCause().equals(PlayerTeleportEvent.TeleportCause.ENDER_PEARL))
            event.setCancelled(true);
    }
}