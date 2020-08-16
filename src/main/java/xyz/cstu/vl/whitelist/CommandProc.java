package xyz.cstu.vl.whitelist;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandProc implements CommandExecutor {
    App app;

    CommandProc(App app) {
        this.app = app;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        switch (args.length) {
            case 1:
                switch (args[0]) {
                    case "list":
                        String res;
                        if (app.whitelistOrig.size() > 0) {
                            res = "[VL WhiteList] All whitelisted players: ";
                            for (String i : app.whitelistOrig)
                                res += i + ", ";
                            res = res.substring(0, res.length() - 2);
                            res += ".";
                        } else
                            res = "[VL WhiteList] No whitelisted player";
                        sender.sendMessage(res);
                        return true;
                    case "enable":
                        app.config.set("enabled", true);
                        app.enabled = true;
                        app.saveConfig();
                        sender.sendMessage("[VL WhiteList] Enabled");
                        return true;
                    case "disable":
                        app.config.set("enabled", false);
                        app.enabled = false;
                        app.saveConfig();
                        sender.sendMessage("[VL WhiteList] Disabled");
                        return true;
                    case "reload":
                        app.reloadConfig();
                        app.config = app.getConfig();
                        app.enabled = app.config.getBoolean("enabled");
                        app.whitelistOrig = app.config.getStringList("ids");
                        sender.sendMessage("[VL WhiteList] Reloaded");
                        return true;
                    default:
                        return false;
                }
            case 2:
                switch (args[0]) {
                    case "add":
                        if (!app.whitelist.contains(args[1].toUpperCase())) {
                            sender.sendMessage("[VL WhiteList] Added");
                            app.whitelistOrig.add(args[1]);
                            app.whitelist.add(args[1].toUpperCase());
                        } else
                            sender.sendMessage("[VL WhiteList] Already existed");
                        app.config.set("ids", app.whitelistOrig);
                        app.saveConfig();
                        return true;
                    case "remove":
                        if (!app.whitelistOrig.contains(args[1]))
                            sender.sendMessage("[VL WhiteList] Not exist");
                        else {
                            app.whitelistOrig.remove(args[1]);
                            sender.sendMessage("[VL WhiteList] Removed");
                        }
                        app.config.set("ids", app.whitelist);
                        app.saveConfig();
                        return true;
                    default:
                        return false;
                }
            default:
                return false;
        }
    }
}
