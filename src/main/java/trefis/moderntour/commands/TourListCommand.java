package trefis.moderntour.commands;

import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import trefis.moderntour.Main;
import trefis.moderntour.Utils;
import trefis.moderntour.sql.Database;

import java.util.LinkedHashMap;
import java.util.UUID;

public class TourListCommand {
    public static boolean executeCommand(Player player, Main plugin) {
        if (!plugin.isTourStarted) {
            Utils.sendMessage(player, "&6[ModernTour] &cTour has not started yet.");
            return true;
        }
        Utils.sendMessage(player, " ");
        Utils.sendMessage(player, "&6 Modern tour list");
        Utils.sendMessage(player, " ");

        if (Database.request.getTourPlayers().size() == 0) {
            Utils.sendMessage(player, "&a Tour list is empty.");
            return true;
        }

        TextComponent textComponent = new TextComponent();
        LinkedHashMap<UUID, String> map = Database.request.getAllTourPlayers();

        for (int i = 0; i < map.size(); ++i) {
            UUID uuid = (UUID) map.keySet().toArray()[i];
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);
            String playerName = offlinePlayer.getName();
            if (!offlinePlayer.isOnline()) {
                textComponent.addExtra("&c[OFFLINE] &r");
            }
            if (plugin.currentPlayer.equals(offlinePlayer.getUniqueId())) {
                textComponent.addExtra("&6→&r");
            } else {
                switch (map.get(uuid)) {
                    case "queue":
                        textComponent.addExtra("&a");
                        break;
                    case "party":
                        textComponent.addExtra("&b");
                        break;
                    case "toured":
                        textComponent.addExtra("&7");
                        break;
                }
            }
            textComponent.addExtra(playerName + "&r ");
            textComponent.addExtra(" ");
        }
        player.spigot().sendMessage(textComponent);

        return true;
    }
}
