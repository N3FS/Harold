package uk.co.n3fs.mc.haroldplugin.platform.spigot;

import org.bukkit.entity.Player;

import java.util.UUID;

public class User implements uk.co.n3fs.mc.haroldplugin.platform.User<Player> {

    private final Player base;

    public User(Player base) {
        this.base = base;
    }

    @Override
    public String getName() {
        return base.getName();
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
