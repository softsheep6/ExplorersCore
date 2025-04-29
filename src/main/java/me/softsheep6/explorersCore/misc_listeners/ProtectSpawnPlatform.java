package me.softsheep6.explorersCore.misc_listeners;

import me.softsheep6.explorersCore.ExplorersCore;
import org.bukkit.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

// protects the spawn platform. no placing or breaking blocks. creative mode players can tho cause theyre simply cooler
// uses persistent data containers from the /spawnprotection command!
public class ProtectSpawnPlatform implements Listener {
    PersistentDataContainer data = Bukkit.getWorlds().getFirst().getPersistentDataContainer();

    // if /spawnprotections coords are set (any of them are not null) then return an array with those coordinates
    // to be used in protecting those blocks in the below EventHandlers !
    public int[] coordsSet() {
        if (data.get(new NamespacedKey(ExplorersCore.getPlugin(), "spx1"), PersistentDataType.INTEGER) != null) {

            int x1 = data.get(new NamespacedKey(ExplorersCore.getPlugin(), "spx1"), PersistentDataType.INTEGER);
            int y1 = data.get(new NamespacedKey(ExplorersCore.getPlugin(), "spy1"), PersistentDataType.INTEGER);
            int z1 = data.get(new NamespacedKey(ExplorersCore.getPlugin(), "spz1"), PersistentDataType.INTEGER);
            int x2 = data.get(new NamespacedKey(ExplorersCore.getPlugin(), "spx2"), PersistentDataType.INTEGER);
            int y2 = data.get(new NamespacedKey(ExplorersCore.getPlugin(), "spy2"), PersistentDataType.INTEGER);
            int z2 = data.get(new NamespacedKey(ExplorersCore.getPlugin(), "spz2"), PersistentDataType.INTEGER);
            return new int[]{x1, y1, z1, x2, y2, z2};
        }
        return null;
    }

    // prevents breaking blocks
    @EventHandler
    public void onBlockBreak (BlockBreakEvent event) {
        if (coordsSet() == null)
            return;
        int[] coords = coordsSet();

        if (event.getPlayer().getGameMode().equals(GameMode.CREATIVE))
            return;
        for (int x = coords[0]; x < coords[3]+1; x++) {
            for (int y = coords[1]; y < coords[4]+1; y++) {
                for (int z = coords[2]; z < coords[5]+1; z++) {
                    if (event.getBlock().getLocation().equals(new Location(event.getBlock().getWorld(), x, y, z)))
                        event.setCancelled(true);
                }
            }
        }
    }

    // prevents placing blocks
    @EventHandler
    public void onBlockPlace (BlockPlaceEvent event) {
        if (coordsSet() == null)
            return;
        int[] coords = coordsSet();

        if (event.getPlayer().getGameMode().equals(GameMode.CREATIVE))
            return;
        for (int x = coords[0]; x < coords[3]+1; x++) {
            for (int y = coords[1]; y < coords[4]+1; y++) {
                for (int z = coords[2]; z < coords[5]+1; z++) {
                    if (event.getBlock().getLocation().equals(new Location(event.getBlock().getWorld(), x, y, z)))
                        event.setCancelled(true);
                }
            }
        }
    }

    // prevents exploding blocks
    @EventHandler
    public void onBlockExplode (BlockExplodeEvent event) {
        if (coordsSet() == null)
            return;
        int[] coords = coordsSet();

        for (int x = coords[0]; x < coords[3]+1; x++) {
            for (int y = coords[1]; y < coords[4]+1; y++) {
                for (int z = coords[2]; z < coords[5]+1; z++) {
                    if (event.getBlock().getLocation().equals(new Location(event.getBlock().getWorld(), x, y, z)))
                        event.setCancelled(true);
                }
            }
        }
    }

    // prevents burning blocks
    @EventHandler
    public void onBlockBurn (BlockBurnEvent event) {
        if (coordsSet() == null)
            return;
        int[] coords = coordsSet();

        for (int x = coords[0]; x < coords[3]+1; x++) {
            for (int y = coords[1]; y < coords[4]+1; y++) {
                for (int z = coords[2]; z < coords[5]+1; z++) {
                    if (event.getBlock().getLocation().equals(new Location(event.getBlock().getWorld(), x, y, z)))
                        event.setCancelled(true);
                }
            }
        }
    }

    // prevents igniting blocks (not sure if this one is needed but better safe than sorry!!)
    @EventHandler
    public void onBlockIgnite (BlockIgniteEvent event) {
        if (coordsSet() == null)
            return;
        int[] coords = coordsSet();

        for (int x = coords[0]; x < coords[3]+1; x++) {
            for (int y = coords[1]; y < coords[4]+1; y++) {
                for (int z = coords[2]; z < coords[5]+1; z++) {
                    if (event.getBlock().getLocation().equals(new Location(event.getBlock().getWorld(), x, y, z)))
                        event.setCancelled(true);
                }
            }
        }
    }

    // prevents changing sign text
    @EventHandler
    public void onSignChange (SignChangeEvent event) {
        if (coordsSet() == null)
            return;
        int[] coords = coordsSet();

        for (int x = coords[0]; x < coords[3]+1; x++) {
            for (int y = coords[1]; y < coords[4]+1; y++) {
                for (int z = coords[2]; z < coords[5]+1; z++) {
                    if (event.getBlock().getLocation().equals(new Location(event.getBlock().getWorld(), x, y, z)))
                        event.setCancelled(true);
                }
            }
        }
    }
}