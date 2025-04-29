package me.softsheep6.explorersCore.blocks;

import me.softsheep6.explorersCore.ExplorersCore;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import com.jeff_media.morepersistentdatatypes.*;

import java.util.ArrayList;
import java.util.Objects;

public class HeatedBrewingStand implements Listener {

    // when a brewing stand with a magma block under it is right clicked:
    //      iterate through a persistentdatacontainer of Locations and see if any match the location of the brewing stand
    //          if one does, set an inventory to an itemstack array from another pdc with parallel indexes to the location one
    //          and then open that inventory
    //          otherwise do that same stuff but with a new itemstack array and then save it and the location to pdcs
    //          to be accessed on future opens

    // used to open an inventory with a new heated brewing stand inventory (as in, this is the first time a heated brewing stand has been opened)
    public Inventory brewInventory() {
        Inventory inv;
        inv = Bukkit.createInventory(null, 54, "Heated Brewing Stand");
        ItemStack[] items = new ItemStack[54];
        // TODO: do gui stuff lol
        inv.setContents(items);
        return inv;
    }
    // used on subsequent openings of the heated brewing stand
    public Inventory brewInventory(ItemStack[] items) {
        Inventory inv;
        inv = Bukkit.createInventory(null, 54, "Heated Brewing Stand");
        inv.setContents(items);
        return inv;
    }

    @EventHandler
    public void onPlayerInteract (PlayerInteractEvent event) {

        if (event.getClickedBlock() == null) return; // no npe! not on my watch!
        // if a brewing stand is interacted with that has a magma block beneath it, open the heated brewing stand inventory
        if (event.getClickedBlock().getType().equals(Material.BREWING_STAND)) {
            Location loc = event.getClickedBlock().getLocation();

            if (loc.add(0, -1, 0).getBlock().getType().equals(Material.MAGMA_BLOCK)) {
                event.setCancelled(true);

                Inventory inv;
                PersistentDataContainer pdc = event.getPlayer().getWorld().getPersistentDataContainer();
                NamespacedKey locKey = new NamespacedKey(ExplorersCore.getPlugin(), "brewlocations");
                NamespacedKey itemKey = new NamespacedKey(ExplorersCore.getPlugin(), "brewitems");

                // make sure the pdc exists first
                if (pdc.get(locKey, DataType.LOCATION_ARRAY) != null) {
                    // iterates through the pdc, if any locations equal the current location then use that inventory instead of creating a new one
                    for (Location l : Objects.requireNonNull(pdc.get(locKey, DataType.LOCATION_ARRAY))) {
                        if (loc.equals(l)) {
                            inv = brewInventory(pdc.get(itemKey, DataType.ITEM_STACK_ARRAY));
                            event.getPlayer().openInventory(inv);
                            return;
                        }
                    }
                    // if no location is found, make a new inventory and save to pdc
                    // pdc datatype is an array, but when things r changed uhhhhhhhhh arraylist now???? or something this is so confusing ahhhhhhhhhh
                    /*ArrayList<Location> locArr = pdc.get(locKey, DataType.asArrayList(DataType.LOCATION_ARRAY));
                    inv = brewInventory();
                    assert locArr != null;
                    locArr.add(loc);
                    pdc.set(locKey, DataType.asArrayList(DataType.LOCATION_ARRAY), locArr);
                    pdc.set(itemKey, DataType.asArrayList(DataType.ITEM_STACK_ARRAY), inv.getContents());*/
                }
                // if it doesnt exist, make a new inventory and save to the pdc
                inv = brewInventory();
//                pdc.set(locKey, DataType.LOCATION_ARRAY, );

            }
        }
    }

    // this prevents normal magma blocks from being placed beneath a brewing stand
    // also merged another class that disabled placing inferno block with this one!
    @EventHandler
    public void onBlockPlace (BlockPlaceEvent event) {

        // if the placed block is a magma block:
        //      if its an inferno block, and it isnt below a brewing stand, cancel the placement. otherwise let it be placed below the brewing stand.
        //      otherwise if its below a brewing stand, and is not an inferno block, cancel the placement.
        if (event.getBlock().getType().equals(Material.MAGMA_BLOCK)) {
            if (event.getItemInHand().containsEnchantment(Enchantment.FLAME)) {
                if (!event.getBlock().getLocation().add(0, 1, 0).getBlock().getType().equals(Material.BREWING_STAND))
                    event.setCancelled(true);
                else
                    return;
            }
            if (event.getBlock().getLocation().add(0, 1, 0).getBlock().getType().equals(Material.BREWING_STAND))
                event.setCancelled(true);
        }
    }
}
