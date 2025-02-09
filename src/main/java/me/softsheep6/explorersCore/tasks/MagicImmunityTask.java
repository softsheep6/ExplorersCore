package me.softsheep6.explorersCore.tasks;

import me.softsheep6.explorersCore.ExplorersCore;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class MagicImmunityTask extends BukkitRunnable {

    ExplorersCore plugin;

    public MagicImmunityTask(ExplorersCore plugin) {
        this.plugin = plugin;
    }

    // goes through players online and sees which one has the increased fly speed (aka dragons breath immunity)
    // and sets it back to normal (ran 10 seconds after dragon egg right click)
    @Override
    public void run() {
        for(Player player : plugin.getServer().getOnlinePlayers()) {
            if (player.getFlySpeed() == 0.2F)
                player.setFlySpeed(0.1F);
        }
    }
}
