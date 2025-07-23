package me.softsheep6.explorersCore.misc_listeners;

import me.softsheep6.explorersCore.ExplorersCore;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import org.bukkit.util.Transformation;
import org.bukkit.util.Vector;
import org.joml.AxisAngle4f;
import org.joml.Vector3f;


public class LightningDragonFight implements Listener {

    static double[] xCoords;
    static double[] zCoords;
    static final Location spawnLoc = new Location(Bukkit.getWorlds().getFirst(), 0.5, 63, 0.5);

    public static void startLightningDragonSpawn() {

        World world = Bukkit.getWorlds().getFirst();
        // ok i learned a few things here:
        // the .spawn asks for 3 parameters: a location, self explanatory,
        // a class, which i was not sure how to pass in but i have now learned it just wants the interface of the entity with a .class at the end
        // (so itd be BlockDisplay.class since im spawning a block display in this case)
        // and the 3rd parameter is optional but needed in this case
        // it basically just wants a function, kinda like a method but single use so theres no public or return or whatever
        // u just give it a name (entity in this case but it can be whatever, the name is basically like a "this", it just references the block display),
        // then -> { function here }
        // and whatevers in the curly braces is ran BEFORE it finishes defining the blockdisplay
        // which is nice in this case so it doesnt just spawn an empty block display, and instead gives it a block before spawning!
        // awesome knowledge here yep https://docs.papermc.io/paper/dev/display-entities/
        ItemDisplay egg = world.spawn(spawnLoc, ItemDisplay.class, entity -> {
            entity.setItemStack(new ItemStack(Material.DRAGON_EGG));
        });

        // animation things
        int duration = 20; // ticks per half rotation

        // some stuff taken from https://docs.papermc.io/paper/dev/display-entities/#transformation-1
        // actually like only the first if statement and 2nd & 3rd to last lines i replaced most of it lol

        new BukkitRunnable() {
            int index = 0;

            @Override
            public void run() {

                if (!egg.isValid()) { // display was removed from the world, abort task
                    this.cancel();
                    return;
                } else if (index >= 30) { // after 30 seconds, stop doing the things here
                    egg.remove();
                    this.cancel();
                    return;
                }

                // egg transformation stuff
                egg.setTransformation(new Transformation(
                        new Vector3f(0f, (float) (1.5+index), 0f), // transform 1 block per second
                        new AxisAngle4f(((float) Math.toRadians(270*(index+1))), 0f, 1f, 0f), // some amount of rotation idek at this point
                        new Vector3f((float) (1+(0.25*index))), // scale by 1.1x every second
                        new AxisAngle4f() // right rotation (none)
                ));
                egg.setInterpolationDelay(0);
                egg.setInterpolationDuration(duration); // duration matches with tasks duration so its in sync!

                index++;
            }
        }.runTaskTimer(ExplorersCore.getPlugin(), 5, duration);


        // then the particles too
        final int radius = 8;
        final int pointCount = 64;
        final Location finalEggLoc = spawnLoc.clone().add(0, 15, 0);

        getPoints(finalEggLoc.getX(), finalEggLoc.getZ(), radius, pointCount);

        BukkitRunnable particleTask = new BukkitRunnable() {
            int i = 0;

            @Override
            public void run() {

                if (i >= pointCount) i = 0;
                Location l = new Location(world, xCoords[i], finalEggLoc.getY(), zCoords[i]);
                world.spawnParticle(Particle.DRAGON_BREATH, l, 4, 0.1, 0.1, 0.1, null);
                //System.out.println("spawned particle at " + l.getX() + ", " + l.getY() + ", " + l.getZ());

                i++;

            }
        };
        particleTask.runTaskTimer(ExplorersCore.getPlugin(), 0, 1);

        // also make it storming
        world.setStorm(true);

        // cancel particles after 30 seconds
        // then SUMMON THE DRAGON!!!!
        new BukkitRunnable() {
            @Override
            public void run() {
                startLightningDragonFight(particleTask);
            }
        }.runTaskLater(ExplorersCore.getPlugin(), 600); // 600 ticks = 30 seconds to match with egg disappearing
    }






    public static void startLightningDragonFight(BukkitRunnable particleTask) {
        particleTask.cancel();
        World world = Bukkit.getWorlds().getFirst();

        // lighting ooo
        world.strikeLightningEffect(spawnLoc);
        world.strikeLightningEffect(spawnLoc);
        world.strikeLightningEffect(spawnLoc);

        // glowing effect
        Scoreboard board = Bukkit.getScoreboardManager().getMainScoreboard();
        Team team;
        if (board.getTeam("dragonteam") == null)
            team = board.registerNewTeam("dragonteam");
        else team = board.getTeam("dragonteam");
        assert team != null;
        team.setColor(ChatColor.YELLOW);

        // message, spawn the dragon
        Bukkit.broadcastMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "⚡ THE LIGHTNING DRAGON HAS AWOKEN! ⚡");
        EnderDragon dragon = world.spawn(spawnLoc.clone().add(0, 15, 0), EnderDragon.class, enderDragon -> {
            enderDragon.setCustomName(ChatColor.YELLOW + "Lightning Dragon");
            enderDragon.getAttribute(Attribute.MAX_HEALTH).setBaseValue(400); // twice the normal dragon health
            enderDragon.setHealth(400);
            team.addEntry(enderDragon.getUniqueId().toString());
            enderDragon.setGlowing(true);
            enderDragon.setPhase(EnderDragon.Phase.CIRCLING); // so it doesnt sit still for a while
        });

        // bossbar
        BossBar bar = dragon.getBossBar();
        assert bar != null;
        for (Player p : Bukkit.getOnlinePlayers()) {
            bar.addPlayer(p);
        }
        bar.addFlag(BarFlag.PLAY_BOSS_MUSIC); // sure i guess
        bar.setColor(BarColor.YELLOW);
        bar.setStyle(BarStyle.SOLID);
        bar.setTitle(ChatColor.YELLOW + "Lightning Dragon");
        bar.setVisible(true);

        // random attack every minute
        new BukkitRunnable() {
            @Override
            public void run() {
                if (dragon.isDead()) {
                    this.cancel();
                    System.out.println("lightning dragon dead!");
                    return;
                }

                //int numOfAttacks = 1;
                //int attack = (int) (Math.random() * ((numOfAttacks) + 1)); // random attack depending on number of attacks there are
                int attack = 0;
                // if i add more attacks, add more cases and uncomment the randomizer
                switch (attack) {
                    case 0:
                        attackLightning(spawnLoc);
                }

            }
        }.runTaskTimer(ExplorersCore.getPlugin(), 1200, 1200); // 1200 ticks = 1 minute
    }


    // probably its only attack unless i have more time
    public static void attackLightning(Location loc) {
        World world = Bukkit.getWorlds().getFirst();
        int boxRadius = 100;

        Bukkit.broadcastMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "<Lightning Dragon> " + ChatColor.RESET + ChatColor.YELLOW + "MORE LIGHTNING!!!");
        for (Entity e : world.getNearbyEntities(loc, boxRadius, boxRadius, boxRadius)) {
            if (e.getType() == EntityType.PLAYER) {
                world.strikeLightning(e.getLocation());
                e.setVelocity(e.getVelocity().add(new Vector(0, 2, 0)));
            }
        }
    }



    // dragon no break blocks
    @EventHandler
    public void onEntityExplodeBlocks(EntityExplodeEvent event) {
        if (event.getEntity().getType() != EntityType.ENDER_DRAGON) return;

        EnderDragon dragon = (EnderDragon) event.getEntity();
        if (dragon.isGlowing()) {
            event.setCancelled(true);
        }
    }

    // dragon dieth, say things and drop sword
    @EventHandler
    public void onDragonDeath(EntityDeathEvent event) {
        if (event.getEntity().getType() != EntityType.ENDER_DRAGON) return;

        EnderDragon dragon = (EnderDragon) event.getEntity();
        if (dragon.isGlowing()) {

            new BukkitRunnable() {

                int message = 1;
                @Override
                public void run() {
                    if (message > 9) this.cancel();

                    switch (message) {
                        case 1:
                            Bukkit.broadcastMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "<Lightning Dragon> " + ChatColor.RESET + ChatColor.YELLOW + "NOOO...the storm...it finally fades...");
                            break;
                        case 2:
                            Bukkit.broadcastMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "<Lightning Dragon> " + ChatColor.RESET + ChatColor.YELLOW + "It seems like you've proven that you, too, are capable of wielding the power of the storm.");
                            break;
                        case 3:
                            Bukkit.broadcastMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "<Lightning Dragon> " + ChatColor.RESET + ChatColor.YELLOW + "In return, I offer you my most prized possession:");
                            break;
                        case 4:
                            Bukkit.broadcastMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "<Lightning Dragon> " + ChatColor.RESET + ChatColor.YELLOW + "The " + ChatColor.BOLD + "Lightning Sword");
                            break;
                        case 5:
                            Bukkit.broadcastMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "<Lightning Dragon> " + ChatColor.RESET + ChatColor.YELLOW + "Use my power responsibly.");
                            break;
                        case 6:
                            Bukkit.broadcastMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "<Lightning Dragon> " + ChatColor.RESET + ChatColor.YELLOW + "I won't be the last dragon you'll meet, and I can only hope you're ready for what's to come.");
                            break;
                        case 7:
                            Bukkit.broadcastMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "<Lightning Dragon> " + ChatColor.RESET + ChatColor.YELLOW + "Goodbye...");
                            break;
                        case 9:
                            event.getEntity().getWorld().dropItem(event.getEntity().getLocation(), ExplorersCore.getPlugin().sword);
                            Bukkit.broadcastMessage(ChatColor.ITALIC + "" + ChatColor.GRAY + "The lightning sword has fallen from the heavens...");
                            break;

                    }

                    message++;
                }
            }.runTaskTimer(ExplorersCore.getPlugin(), 20, 40); //2s delay between each message

        }
    }



    // borrowed from squid game plugin lol
    private static void getPoints(double x0, double y0, int r, int noOfDividingPoints) {

        double angle;

        xCoords = new double[noOfDividingPoints];
        zCoords = new double[noOfDividingPoints];

        for(int i = 0 ; i < noOfDividingPoints ; i++)
        {
            angle = i * (360.0/noOfDividingPoints);

            xCoords[i] = x0 + r * Math.cos(Math.toRadians(angle));
            zCoords[i] = y0 + r * Math.sin(Math.toRadians(angle));

        }
    }
}
