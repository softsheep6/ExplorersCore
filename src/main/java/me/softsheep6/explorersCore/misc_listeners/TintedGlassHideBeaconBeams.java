package me.softsheep6.explorersCore.misc_listeners;


//
// commented out for now because sadly i am not pro enough plugin dev to do this :[
//

/*
import me.softsheep6.explorersCore.ExplorersCore;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
//import org.bukkit.event.player.PlayerJoinEvent;
import com.jeff_media.morepersistentdatatypes.*;
import org.bukkit.persistence.PersistentDataContainer;

import java.util.ArrayList;

// this class will be for detecting & saving placed tinted glass, and then the packetevent thingy in main will be for actually showing the glass
public class TintedGlassHideBeaconBeams implements Listener {

    // hide beacon beams when tinted glass is placed on top of the beacon
    @EventHandler
    public void onBlockPlace (BlockPlaceEvent event) {
        // return if block isnt tinted glass
        if (!event.getBlock().getType().equals(Material.TINTED_GLASS))
            return;

        Location loc = event.getBlock().getLocation();
        Location beaconLoc = new Location(event.getBlock().getWorld(), loc.getX(), loc.getY()-1, loc.getZ());
        // if the block under the glass is a beacon, cancel the placement, and send a fake block place to all players
        if (beaconLoc.getBlock().getType().equals(Material.BEACON)) {

            event.setCancelled(true);
            for (Player p : Bukkit.getOnlinePlayers())
                sendTintedGlassPlacement(new Location(event.getBlock().getWorld(), loc.getX(), loc.getY()+1, loc.getZ()), p);
            System.out.println("sent block changes!");
            System.out.println(new Location(event.getBlock().getWorld(), loc.getX(), loc.getY()+1, loc.getZ()));


            // pdc for future players (forgot id have to do this sigh.....)
            PersistentDataContainer pdc = event.getBlock().getWorld().getPersistentDataContainer();
            NamespacedKey key = new NamespacedKey(ExplorersCore.getPlugin(), "tintedglass");

            // if key exists, set locArr to it, if not, make new one
            ArrayList<Location> locArr;
            if (pdc.has(key)) {
                locArr = pdc.get(key, DataType.asArrayList(DataType.LOCATION));
            }
            else {
                locArr = new ArrayList<>();
            }

            // then add the location of the tinted glass to locArr and then locArr to the pdc
            assert locArr != null; //SHUTUP INTELLIJ
            locArr.add(loc);
            pdc.set(key, DataType.asArrayList(DataType.LOCATION), locArr);
        }
    }

    // this is a separate method so that it can be called when new players join after the initial placement
    public void sendTintedGlassPlacement(Location loc, Player p) {
        p.sendBlockChange(loc, Material.TINTED_GLASS.createBlockData());
        System.out.println(Material.TINTED_GLASS.createBlockData());
    }



    /*@EventHandler
    public void onPlayerJoin (PlayerJoinEvent event) {
        // pdc stuff to make sure newly joining players get sent ALL placed glass yea
        PersistentDataContainer pdc = event.getPlayer().getWorld().getPersistentDataContainer();
        NamespacedKey key = new NamespacedKey(ExplorersCore.getPlugin(), "tintedglass");
        ArrayList<Location> locArr;
        if (pdc.has(key)) {
            locArr = pdc.get(key, DataType.asArrayList(DataType.LOCATION));
        } else return;

        assert locArr != null;
        for (Location loc : locArr) {
            event.getPlayer().sendBlockChange(loc, Material.TINTED_GLASS.createBlockData());
        }


    // probably goes in main class idK im just gonna leave it here for now
    ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
        protocolManager.addPacketListener(new PacketAdapter(this, ListenerPriority.NORMAL, PacketType.Play.Server.MAP_CHUNK) {
            @Override
            public void onPacketSending(PacketEvent event) {
                if (event.getPacketType() == PacketType.Play.Server.MAP_CHUNK) {
                    Bukkit.getScheduler().scheduleSyncDelayedTask(ExplorersCore.getPlugin(), () -> {
                        System.out.println("chunk loaded!");
                    },1);
                }
            }
        });
    }*/
/*
}
*/