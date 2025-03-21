package me.softsheep6.explorersCore.tasks;

import me.softsheep6.explorersCore.ExplorersCore;
import me.softsheep6.explorersCore.items.event.LightningSword;
import org.bukkit.scheduler.BukkitRunnable;

public class LightningCooldownTask extends BukkitRunnable {

    ExplorersCore plugin;

    public LightningCooldownTask(ExplorersCore plugin) {
        this.plugin = plugin;
    }

    // self explanatory !
    @Override
    public void run() {
        LightningSword.setCanStrikeLightning(true);
    }
}
