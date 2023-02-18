package trefis.moderntour.events;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCreativeEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import trefis.moderntour.Main;
import trefis.moderntour.Utils;
import trefis.moderntour.sql.Database;

public class Events implements Listener {
    private final Main plugin;

    public Events(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInvClick(InventoryClickEvent e) {
        if (plugin.isTourStarted) {
            if (e.getWhoClicked().getName().equals(plugin.partyOwner)) {
                try {
                    Material item = e.getCurrentItem().getType();
                    if (item != Material.PLAYER_HEAD) {
                        return;
                    }

                    String displayName = e.getCurrentItem().getItemMeta().getDisplayName();
                    if (!displayName.equals("§aNext")) {
                        return;
                    }

                    e.setCancelled(true);
                } catch (Exception var4) {
                }

            }
        }
    }

    @EventHandler
    public void creativeEvent(InventoryCreativeEvent e) {
        if (plugin.isTourStarted) {
            if (!e.getWhoClicked().getName().equals(plugin.partyOwner)) {
                try {
                    String name = e.getCursor().getItemMeta().getDisplayName();
                    if (!name.equals("§aNext")) {
                        return;
                    }

                    e.setCancelled(true);
                } catch (Exception var3) {
                }

            }
        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent e) {
        if (plugin.isTourStarted) {
            if (e.getPlayer().getName().equals(plugin.partyOwner)) {
                try {
                    Material item = e.getItemDrop().getItemStack().getType();
                    if (item != Material.PLAYER_HEAD) {
                        return;
                    }

                    String displayName = e.getItemDrop().getItemStack().getItemMeta().getDisplayName();
                    if (!displayName.equals("§aNext")) {
                        return;
                    }

                    e.setCancelled(true);
                } catch (Exception var4) {
                }

            }
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if (plugin.isTourStarted && e.getPlayer().hasPermission("tour.admin") && (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) && e.getPlayer().getName().equals(plugin.partyOwner)) {
            Material item = e.getPlayer().getInventory().getItemInMainHand().getType();
            if (item == Material.PLAYER_HEAD) {
                ItemStack head = e.getPlayer().getInventory().getItemInMainHand();
                String displayName = head.getItemMeta().getDisplayName();
                if (displayName.equals("§aNext")) {
                    if ("§aNext".equals(displayName)) {
                        Bukkit.dispatchCommand(e.getPlayer(), "tour next");
                        e.setCancelled(true);
                    }

                }
            }
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if (plugin.isTourStarted) {
            if (Database.request.getTourPlayers().contains(p.getUniqueId())) {
                Utils.sendMessage(p, "&6[ModernTour] &aВы все еще в очереди обхода.");
            } else if (!plugin.partyOwner.equals(p.getName())) {
                TextComponent textComponent = new TextComponent("§6[ModernTour] §bОбход уже начался, заходите в очередь скорее!\n");
                textComponent.addExtra("§aНажмите чтобы зайти в очередь обхода ");

                TextComponent joinButton = new TextComponent("[JOIN]");
                joinButton.addExtra(" ");
                joinButton.setColor(ChatColor.GOLD);
                joinButton.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tour join"));
                joinButton.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, (new ComponentBuilder(ChatColor.RED + "Join tour")).create()));

                textComponent.addExtra(joinButton);
                p.spigot().sendMessage(textComponent);
            }
        }
    }
}
