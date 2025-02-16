package me.softsheep6.explorersCore;

import me.softsheep6.explorersCore.tasks.DamagePreventionTask;
import me.softsheep6.explorersCore.tasks.LightningCooldown;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

import java.util.Objects;


public class LightningSword implements Listener {

    public static boolean canStrikeLightning = true;

    public static void setCanStrikeLightning(boolean lightning) {canStrikeLightning = lightning;}

    @EventHandler void onEntityDamageEntity (EntityDamageByEntityEvent event) {
        Player player;
        if (event.getDamager() instanceof Player)
            player = (Player) event.getDamager();
        else return;
        Entity attacked = event.getEntity();

        Location loc = attacked.getLocation();

        // if the player hits an entity with the lightning sword, and the lightning is not on cooldown (2 sec),
        // then 20% chance to strike lightning at the entity's location.!
        if (player.getInventory().getItemInMainHand().getEnchantmentLevel(Enchantment.CHANNELING) == 1 && player.getInventory().getItemInMainHand().getType().equals(Material.DIAMOND_SWORD)) {
            if ((Math.random() * 100 < 20) && canStrikeLightning) {

                // strike lightning
                player.getWorld().strikeLightning(loc);

                // damages the enemy the same damage as netherite sword
                // (with sharp 5 cause im assuming ur gonna put sharp 5 on the lightning sword lol)
                if (player.hasPotionEffect(PotionEffectType.STRENGTH))
                    ((Damageable) attacked).damage(24);
                else
                    ((Damageable) attacked).damage(15);

                // sets the cooldown for lightning
                canStrikeLightning = false;
                new LightningCooldown(ExplorersCore.getPlugin()).runTaskLater(ExplorersCore.getPlugin(), 40);
                // prevents player from taking lightning damage for 2 seconds
                player.setFlySpeed(0.3F);
                new DamagePreventionTask(ExplorersCore.getPlugin()).runTaskLaterAsynchronously(ExplorersCore.getPlugin(), 40L);
            }
        }
    }
}