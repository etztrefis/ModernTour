package trefis.moderntour;

import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.util.Vector;
import trefis.moderntour.sql.SQLWorker;

import java.util.ArrayList;

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

    public static void pluginStop(Main plugin){
        if(Bukkit.getOfflinePlayer(plugin.partyOwner).isOnline()){
            Player owner = Bukkit.getPlayer(plugin.partyOwner);
            owner.getInventory().setItem(4, null);
            Location location = owner.getLocation();
            World world = location.getWorld();

            for(int i = 0; i < 3; ++i) {
                Location newLocation = location.add((new Vector(Math.random() - 0.5D, 1.0D, Math.random() - 0.5D)).multiply(3));
                Firework fw = (Firework)world.spawnEntity(newLocation, EntityType.FIREWORK);
                FireworkMeta fwm = fw.getFireworkMeta();
                FireworkEffect effect = FireworkEffect.builder().flicker(true).withColor(Color.GREEN).withFade(Color.TEAL).with(FireworkEffect.Type.BALL_LARGE).trail(true).build();
                fwm.addEffect(effect);
                fwm.setPower(0);
                fw.setFireworkMeta(fwm);
            }
        }

        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(plugin.partyOwner);
        if(offlinePlayer.isOnline()){
            Player p = Bukkit.getPlayer(plugin.partyOwner);
            if(offlinePlayer.getName().equals(plugin.partyOwner));
            p.sendTitle("", "§6 The tour is over!", 10, 40, 10);
        }

        plugin.isTourStarted = false;
        plugin.partyOwner = "";
        plugin.party = new ArrayList<>();
        plugin.getConfig().set("isTourStarted", false);
        plugin.getConfig().set("partyOwner", "");
        plugin.saveConfig();

        Utils.broadcast(" \n&6" + "The tour is over! " + "\n ");
        SQLWorker.executeUpdate("TRUNCATE mt_data");
    }
}
