package trefis.moderntour.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import trefis.moderntour.Main;
import trefis.moderntour.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TourCommand implements TabExecutor {
    private final Main plugin;

    public TourCommand(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("&6[ModernTour] This command must be run on behalf of the player.");
            return true;
        }

        Player player = (Player) sender;
        if (args.length == 0) {
            Utils.sendMessage(player, "&6[ModernTour]&r Version " + plugin.version);
            Utils.sendMessage(player, "&6[ModernTour]&r " + plugin.description);
            Utils.sendMessage(player, "&6[ModernTour]&r Made with &c❤&r by &ntrefis");
        } else {
            switch (args[0]) {
                case "help":
                    if (player.hasPermission("tour.admin")) {
                        TourHelpCommand.executeCommandWithPermission(player, plugin);
                    } else {
                        TourHelpCommand.executeCommandWithoutPermission(player, plugin);
                    }
                    break;
                case "party":
                    if (player.hasPermission("tour.admin")) {
                        TourPartyCommand.executeCommand(player, plugin, args);
                    } else {
                        Utils.sendMessage(player, "&6[ModernTour] &cYou don't have permission to run this command.");
                    }
                    break;
                case "join":
                    TourJoinCommand.executeCommand(player, plugin);
                    break;
                case "leave":
                    TourLeaveCommand.executeCommand(player, plugin);
                    break;
                case "next":
                    if (player.hasPermission("tour.admin")) {
                        TourNextCommand.executeCommand(player, plugin);
                    } else {
                        Utils.sendMessage(player, "&6[ModernTour] &cYou don't have permission to run this command.");
                    }
                    break;
                case "list":
                    TourListCommand.executeCommand(player, plugin);
                    break;
                case "stop":
                    if (player.hasPermission("tour.admin")) {
                        TourStopCommand.executeCommand(player, plugin);
                    } else {
                        Utils.sendMessage(player, "&6[ModernTour] &cYou don't have permission to run this command.");
                    }
                    break;
                case "start":
                    if (player.hasPermission("tour.admin")) {
                        TourStartCommand.executeCommand(player, plugin);
                    } else {
                        Utils.sendMessage(player, "&6[ModernTour] &cYou don't have permission to run this command.");
                    }
                    break;
                default:
                    if (player.hasPermission("tour.admin")) {
                        TourHelpCommand.executeCommandWithPermission(player, plugin);
                    } else {
                        TourHelpCommand.executeCommandWithoutPermission(player, plugin);
                    }
            }
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        List<String> subArguments = new ArrayList<>();
        if (!commandSender.hasPermission("tour.admin")) {
            if (strings.length == 1) {
                subArguments.add("join");
                subArguments.add("leave");
                subArguments.add("help");
                subArguments.add("list");
            }
            return subArguments;
        } else {
            if (strings.length == 1) {
                subArguments.add("start");
                subArguments.add("stop");
                subArguments.add("party");
                subArguments.add("next");
                subArguments.add("join");
                subArguments.add("leave");
                subArguments.add("help");
                subArguments.add("list");
                return subArguments;
            }
            if (strings[0].equals("party")) {
                if (strings.length == 2) {
                    List<String> addictionalCommands = new ArrayList<>();
                    addictionalCommands.add("add");
                    addictionalCommands.add("remove");
                    addictionalCommands.add("list");
                    return addictionalCommands;
                }
            }
            return null;
        }
    }
}
