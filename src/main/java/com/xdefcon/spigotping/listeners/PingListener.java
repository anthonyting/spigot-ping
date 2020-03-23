package com.xdefcon.spigotping.listeners;

import com.xdefcon.spigotping.SpigotPing;
import com.xdefcon.spigotping.tablist.PingTabList;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class PingListener implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerChat(final AsyncPlayerChatEvent event) {
        PingTabList.updateTabPing(event.getPlayer(), SpigotPing.getInstance());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerJoin(final PlayerJoinEvent event) {
        SpigotPing plugin = SpigotPing.getInstance();
        plugin.getServer().getScheduler().runTaskLater(plugin,
                () -> PingTabList.updateTabPing(event.getPlayer(), plugin), 20L);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerDeath(final PlayerDeathEvent event) {
        PingTabList.updateTabPing(event.getEntity(), SpigotPing.getInstance());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onWorldChange(final PlayerChangedWorldEvent event) {
        PingTabList.updateTabPing(event.getPlayer(), SpigotPing.getInstance());
    }

}