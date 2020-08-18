package xyz.cstu.vl.whitelist;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Server;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginLogger;
import org.bukkit.plugin.PluginManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ PluginLogger.class, App.class, FileConfiguration.class, Join.class, PluginCommand.class, PluginManager.class, Listener.class, Plugin.class })

public class AppTest {
    @Test
    public void notOnEnabled() throws Exception {
        FileConfiguration config = PowerMockito.mock(FileConfiguration.class);
        PowerMockito.when(config.getBoolean("enabled")).thenReturn(false);

        PluginLogger logger = PowerMockito.mock(PluginLogger.class);

        PluginCommand command = PowerMockito.mock(PluginCommand.class);

        App app = PowerMockito.mock(App.class);
        app.config = config;
        PowerMockito.when(app.getLogger()).thenReturn(logger);
        PowerMockito.when(app.getCommand(anyString())).thenReturn(command);
        
        PowerMockito.doCallRealMethod().when(app).onEnable();
        app.onEnable();

        Mockito.verify(logger, times(1)).info("[VL WhiteList] Started but not enabled");
    }

    @Test
    public void onEnabled() throws Exception {
        FileConfiguration config = PowerMockito.mock(FileConfiguration.class);
        PowerMockito.when(config.getBoolean("enabled")).thenReturn(true);

        PluginLogger logger = PowerMockito.mock(PluginLogger.class);

        PluginCommand command = PowerMockito.mock(PluginCommand.class);

        PluginManager manager = PowerMockito.mock(PluginManager.class);
        PowerMockito.doNothing().when(manager).registerEvents(any(Listener.class), any(Plugin.class));

        Server server = PowerMockito.mock(Server.class);
        PowerMockito.when(server.getPluginManager()).thenReturn(manager);

        App app = PowerMockito.mock(App.class);
        app.config = config;
        PowerMockito.when(app.getLogger()).thenReturn(logger);
        PowerMockito.when(app.getCommand(anyString())).thenReturn(command);
        PowerMockito.when(app.getServer()).thenReturn(server);
        
        PowerMockito.doCallRealMethod().when(app).onEnable();
        app.onEnable();

        Mockito.verify(logger, times(1)).info("[VL WhiteList] Enabled");
    }

    @Test
    public void loadWhiteList() throws Exception {
        List<String> whitelistOrig = new ArrayList<String>(), whitelist = new ArrayList<String>();
        whitelistOrig.add("aBcD123");
        whitelist.add("ABCD123");

        App app = PowerMockito.mock(App.class);
        app.whitelistOrig = whitelistOrig;
        app.whitelist = new ArrayList<String>();

        PowerMockito.doCallRealMethod().when(app).loadWhiteList();
        app.loadWhiteList();

        assertEquals(app.whitelist, whitelist);
    }
}
