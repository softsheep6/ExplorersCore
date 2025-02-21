package me.softsheep6.explorersCore.tasks;

import me.softsheep6.explorersCore.ExplorersCore;
import me.softsheep6.explorersCore.LightningSword;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.scheduler.BukkitRunnable;

public class DamageAfterLightningTask extends BukkitRunnable {

    ExplorersCore plugin;

    public DamageAfterLightningTask(ExplorersCore plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        Entity attacked = LightningSword.getAttacked();
        ((Damageable) attacked).damage(2);
        ((LivingEntity) attacked).setMaximumNoDamageTicks(20);
    }
}
