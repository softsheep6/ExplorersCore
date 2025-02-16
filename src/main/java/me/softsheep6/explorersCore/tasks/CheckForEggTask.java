package me.softsheep6.explorersCore.tasks;

import me.softsheep6.explorersCore.ExplorersCore;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class CheckForEggTask extends BukkitRunnable {

    ExplorersCore plugin;

    public CheckForEggTask(ExplorersCore plugin) {
        this.plugin = plugin;
    }


    @Override
    public void run() {
        // this task runs every tick
        // when ran, checks if anyone on the server is holding a dragon egg in their main/off hand
        // if so, give them the effects and set the eggPlayer variable to that player
        // then, check if the eggPlayer exists and is not holding the egg,
        // and if so remove the effects and set the eggPlayer back to null
        // this works because there is only 1 dragon egg meaning only 1 person that can have the effects !
        //IT WORKS YESS
        Player eggPlayer = ExplorersCore.getPlugin().playerWithEgg;
        for(Player player : plugin.getServer().getOnlinePlayers()) {
            if (player.getInventory().getItemInMainHand().getType().equals(Material.DRAGON_EGG) || player.getInventory().getItemInOffHand().getType().equals(Material.DRAGON_EGG)) {
                PotionEffect eff = new PotionEffect(PotionEffectType.STRENGTH, -1, 1);
                eff.apply(player);
                PotionEffect eff2 = new PotionEffect(PotionEffectType.FIRE_RESISTANCE, -1, 0);
                eff2.apply(player);

                ExplorersCore.getPlugin().playerWithEgg = player;
            }
        }


        if (eggPlayer != null && (!eggPlayer.getInventory().getItemInMainHand().getType().equals(Material.DRAGON_EGG) && !eggPlayer.getInventory().getItemInOffHand().getType().equals(Material.DRAGON_EGG))) {
            eggPlayer.removePotionEffect(PotionEffectType.STRENGTH);
            eggPlayer.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
            ExplorersCore.getPlugin().playerWithEgg = null;
        }
    }
}
