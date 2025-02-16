package me.softsheep6.explorersCore.tasks;

import me.softsheep6.explorersCore.ExplorersCore;
import me.softsheep6.explorersCore.LightningSword;
import org.bukkit.scheduler.BukkitRunnable;

public class LightningCooldown  extends BukkitRunnable {

    ExplorersCore plugin;

    public LightningCooldown(ExplorersCore plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        LightningSword.setCanStrikeLightning(true);
    }
}
