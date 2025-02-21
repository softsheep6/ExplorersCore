package me.softsheep6.explorersCore.tasks;

import me.softsheep6.explorersCore.ExplorersCore;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class ReaddHealthBoostTask  extends BukkitRunnable {

    ExplorersCore plugin;

    public ReaddHealthBoostTask(ExplorersCore plugin) {
        this.plugin = plugin;
    }

    // this task gives crown wearers their effect back. currently only used after totem pop because totems clear effects
    @Override
    public void run() {
        for(Player player : plugin.getServer().getOnlinePlayers()) {
            if (player.getInventory().getHelmet() != null && player.getInventory().getHelmet().containsEnchantment(Enchantment.LOYALTY)) {
                player.addPotionEffect(PotionEffectType.HEALTH_BOOST.createEffect(-1, 4));
            }

        }
    }
}
