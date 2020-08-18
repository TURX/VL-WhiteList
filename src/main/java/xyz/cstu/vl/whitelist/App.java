package xyz.cstu.vl.whitelist;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class App extends JavaPlugin {
    FileConfiguration config = getConfig();
    Boolean enabled = false;
    List<String> whitelistOrig = new ArrayList<String>(), whitelist = new ArrayList<String>();

    @Override
    public void onEnable() {
        config.addDefault("enabled", false);
        config.addDefault("ids", whitelistOrig);
        saveDefaultConfig();
        enabled = config.getBoolean("enabled");
        whitelistOrig = config.getStringList("ids");
        loadWhiteList();
        getLogger().info("[VL WhiteList] " + (enabled ? "Enabled" : "Started but not enabled"));
        if (enabled)
            getServer().getPluginManager().registerEvents(new Join(this), this);
        getCommand("wl").setExecutor(new CommandProc(this));
    }

    @Override
    public void onDisable() {
        saveConfig();
        getLogger().info("[VL WhiteList] Saved and stopped");
    }

    public void loadWhiteList() {
        whitelist.clear();
        for (String i : whitelistOrig) {
            whitelist.add(i.toUpperCase());
        }
    }
}
