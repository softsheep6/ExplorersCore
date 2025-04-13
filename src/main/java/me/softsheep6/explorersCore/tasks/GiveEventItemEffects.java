package me.softsheep6.explorersCore.tasks;

import me.softsheep6.explorersCore.ExplorersCore;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class GiveEventItemEffects extends BukkitRunnable {

    ExplorersCore plugin;

    public GiveEventItemEffects(ExplorersCore plugin) {
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
        // this is now reused for axe of swiftness too ok
        Player eggPlayer = ExplorersCore.getPlugin().playerWithEgg;
        Player axePlayer = ExplorersCore.getPlugin().playerWithAxe;
        for(Player player : plugin.getServer().getOnlinePlayers()) {
            if (player.getInventory().getItemInMainHand().getType().equals(Material.DRAGON_EGG) || player.getInventory().getItemInOffHand().getType().equals(Material.DRAGON_EGG)) {
                PotionEffect eff = new PotionEffect(PotionEffectType.STRENGTH, -1, 1);
                eff.apply(player);
                PotionEffect eff2 = new PotionEffect(PotionEffectType.FIRE_RESISTANCE, -1, 0);
                eff2.apply(player);

                ExplorersCore.getPlugin().playerWithEgg = player;
            }
            // very short if statement yea
            if ((player.getInventory().getItemInMainHand().getType().equals(Material.DIAMOND_AXE) && player.getInventory().getItemInMainHand().containsEnchantment(Enchantment.SWIFT_SNEAK)) || (player.getInventory().getItemInOffHand().getType().equals(Material.DIAMOND_AXE) && player.getInventory().getItemInOffHand().containsEnchantment(Enchantment.SWIFT_SNEAK))) {
                PotionEffect eff = new PotionEffect(PotionEffectType.SPEED, -1, 1);
                eff.apply(player);

                ExplorersCore.getPlugin().playerWithAxe = player;
            }
        }


        if (eggPlayer != null && (!eggPlayer.getInventory().getItemInMainHand().getType().equals(Material.DRAGON_EGG) && !eggPlayer.getInventory().getItemInOffHand().getType().equals(Material.DRAGON_EGG))) {
            eggPlayer.removePotionEffect(PotionEffectType.STRENGTH);
            eggPlayer.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
            ExplorersCore.getPlugin().playerWithEgg = null;
        }
        if (axePlayer != null && ((!axePlayer.getInventory().getItemInMainHand().getType().equals(Material.DIAMOND_AXE) && !axePlayer.getInventory().getItemInMainHand().containsEnchantment(Enchantment.SWIFT_SNEAK)) && (!axePlayer.getInventory().getItemInOffHand().getType().equals(Material.DIAMOND_AXE) && !axePlayer.getInventory().getItemInOffHand().containsEnchantment(Enchantment.SWIFT_SNEAK)))) {
            axePlayer.removePotionEffect(PotionEffectType.SPEED);
            ExplorersCore.getPlugin().playerWithAxe = null;
        }
    }
}
