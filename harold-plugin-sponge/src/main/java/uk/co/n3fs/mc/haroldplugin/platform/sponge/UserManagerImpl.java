package uk.co.n3fs.mc.haroldplugin.platform.sponge;

import org.spongepowered.api.Server;
import org.spongepowered.api.entity.living.player.Player;

import java.util.Optional;
import java.util.UUID;

public class UserManagerImpl implements uk.co.n3fs.mc.haroldplugin.platform.UserManager<org.spongepowered.api.entity.living.player.User> {

    private final Bootstrap plugin;

    UserManagerImpl(Bootstrap plugin) {
        this.plugin = plugin;
    }

    @Override
    public UserImpl getUser(org.spongepowered.api.entity.living.player.User base) {
        return new UserImpl(base);
    }

    @Override
    public UserImpl getUser(UUID uniqueId) {
        Optional<Server> serverOpt = plugin.getServer();
        if (!serverOpt.isPresent()) {
            throw new RuntimeException("Server not yet started; player lookups are not currently available");
        }

        Optional<Player> player = serverOpt.get().getPlayer(uniqueId);
        return player.map(this::getUser).orElse(null);
    }

    @Override
    public UserImpl getOnlineUser(String username) {
        Optional<Server> serverOpt = plugin.getServer();
        if (!serverOpt.isPresent()) {
            throw new RuntimeException("Server not yet started; player lookups are not currently available");
        }

        Optional<Player> player = serverOpt.get().getPlayer(username);
        return player.map(this::getUser).orElse(null);
    }
}
