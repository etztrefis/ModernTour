package trefis.moderntour.commands;

import org.bukkit.entity.Player;
import trefis.moderntour.Main;
import trefis.moderntour.Utils;
import trefis.moderntour.sql.Database;

public class TourLeaveCommand {
    public static boolean executeCommand(Player player, Main plugin) {
        if (!plugin.isTourStarted) {
            Utils.sendMessage(player, "&6[ModernTour] &cОбход еще не начался.");
            return true;
        }
        if (!Database.request.getTourPlayers().contains(player.getUniqueId())) {
            Utils.sendMessage(player, "&6[ModernTour] &cВы еще не зашли в очередь, а уже хотите выйти?");
            return true;
        }

        Database.request.tourRemovePlayer(player.getUniqueId());
        Utils.sendMessage(player, "&6[ModernTour] &aВы успешно вышли из очереди обхода.");
        return true;
    }
}
