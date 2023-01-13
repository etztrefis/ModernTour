package trefis.moderntour;

import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;

public class Utils {
    public void broadcast(String message) {
        Bukkit.broadcastMessage(message.replace("&", "§"));
    }

    public void broadcast(TextComponent textComponent) {
        Bukkit.spigot().broadcast(textComponent);
    }

    public void broadcastTitle(String title, String message) {
        Bukkit.getOnlinePlayers().forEach((player) -> {
            player.sendTitle(title, message, 10, 40, 10);
        });
    }
}
