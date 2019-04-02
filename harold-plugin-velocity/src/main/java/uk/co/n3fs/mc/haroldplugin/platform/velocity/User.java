package uk.co.n3fs.mc.haroldplugin.platform.velocity;

import com.velocitypowered.api.proxy.Player;

import java.util.Objects;
import java.util.UUID;

public class User implements uk.co.n3fs.mc.haroldplugin.platform.User<Player> {

    private final Player base;

    public User(Player base) {
        Objects.requireNonNull(base);
        this.base = base;
    }

    @Override
    public String getName() {
        return base.getUsername();
    }

    @Override
    public UUID getUniqueId() {
        return base.getUniqueId();
    }

    @Override
    public boolean isAuthorized(String permission) {
        return base.hasPermission(permission);
    }

    @Override
    public Player getBase() {
        return base;
    }
}
