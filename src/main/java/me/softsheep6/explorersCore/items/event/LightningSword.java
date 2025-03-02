package me.softsheep6.explorersCore.items.event;

import me.softsheep6.explorersCore.ExplorersCore;
import me.softsheep6.explorersCore.tasks.DamageAfterLightningTask;
import me.softsheep6.explorersCore.tasks.LightningCooldownTask;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.damage.DamageType;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;


public class LightningSword implements Listener {

    public static boolean canStrikeLightning = true;

    public static void setCanStrikeLightning(boolean lightning) {canStrikeLightning = lightning;}

    // these are so the attacked entity can be referenced from the DamageAfterLightningTask task
    public static Entity attacked;
    public static Entity getAttacked() {return attacked;}

    @EventHandler void onEntityDamageEntity (EntityDamageByEntityEvent event) {
        Player player;
        if (event.getDamager() instanceof Player)
            player = (Player) event.getDamager();
        else return;
        attacked = event.getEntity();

        Location loc = attacked.getLocation();

        // if the player hits an entity with the lightning sword, and the lightning is not on cooldown (2 sec),
        // then 20% chance to strike lightning at the entity's location.!
        if (player.getInventory().getItemInMainHand().getEnchantmentLevel(Enchantment.CHANNELING) == 1 && player.getInventory().getItemInMainHand().getType().equals(Material.DIAMOND_SWORD)) {
            if ((Math.random() * 100 < 20) && canStrikeLightning) {

                // strike lightning
                player.getWorld().strikeLightning(loc);

                // deals 1 heart of damage ignoring armor to the attacked
                ((LivingEntity) attacked).setMaximumNoDamageTicks(0);
                ((LivingEntity) attacked).setNoDamageTicks(0);
                new DamageAfterLightningTask(ExplorersCore.getPlugin()).runTaskLater(ExplorersCore.getPlugin(), 1);

                // sets the cooldown for lightning
                canStrikeLightning = false;
                new LightningCooldownTask(ExplorersCore.getPlugin()).runTaskLater(ExplorersCore.getPlugin(), 40);

            }
        }
    }

    // this makes the lightning sword's holder immune to lightning damage !
    @EventHandler void onEntityDamage (EntityDamageEvent event) {
        Player player;
        if (event.getEntity() instanceof Player)
            player = (Player) event.getEntity();
        else return;

        // if the player is holding a diamond sword, and it has channeling, and the damage type is a lightning bolt,
        // um. Yk what happens.
        if (player.getInventory().getItemInMainHand().getType().equals(Material.DIAMOND_SWORD)
           && player.getInventory().getItemInMainHand().containsEnchantment(Enchantment.CHANNELING)
           && event.getDamageSource().getDamageType().equals(DamageType.LIGHTNING_BOLT)) {
            event.setCancelled(true);
        }
    }
}