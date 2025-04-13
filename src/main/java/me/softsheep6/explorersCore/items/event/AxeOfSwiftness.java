package me.softsheep6.explorersCore.items.event;

import me.softsheep6.explorersCore.ExplorersCore;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class AxeOfSwiftness implements Listener {

    // you dont get comments for this one sorry.
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack axe = player.getInventory().getItemInMainHand();
        if (axe.containsEnchantment(Enchantment.SWIFT_SNEAK) && axe.getType().equals(Material.DIAMOND_AXE) && (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK))) {
            Vector dir = player.getLocation().getDirection();
            if (player.getCooldown(axe) == 0) {
                player.setVelocity(new Vector(dir.getX() * 1.25, .4, dir.getZ() * 1.25));
                player.setCooldown(axe, 80);
            }
        }
    }
}
