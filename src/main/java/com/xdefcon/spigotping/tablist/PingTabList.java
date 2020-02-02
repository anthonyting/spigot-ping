package com.xdefcon.spigotping.tablist;

import com.xdefcon.spigotping.SpigotPing;
import com.xdefcon.spigotping.utils.PingUtil;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class PingTabList extends BukkitRunnable {
    public static void updateTabPing(Player player, SpigotPing plugin) {
        String currentName = player.getDisplayName();
        String prefix = plugin.getConfig().getString("tablist.prefix");
        if (!prefix.equals("")) {
            player.setPlayerListName(ChatColor.translateAlternateColorCodes('&',
                    prefix.replace("%ping%", "" + PingUtil.getPing(player))) + " " + currentName);
        }
        String suffix = plugin.getConfig().getString("tablist.suffix");
        if (!suffix.equals("")) {
            player.setPlayerListName(currentName + " " + ChatColor.translateAlternateColorCodes('&',
                    suffix.replace("%ping%", "" + PingUtil.getPing(player))));
        }
    }

    public void run() {
        SpigotPing plugin = SpigotPing.getInstance();
        for (Player player : plugin.getServer().getOnlinePlayers()) {
            updateTabPing(player, plugin);
        }
    }
}

