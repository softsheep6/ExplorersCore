package me.softsheep6.explorersCore;

import me.softsheep6.explorersCore.tasks.MagicImmunityTask;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

        if ((event.getAction() == Action.RIGHT_CLICK_AIR | event.getAction() == Action.RIGHT_CLICK_BLOCK) && (inMainHand | inOffHand) && player.getCooldown(Material.DRAGON_EGG) == 0) {
            /*spawns dragons breath as a projectile instead of at the player (Bad) (Not the good)
            event.getPlayer().getWorld().spawnEntity(event.getPlayer().getLocation(), EntityType.AREA_EFFECT_CLOUD); */

            // create dragons breath and give it thingys ok
            Entity breath = player.getWorld().spawnEntity(loc, EntityType.AREA_EFFECT_CLOUD);

            ((AreaEffectCloud) breath).setParticle(Particle.DRAGON_BREATH);
            ((AreaEffectCloud) breath).setBasePotionType(PotionType.STRONG_HARMING);
            ((AreaEffectCloud) breath).setReapplicationDelay(10);
            ((AreaEffectCloud) breath).setDuration(200);
            ((AreaEffectCloud) breath).setRadius(5);
            ((AreaEffectCloud) breath).setRadiusPerTick((float)-0.01);
            ((AreaEffectCloud) breath).setWaitTime(20);


            // sound effec mmmmmm
            player.getWorld().playSound(loc, Sound.ENTITY_ENDER_DRAGON_GROWL, 1, 0);

            // resistance for 10 seconds
            PotionEffect eff = new PotionEffect(PotionEffectType.RESISTANCE, 100, 0);
            eff.apply(player);


            // adds the Lore in case it doesnt have it !
            List<String> lore = new ArrayList<>();
            lore.add(ChatColor.AQUA + "" + ChatColor.ITALIC + "omelette.");
            lore.add(ChatColor.RESET + "" + ChatColor.LIGHT_PURPLE + ChatColor.BOLD + "  ABILITY:" + ChatColor.RESET + " " + ChatColor.WHITE + "Right Click to summon dragon's breath");
            lore.add(ChatColor.RESET + " " + ChatColor.WHITE + " and receive 5 seconds of Resistance I!");
            lore.add(ChatColor.RESET + "" + ChatColor.LIGHT_PURPLE + ChatColor.BOLD + "  PASSIVE:" + ChatColor.RESET + " " + ChatColor.WHITE + "Holding the egg grants Strength II and ");
            lore.add(ChatColor.RESET + " " + ChatColor.WHITE + " Fire Resistance!");

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

            // changes the player's fly speed for 10 seconds (this is how the immunity to magic damage is detected)
            player.setFlySpeed(0.2F);
            new MagicImmunityTask(ExplorersCore.getPlugin()).runTaskLaterAsynchronously(ExplorersCore.getPlugin(), 200L);

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


    // THIS METHOD IS for all the events involving the item in hand changing. since this block of codell be reused for each one.
    public void applyEffects(ItemStack item1, ItemStack item2, Player player) {
        if (Objects.equals(item1.getType(), Material.DRAGON_EGG)) {
            PotionEffect eff = new PotionEffect(PotionEffectType.STRENGTH, -1, 1);
            eff.apply(player);
            PotionEffect eff2 = new PotionEffect(PotionEffectType.FIRE_RESISTANCE, -1, 0);
            eff2.apply(player);
        } else if (!(Objects.equals(item1.getType(), Material.DRAGON_EGG)) && Objects.equals(item2.getType(), Material.DRAGON_EGG)) {
            player.removePotionEffect(PotionEffectType.STRENGTH);
            player.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
        } else {
            player.removePotionEffect(PotionEffectType.STRENGTH);
            player.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
        }
    }



    //
    // next 4 event listeners are all for giving egg effects.
    // is there a better way to do this ,Maybe posssibly idk we are doing it this way because i said so ok
    //



    // checks when player main hand changes if theyre now holding Egg.
    @EventHandler void onItemHeldChanges(PlayerItemHeldEvent event) {
        Player player = event.getPlayer();
        ItemStack previousItem;
        ItemStack newItem;
        if (player.getInventory().getItem(event.getPreviousSlot()) == null)
            previousItem = new ItemStack(Material.AIR);
        else
            previousItem = player.getInventory().getItem(event.getPreviousSlot());
        if (player.getInventory().getItem(event.getNewSlot()) == null)
            newItem = new ItemStack(Material.AIR);
        else
            newItem = player.getInventory().getItem(event.getNewSlot());

        assert previousItem != null;
        assert newItem != null;
        applyEffects(previousItem, newItem, player);
    }
    // checks when off hand / main hand changes and if theyre now holding Egg.
    @EventHandler void onSwapHandsItems(PlayerSwapHandItemsEvent event) {
        Player player = event.getPlayer();
        ItemStack mainItem;
        ItemStack offItem;

        if (event.getMainHandItem() == null)
            mainItem = new ItemStack(Material.AIR);
        else
            mainItem = event.getMainHandItem();
        if (event.getOffHandItem() == null)
            offItem = new ItemStack(Material.AIR);
        else
            offItem = event.getOffHandItem();

        assert mainItem != null;
        assert offItem != null;
        applyEffects(mainItem, offItem, player);
    }
    // checks when item is dropped and if its egg BYE BYE EFFECTs
    @EventHandler void onDropItem (PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        ItemStack dropped = event.getItemDrop().getItemStack();

        if (dropped.getType() == Material.DRAGON_EGG) {
            player.removePotionEffect(PotionEffectType.STRENGTH);
            player.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
        }

    }
    // checks if item picked up is egg and EFFECT GIVE
    @EventHandler void onPickUpItem (EntityPickupItemEvent event) {
        Player player;
        if (event.getEntity() instanceof Player)
            player = (Player) event.getEntity();
        else return;
        ItemStack picked = event.getItem().getItemStack();

        if (picked.getType() == Material.DRAGON_EGG) {
            PotionEffect eff = new PotionEffect(PotionEffectType.STRENGTH, -1, 1);
            eff.apply(player);
            PotionEffect eff2 = new PotionEffect(PotionEffectType.FIRE_RESISTANCE, -1, 0);
            eff2.apply(player);
        }

    }





    // this checks if a player damaged by an area effect cloud has used egg ability (flyspeed = 0.2)
    // and if so gives immunity to the effect by cancelling it
    @EventHandler void onEntityDamage (EntityDamageEvent event) {
        Player player;
        if (event.getEntity() instanceof Player)
            player = (Player) event.getEntity();
        else return;

        System.out.println(Objects.requireNonNull(event.getDamageSource().getCausingEntity()).getType());
        // cancels the dragons breath damage for the egg's user
        if (player.getFlySpeed() == 0.2F && Objects.requireNonNull(event.getDamageSource().getCausingEntity()).getType().equals(EntityType.AREA_EFFECT_CLOUD)) {
            event.setCancelled(true);
        }
    }
}
