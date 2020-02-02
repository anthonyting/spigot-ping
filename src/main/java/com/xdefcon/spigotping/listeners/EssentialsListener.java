package com.xdefcon.spigotping.listeners;

import com.xdefcon.spigotping.SpigotPing;
import com.xdefcon.spigotping.tablist.PingTabList;
import net.ess3.api.events.NickChangeEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class EssentialsListener implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onNickChange(NickChangeEvent event) {
        SpigotPing plugin = SpigotPing.getInstance();
        Player player = event.getAffected().getBase();
        plugin.getServer().getScheduler().runTaskLater(plugin,
                () -> PingTabList.updateTabPing(player, plugin), 1L);
    }
}
