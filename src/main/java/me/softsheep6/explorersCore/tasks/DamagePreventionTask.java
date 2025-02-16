package me.softsheep6.explorersCore.tasks;

import me.softsheep6.explorersCore.ExplorersCore;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class DamagePreventionTask extends BukkitRunnable {

    ExplorersCore plugin;

    public DamagePreventionTask(ExplorersCore plugin) {
        this.plugin = plugin;
    }

    // this was originally only for dragons breath damage cancelling but i reused it for lightning bolt damage cancelling too

    // goes through players online and sees which one has the increased fly speed (aka dragons breath immunity OR lightning bolt immunity)
    // and sets it back to normal
    @Override
    public void run() {
        for(Player player : plugin.getServer().getOnlinePlayers()) {
            if (player.getFlySpeed() == 0.2F)
                player.setFlySpeed(0.1F);
            else if (player.getFlySpeed() == 0.3F)
                player.setFlySpeed(0.1F);
        }
    }
}
