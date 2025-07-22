package me.softsheep6.explorersCore.items.craftable;

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

    // list of logs to break with delay for the coolness!
    public static ArrayList<Location> logs = new ArrayList<>();

    @EventHandler
    public void onBlockBreak (BlockBreakEvent event) {

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
            int runs = 0;
            while (true) {
                if (runs > 1000) break;
                runs++;

                ArrayList<Location> surroundingLogs = getLogs(loc);
                // if there are no other logs surrounding the location,
                // and there are no other logs above the location, break
                if (surroundingLogs == null && !hasLogAbove(loc)) {
                    System.out.println("breaking!");
                    break;
                }
                // if there are no logs surrounding the location but there is a log above the location,
                // break the log at the location and move the location up by 1
                else if (surroundingLogs == null && hasLogAbove(loc)) {
                    System.out.println("there are no surrounding logs, but there are logs above");
                    logs.add(loc);
                    loc.setY(loc.getY()+1);
                }
                // if there are logs surrounding the location, add them to the logs arraylist
                // and continue
                else if (surroundingLogs != null) {
                    System.out.println("there are surrounding logs still");
                    logs.add(loc);
                    logs.addAll(surroundingLogs);
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


    // returns a list of logs in a 3x3 area surrounding the given location
    // or null if there are no logs surrounding the given location
    public static ArrayList<Location> getLogs(Location loc) {

        // iterates through the locations around the given location in a 3x3 area, adds any logs it finds to an arraylist
        ArrayList<Location> surroundingLogs = new ArrayList<>();
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                Location newLoc = new Location(loc.getWorld(), loc.getX() + i, loc.getY(), loc.getZ() + j);
                // if the logs arraylist doesnt already contain the location, and the location is a log, add it to the surroundingLogs arraylist
                for (Material m : Tag.LOGS.getValues()) {
                    if (!logs.contains(newLoc) && newLoc.getBlock().getType().equals(m)) {
                        surroundingLogs.add(newLoc);
                    }
                }

            }
        }

        if (!surroundingLogs.isEmpty())
            return surroundingLogs;
        else return null;
    }

    // returns true if a location has a log above it in a 3x3 area, false otherwise
    public boolean hasLogAbove(Location loc) {

        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                Location newLoc = new Location(loc.getWorld(), loc.getX() + i, loc.getY()+1, loc.getZ() + j);
                for (Material m : Tag.LOGS.getValues()) {
                    if (newLoc.getBlock().getType().equals(m)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }


}
