package me.softsheep6.explorersCore.items.event;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.RayTraceResult;

import java.util.ArrayList;

// at last ...
public class WardenBow implements Listener {

    @EventHandler
    public void onPlayerInteract (PlayerInteractEvent event) {

        Player p = event.getPlayer();
        ItemStack item = event.getItem(); if (item == null) return;

        // if player left clicks while holding the warden bow, and it isnt on cooldown, DO STUF
        if ((event.getAction().equals(Action.LEFT_CLICK_AIR) || event.getAction().equals(Action.LEFT_CLICK_BLOCK))
                && item.getType().equals(Material.BOW)
                && item.containsEnchantment(Enchantment.IMPALING)
                && p.getCooldown(Material.BOW) == 0) {

            //Predicate<Entity> test = i -> i != p; // this is a filter to make sure the ray doesnt detect the player using the bow // nvm idk how predicates even work

            // below line is mostly taken from https://www.spigotmc.org/threads/why-is-world-raytraceentities-always-returning-null.535198/#post-4458224
            RayTraceResult ray = p.getWorld().rayTraceEntities(p.getEyeLocation().add(p.getLocation().getDirection()), p.getEyeLocation().getDirection(), 30, entity -> !entity.getUniqueId().equals(p.getUniqueId()));
            if (ray == null) return;
            Entity hit = ray.getHitEntity();
            if (hit == null) return;

            //um wait....am igonna need to use recursion to make the particle trail....gulp....
            // surely theres an easier way right
            // ok i had to chatgpt this part SORRY i just dont really know how to do that linear interpolation jazz sorry
            // well i mean i do now i have Learned thanks to artificial intelligence  cowabunga
            Location loc1 = p.getLocation();
            Location loc2 = hit.getLocation();
            int divisions = 7; // this number is how many particles will be between the bow user and the hit entity, minus 1. eg divisions = 9 will spawn 10 particles
            ArrayList<Location> locs = new ArrayList<>();

            // will add interpolated points to locs arraylist, to be used to spawn shriek particle at each point emulating the real sonic boom thingy
            for (int i = 0; i <= divisions; i++) {
                double t = (double) i / divisions;

                double x = (1 - t) * loc1.getX() + t * loc2.getX();
                double y = (1 - t) * loc1.getY() + t * loc2.getY();
                double z = (1 - t) * loc1.getZ() + t * loc2.getZ();

                Location interpolated = new Location(loc1.getWorld(), x, y+1, z);
                locs.add(interpolated);
            }

            for (Location loc : locs) {
                // spawn particle
                if (loc.getWorld() == null) return;
                loc.getWorld().spawnParticle(Particle.SONIC_BOOM, loc, 1);
            }

            // apply damage to the poor poor victim.....
            ((Damageable)hit).damage(5, p); // 2 and a half hearts damage

            // apply darkness effect to the even poorer victim SO SAD.....
            if (!hit.isDead()) { // only if they didnt die to the damage ofc
                ((LivingEntity)hit).addPotionEffect(new PotionEffect(PotionEffectType.DARKNESS, 120, 0, true)); // darkness 1 for 6 seconds
            }

            // cooldown
            p.setCooldown(Material.BOW, 300); // 15 second

            // im surprised at how easy this was i thought replicating the wardens sonic boom attack would be harder
            // but its really just some particles and some damage at the end of the day
            // asking chatgpt how to do the linear interpolation thing made me feel really stupid not gonna lie i hate using ai when coding
            // which is stupid i know but i think coding things on ur own is much more fun and
            // makes u remember things better BUT i really had no clue how to even start on it soooo for the first time
            // since starting this plugin i chatgpted that for loop that got the locations of particles SORRY !!!!! have u heard! about the flightless bird! theyre quite content theyre on the ground to see them dont look up look down

        }
    }

    @EventHandler
    public void onBowShoot (EntityShootBowEvent event) {
        // return if bow isnt a warden bow or isnt shot by a player or if shot projectile isnt an arrow somehow
        if (!(event.getEntity() instanceof Player)
                || event.getBow() == null
                || !event.getBow().containsEnchantment(Enchantment.IMPALING)
                || !(event.getProjectile() instanceof Arrow arrow)) return;

        // add darkness 1 for 6 seconds to the arrow, so if the player uses tipped arrows their effects wont be overwritten by the darkness
        arrow.addCustomEffect(new PotionEffect(PotionEffectType.DARKNESS, 120, 0, true), false);
        // disallow picking up the arrow since the above makes it a weird arrow that doesnt stack with other arrows...
        arrow.setPickupStatus(AbstractArrow.PickupStatus.DISALLOWED);
    }
}
