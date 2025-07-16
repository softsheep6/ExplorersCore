package me.softsheep6.explorersCore.misc_listeners;

import me.softsheep6.explorersCore.ExplorersCore;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Collection;
import java.util.List;

import static org.bukkit.Bukkit.getServer;

public class CustomFishingStuff implements Listener {

    @EventHandler
    public void onPlayerFish (PlayerFishEvent event) {

        // i used chatgpt for this method and i should throw myself out of a window because of it :thumbs ujp:

        // HOW WAS I SUPPOSED TO KNOW THAT "this" CAN MEAN EITHER THE PLAYER OR THE ROD IN DIFFERENT CONTEXTS
        // I MEAN YEA IT MAKES SENSE I JUST DIDNT THINK DATAPACKS WORKED LIKE THAT AND THE THOUGHT OF JUST
        // OVERRIDING FISHING WITH PLUGIN INSTEAD OF DATAPACK NEVER EVEN CROSSED MY MIND FOR WHATEVER REASON AGHHHHHHHHHHHHHHHHHHHH
        if (event.getState().equals(PlayerFishEvent.State.CAUGHT_FISH)) {
            // first check what hand the rod is in
            ItemStack mainHand = event.getPlayer().getInventory().getItemInMainHand();
            ItemStack offHand = event.getPlayer().getInventory().getItemInOffHand();
            ItemStack rod;
            if (mainHand.getType() == Material.FISHING_ROD && mainHand.hasItemMeta() && mainHand.getItemMeta().hasCustomModelData())
                rod = mainHand;
            else if (offHand.getType() == Material.FISHING_ROD && offHand.hasItemMeta() && offHand.getItemMeta().hasCustomModelData())
                rod = offHand;
            else return;

            // u can never be too sure
            if (rod.getType() == Material.FISHING_ROD
                    && rod.hasItemMeta()
                    && rod.getItemMeta().hasCustomModelData()) {

                event.setCancelled(true);
                Location loc = event.getHook().getLocation();
                if (rod.getItemMeta().getCustomModelDataComponent().getStrings().equals(List.of("midasrod"))) {
                    getServer().dispatchCommand(getServer().getConsoleSender(), "loot spawn " + loc.getX() + " " + loc.getY() + " " + loc.getZ() + " loot explorerscore:gameplay/fishing/midas_rod_loot");
                } else if (rod.getItemMeta().getCustomModelDataComponent().getStrings().equals(List.of("blablabla"))) {
                    // bla bla bla more rods here
                }

                new BukkitRunnable() {

                    @Override
                    public void run() {
                        if (loc.getWorld() == null) return; // how would world even be null i hate this
                        Collection<Entity> entities = loc.getWorld().getNearbyEntities(loc, 1, 1, 1);
                        double distance = 1000;
                        Entity closest = null;
                        for (Entity e : entities) {
                            if (!e.getType().equals(EntityType.ITEM)) continue;
                            if (e.getLocation().distance(loc) < distance) {
                                distance = e.getLocation().distance(loc);
                                closest = e;
                            }
                        }
                        if (closest == null) return;

                        Item item = (Item)closest;
                        Vector vec = event.getPlayer().getLocation().toVector().subtract(item.getLocation().toVector()).normalize().multiply(0.35);
                        vec.setY(0.4);
                        item.setVelocity(vec);

                    }
                }.runTaskLater(ExplorersCore.getPlugin(), 2);

                event.getHook().remove();
            }
        }
    }
}
