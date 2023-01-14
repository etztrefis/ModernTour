package trefis.moderntour.commands;

import org.bukkit.entity.Player;
import trefis.moderntour.Main;
import trefis.moderntour.Utils;
import trefis.moderntour.sql.Database;

public class TourLeaveCommand {
    public static boolean executeCommand(Player player, Main plugin) {
        if (!plugin.isTourStarted) {
            Utils.sendMessage(player, "&6[ModernTour] &cTour has not started yet.");
            return true;
        }
        if (!Database.request.getTourPlayers().contains(player.getUniqueId())) {
            Utils.sendMessage(player, "&6[ModernTour] &cYou have not joined the queue yet.");
            return true;
        }

        Database.request.tourRemovePlayer(player.getUniqueId());
        Utils.sendMessage(player, "&6[ModernTour] &cYou just left from the tour queue.");
        return true;
    }
}
