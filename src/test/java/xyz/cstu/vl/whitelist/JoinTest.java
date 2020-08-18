package xyz.cstu.vl.whitelist;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.PluginLogger;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ PluginLogger.class, App.class, Player.class, PlayerJoinEvent.class, Join.class })

public class JoinTest {
    public static List<String> whitelist, whitelistOrig;

    @BeforeClass
    public static void prepare() {
        whitelist = new ArrayList<String>();
        whitelistOrig = new ArrayList<String>();
        whitelistOrig.add("aBcD123");
    }

    @Test
    public void validPlayerJoin() throws Exception {
        PluginLogger logger = PowerMockito.mock(PluginLogger.class);

        App app = PowerMockito.mock(App.class);
        app.enabled = true;
        app.whitelistOrig = whitelistOrig;
        app.whitelist = whitelist;
        PowerMockito.doCallRealMethod().when(app).loadWhiteList();
        app.loadWhiteList();
        PowerMockito.when(app.getLogger()).thenReturn(logger);

        Player player = PowerMockito.mock(Player.class);
        PowerMockito.when(player.getName()).thenReturn("abcd123");

        PlayerJoinEvent playerJoinEvent = PowerMockito.mock(PlayerJoinEvent.class);
        PowerMockito.when(playerJoinEvent.getPlayer()).thenReturn(player);

        Join join = new Join(app);
        join.onPlayerJoin(playerJoinEvent);

        Mockito.verify(player, times(0)).kickPlayer("You are not on the whitelist.\n您不在白名单上。\nお名前はホワイトリストにありません。");
        Mockito.verify(logger, times(1)).info("[VL WhiteList] abcd123 is existed on whitelist");
    }

    @Test
    public void invalidPlayerJoin() throws Exception {
        List<String> whitelist = new ArrayList<String>(), whitelistOrig = new ArrayList<String>();

        PluginLogger logger = PowerMockito.mock(PluginLogger.class);

        App app = PowerMockito.mock(App.class);
        app.enabled = true;
        app.whitelistOrig = whitelistOrig;
        app.whitelist = whitelist;
        PowerMockito.doCallRealMethod().when(app).loadWhiteList();
        app.loadWhiteList();
        PowerMockito.when(app.getLogger()).thenReturn(logger);

        Player player = PowerMockito.mock(Player.class);
        PowerMockito.when(player.getName()).thenReturn("bcd123");

        PlayerJoinEvent playerJoinEvent = PowerMockito.mock(PlayerJoinEvent.class);
        PowerMockito.when(playerJoinEvent.getPlayer()).thenReturn(player);

        Join join = new Join(app);
        join.onPlayerJoin(playerJoinEvent);

        Mockito.verify(player, times(1)).kickPlayer("You are not on the whitelist.\n您不在白名单上。\nお名前はホワイトリストにありません。");
        Mockito.verify(logger, times(1)).info("[VL WhiteList] bcd123 is not existed on whitelist");
    }

    @Test
    public void notEnabledPlayerJoin() throws Exception {
        PluginLogger logger = PowerMockito.mock(PluginLogger.class);

        App app = PowerMockito.mock(App.class);
        app.enabled = false;
        PowerMockito.when(app.getLogger()).thenReturn(logger);

        Player player = PowerMockito.mock(Player.class);
        PowerMockito.when(player.getName()).thenReturn("bcd123");

        PlayerJoinEvent playerJoinEvent = PowerMockito.mock(PlayerJoinEvent.class);
        PowerMockito.when(playerJoinEvent.getPlayer()).thenReturn(player);

        Join join = new Join(app);
        join.onPlayerJoin(playerJoinEvent);

        Mockito.verify(logger, times(0)).info(anyString());
        Mockito.verify(player, times(0)).kickPlayer(anyString());
    }
}
