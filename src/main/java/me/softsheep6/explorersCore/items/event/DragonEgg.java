package me.softsheep6.explorersCore.items.event;

import me.softsheep6.explorersCore.ExplorersCore;
import me.softsheep6.explorersCore.tasks.DamagePreventionTask;
import org.bukkit.*;
import org.bukkit.damage.DamageType;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import java.util.ArrayList;
import java.util.List;

// sorry for how long and horrible and bad this class is IM SIMPLY the best at coding.
public class DragonEgg implements Listener {

    // event listener 4 clicking with the egg & spawning the breath and stuff
    @EventHandler
    void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack itemInMainHand = player.getInventory().getItemInMainHand();
        ItemStack itemInOffHand = player.getInventory().getItemInOffHand();
        Location loc = player.getLocation();
        boolean inMainHand = false;
        boolean inOffHand = false;

        if (itemInMainHand.getType() == Material.DRAGON_EGG)
            inMainHand = true;
        if (itemInOffHand.getType() == Material.DRAGON_EGG)
            inOffHand = true;

        /*  | event.getAction() == Action.RIGHT_CLICK_BLOCK (removed because activating when placing blocks is annoying) */
        // if the player right clicks the air while sneaking, and the dragon egg is in either of their hands, and the egg isnt on cooldown,
        // DO THINGS    !
        if ((event.getAction() == Action.RIGHT_CLICK_AIR) && (inMainHand | inOffHand) && player.isSneaking() && player.getCooldown(Material.DRAGON_EGG) == 0) {

            // create dragons breath and give it thingys ok
            Entity breath = player.getWorld().spawnEntity(loc, EntityType.AREA_EFFECT_CLOUD);

            ((AreaEffectCloud) breath).setParticle(Particle.DRAGON_BREATH);
            ((AreaEffectCloud) breath).setBasePotionType(PotionType.STRONG_HARMING);
            ((AreaEffectCloud) breath).setReapplicationDelay(10);
            ((AreaEffectCloud) breath).setDuration(200);
            ((AreaEffectCloud) breath).setRadius(5);
            ((AreaEffectCloud) breath).setRadiusPerTick((float)-0.0025);
            ((AreaEffectCloud) breath).setWaitTime(20);


            // sound effec mmmmmm
            player.getWorld().playSound(loc, Sound.ENTITY_ENDER_DRAGON_GROWL, 1, 0);

            // resistance for 10 seconds
            PotionEffect eff = new PotionEffect(PotionEffectType.RESISTANCE, 200, 0);
            eff.apply(player);

            // adds the Lore in case it doesnt have it !
            List<String> lore = new ArrayList<>();
            lore.add(ChatColor.AQUA + "" + ChatColor.ITALIC + "omelette.");
            lore.add(ChatColor.RESET + "" + ChatColor.LIGHT_PURPLE + ChatColor.BOLD + "  ABILITY:" + ChatColor.RESET + " " + ChatColor.WHITE + "Right Click to summon dragon's breath");
            lore.add(ChatColor.RESET + " " + ChatColor.WHITE + " and receive 10 seconds of Resistance I!");
            lore.add(ChatColor.RESET + "" + ChatColor.LIGHT_PURPLE + ChatColor.BOLD + "  PASSIVE:" + ChatColor.RESET + " " + ChatColor.WHITE + "Holding the egg grants Strength II and ");
            lore.add(ChatColor.RESET + " " + ChatColor.WHITE + " Fire Resistance!");
            lore.add("");
            lore.add("" + ChatColor.DARK_PURPLE + ChatColor.BOLD + "EVENT ITEM");

            // checks which hand the egg is in, adds the meta to it
            if (inMainHand) {
                ItemMeta eggMeta = itemInMainHand.getItemMeta();
                assert eggMeta != null; //STOP YELLING AT ME INTELLIJ!!!
                eggMeta.setLore(lore);
                eggMeta.setDisplayName(ChatColor.RESET + "The Dragon Egg");
                itemInMainHand.setItemMeta(eggMeta);
            }
            if (inOffHand) {
                ItemMeta eggMeta = itemInOffHand.getItemMeta();
                assert eggMeta != null;//AHHHHHH
                eggMeta.setLore(lore);
                eggMeta.setDisplayName(ChatColor.RESET + "The Dragon Egg");
                itemInOffHand.setItemMeta(eggMeta);
            }

            // changes the player's fly speed for 12 seconds (this is how the immunity to magic damage is detected)
            player.setFlySpeed(0.2F);
            System.out.println("set player fly speed! " + player);
            new DamagePreventionTask(ExplorersCore.getPlugin()).runTaskLaterAsynchronously(ExplorersCore.getPlugin(), 240L);

            // cooldown! so cool HAh get it lolz
            player.setCooldown(Material.DRAGON_EGG, 900); //900
        }
    }

    // prevents dragon egg from being placed by cancelling the event
    @EventHandler void onBlockPlace(BlockPlaceEvent event) {
        Material placed = event.getBlockPlaced().getType();

        if (placed == Material.DRAGON_EGG) {
            event.setCancelled(true);
        }

    }


    // this checks if a player damaged by an area effect cloud has used egg ability (flyspeed = 0.2)
    // and if so gives immunity to the effect by cancelling it
    @EventHandler void onEntityDamage (EntityDamageEvent event) {
        Player player;
        if (event.getEntity() instanceof Player)
            player = (Player) event.getEntity();
        else return;
        boolean damagerExists = event.getDamageSource().getDamageType() != null;
        if (!damagerExists) return; // PLEASE stop giving me NullPointerException PLEASE PLEASE PLEASE
        // i dont think i need damagerExists anymore but im keeping it whatever

        // cancels the dragons breath damage for the egg's user
        if (player.getFlySpeed() == 0.2F && damagerExists && event.getDamageSource().getDamageType().equals(DamageType.INDIRECT_MAGIC)) {
            event.setCancelled(true);
        }
    }
}
