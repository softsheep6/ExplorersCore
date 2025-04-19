package me.softsheep6.explorersCore.misc_listeners;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

// protects the spawn platform. no placing or breaking blocks. creative mode players can tho cause theyre simply cooler
public class ProtectSpawnPlatform implements Listener {
    @EventHandler
    public void onBlockBreak (BlockBreakEvent event) {
        if (event.getPlayer().getGameMode().equals(GameMode.CREATIVE))
            return;
        for (int x = -5; x < 6; x++) {
            for (int y = 65; y < 75; y++) {
                for (int z = -5; z < 6; z++) {
                    if (event.getBlock().getLocation().equals(new Location(event.getBlock().getWorld(), x, y, z)))
                        event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onBlockPlace (BlockPlaceEvent event) {
        if (event.getPlayer().getGameMode().equals(GameMode.CREATIVE))
            return;
        for (int x = -5; x < 6; x++) {
            for (int y = 65; y < 75; y++) {
                for (int z = -5; z < 6; z++) {
                    if (event.getBlock().getLocation().equals(new Location(event.getBlock().getWorld(), x, y, z)))
                        event.setCancelled(true);
                }
            }
        }
    }
}