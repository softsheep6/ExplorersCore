package me.softsheep6.explorersCore.misc_listeners;

import me.softsheep6.explorersCore.ExplorersCore;
import org.bukkit.*;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

// this is so stupid lol
public class DragonFightMessages implements Listener {

    @EventHandler
    public void onPlayerTeleport (PlayerTeleportEvent event) {

        Player p = event.getPlayer();
        if (p.getLocation().getWorld() == null) return; // how could this even be null why is this Nullable what
        // return if teleport isnt from an end portal and isnt going into the end
        if (!event.getCause().equals(PlayerTeleportEvent.TeleportCause.END_PORTAL) || !p.getLocation().getWorld().getEnvironment().equals(World.Environment.NORMAL))
            return;
        assert p.getWorld().getEnderDragonBattle() != null;
        // if dragon is alive and dragon hasnt died before make the dragon say Hi!
        BukkitRunnable task = new BukkitRunnable() {
            @Override
            public void run() {

                if (!p.getWorld().getPersistentDataContainer().has(new NamespacedKey(ExplorersCore.getPlugin(), "dragondead")))
                    p.getWorld().getPersistentDataContainer().set(new NamespacedKey(ExplorersCore.getPlugin(), "dragondead"), PersistentDataType.BOOLEAN, false);
                if (!(p.getWorld().getEnderDragonBattle().getEnderDragon() == null) // getEnderDragon returns null if no dragon is found
                        && !p.getWorld().getPersistentDataContainer().get(new NamespacedKey(ExplorersCore.getPlugin(), "dragondead"), PersistentDataType.BOOLEAN))
                    p.sendMessage(ChatColor.DARK_PURPLE + "<The Ender Dragon>" + ChatColor.LIGHT_PURPLE + " Welcome to my domain...");

            }
        };
        // message is delayed to ensure the player is actually in the end, so getEnderDragonBattle wont be null
        task.runTaskLater(ExplorersCore.getPlugin(), 20);

    }

    // death messages
    @EventHandler
    public void onEntityDeath (EntityDeathEvent event) {
        // return if dead entity isnt a dragon
        if (!event.getEntity().getType().equals(EntityType.ENDER_DRAGON)) return;

        // set dragon dead pdc to true for the message in the above eventhandler
        event.getEntity().getWorld().getPersistentDataContainer().set(new NamespacedKey(ExplorersCore.getPlugin(), "dragondead"), PersistentDataType.BOOLEAN, true);

        final int[] message = {0}; // no idea why this has to be an array but ok thanks intellij
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.getWorld().getEnvironment().equals(World.Environment.THE_END)) {

                BukkitRunnable task = new BukkitRunnable() { // i dont know why this needs to be casted either What everrrrrrr
                    @Override
                    public void run() {
                        if (message[0] > 9) this.cancel();
                        switch (message[0]) {
                            case 0:
                                p.sendMessage(ChatColor.DARK_PURPLE + "<The Ender Dragon>" + ChatColor.LIGHT_PURPLE + " AHHHHHHHHHH");
                                p.playSound(p.getLocation(), Sound.ENTITY_ENDER_DRAGON_HURT, 1, 0);
                                break;
                            case 1:
                                p.sendMessage(ChatColor.DARK_PURPLE + "<The Ender Dragon>" + ChatColor.LIGHT_PURPLE + " I never wanted war");
                                p.playSound(p.getLocation(), Sound.ENTITY_ENDER_DRAGON_HURT, 1, 0);
                                break;
                            case 2:
                                p.sendMessage(ChatColor.DARK_PURPLE + "<The Ender Dragon>" + ChatColor.LIGHT_PURPLE + " I lived in silence and protected what matters - my home, my sky, my son;");
                                p.playSound(p.getLocation(), Sound.ENTITY_ENDER_DRAGON_HURT, 1, 0);
                                break;
                            case 3:
                                p.sendMessage(ChatColor.DARK_PURPLE + "<The Ender Dragon>" + ChatColor.LIGHT_PURPLE + " I didn’t attack out of rage, I fought to protect...");
                                p.playSound(p.getLocation(), Sound.ENTITY_ENDER_DRAGON_HURT, 1, 0);
                                break;
                            case 4:
                                p.sendMessage(ChatColor.DARK_PURPLE + "<The Ender Dragon>" + ChatColor.LIGHT_PURPLE + " You came not with peace but with fire - you shattered my sky, destroyed the crystals that kept me alive, and called it a challenge.");
                                p.playSound(p.getLocation(), Sound.ENTITY_ENDER_DRAGON_HURT, 1, 0);
                                break;
                            case 5:
                                p.sendMessage(ChatColor.DARK_PURPLE + "<The Ender Dragon>" + ChatColor.LIGHT_PURPLE + " But I was never your enemy; I was but a mother");
                                p.playSound(p.getLocation(), Sound.ENTITY_ENDER_DRAGON_HURT, 1, 0);
                                break;
                            case 6:
                                p.sendMessage(ChatColor.DARK_PURPLE + "<The Ender Dragon>" + ChatColor.LIGHT_PURPLE + " Now I fade, not as a monster, but as a guardian, a memory of the past");
                                p.playSound(p.getLocation(), Sound.ENTITY_ENDER_DRAGON_HURT, 1, 0);
                                break;
                            case 7:
                                p.sendMessage(ChatColor.DARK_PURPLE + "<The Ender Dragon>" + ChatColor.LIGHT_PURPLE + " Fading into the end sky");
                                p.playSound(p.getLocation(), Sound.ENTITY_ENDER_DRAGON_HURT, 1, 0);
                                break;
                            case 8:
                                p.sendMessage(ChatColor.DARK_PURPLE + "<The Ender Dragon>" + ChatColor.LIGHT_PURPLE + " All I ask of you, explorer...");
                                p.playSound(p.getLocation(), Sound.ENTITY_ENDER_DRAGON_HURT, 1, 0);
                                break;
                            case 9:
                                p.sendMessage(ChatColor.DARK_PURPLE + "<The Ender Dragon>" + ChatColor.LIGHT_PURPLE + " ...is to keep my child safe.");
                                p.playSound(p.getLocation(), Sound.ENTITY_ENDER_DRAGON_HURT, 1, 0);
                                break;

                        }
                        message[0]++;
                    }
                };
                task.runTaskTimer(ExplorersCore.getPlugin(), 0, 40);
            }
        }
    }
}
