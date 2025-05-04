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
    public boolean onCommand(@NotNull CommandSender p, @NotNull Command command, @NotNull String s, @NotNull String[] args) {



            if (args.length < 2 || args.length > 3) {
                p.sendMessage(ChatColor.RED + "wrong arguments do /namecolor <name> <color> [format] stupid!!!!");
                return true;
            } else {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (args[0].equalsIgnoreCase(player.getName())) {
                        // iterate through chatcolors, if first argument is a valid color save it, if second exists and is a valid format save it,
                        // then set the displayname and playerlistname depending on which saved thingys arent null
                        ChatColor colorCode = null;
                        ChatColor formatCode = null;
                        for (ChatColor color : ChatColor.values()) {
                            if (args[1].equalsIgnoreCase(color.name())) {
                                colorCode = color;
                                if (!color.isColor()) {
                                    p.sendMessage(ChatColor.RED + "wrong arguments do /namecolor <name> <color> [format] stupid!!!!");
                                    return true;
                                }
                            }
                            if (args.length == 3 && args[2].equalsIgnoreCase(color.name())) {
                                formatCode = color;
                                if (!color.isFormat()) {
                                    p.sendMessage(ChatColor.RED + "wrong arguments do /namecolor <name> <color> [format] stupid!!!!");
                                    return true;
                                }
                            }

                        }
                        if (colorCode == null) {
                            p.sendMessage(ChatColor.RED + "thats not a color!!!");
                            StringBuilder colors = new StringBuilder();
                            StringBuilder formats = new StringBuilder();
                            for (ChatColor color : ChatColor.values()) {
                                if (color.isColor()) colors.append(color.name()).append(" ");
                                if (color.isFormat()) formats.append(color.name()).append(" ");
                            }
                            p.sendMessage("colors: " + colors);
                            p.sendMessage("formats: " + formats);
                            return true;
                        }
                        else if (formatCode == null) {
                            player.setDisplayName(colorCode + player.getName() + ChatColor.RESET);
                            player.setPlayerListName(colorCode + player.getName() + ChatColor.RESET);
                            p.sendMessage(ChatColor.GREEN + "successfully set name color!");
                            return true;
                        } else {
                            player.setDisplayName("" + colorCode + formatCode + player.getName() + ChatColor.RESET);
                            player.setPlayerListName("" + colorCode + formatCode + player.getName() + ChatColor.RESET);
                            p.sendMessage(ChatColor.GREEN + "successfully set name color and format!");
                            return true;
                        }

                    }
                }
                p.sendMessage(ChatColor.RED + "thats not a player!!!");
            }



        return true;
    }
}
