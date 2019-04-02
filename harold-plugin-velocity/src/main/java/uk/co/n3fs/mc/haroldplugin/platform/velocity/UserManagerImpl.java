package uk.co.n3fs.mc.haroldplugin.platform.velocity;

import com.velocitypowered.api.proxy.Player;
import uk.co.n3fs.mc.haroldplugin.platform.UserManager;

import java.util.UUID;

public class UserManagerImpl implements UserManager<Player> {

    private final Bootstrap plugin;

    public UserManagerImpl(Bootstrap plugin) {
        this.plugin = plugin;
    }

    @Override
    public UserImpl getUser(Player base) {
        return null;
    }

    @Override
    public UserImpl getUser(UUID uniqueId) {
        return plugin.getProxy().getPlayer(uniqueId).map(this::getUser).orElse(null);
    }

    @Override
    public UserImpl getOnlineUser(String username) {
        return plugin.getProxy().getPlayer(username).map(this::getUser).orElse(null);
    }
}
