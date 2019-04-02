package uk.co.n3fs.mc.haroldplugin.platform.sponge;

import java.util.Objects;
import java.util.UUID;

public class User implements uk.co.n3fs.mc.haroldplugin.platform.User<org.spongepowered.api.entity.living.player.User> {

    private final org.spongepowered.api.entity.living.player.User base;

    public User(org.spongepowered.api.entity.living.player.User base) {
        Objects.requireNonNull(base);
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
    public org.spongepowered.api.entity.living.player.User getBase() {
        return base;
    }
}
