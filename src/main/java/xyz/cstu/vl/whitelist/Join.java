package xyz.cstu.vl.whitelist;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class Join implements Listener {
    App app;

    Join(App app) {
        this.app = app;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (!app.enabled)
            return;
        String name = event.getPlayer().getName();
        Boolean exist = app.whitelist.contains(name.toUpperCase());
        if (!exist)
            event.getPlayer().kickPlayer("You are not on the whitelist.\n您不在白名单上。\nお名前はホワイトリストにありません。");
        app.getLogger().info("[VL WhiteList] " + name + " is " +  (exist ? "" : "not ") + "existed on whitelist");
    }
}
