package me.softsheep6.explorersCore.items.craftable;
/*
import me.softsheep6.explorersCore.ExplorersCore;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.Tag;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class LumberAxe implements Listener {

    @EventHandler
    public void onBlockBreak (BlockBreakEvent event) {
        // list of logs to break with delay for the coolness!
        ArrayList<Location> logs = new ArrayList<>();

        // check to see if the tool used to break the block is the lumber axe
        Player p = event.getPlayer();
        if (p.getInventory().getItemInMainHand().getType().equals(Material.DIAMOND_AXE)
            && p.getInventory().getItemInMainHand().hasItemMeta()
            && p.getInventory().getItemInMainHand().getItemMeta().hasCustomModelData()
            && p.getInventory().getItemInMainHand().getItemMeta().getCustomModelDataComponent().getStrings().getFirst().equals("lumberaxe")) {

            // if the block broken is a log, continue, otherwise return
            boolean isLog = false;
            for (Material m : Tag.LOGS.getValues()) {
                if (event.getBlock().getType().equals(m)) {
                    isLog = true;
                }
            }
            if (!isLog) return;

            // while loop, keep breaking blocks above the original broken block until there are no blocks left to break.
            // first checks if there is a log next to the location, if there is then break it
            // if there are no blocks next to the location, then break the block above, and repeat and stuff yea
            Location loc = event.getBlock().getLocation();
            while (true) {

                Location logLoc = getLog(loc, 0);
                if (logLoc == null) {
                    // logLoc being null means there are no logs adjacent to the previously broken log

                } else {
                    logs.add(logLoc);
                    loc = logLoc;
                }

            }

            // swag
            final int[] index = {0};
            new BukkitRunnable() {

                @Override
                public void run() {

                    if (index[0] >= logs.size()) this.cancel();
                    logs.get(index[0]).getBlock().breakNaturally(p.getInventory().getItemInMainHand());
                    logs.get(index[0]).getWorld().playSound(logs.get(index[0]), Sound.BLOCK_WOOD_BREAK, 1, 0);
                    index[0]++;
                }
            }.runTaskTimer(ExplorersCore.getPlugin(), 2, 2);

        }
    }


    // returns the location of a log above/adjacent to the given location (depending on parameter),
    // or null if there is no log in a 3x3 area above the given location
    // above = 0: adjacent, above = 1: above
    public Location getLog (Location loc, int aboveOrAdjacent) {

        // iterates through the locations above the given location in a 3x3 area, the first one it finds that is a log is returned
        // otherwise returns null after checking every block in the 3x3 area.
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                Location newLoc = new Location(loc.getWorld(), loc.getX() + i, loc.getY()+aboveOrAdjacent, loc.getZ() + j);
                for (Material m : Tag.LOGS.getValues()) {
                    if (newLoc.getBlock().getType().equals(m)) {
                        return newLoc;
                    }
                }
            }
        }

        return null;
    }


}
*/