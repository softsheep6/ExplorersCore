package me.softsheep6.explorersCore.items.craftable;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityToggleGlideEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class Parachute implements Listener {

    // worse craftable version of elytra
    @EventHandler
    public void onRightClick (PlayerInteractEvent event) {

        Player p = event.getPlayer();
        ItemStack holding = p.getInventory().getItemInMainHand();

        // if player right clicks while holding parachute
        // and parachute isnt on cooldown
        // make them GLIDE !
        if ((event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)
                && holding.getType().equals(Material.WHITE_CARPET)
                && holding.containsEnchantment(Enchantment.FEATHER_FALLING)) {
            if (p.getCooldown(Material.WHITE_CARPET) == 0) {
                p.setGliding(true);
                p.setCooldown(Material.WHITE_CARPET, 2400); //2400
            }
        }

        // this is to ensure fireworks cant be used when parachuting
        // if player right clicks with a firework, and is currently parachuting, cancel the event
        if ((event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)
                && (p.getInventory().getItemInMainHand().getType().equals(Material.FIREWORK_ROCKET)
                || p.getInventory().getItemInOffHand().getType().equals(Material.FIREWORK_ROCKET))) {
            if (holding.getType().equals(Material.WHITE_CARPET) && holding.containsEnchantment(Enchantment.FEATHER_FALLING)) {
                event.setCancelled(true);
            }
        }
    }

    // prevents placing parachute
    @EventHandler
    public void onBlockPlace (BlockPlaceEvent event) {
        if (event.getBlock().getType().equals(Material.WHITE_CARPET) && event.getPlayer().getInventory().getItemInMainHand().containsEnchantment(Enchantment.FEATHER_FALLING)) {
            event.setCancelled(true);
        }
    }

    // this is needed or else server will immediately cancel the gliding due to lack of elytra
    @EventHandler
    public void onGlide (EntityToggleGlideEvent event) {
        if (!(event.getEntity() instanceof Player p)) return;

        // if this event is called due to the server attempting to stop the parachuting player from gliding,
        // and the player is holding the parachute,
        // and the player isnt on the ground, cancel the event!
        if (!event.isGliding()
                && p.getInventory().getItemInMainHand().getType().equals(Material.WHITE_CARPET)
                && p.getInventory().getItemInMainHand().containsEnchantment(Enchantment.FEATHER_FALLING)
                && !((Entity) p).isOnGround()) {
            event.setCancelled(true);
        }
    }
}
