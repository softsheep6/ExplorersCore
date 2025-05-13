package me.softsheep6.explorersCore.items.event;

import me.softsheep6.explorersCore.ExplorersCore;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class HasteHammer implements Listener {

    // I FORGOT BUILD COMPETITION ENDS TODAY AHHHH QUICK MAKE THE PRIZE THINGY

    // haste potion effect is not used because it also increases attack speed and if im gonna be messing with attributes id rather just
    // set the block break speed attribute to be higher instead of attack speed to be lower cause thats Easier  ok
    @EventHandler
    public void onPlayerInteract (PlayerInteractEvent event) {

        Player p = event.getPlayer();
        ItemStack item = event.getPlayer().getInventory().getItemInMainHand();
        // if player is holding a haste hammer and it isnt on cooldown and they right clicked while sneaking,
        // give them "haste" and set the cooldown!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        if ((event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)
                && item.containsEnchantment(Enchantment.QUICK_CHARGE) && p.isSneaking() && p.getCooldown(Material.NETHERITE_PICKAXE) == 0
                && (item.getType().equals(Material.DIAMOND_PICKAXE) || item.getType().equals(Material.NETHERITE_PICKAXE))) {
            p.setCooldown(Material.NETHERITE_PICKAXE, 600);
            p.getAttribute(Attribute.BLOCK_BREAK_SPEED).setBaseValue(p.getAttribute(Attribute.BLOCK_BREAK_SPEED).getDefaultValue() * 2.6);//2.55<x<2.6
            // approximately haste 8, slightly over i think
            BukkitRunnable task = new BukkitRunnable() {
                @Override
                public void run() {
                    p.getAttribute(Attribute.BLOCK_BREAK_SPEED).setBaseValue(p.getAttribute(Attribute.BLOCK_BREAK_SPEED).getDefaultValue());
                }
            };
            task.runTaskLater(ExplorersCore.getPlugin(), 200);
        }
    }

}
