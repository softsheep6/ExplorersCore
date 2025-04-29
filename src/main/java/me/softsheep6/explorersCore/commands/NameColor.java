package me.softsheep6.explorersCore.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class NameColor implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        if (sender instanceof Player p) {

            if (args.length != 2) {
                p.sendMessage(ChatColor.RED + "wrong format do /namecolor <name> <color> stupid!!!!");
            } else {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (args[0].equalsIgnoreCase(player.getName())) {
                        for (ChatColor color : ChatColor.values()) {
                            if (args[1].equalsIgnoreCase(color.name())) {
                                player.setDisplayName(color + player.getName() + ChatColor.RESET);
                                player.setPlayerListName(color + player.getName() + ChatColor.RESET);
                                p.sendMessage(ChatColor.GREEN + "successfully set name color!");
                                return true;
                            }
                        }
                        p.sendMessage(ChatColor.RED + "thats not a color!!!");
                        StringBuilder colors = new StringBuilder();
                        for (ChatColor color : ChatColor.values()) {
                            colors.append(color.name()).append(" ");
                        }
                        p.sendMessage("colors: " + colors);
                        return true;
                    }
                }
                p.sendMessage(ChatColor.RED + "thats not a player!!!");
            }

        }


        return true;
    }
}
