package me.softsheep6.explorersCore.misc_listeners.death_listeners;

//import me.softsheep6.explorersCore.loot_tables.BlazeLoot;
import org.bukkit.entity.Blaze;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.loot.LootContext;

import java.util.Collection;
import java.util.Random;

// commented until brewing shenanigans is done

/*
// SO MUCH STOLEN CODE!!!! WOW!!! ALL CREDITS GO TO https://www.spigotmc.org/threads/help-with-custom-loot-tables.514477/#post-4208461 !!!!
// YOU CANNOT MAKE ME LEARN DATAPACKS PLUGINS ARE ALL I NEED
public class BlazeDeath implements Listener {
    @EventHandler
    void onBlazeDeath (EntityDeathEvent event) {
        if (event.getEntity().getLastDamageCause() == null) {
            return;
        }
        //actually the 2nd half of the if statement Was made by me cause spigot added an easier way to get damage source (getDamageSource)
        if (event.getEntity() instanceof Blaze && event.getDamageSource().getCausingEntity() instanceof Player player) {

            LootContext context =
                    new LootContext.Builder(event.getEntity().getLocation())
                            .killer(player)
                            .lootedEntity(event.getEntity())
                            .build();
            Collection<ItemStack> extraItems = new BlazeLoot().populateLoot(new Random(), context);

            event.getDrops().addAll(extraItems);
            }
    }

}
*/