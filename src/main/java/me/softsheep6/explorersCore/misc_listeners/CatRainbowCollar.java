package me.softsheep6.explorersCore.misc_listeners;

import me.softsheep6.explorersCore.ExplorersCore;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Cat;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.scheduler.BukkitRunnable;

// anything but physics homework.......
// this was just made for fun it doesnt really work
// (as in if you unload chunks/restart server it breaks)
// it does work if the above arent true tho!! its coolish i guess
public class CatRainbowCollar implements Listener {

    @EventHandler
    public void onPlayerInteractEntity (PlayerInteractEntityEvent event) {

        // return if entity clicked isnt a cat or if the player isnt holding a name tag
        if (!event.getRightClicked().getType().equals(EntityType.CAT) || !event.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.NAME_TAG))
            return;

        BukkitRunnable collarTask = new BukkitRunnable() {
            @Override
            public void run() {
                Cat cat = (Cat) event.getRightClicked();
                // cancel + return if cat died noooo:(
                if (cat.getHealth() == 0) {
                    this.cancel();
                    return;
                }

                int index = 0;
                DyeColor[] colors = DyeColor.values();
                // this finds the current collar color and converts it to an int. this is so i can just add 1 to the color enum and yea
                for (int i = 0; i < colors.length; i++) {
                    if (colors[i].equals(cat.getCollarColor())) {
                        index = i + 1;
                    }
                }
                if (index == 15) index = 0;
                cat.setCollarColor(colors[index]);

            }
        };

        BukkitRunnable nameTask = new BukkitRunnable() {
            @Override
            public void run() {
                if (event.getRightClicked().getName().equals("jeb_")) {
                    collarTask.runTaskTimer(ExplorersCore.getPlugin(), 0, 5);
                }
            }
        };
        nameTask.runTaskLater(ExplorersCore.getPlugin(), 10); // delay on running the task so that the cat actually gets named first
    }
}
