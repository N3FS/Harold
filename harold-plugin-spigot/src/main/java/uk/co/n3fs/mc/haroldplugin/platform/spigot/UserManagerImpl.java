package uk.co.n3fs.mc.haroldplugin.platform.spigot;

import org.bukkit.entity.Player;
import uk.co.n3fs.mc.haroldplugin.platform.User;

import java.util.UUID;

public class UserManagerImpl implements uk.co.n3fs.mc.haroldplugin.platform.UserManager<Player> {

    private final Bootstrap plugin;

    UserManagerImpl(Bootstrap plugin) {
        this.plugin = plugin;
    }

    @Override
    public UserImpl getUser(Player base) {
        return new UserImpl(base);
    }

    @Override
    public UserImpl getUser(UUID uniqueId) {
        return getUser(plugin.getServer().getPlayer(uniqueId));
    }

    @Override
    public UserImpl getOnlineUser(String username) {
        return getUser(plugin.getServer().getPlayerExact(username));
    }
}
