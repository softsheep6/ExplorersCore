package me.softsheep6.explorersCore.commands;

import me.softsheep6.explorersCore.ExplorersCore;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import javax.naming.Name;

public class SpawnProtection implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if (commandSender instanceof Player player) {
            World world = player.getWorld();
            PersistentDataContainer data = world.getPersistentDataContainer();


            // theres so many if statements and im not explaining any of them Ok
            if (strings.length == 0) {
                player.sendMessage(ChatColor.RED + "u didnt provide any arguments ...");
                player.sendMessage("do /spawnproteection set or /spawnprotection get !!!");
            } else {
                if (strings[0].equals("get")) {
                    if (data.get(new NamespacedKey(ExplorersCore.getPlugin(), "spx1"), PersistentDataType.INTEGER) == null) {
                        player.sendMessage(ChatColor.RED + "spawn protection is currently not set!");
                        player.sendMessage("to set, do something like /spawnprotection set -5 5 -10 10 -5 5 whichll protect x -5 to 5, y -10 to 10, and z -5 to 5");
                        return true;
                    }
                    int x1 = data.get(new NamespacedKey(ExplorersCore.getPlugin(), "spx1"), PersistentDataType.INTEGER);
                    int y1 = data.get(new NamespacedKey(ExplorersCore.getPlugin(), "spy1"), PersistentDataType.INTEGER);
                    int z1 = data.get(new NamespacedKey(ExplorersCore.getPlugin(), "spz1"), PersistentDataType.INTEGER);
                    int x2 = data.get(new NamespacedKey(ExplorersCore.getPlugin(), "spx2"), PersistentDataType.INTEGER);
                    int y2 = data.get(new NamespacedKey(ExplorersCore.getPlugin(), "spy2"), PersistentDataType.INTEGER);
                    int z2 = data.get(new NamespacedKey(ExplorersCore.getPlugin(), "spz2"), PersistentDataType.INTEGER);
                    player.sendMessage("current spawn protection size: x " + x1 + " to " + x2 + ", y " + y1 + " to " + y2 + ", z " + z1 + " to " + z2);
                } else if (strings[0].equals("set")) {
                    if (strings.length != 7) {
                        player.sendMessage(ChatColor.RED + "wrong amount of arguments!! there should be 6 following the \"set\"");
                        player.sendMessage("for example, /spawnprotection set -5 5 -10 10 -5 5 will protect x -5 to 5, y -10 to 10, and z -5 to 5");
                    } else {
                        try {
                            data.set(new NamespacedKey(ExplorersCore.getPlugin(), "spx1"), PersistentDataType.INTEGER, Integer.valueOf(strings[1]));
                            data.set(new NamespacedKey(ExplorersCore.getPlugin(), "spx2"), PersistentDataType.INTEGER, Integer.valueOf(strings[2]));
                            data.set(new NamespacedKey(ExplorersCore.getPlugin(), "spy1"), PersistentDataType.INTEGER, Integer.valueOf(strings[3]));
                            data.set(new NamespacedKey(ExplorersCore.getPlugin(), "spy2"), PersistentDataType.INTEGER, Integer.valueOf(strings[4]));
                            data.set(new NamespacedKey(ExplorersCore.getPlugin(), "spz1"), PersistentDataType.INTEGER, Integer.valueOf(strings[5]));
                            data.set(new NamespacedKey(ExplorersCore.getPlugin(), "spz2"), PersistentDataType.INTEGER, Integer.valueOf(strings[6]));
                            player.sendMessage(ChatColor.GREEN + "successfully set spawn protection!");
                        } catch (NumberFormatException e) {
                            player.sendMessage(ChatColor.RED + "those r not numbers !!!!!!");
                        }
                    }
                } else {
                    player.sendMessage(ChatColor.RED + "do /spawnproteection set or /spawnprotection get !!!");
                }
            }
        }


        return true;
    }
}
