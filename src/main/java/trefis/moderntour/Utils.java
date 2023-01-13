package trefis.moderntour;

import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Utils {

    public static void broadcast(String message) {
        Bukkit.broadcastMessage(message.replace("&", "§"));
    }

    public static void broadcast(TextComponent textComponent) {
        Bukkit.spigot().broadcast(textComponent);
    }

    public static void broadcastTitle(String title, String message) {
        Bukkit.getOnlinePlayers().forEach((player) -> {
            player.sendTitle(title, message, 10, 40, 10);
        });
    }

    public static void log(String message) {
        Bukkit.getConsoleSender().sendMessage(message.replace("&", "§"));
    }

    public static void sendMessage(Player player, String message) {
        player.sendMessage(message.replace("&", "§"));
    }
}
